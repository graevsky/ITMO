#!/usr/bin/python3

import sys
from isa import Opcode, write_code

PAD_ADDRESS = "0x0100"  # Пример адреса для PAD


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
        line = line.split("\\")[0].strip()  # Отсекаем комментарии
        if (
                not line or line.startswith(":") or line.startswith(";")
        ):  # Пропуск пустых строк и начальной команды, а также конца программы
            continue

        commands = line.split()
        i = 0  # Индекс для перебора команд в строке
        while i < len(commands):
            command = commands[i]
            opcode = None
            args = []

            if command == "cr":
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
                    i += 2  # Пропускаем следующие два элемента (число и 'accept')
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
                    i += 1  # Пропускаем 'swap'
            elif command == "type":
                opcode = Opcode.TYPE
            elif command == '."':
                end_of_string = len(commands)
                for j in range(i + 1, len(commands)):
                    if commands[j] in ["cr", "pad", "type", "dup", "LOAD_ADDR", ";"]:
                        end_of_string = j
                        break
                args = [" ".join(commands[i + 1: end_of_string])]
                opcode = Opcode.PRINT_STRING
                i = end_of_string  # Перемещаем индекс за последний обработанный элемент
            elif command == "dup":
                opcode = Opcode.DUP
            elif command == "LOAD_ADDR":
                if i + 1 < len(commands):
                    opcode = Opcode.LOAD_ADDR
                    args = [commands[i + 1]]
                    i += 1  # Пропускаем следующий элемент

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
