from isa import Opcode, write_code, IOAddresses
import sys

PAD_ADDRESS = "0x0100"  # адрес для буфера IO


def parse_line(line):
    parts = line.strip().split()
    command = parts[0]
    args = parts[1:] if len(parts) > 1 else []
    return command, args


def translate(text):
    code = []
    lines = text.strip().split("\n")
    index = 0
    loop_stack = []
    string_storage_address = IOAddresses.STRING_STORAGE

    for line_number, line in enumerate(lines):
        line = line.split("\\")[0].strip()
        if (
            not line or line.startswith(":") or line.startswith(";")
        ):  # убрать в будущем, чтобы поддерживались переменные-функции
            continue

        commands = line.split()
        i = 0
        while i < len(commands):
            command = commands[i]
            opcode = None
            args = []
            if command.isdigit():
                if len(commands) == 1:  # Проверка, что в строке только это число
                    args.append(int(command))
                    opcode = Opcode.PUSH
                    i += 1
                else:
                    i += 1
                    continue
            elif command == "do":
                initial_value = int(commands[1])  # Начальное значение
                max_value = int(commands[0]) - 1  # Максимальное значение
                step = 1  # Шаг цикла всегда равен 1

                start_index = len(code)
                loop_stack.append(start_index)
                code.append(
                    {
                        "index": index,
                        "opcode": Opcode.LOOP_START.value,
                        "arg": [initial_value, max_value, step],
                    }
                )  # убрать
                index += 1
                i += 2
            elif command == "loop":
                if not loop_stack:
                    raise ValueError("Mismatched 'loop' without 'do'")

                start_index = loop_stack.pop()
                args.append(start_index)
                opcode = Opcode.LOOP_END
                index += 1
            elif command == "i":
                args.append("i")
                opcode = Opcode.PUSH
                index += 1
            elif command == ".":
                code.append(
                    {"index": index, "opcode": Opcode.PRINT_TOP.value, "arg": None}
                )
                index += 1
            elif command == "mod":
                args.append(int(commands[0]))
                opcode = Opcode.MOD
                i += 2
            elif command == "and":
                opcode = Opcode.AND
                i += 1
            elif command == "or":
                opcode = Opcode.OR
                i += 1
            elif command == "<":
                args.append(int(commands[0]))
                opcode = Opcode.LESS_THAN
                i += 2
            elif command == ">":
                args.append(int(commands[0]))
                opcode = Opcode.GREATER_THAN
                i += 2
            elif command == "==":
                args.append(int(commands[0]))
                opcode = Opcode.EQUALS
                i += 2
            elif command == "if":
                opcode = Opcode.IF
                i += 1
            elif command == "then":
                opcode = Opcode.THEN
                i += 1
            elif command.startswith('."'):  # Обработка строк с префиксом длины
                string = command[2:] + " ".join(commands[i + 1:])
                length = len(string)
                # Записываем длину и строку в память
                code.append(
                    {
                        "index": index,
                        "opcode": Opcode.PUSH.value,
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
                index += 1
                # Обновляем адрес хранения следующей строки
                string_storage_address += length + 1
                i += len(commands) - i
            elif command == "cr":
                opcode = Opcode.CR
            elif command == "+":
                opcode = Opcode.ADD
                i += 1
            elif command == "pad":
                if (
                    i + 2 < len(commands)
                    and commands[i + 1].isdigit()
                    and commands[i + 2] == "accept"
                ):
                    args = [int(commands[i + 1])]
                    opcode = Opcode.ACCEPT
                    code.append(
                        {
                            "index": index,
                            "opcode": Opcode.LOAD_ADDR.value,
                            "arg": IOAddresses.INPUT_BUFFER,
                        }
                    )
                    index += 1
                    i += 3
            elif command == "type":
                opcode = Opcode.TYPE
                i += 1
            elif command == "dup":
                opcode = Opcode.DUP
            elif command == "LOAD_ADDR":
                if i + 1 < len(commands):
                    args = [
                        (
                            int(commands[i + 1], 16)
                            if "x" in commands[i + 1]
                            else int(commands[i + 1])
                        )
                    ]
                    opcode = Opcode.LOAD_ADDR
                    i += 2
            """
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
            """
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


def main(source_file):
    with open(source_file, "r", encoding="utf-8") as file:
        source_text = file.read()
    machine_code = translate(source_text)
    output_file = f"{source_file.split('/')[-1].replace('.forth', '.json')}"
    write_code(output_file, machine_code)
    print(f"Machine code has been written to {output_file}")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python translator.py <source_file>")
    else:
        _, source_file = sys.argv
        main(source_file)
