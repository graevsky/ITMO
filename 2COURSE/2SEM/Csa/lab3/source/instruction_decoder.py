from isa import Opcode, IOAddresses


class InstructionDecoder:
    def __init__(self, control_unit):
        self.control_unit = control_unit
        self.mem_inp_pointer = IOAddresses.INPUT_STORAGE
        self.mem_out_pointer = IOAddresses.INPUT_STORAGE

    def decode(self, instruction):
        opcode = instruction.get("opcode")
        method_name = f"execute_{opcode.lower()}"
        method = getattr(self, method_name, self.unknown_instruction)
        method(instruction)

    def execute_pop(self, instruction):
        self.control_unit.data_path.pop_from_stack()

    def execute_swap(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.push_to_stack(a)
        self.control_unit.data_path.push_to_stack(b)

    def execute_save(self, instruction):
        addr = self.control_unit.data_path.pop_from_stack()
        val = self.control_unit.data_path.pop_from_stack()
        if addr == IOAddresses.OUT_ADDR:
            self.control_unit.data_path.write_io(val, True)
        else:
            self.control_unit.data_path.memory[addr] = val
            # print("Saved ", str(val), " to ", str(addr))

    def execute_dec_i(self, instruction):
        current_value = self.control_unit.data_path.loop_counter.get_data()
        self.control_unit.data_path.loop_counter.set_data(current_value - 1)

    def execute_load(self, instruction):
        addr = self.control_unit.data_path.pop_from_stack()
        if addr == IOAddresses.INP_ADDR:
            if self.control_unit.data_path.input_buffer:
                value = self.control_unit.data_path.input_buffer.pop(0)
                if isinstance(value, str):
                    value = ord(value)
                self.control_unit.data_path.push_to_stack(value)
            else:
                self.control_unit.data_path.push_to_stack(0)  # Ввод закончен
        else:
            value = self.control_unit.data_path.memory[addr]
            # print("loaded ", str(value), " from ", str(addr))
            self.control_unit.data_path.push_to_stack(value)

    def execute_add(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.ADD, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch.get_data())

    def execute_less_than(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.LESS_THAN, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch.get_data())

    def execute_greater_than(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.GREATER_THAN, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch.get_data())

    def execute_equals(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.EQUALS, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch.get_data())

    def execute_mod(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.MOD, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch.get_data())

    def execute_and(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.AND, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch.get_data())

    def execute_or(self, instruction):
        a = self.control_unit.data_path.pop_from_stack()
        b = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.alu.execute(Opcode.OR, a, b)
        self.control_unit.data_path.push_to_stack(self.control_unit.data_path.alu_latch.get_data())

    def execute_loop_start(self, instruction):
        initial, max_value, step = instruction.get("arg")
        self.control_unit.data_path.start_loop(initial, max_value, step)

    def execute_loop_end(self, instruction):
        continue_loop = self.control_unit.data_path.end_loop()
        if continue_loop:
            self.control_unit.pc.set_data(instruction.get("arg"))

    def execute_jz(self, instruction):
        condition = self.control_unit.data_path.pop_from_stack()
        if condition == 0:
            target = instruction.get("arg")
            self.control_unit.pc.set_data(target)
            self.control_unit.data_path.jump_latch.set_data(1)

    def execute_push(self, instruction):
        arg = instruction.get("arg")
        if isinstance(arg, int):
            self.control_unit.data_path.push_to_stack(arg)
            st = ''
            for i in range(IOAddresses.INPUT_STORAGE, IOAddresses.INPUT_STORAGE + 20):
                st += (str(self.control_unit.data_path.memory[i]) + ' ')
        elif arg == "i":
            self.control_unit.data_path.push_to_stack(self.control_unit.data_path.loop_counter.get_data())
        elif arg == "in_pointer":
            self.control_unit.data_path.push_to_stack(self.mem_inp_pointer)
            self.mem_inp_pointer += 1
        elif arg == "out_pointer":
            self.control_unit.data_path.push_to_stack(self.mem_out_pointer)
            # print("Pushed ", str(self.mem_out_pointer))
            self.mem_out_pointer += 1

    def execute_print_top(self, instruction):
        val = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.write_io(val, False)

    def execute_cr(self, instruction):
        self.control_unit.data_path.write_io(10, True)

    def execute_dup(self, instruction):
        val = self.control_unit.data_path.pop_from_stack()
        self.control_unit.data_path.push_to_stack(val)
        self.control_unit.data_path.push_to_stack(val)

    def execute_halt(self, instruction):
        self.control_unit.halted = True

    def unknown_instruction(self, instruction):
        raise ValueError(f"Unknown opcode: {instruction.get('opcode')}")
