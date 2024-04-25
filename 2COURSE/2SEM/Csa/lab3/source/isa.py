import json
from collections import namedtuple
from enum import Enum


class Opcode(str, Enum):
    """Операции"""

    CR = "CR"  # Перевод строки
    LOAD_ADDR = "LOAD_ADDR"  # Загрузка адреса
    ACCEPT = "ACCEPT"  # Принять элементы из пользовательского ввода
    SWAP = "SWAP"  # Заменить верхний и предыдущий элементы в стеке
    TYPE = "TYPE"  # Вывести текст
    DUP = "DUP"  # Продублировать верхний элемент стека
    PRINT_STRING = "PRINT_STRING"  # Вывести строку из кода
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
    HALT = "HALT"  # Остановка

    def __str__(self):
        return str(self.value)


"""Связь с исходным кодом"""


class Term(namedtuple("Term", "line pos symbol")):
    pass


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
