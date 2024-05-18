from isa import Opcode


class InstructionDecoder:
    def __init__(self, control_unit):
        self.control_unit = control_unit

    def decode(self, instruction):
        #print(instruction)
        opcode = instruction.get("opcode")
        method_name = f"execute_{opcode.lower()}"
        method = getattr(self, method_name, self.unknown_instruction)
        method(instruction)

    def execute_pstr(self, instruction):
        self.control_unit.data_path.print_pstr(instruction.get("arg"))

    def execute_add(self,instruction):
        self.control_unit.data_path.perform_operation(Opcode.ADD)

    def execute_less_than(self, instruction):
        self.control_unit.data_path.perform_operation(Opcode.LESS_THAN)

    def execute_greater_than(self, instruction):
        self.control_unit.data_path.perform_operation(Opcode.GREATER_THAN)

    def execute_equals(self, instruction):
        self.control_unit.data_path.perform_operation(Opcode.EQUALS)

    def execute_mod(self, instruction):
        self.control_unit.data_path.perform_operation(Opcode.MOD)

    def execute_and(self, instruction):
        self.control_unit.data_path.perform_operation(Opcode.AND)

    def execute_or(self, instruction):
        self.control_unit.data_path.perform_operation(Opcode.OR)

    def execute_loop_start(self, instruction):
        initial, max_value, step = instruction.get("arg")
        self.control_unit.data_path.start_loop(initial, max_value, step)

    def execute_loop_end(self, instruction):
        continue_loop = self.control_unit.data_path.end_loop()
        if continue_loop:
            self.control_unit.pc.set_data(instruction.get("arg"))

    def execute_save_string(self, instruction):
        arg = instruction.get("arg")
        self.control_unit.data_path.store_string_in_memory(arg[0], arg[1], arg[2])

    def execute_jump(self, instruction):
        target = instruction.get("arg")
        self.control_unit.pc.set_data(target)
        self.control_unit.data_path.jump_latch.set_data(1)

    def execute_jz(self, instruction):
        condition = self.control_unit.data_path.pop_from_stack()
        if condition == 0:
            target = instruction.get("arg")
            self.control_unit.pc.set_data(target)
            self.control_unit.data_path.jump_latch.set_data(1)

    def execute_jnz(self, instruction):
        condition = self.control_unit.data_path.pop_from_stack()
        if condition != 0:
            target = instruction.get("arg")
            self.control_unit.pc.set_data(target)
            self.control_unit.data_path.jump_latch.set_data(1)

    def execute_push(self, instruction):
        arg = instruction.get("arg")
        if isinstance(arg, int):
            self.control_unit.data_path.push_to_stack("direct_value", arg)
        elif arg == "i":
            self.control_unit.data_path.push_to_stack("loop_counter")
        else:
            raise ValueError("Unsupported argument for PUSH operation")

    def execute_print_top(self, instruction):
        self.control_unit.data_path.print_top()

    def execute_cr(self, instruction):
        print()

    def execute_accept(self, instruction):
        self.control_unit.data_path.accept_input()

    def execute_type(self, instruction):
        self.control_unit.data_path.write_output()

    def execute_dup(self, instruction):
        self.control_unit.data_path.push_to_stack("duplicate_top")

    def execute_call(self, instruction):
        target = instruction.get("arg")
        current_pc = self.control_unit.pc.get_data()
        new_pc = self.control_unit.data_path.call_procedure(current_pc, target)
        self.control_unit.pc.set_data(new_pc)

    def execute_return(self, instruction):
        return_pc = self.control_unit.data_path.return_from_procedure()
        self.control_unit.pc.set_data(return_pc)
        self.control_unit.data_path.jump_latch.set_data(1)

    def execute_halt(self, instruction):
        self.control_unit.halted = True

    def unknown_instruction(self, instruction):
        raise ValueError(f"Unknown opcode: {instruction.get('opcode')}")
