from isa import Opcode
from instruction_decoder import InstructionDecoder

# Создаем экземпляр декодера
decoder = InstructionDecoder(control_unit=None)

# Ваш машинный код
machine_code = [
    {"index": 0, "opcode": "LOOP_START", "arg": [1, 99, 1]},
    {"index": 2, "opcode": "PUSH", "arg": "i"},
    {"index": 3, "opcode": "LESS_THAN", "arg": 20},
    {"index": 4, "opcode": "IF", "arg": None},
    {"index": 5, "opcode": "PRINT_STRING", "arg": "less than 20"},
    {"index": 6, "opcode": "CR", "arg": None},
    {"index": 7, "opcode": "THEN", "arg": None},
    {"index": 9, "opcode": "LOOP_END", "arg": 0},
    {"index": 10, "opcode": "HALT", "arg": None},
]

# Проверяем каждую инструкцию
for instruction in machine_code:
    opcode = Opcode[instruction["opcode"]]
    method_name = f"execute_{opcode.name.lower()}"
    method = getattr(decoder, method_name, decoder.unknown_instruction)
    print(f"Index: {instruction['index']}, Opcode: {opcode.name}, Method: {method.__name__}")
