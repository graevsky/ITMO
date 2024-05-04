from isa import Opcode


class ALU:
    def __init__(self):
        self.flags = {
            'Z': 0,  # Zero flag
            'C': 0,  # Carry flag
            'V': 0,  # Overflow flag
            'N': 0  # Negative flag
        }

    def execute(self, opcode, a, b=None):
        result = 0
        if opcode == Opcode.ADD:
            result, self.flags['C'], self.flags['V'] = self.add(a, b)
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

        self.update_flags(result)
        return result

    def add(self, a, b):
        r = a + b
        carry = 1 if r > 0xFFFFFFFF else 0
        overflow = 1 if (a > 0 and b > 0 and r < 0) or (a < 0 and b < 0 and r > 0) else 0
        return r & 0xFFFFFFFF, carry, overflow

    def sub(self, a, b):
        r = a - b
        carry = 1 if r < 0 else 0
        overflow = 1 if (a > 0 and b < 0 and r < 0) or (a < 0 and b > 0 and r > 0) else 0
        return r & 0xFFFFFFFF, carry, overflow

    def and_op(self, a, b):
        return a & b

    def or_op(self, a, b):
        return a | b

    def mod(self, a, b):
        if b == 0:
            raise Exception("Division by zero")
        return a % b

    def less_than(self, a, b):
        return 1 if a < b else 0

    def greater_than(self, a, b):
        return 1 if a > b else 0

    def equals(self, a, b):
        return 1 if a == b else 0

    def update_flags(self, result):
        self.flags['Z'] = 1 if result == 0 else 0
        self.flags['N'] = 1 if result < 0 else 0
