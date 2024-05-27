import argparse
import contextlib
import io
import logging
import os
import tempfile
import sys
import pytest

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

import source.machine as machine
import source.translator as translator



def parse_translator_args(args):
    parser = argparse.ArgumentParser(description="Translate FORTH code to machine code.")
    parser.add_argument('source_file', help="The FORTH source file to translate.")
    parser.add_argument('-a', '--all', action='store_true', help="Process all FORTH files in the specified directory.")
    parser.add_argument('-i', '--input_folder', default='./progs', help="Directory containing FORTH files.")
    parser.add_argument('-o', '--output_folder', default='./source/machine_code',
                        help="Directory to store the output JSON files.")
    return parser.parse_args(args)


def parse_machine_args(args):
    parser = argparse.ArgumentParser(description="Run FORTH machine code simulations.")
    parser.add_argument("-a", "--all", action="store_true",
                        help="Process all JSON files in the machine code directory.")
    parser.add_argument("input_file", type=str, nargs='?', default=None, help="Path to the input file for the machine.")
    parser.add_argument("machine_code_file", type=str, nargs='?', help="Path to a specific machine code file to run.")
    return parser.parse_args(args)


@pytest.mark.golden_test("golden/*.yml")
def test_translator_and_machine(golden, caplog):
    caplog.set_level(logging.DEBUG)
    with tempfile.TemporaryDirectory() as tmpdirname:
        source = os.path.join(tmpdirname, "source.forth")
        input_stream = os.path.join(tmpdirname, "input.txt")
        target = os.path.join(tmpdirname, "target.json")
        with open(source, "w", encoding="utf-8") as file:
            file.write(golden["in_source"])
        with open(input_stream, "w", encoding="utf-8") as file:
            file.write(golden["in_stdin"])
        with contextlib.redirect_stdout(io.StringIO()) as stdout:
            try:
                translator_args = parse_translator_args([source, "-o", target])
                translator.main(translator_args)
                print("============================================================")
                machine_args = parse_machine_args(["-a", input_stream, target])
                machine.main(machine_args)
            except Exception as e:
                print(f"Error during test execution: {e}")
        try:
            with open(target, encoding="utf-8") as file:
                code = file.read()
            assert code == golden.out["out_code"]
            assert stdout.getvalue() == golden.out["out_stdout"]
            if "out_log" in golden:
                assert caplog.text == golden.out["out_log"]
        except Exception as e:
            print(f"Error during verification: {e}")
