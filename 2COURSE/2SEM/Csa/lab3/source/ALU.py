# ALU.py
from isa import Opcode
from Latch import Latch


class ALU:
    def __init__(self, data_path):
        self.data_path = data_path

    def execute(self, opcode, a, b=None):
        result = 0
        if opcode == Opcode.ADD:
            result = self.add(a, b)
        elif opcode == Opcode.AND:
            result = self.and_op(a, b)
        elif opcode == Opcode.OR:
            result = self.or_op(a, b)
        elif opcode == Opcode.MOD:
            result = self.mod(a, b)
        elif opcode == Opcode.LESS_THAN:
            result = self.less_than(a, b)
        elif opcode == Opcode.GREATER_THAN:
            result = self.greater_than(a, b)
        elif opcode == Opcode.EQUALS:
            result = self.equals(a, b)

        self.data_path.alu_latch.set_data(result)

    def add(self, a, b):
        result = a + b
        result = self.sign_extend(result)
        return result

    @staticmethod
    def sign_extend(value):
        value = value & 0xFFFFFFFF
        if value & 0x80000000:
            value -= 0x100000000
        return value

    def and_op(self, a, b):
        result = a & b
        return result

    def or_op(self, a, b):
        result = a | b
        return result

    def mod(self, a, b):
        if b == 0:
            raise Exception("Division by zero")
        result = a % b
        return result

    def less_than(self, a, b):
        result = 1 if a < b else 0
        return result

    def greater_than(self, a, b):
        result = 1 if a > b else 0
        return result

    def equals(self, a, b):
        result = 1 if a == b else 0
        return result
