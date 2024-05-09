import json
from collections import namedtuple
from enum import Enum


class Opcode(str, Enum):
    """Операции"""

    CR = "CR"  # Перевод строки
    ACCEPT = "ACCEPT"  # Принять элементы из пользовательского ввода
    SWAP = "SWAP"  # Заменить верхний и предыдущий элементы в стеке
    TYPE = "TYPE"  # Вывести пользовательский ввод
    DUP = "DUP"  # Продублировать верхний элемент стека
    SAVE_STRING = "SAVE_STRING"
    LOOP_START = "LOOP_START"  # Начало цикла и его параметры
    LOOP_END = "LOOP_END"  # Окончание цикла
    PUSH = "PUSH"  # Добавить значение в стек
    PRINT_TOP = "PRINT_TOP"  # Вывод верхнего элемент
    LESS_THAN = "LESS_THAN"  # Меньше
    GREATER_THAN = "GREATER_THAN"  # Больше
    EQUALS = "EQUALS"  # Равно
    IF = "IF"  # Условие
    THEN = "THEN"  # Окончание условия
    MOD = "MOD"  # Деление по модулю
    AND = "AND"  # И
    OR = "OR"  # Или
    ADD = "ADD"
    PSTR = "PSTR"  # Вывести длину-префиксную строку
    HALT = "HALT"  # Остановка

    def __str__(self):
        return str(self.value)


"""Связь с исходным кодом"""


class Term(namedtuple("Term", "line pos symbol")):
    pass


class IOAddresses:
    INPUT_BUFFER = 0x0100
    INPUT_BUFFER_SIZE = 256  # размер буфера ввода
    OUTPUT_ADDRESS = 0x0200  # начало области для вывода данных
    STRING_STORAGE = 0x0300  # Новый адрес для хранения строк


"""Машкод в файл"""


def write_code(filename, code):
    with open(filename, "w", encoding="utf-8") as file:
        json.dump(code, file, indent=4)


"""Чтение машкода из файла"""


def read_code(filename):
    with open(filename, "r", encoding="utf-8") as file:
        code = json.load(file)
    for instruction in code:
        instruction["opcode"] = Opcode(instruction["opcode"])
    return code
