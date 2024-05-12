from isa import Opcode


class Multiplexer:
    def __init__(self, data_path):
        self.data_path = data_path

    def select_sources(self, selector, *args):
        if selector == "ALU":
            return self.select_for_alu(*args)
        elif selector == "STACK":
            return self.select_for_stack(*args)
        else:
            raise ValueError("Unknown selector")

    def select_for_stack(self, source_type):
        if source_type == "alu_result":
            return self.data_path.alu_latch.get_data()
        elif source_type == "loop_counter":
            return self.data_path.loop_counter.get_data()
        elif source_type == "duplicate_top":
            if self.data_path.sp.get_data() > 0:
                return self.data_path.stack[-1]
            else:
                raise IndexError("Stack underflow, cannot duplicate top")
        elif source_type == "direct_value":
            return source_type
        else:
            raise ValueError("Invalid source type for stack operation")

    def select_for_alu(self, opcode):
        if opcode in {Opcode.ADD, Opcode.AND, Opcode.OR}:
            b = self.data_path.pop_from_stack()
            a = self.data_path.pop_from_stack()
            return a, b
        else:
            a = self.data_path.pop_from_stack()
            b = self.data_path.comp_latch.get_data()
            return a, b
