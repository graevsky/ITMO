from isa import Opcode
class InstructionDecoder:
    def __init__(self, control_unit):
        self.control_unit = control_unit

    def decode(self, opcode,arg):
        #print(instruction)
        #opcode = opcode
        method_name = f"execute_{opcode.lower()}"
        method = getattr(self, method_name, self.unknown_instruction)
        method(opcode,arg)

    def execute_add(self, opcode, arg):
        self.control_unit.data_path.perform_operation(opcode)

    def execute_pstr(self, opcode, arg):
        self.control_unit.data_path.print_pstr(arg)

    def execute_less_than(self, opcode, arg):
        self.control_unit.data_path.comp_latch.set_data(arg)
        self.control_unit.data_path.perform_operation(opcode)

    def execute_greater_than(self, opcode, arg):
        self.control_unit.data_path.comp_latch.set_data(arg)
        self.control_unit.data_path.perform_operation(opcode)

    def execute_equals(self, opcode, arg):
        self.control_unit.data_path.comp_latch.set_data(arg)
        self.control_unit.data_path.perform_operation(opcode)

    def execute_if(self, opcode, arg):
        if not self.control_unit.data_path.pop_from_stack():
            self.control_unit.pc += 1
            #print(self.control_unit.memory[self.control_unit.pc].get("opcode"))
            while self.control_unit.memory[self.control_unit.pc].get("opcode") != Opcode.THEN.value:
                self.control_unit.pc += 1
            return

    def execute_then(self, opcode, arg):
        pass

    def execute_loop_start(self, opcode, arg):
        initial, max_value, step = arg
        self.control_unit.data_path.start_loop(initial, max_value, step)

    def execute_loop_end(self, opcode, arg):
        new_index = self.control_unit.data_path.end_loop(arg)
        if new_index is not None:
            self.control_unit.pc = new_index
        else:
            self.control_unit.pc += 1

    def execute_push(self, opcode, arg):
        if type(arg) == list:
            self.control_unit.data_path.store_string_in_memory(arg[0], arg[1], arg[2])
        elif arg == "i":
            self.control_unit.data_path.push_i()
        else:
            self.control_unit.data_path.push_to_stack(arg)

    def execute_print_top(self, opcode, arg):
        self.control_unit.data_path.print_top()

    def execute_cr(self, opcode,arg):
        print()

    def execute_load_addr(self, opcode, arg):
        address = int(arg, 16) if isinstance(arg, str) else int(arg)
        self.control_unit.data_path.read_io(address)

    def execute_accept(self, opcode, arg):
        self.control_unit.data_path.accept_input(arg)

    def execute_type(self, opcode, arg):
        self.control_unit.data_path.signal_output()

    def execute_print_string(self, opcode, arg):
        print(arg, end="")

    def execute_dup(self, opcode, arg):
        if self.control_unit.data_path.sp > 0:
            value = self.control_unit.data_path.stack[-1]
            self.control_unit.data_path.push_to_stack(value)

    def execute_halt(self, opcode, arg):
        print("aba")
        self.control_unit.halted = True


    def unknown_instruction(self, opcode, arg):
        raise ValueError(f"Unknown opcode")
