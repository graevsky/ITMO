from isa import Opcode, IOAddresses


class InstructionDecoder:
    def __init__(self, control_unit):
        self.control_unit = control_unit

    def decode(self, instruction):
        opcode = instruction.get("opcode")
        method_name = f"execute_{opcode.lower()}"
        method = getattr(self, method_name, self.unknown_instruction)
        method(instruction)

    def execute_save(self, instruction):
        self.control_unit.data_path.save()

    def execute_dec_i(self, instruction):
        current_value = self.control_unit.loop_counter
        self.control_unit.loop_counter = current_value - 1

    def execute_load(self, instruction):
        self.control_unit.data_path.load()

    def execute_add(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.ADD, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch)

    def execute_equals(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.EQUALS, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch)

    def execute_mod(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.MOD, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch)

    def execute_or(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.OR, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch)

    def execute_loop_start(self, instruction):
        initial, max_value, step = instruction.get("arg")
        self.control_unit.start_loop(initial, max_value, step)

    def execute_loop_end(self, instruction):
        continue_loop = self.control_unit.end_loop()
        if continue_loop:
            self.control_unit.pc = instruction.get("arg")

    def execute_jz(self, instruction):
        condition = self.control_unit.data_path.pop_from_stack()
        if condition == 0:
            target = instruction.get("arg")
            self.control_unit.pc = target
            self.control_unit.data_path.jump_latch = 1

    def execute_push(self, instruction):
        arg = instruction.get("arg")
        if isinstance(arg, int):
            self.control_unit.data_path.push_to_stack(arg)
            st = ''
            for i in range(IOAddresses.INPUT_STORAGE, IOAddresses.INPUT_STORAGE + 20):
                st += (str(self.control_unit.data_path.memory[i]) + ' ')
        elif arg == "i":
            self.control_unit.data_path.push_to_stack(self.control_unit.loop_counter)
        elif arg == "in_pointer":
            self.control_unit.data_path.push_to_stack(self.control_unit.mem_inp_pointer)
            self.control_unit.mem_inp_pointer += 1
        elif arg == "out_pointer":
            self.control_unit.data_path.push_to_stack(self.control_unit.mem_out_pointer)
            self.control_unit.mem_out_pointer += 1

    def execute_print_top(self, instruction):
        self.control_unit.data_path.push_to_stack(IOAddresses.OUT_ADDR)
        self.control_unit.data_path.save(False)

    def execute_cr(self, instruction):
        self.control_unit.data_path.push_to_stack(10)
        self.control_unit.data_path.push_to_stack(IOAddresses.OUT_ADDR)
        self.control_unit.data_path.save(True)

    def execute_dup(self, instruction):
        val = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.push_to_stack(val)
        self.control_unit.data_path.push_to_stack(val)

    def execute_halt(self, instruction):
        self.control_unit.halted = True

    def unknown_instruction(self, instruction):
        raise ValueError(f"Unknown opcode: {instruction.get('opcode')}")
