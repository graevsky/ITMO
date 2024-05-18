import argparse
import os

from isa import Opcode, write_code, IOAddresses

PAD_ADDRESS = "0x0100"  # адрес для буфера IO


def parse_line(line):
    parts = line.strip().split()
    command = parts[0]
    arguments = parts[1:] if len(parts) > 1 else []
    return command, arguments


def first_pass(lines):
    procedures = {}
    main_program = []
    current_proc = None

    for line in lines:
        line = line.split("\\")[0].strip()  # Убираем комментарии и пробелы
        if not line:
            continue
        commands = line.split()
        i = 0
        while i < len(commands):
            command = commands[i]
            if command == ':':
                current_proc = commands[i + 1]
                procedures[current_proc] = []
                i += 2
            elif command == ';':
                current_proc = None
                i += 1
            elif current_proc:
                procedures[current_proc].append(commands[i])
                i += 1
            else:
                main_program.append(commands[i])
                i += 1

    return procedures, main_program


def expand_procedures(commands, procedures):
    expanded_program = []
    for command in commands:
        if command in procedures:
            expanded_program.extend(expand_procedures(procedures[command], procedures))
        else:
            expanded_program.append(command)
    return expanded_program


def second_pass(commands):
    code = []
    index = 0
    loop_stack = []
    if_stack = []
    string_storage_address = IOAddresses.STRING_STORAGE

    i = 0
    while i < len(commands):
        command = commands[i]
        opcode = None
        arguments = []
        if command.isdigit():
            arguments.append(int(command))
            opcode = Opcode.PUSH
        elif command == "do":
            print(commands)
            print(commands[i+1])
            initial_value = int(commands[i + 1])
            max_value = int(commands[i + 2]) - 1
            step = 1  # Шаг цикла всегда равен 1

            start_index = len(code)
            loop_stack.append(start_index)
            code.append(
                {
                    "index": index,
                    "opcode": Opcode.LOOP_START.value,
                    "arg": [initial_value, max_value, step],
                }
            )
            index += 1
            i += 3
            continue
        elif command == "loop":
            if not loop_stack:
                raise ValueError("Mismatched 'loop' without 'do'")

            start_index = loop_stack.pop()
            code.append(
                {
                    "index": index,
                    "opcode": Opcode.LOOP_END.value,
                    "arg": start_index
                }
            )
            index += 1
            i += 1
            continue
        elif command == "i":
            arguments.append("i")
            opcode = Opcode.PUSH
        elif command == ".":
            code.append(
                {"index": index, "opcode": Opcode.PRINT_TOP.value, "arg": None}
            )
            index += 1
        elif command == "mod":
            arguments.append(int(commands[i + 1]))
            opcode = Opcode.MOD
            i += 2
        elif command == "and":
            opcode = Opcode.AND
        elif command == "or":
            opcode = Opcode.OR
        elif command == "<":
            arguments.append(int(commands[i + 1]))
            opcode = Opcode.LESS_THAN
            i += 2
        elif command == ">":
            arguments.append(int(commands[i + 1]))
            opcode = Opcode.GREATER_THAN
            i += 2
        elif command == "==":
            arguments.append(int(commands[i + 1]))
            opcode = Opcode.EQUALS
            i += 2
        elif command == "if":
            if_stack.append(index)
            code.append(
                {"index": index, "opcode": Opcode.JZ.value, "arg": None})
            index += 1
        elif command == "then":
            if_index = if_stack.pop()
            code[if_index]['arg'] = index
        elif command.startswith('."'):
            string = command[2:]
            length = len(string)
            code.append(
                {
                    "index": index,
                    "opcode": Opcode.SAVE_STRING.value,
                    "arg": [string_storage_address, length, string],
                }
            )
            index += 1
            code.append(
                {
                    "index": index,
                    "opcode": Opcode.PSTR.value,
                    "arg": string_storage_address,
                }
            )
            string_storage_address += length + 1
        elif command == "cr":
            opcode = Opcode.CR
        elif command == "+":
            opcode = Opcode.ADD
        elif command == "pad":
            if (
                    i + 2 < len(commands)
                    and commands[i + 1].isdigit()
                    and commands[i + 2] == "accept"
            ):
                arguments = [int(commands[i + 1])]
                opcode = Opcode.ACCEPT
                i += 3
        elif command == "type":
            opcode = Opcode.TYPE
        elif command == "dup":
            opcode = Opcode.DUP

        if opcode:
            code.append(
                {
                    "index": index,
                    "opcode": opcode.value,
                    "arg": arguments[0] if arguments else None,
                }
            )
            index += 1
        i += 1

    code.append({"index": index, "opcode": Opcode.HALT.value, "arg": None})
    for i, instr in enumerate(code):
        instr["index"] = i
    return code


def translate(text):
    lines = text.strip().split("\n")

    # Первый проход: находим процедуры и сохраняем их в словарь
    procedures, main_program = first_pass(lines)

    # Второй проход: разворачиваем процедуры и создаем окончательный список инструкций
    expanded_program = expand_procedures(main_program, procedures)
    code = second_pass(expanded_program)

    return code

def process_dir(directory, output_folder):
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.forth'):
                source_path = os.path.join(root, file)
                machine_code = translate(open(source_path, "r", encoding="utf-8").read())
                output_file = os.path.join(output_folder, file.replace('.forth', '.json'))
                write_code(output_file, machine_code)
                print(f"Machine code has been written to {output_file}")


def main(arguments):
    if arguments.all:
        process_dir(arguments.input_folder, arguments.output_folder)
    else:
        source_file = arguments.source_file
        with open(source_file, "r", encoding="utf-8") as file:
            source_text = file.read()
        machine_code = translate(source_text)
        output_file = f"{source_file.split('/')[-1].replace('.forth', '.json')}"
        write_code(output_file, machine_code)
        print(f"Machine code has been written to {output_file}")


def parse_args():
    parser = argparse.ArgumentParser(description="Translate FORTH code to machine code.")
    parser.add_argument('source_file', nargs='?', help="The FORTH source file to translate.")
    parser.add_argument('-a', '--all', action='store_true', help="Process all FORTH files in the specified directory.")
    parser.add_argument('-i', '--input_folder', action='store_true', default='./progs',
                        help="Directory containing FORTH files.")
    parser.add_argument('-o', '--output_folder', action='store_true', default='./source/machine_code',
                        help="Directory to store the output JSON files.")
    return parser.parse_args()


if __name__ == "__main__":
    args = parse_args()
    main(args)
