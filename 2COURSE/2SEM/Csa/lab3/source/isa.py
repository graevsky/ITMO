import json
from collections import namedtuple
from enum import Enum


class Opcode(str, Enum):
    """Операции"""
    CR = "CR"  # Перевод строки
    SWAP = "SWAP"  # Заменить верхний и предыдущий элементы в стеке
    TYPE = "TYPE"  # Вывести пользовательский ввод
    DUP = "DUP"  # Продублировать верхний элемент стека
    JZ = "JZ"  # Условный переход, если на вершине стека ноль (jump if zero)
    LOOP_START = "LOOP_START"  # Начало цикла и его параметры
    LOOP_END = "LOOP_END"  # Окончание цикла
    PUSH = "PUSH"  # Добавить значение в стек
    PRINT_TOP = "PRINT_TOP"  # Вывод верхнего элемент
    LESS_THAN = "LESS_THAN"  # Меньше
    GREATER_THAN = "GREATER_THAN"  # Больше
    EQUALS = "EQUALS"  # Равно
    MOD = "MOD"  # Деление по модулю
    AND = "AND"  # И
    OR = "OR"  # Или
    ADD = "ADD"
    PSTR = "PSTR"  # Вывести длину-префиксную строку
    INPUT = "INPUT"  # Чтение ввода
    LOAD = "LOAD"
    OUT = "OUT"
    DEC_I = "DEC_I"
    SAVE = "SAVE"
    INP = "INP"
    POP = "POP"
    HALT = "HALT"  # Остановка

    def __str__(self):
        return str(self.value)


"""Связь с исходным кодом"""


class Term(namedtuple("Term", "line pos symbol")):
    pass


class IOAddresses:
    USER_INPUT = 0x0100  # Начало области буфера ввода
    INP_ADDR = 0x0101  # Адрес ввода
    OUT_ADDR = 0x0102  # Адрес вывода
    OUTPUT_ADDRESS = 0x0200  # Начало области для вывода данных
    STRING_STORAGE = 0x0300  # Начало области хранения строк


"""Машкод в файл"""


def write_code(filename, code):
    with open(filename, "w", encoding="utf-8") as file:
        json.dump(code, file, indent=4)


"""Чтение машкода из файла"""


def read_code(filename):
    with open(filename, "r", encoding="utf-8") as file:
        code = json.load(file)
    for instruction in code["program"]:
        instruction["opcode"] = Opcode(instruction["opcode"])
    return code
