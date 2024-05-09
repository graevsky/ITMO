from isa import Opcode, read_code, IOAddresses
import logging
import sys
from io import StringIO
from ALU import ALU
from MUX import Multiplexer
from Latch import Latch
from instruction_decoder import InstructionDecoder

log_stream = StringIO()
logging.basicConfig(
    stream=log_stream,
    level=logging.DEBUG,
    format="%(asctime)s - %(levelname)s - %(message)s",
)


class DataPath:
    def __init__(self, memory, inp_data):
        self.loop_step = None
        self.memory = memory  # Общая память
        self.stack = []
        self.sp = Latch() # Указатель стека
        self.sp.set_data(0)  # Указатель стека
        self.input_buffer = []  # Буфер для входных данных
        self.ip = Latch() # Указатель input buffer

        """loop control"""
        self.loop_index = Latch()
        self.loop_counter = Latch()
        self.loop_max = Latch()
        self.loop_index.set_data(0)
        self.loop_counter.set_data(0)
        self.loop_max.set_data(0)

        """ALU"""
        self.alu_latch = Latch()
        self.alu = ALU(self)

        self.latch = Latch() # ???

        """MUX"""
        self.comp_latch = Latch()
        self.mux = Multiplexer(self)

        self.push_latch = Latch()

        """input data"""
        self.data = inp_data

    def write_io(self, address, value):
        if address == IOAddresses.OUTPUT_ADDRESS:
            print(chr(value), end="")
        else:
            self.memory[address] = value

    def accept_input(self):
        """Загружает входные данные в буфер и память."""
        self.input_buffer = self.data
        self.ip.set_data(len(self.data))
        for i, char in enumerate(self.data):
            if i < IOAddresses.INPUT_BUFFER_SIZE:
                self.memory[IOAddresses.INPUT_BUFFER + i] = ord(char)

    def push_to_stack(self, value):
        """Помещает значение в стек"""
        self.latch.set_data(value)  # Значение сначала помещается в защелку
        self.stack.append(self.latch.get_data())
        self.sp.set_data(self.sp.get_data() + 1)

    def pop_from_stack(self):
        """Возвращает значение из стека"""
        if self.sp.get_data() > 0:
            self.sp.set_data(self.sp.get_data() - 1)
            return self.stack.pop()
        raise IndexError("Stack underflow")

    def write_output(self):
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
            self.loop_counter.set_data(initial)
            self.loop_max.set_data(max_value)
            self.loop_step = step
            self.loop_index.set_data(self.sp.get_data())

    def end_loop(self, start_index):
        self.loop_counter.set_data(self.loop_counter.get_data() + self.loop_step)
        if self.loop_counter.get_data() <= self.loop_max.get_data():
            return start_index
        else:
            self.loop_counter.set_data(self.loop_index.get_data())
            return None

    # Добавление значения i
    def push_i(self):
        self.push_to_stack(self.loop_counter.get_data())

    def print_top(self):
        if self.stack:
            print(self.stack[-1])
            self.stack.pop()
        else:
            raise Exception("Attempt to print from an empty stack")

    def perform_operation(self, opcode):
        a, b = self.mux.select_sources("ALU", opcode)
        self.alu.execute(opcode, a, b)
        self.push_to_stack(self.alu_latch.get_data())


class ControlUnit:
    def __init__(self, memory,input_data):
        self.memory = memory  # Общая память для данных и программы
        self.pc = Latch()  # Счётчик программ
        self.pc.set_data(0)
        self.halted = False
        self.data_path = DataPath(memory, input_data)  # Общая память
        self.instr_counter = 0  # Счетчик выполненных инструкций
        self.tick_counter = 0  # Счетчик тиков (модельного времени)
        self.instr_latch = Latch()
        self.decoder = InstructionDecoder(self)  # Декодера


    def fetch_instruction(self):
        if self.pc.get_data() < len(self.memory) and self.memory[self.pc.get_data()] is not None:
            self.instr_latch.set_data(self.memory[self.pc.get_data()])
        else:
            raise IndexError("Program counter out of bounds")

    def execute_instruction(self):
        instruction = self.instr_latch.get_data()
        logging.debug(f"Executing instruction at PC={self.pc.get_data()}: {instruction}")
        self.decoder.decode(instruction)  # Использование декодера для выполнения инструкции
        self.pc.set_data(self.pc.get_data() + 1)

    def run(self):
        while not self.halted:
            self.fetch_instruction()
            self.execute_instruction()
            self.tick_counter += 1


def simulation(program, input_data):
    memory = [0] * 1024
    for i, instruction in enumerate(program):
        memory[i] = instruction

    control_unit = ControlUnit(memory,input_data)
    #control_unit.data_path.set_input_buffer(input_data)
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

    main("./machine_code/prob1.json", "./machine_code/input.txt")
