# ALU.py
from isa import Opcode
from Latch import Latch

class ALU:
    def __init__(self, data_path):
        self.data_path = data_path
        self.flags = {
            'Z': Latch(),  # Zero flag
            'C': Latch(),  # Carry flag
            'V': Latch(),  # Overflow flag
            'N': Latch()   # Negative flag
        }

    def execute(self, opcode, a, b=None):
        result = 0
        carry = 0
        overflow = 0
        if opcode == Opcode.ADD:
            result, carry, overflow = self.add(a, b)
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

        self.update_flags(result, carry, overflow)
        self.data_path.alu_latch.set_data(result)

    def add(self, a, b):
        result = a + b
        carry = 1 if result > 0xFFFFFFFF or result < -0x80000000 else 0
        overflow = 1 if (a > 0 > result and b > 0) or (a < 0 < result and b < 0) else 0
        result = self.sign_extend(result)
        return result, carry, overflow

    @staticmethod
    def sign_extend(value):
        value = value & 0xFFFFFFFF
        if value & 0x80000000:
            value -= 0x100000000
        return value

    def and_op(self, a, b):
        result = a & b
        self.update_flags(result)
        return result

    def or_op(self, a, b):
        result = a | b
        self.update_flags(result)
        return result

    def mod(self, a, b):
        if b == 0:
            raise Exception("Division by zero")
        result = a % b
        self.update_flags(result)
        return result

    def less_than(self, a, b):
        result = 1 if a < b else 0
        self.update_flags(result)
        return result

    def greater_than(self, a, b):
        result = 1 if a > b else 0
        self.update_flags(result)
        return result

    def equals(self, a, b):
        result = 1 if a == b else 0
        self.update_flags(result)
        return result

    def update_flags(self, result, carry=0, overflow=0):
        self.flags['Z'].set_data(1 if result == 0 else 0)
        self.flags['N'].set_data(1 if result < 0 else 0)
        self.flags['C'].set_data(carry)
        self.flags['V'].set_data(overflow)
        print(f"Flags - Z: {self.flags['Z'].get_data()} C: {self.flags['C'].get_data()} V: {self.flags['V'].get_data()} N: {self.flags['N'].get_data()}")
