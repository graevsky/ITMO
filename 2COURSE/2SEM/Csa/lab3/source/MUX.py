
class Multiplexer:
    def __init__(self, data_path):
        self.data_path = data_path

    def select_sources(self, selector, *args):
        if selector == "ALU":
            return self.select_for_alu(*args)
        elif selector == "PUSH_COUNTER":
            return self.select_for_io(*args)
        else:
            raise ValueError("Unknown selector")

    def select_for_alu(self, opcode):
        if opcode in {Opcode.ADD, Opcode.AND, Opcode.OR}:
            b = self.data_path.pop_from_stack()
            a = self.data_path.pop_from_stack()
            return a, b
        else:
            a = self.data_path.pop_from_stack()
            b = self.data_path.comp_latch.get_data()
            return a, b
