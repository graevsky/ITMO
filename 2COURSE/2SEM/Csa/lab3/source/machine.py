from isa import Opcode, read_code, IOAddresses
import logging
import sys
from io import StringIO
from util import ALU, Multiplexer, Latch

log_stream = StringIO()
logging.basicConfig(
    stream=log_stream,
    level=logging.DEBUG,
    format="%(asctime)s - %(levelname)s - %(message)s",
)


class DataPath:
    def __init__(self, memory):
        self.loop_step = None
        self.memory = memory  # Общая память
        self.stack = []
        self.sp = 0  # Указатель стека
        self.input_buffer = []  # Буфер для входных данных
        self.input_pointer = 0
        """"""
        self.loop_index = 0  # Индекс начала цикла
        self.loop_counter = 0  # Счетчик цикла
        self.loop_max = 0  # Максимальное значение цикла

        self.alu = ALU()
        self.latch = Latch()
        self.comp_latch = Latch()
        self.mux = Multiplexer(self, self.comp_latch)

    def read_io(self, address):
        if address == IOAddresses.INPUT_BUFFER:
            return self.input_buffer[: IOAddresses.INPUT_BUFFER_SIZE]
        return self.memory[address]

    def write_io(self, address, value):
        if address == IOAddresses.OUTPUT_ADDRESS:
            print(chr(value), end="")
        else:
            self.memory[address] = value

    def accept_input(self, size):
        """Имитирует загрузку данных из буфера в стек или другие структуры данных."""
        end_pos = min(self.input_pointer + size, len(self.input_buffer))
        input_data = self.input_buffer[self.input_pointer: end_pos]
        self.input_pointer = end_pos
        for char in input_data:
            self.push_to_stack(ord(char))

    def set_input_buffer(self, data):
        """Загружает входные данные в буфер и память."""
        self.input_buffer = data
        self.input_pointer = 0
        for i, char in enumerate(data):
            if i < IOAddresses.INPUT_BUFFER_SIZE:
                self.memory[IOAddresses.INPUT_BUFFER + i] = ord(char)

    def push_to_stack(self, value):
        """Помещает значение в стек"""
        self.latch.set_data(value)  # Значение сначала помещается в защелку
        self.stack.append(self.latch.get_data())
        self.sp += 1

    def pop_from_stack(self):
        """Возвращает значение из стека"""
        if self.sp > 0:
            self.sp -= 1
            return self.stack.pop()
        raise IndexError("Stack underflow")

    def signal_output(self):
        """Выводит все данные из памяти начиная с адреса INPUT_BUFFER до первого нулевого символа."""
        start_address = IOAddresses.INPUT_BUFFER
        while self.memory[start_address] != 0:
            self.write_io(IOAddresses.OUTPUT_ADDRESS,
                          self.memory[start_address])
            start_address += 1

    def store_string_in_memory(self, address, length, string_data):
        """Сохранение строки в память начиная с указанного адреса."""
        self.memory[address] = length  # Сохраняем длину строки в первой ячейке
        for i in range(length):
            self.memory[address + 1 + i] = ord(string_data[i])

    def print_pstr(self, address):
        length = self.memory[address]
        for i in range(length):
            self.write_io(IOAddresses.OUTPUT_ADDRESS,
                          self.memory[address + 1 + i])

    def start_loop(self, initial, max_value, step):
        if self.loop_step is None:
            self.loop_counter = initial
            self.loop_max = max_value
            self.loop_step = step
            self.loop_index = self.sp

    def end_loop(self, start_index):
        self.loop_counter += self.loop_step
        if self.loop_counter <= self.loop_max:
            return start_index
        else:
            self.loop_counter = self.loop_index
            return None

    # Добавление и получение значения i
    def push_i(self):
        self.push_to_stack(self.loop_counter)

    def print_top(self):
        if self.stack:
            print(self.stack[-1])
            self.stack.pop()
        else:
            raise Exception("Attempt to print from an empty stack")

    def perform_operation(self, opcode):
        a, b = self.mux.select_sources(opcode)
        result = self.alu.execute(opcode, a, b)
        self.push_to_stack(result)


