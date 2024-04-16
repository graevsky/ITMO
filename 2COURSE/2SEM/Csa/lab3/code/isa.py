import json
from collections import namedtuple
from enum import Enum


class Opcode(str, Enum):
    """Операции"""
    CR = "CR"
    LOAD_ADDR = "LOAD_ADDR"
    ACCEPT = "ACCEPT"
    SWAP = "SWAP"
    TYPE = "TYPE"
    DUP = "DUP"
    PRINT_STRING = "PRINT_STRING"

    def __str__(self):
        return str(self.value)


"""Связб с исходным кодом"""


class Term(namedtuple("Term", "line pos symbol")):
    pass


# машкод в файл
def write_code(filename, code):
    with open(filename, "w", encoding="utf-8") as file:
        """Из примера
        buf = []
        for instr in code:
            buf.append(json.dumps(instr))
        file.write("[" + ",\n ".join(buf) + "]")
        """
        json.dump(code, file, indent=4)


# чтение машкода из файла
def read_code(filename):
    with open(filename, "r", encoding="utf-8") as file:
        code = json.load(file)

    # Нужно?
    for instruction in code:
        instruction["opcode"] = Opcode(instruction["opcode"])
        if "term" in instruction:
            instruction["term"] = Term(*instruction["term"])

    return code
