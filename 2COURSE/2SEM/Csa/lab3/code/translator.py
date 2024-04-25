#!/usr/bin/python3

import sys
from isa import Opcode, write_code

PAD_ADDRESS = "0x0100"  # адрес для буфера IO


def parse_line(line):
    parts = line.strip().split()
    command = parts[0]
    args = parts[1:] if len(parts) > 1 else []
    return command, args


def translate(text):
    """Трансляция текста программы в машинный код."""
    code = []
    lines = text.strip().split("\n")
    index = 0

    for line in lines:
        line = line.split("\\")[0].strip()  # Удаление комментов
        if (
            not line or line.startswith(":") or line.startswith(";")
        ):  # Пропуск спец. строк форта
            continue

        commands = line.split()
        i = 0
        while i < len(commands):
            command = commands[i]
            opcode = None
            args = []

            if command == '."':  # Начало строки вывода
                end_of_string = len(commands)
                for j in range(i + 1, len(commands)):
                    if commands[j] == '."':
                        end_of_string = j
                        break
                args = [" ".join(commands[i + 1 : end_of_string]).replace('"', "")]
                opcode = Opcode.PRINT_STRING
                i = end_of_string  # Индекс за последний элемент
            elif command == "cr":
                opcode = Opcode.CR
            elif command == "pad":
                if (
                    i + 2 < len(commands)
                    and commands[i + 1].isdigit()
                    and commands[i + 2] == "accept"
                ):
                    opcode = Opcode.ACCEPT
                    args = [int(commands[i + 1])]
                    code.append(
                        {
                            "index": index,
                            "opcode": Opcode.LOAD_ADDR.value,
                            "arg": PAD_ADDRESS,
                        }
                    )
                    index += 1
                    i += 2
                elif i + 1 < len(commands) and commands[i + 1] == "swap":
                    opcode = Opcode.SWAP
                    code.append(
                        {
                            "index": index,
                            "opcode": Opcode.LOAD_ADDR.value,
                            "arg": PAD_ADDRESS,
                        }
                    )
                    index += 1
                    i += 1
            elif command == "type":
                opcode = Opcode.TYPE
            elif command == "dup":
                opcode = Opcode.DUP
            elif command == "LOAD_ADDR":
                if i + 1 < len(commands):
                    opcode = Opcode.LOAD_ADDR
                    address = (
                        int(commands[i + 1], 16)
                        if "x" in commands[i + 1]
                        else int(commands[i + 1])
                    )  # Сложно как то?
                    args = [address]
                    i += 1

            if opcode:
                code.append(
                    {
                        "index": index,
                        "opcode": opcode.value,
                        "arg": args[0] if args else None,
                    }
                )
                index += 1
            i += 1

    code.append({"index": index, "opcode": Opcode.HALT.value, "arg": None})
    return code


def main(source, target):
    with open(source, "r", encoding="utf-8") as f:
        source_text = f.read()

    machine_code = translate(source_text)
    write_code(target, machine_code)
    print(
        "Source lines:",
        len(source_text.split("\n")),
        "Instructions:",
        len(machine_code),
    )


if __name__ == "__main__":
    assert len(sys.argv) == 3, "Usage: translator.py <source file> <target file>"
    _, source_file, target_file = sys.argv
    main(source_file, target_file)