class ControlUnit:
    def __init__(self, memory):
        self.memory = memory  # Общая память для данных и программы
        self.pc = 0  # Счётчик программ
        self.halted = False
        self.data_path = DataPath(memory)  # Общая память
        self.instr_counter = 0  # Счетчик выполненных инструкций
        self.tick_counter = 0  # Счетчик тиков (модельного времени)
        self.instr_latch = Latch()

    def fetch_instruction(self):
        if self.pc < len(self.memory) and self.memory[self.pc] is not None:
            self.instr_latch.set_data(self.memory[self.pc])
        else:
            raise IndexError("Program counter out of bounds")

    def execute_instruction(self):
        instruction = self.instr_latch.get_data()
        opcode = instruction.get("opcode")
        arg = instruction.get("arg")
        logging.debug(f"Executing instruction at PC={self.pc}: {instruction}")
        self.instr_counter += 1

        if opcode == Opcode.PSTR:
            self.data_path.print_pstr(arg)

        elif opcode == Opcode.ADD.value:
            self.data_path.perform_operation(opcode)

        elif opcode == Opcode.LESS_THAN.value:
            self.data_path.comp_latch.set_data(arg)
            self.data_path.perform_operation(opcode)
        elif opcode == Opcode.GREATER_THAN.value:
            self.data_path.comp_latch.set_data(arg)
            self.data_path.perform_operation(opcode)
        elif opcode == Opcode.EQUALS.value:
            self.data_path.comp_latch.set_data(arg)
            self.data_path.perform_operation(opcode)

        elif opcode == Opcode.MOD.value:
            self.data_path.perform_operation(opcode)
        elif opcode == Opcode.AND.value:
            self.data_path.perform_operation(opcode)
        elif opcode == Opcode.OR.value:
            self.data_path.perform_operation(opcode)

        elif opcode == Opcode.IF.value:
            # Проверка условия и пропуск инструкций до THEN, если условие ложно
            if not self.data_path.pop_from_stack():
                # Пропустить все до THEN
                self.pc += 1  # Следующая инструкция до окончания if
                while self.memory[self.pc].get("opcode") != Opcode.THEN.value:
                    self.pc += 1
                return  # Завершение текущей инструкции после перехода
        elif opcode == Opcode.THEN.value:
            # Заглушка
            pass

        elif opcode == Opcode.LOOP_START.value:
            initial, max_value, step = arg
            self.data_path.start_loop(initial, max_value, step)
        elif opcode == Opcode.LOOP_END.value:
            new_index = self.data_path.end_loop(arg)
            if new_index is not None:
                self.pc = new_index
            else:
                self.pc += 1
            return

        elif opcode == Opcode.PUSH.value:
            if type(arg) == list:
                self.data_path.store_string_in_memory(arg[0], arg[1], arg[2])
            elif arg == "i":
                self.data_path.push_i()
            else:
                self.data_path.push_to_stack(arg)
        elif opcode == Opcode.PRINT_TOP.value:
            self.data_path.print_top()
        elif opcode == Opcode.CR.value:
            print()
        elif opcode == Opcode.LOAD_ADDR.value:
            address = int(arg, 16) if isinstance(arg, str) else int(arg)
            self.data_path.read_io(address)

        elif opcode == Opcode.ACCEPT.value:
            self.data_path.accept_input(arg)
        elif opcode == Opcode.TYPE.value:
            self.data_path.signal_output()
        elif opcode == Opcode.PRINT_STRING.value:
            print(arg, end="")
        elif opcode == Opcode.DUP.value:
            if self.data_path.sp > 0:
                value = self.data_path.stack[-1]
                self.data_path.push_to_stack(value)
        elif opcode == Opcode.HALT.value:
            self.halted = True
        else:
            raise ValueError(f"Unknown opcode: {opcode}")

        self.pc += 1

    def run(self):
        while not self.halted:
            self.fetch_instruction()
            self.execute_instruction()
            self.tick_counter += 1


def simulation(program, input_data):
    memory = [0] * 1024
    for i, instruction in enumerate(program):
        memory[i] = instruction

    control_unit = ControlUnit(memory)
    control_unit.data_path.set_input_buffer(input_data)
    control_unit.run()

    logs = log_stream.getvalue()  # Сбор логов, не закрывайте поток здесь
    return control_unit.instr_counter, control_unit.tick_counter, logs


def main(code_file, input_file):
    program = read_code(code_file)
    with open(input_file, "r", encoding="utf-8") as file:
        input_data = file.read()

    instr_count, ticks, logs = simulation(program, input_data)
    print(f"Instructions executed: {instr_count}, Ticks: {ticks}")


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python machine.py <machine_code_file> <input_file>")
    else:
        _, code_file, input_file = sys.argv
        # main(code_file, input_file)

    main("./machine_code/add.json", "./machine_code/input.txt")
