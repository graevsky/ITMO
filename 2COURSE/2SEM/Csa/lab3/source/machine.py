import argparse
import os

from isa import read_code, IOAddresses
import logging
from io import StringIO
from ALU import ALU
from MUX import Multiplexer
from Latch import Latch
from instruction_decoder import InstructionDecoder

log_stream = StringIO()
logging.basicConfig(
    stream=log_stream,
    level=logging.DEBUG,
    format="%(asctime)s - %(levelность)s - %(сообщение)s",
)


class DataPath:
    def __init__(self, memory, inp_data):
        self.memory = memory  # Общая память
        self.stack = []
        self.sp = Latch()  # Указатель стека
        self.sp.set_data(0)  # Указатель стека
        self.input_buffer = list(inp_data) + [0]  # Буфер для входных данных
        self.ip = Latch()  # Указатель input buffer
        self.return_stack = []  # Вспомогательный стек для управления циклами
        self.output_addr_counter = 0

        """loop control"""
        self.loop_counter = Latch()
        self.loop_counter.set_data(0)

        """ALU"""
        self.alu_latch = Latch()
        self.alu = ALU(self)

        self.latch = Latch()  # Кто ты воин

        """MUX"""
        self.comp_latch = Latch()
        self.mux = Multiplexer(self)

        self.push_latch = Latch()

        """Jump latch"""
        self.jump_latch = Latch()
        self.jump_latch.set_data(0)

    # Сделать из этого oe (не только вывод io, но еще и запись в память).
    def write_io(self, address, value):
        """Вывод IO"""
        print(chr(value), end="")  # Заменить на лог
        self.memory[address] = value  # addr + self.output_addr_counter
        self.output_addr_counter += 1

    # Убрать dup отсюда (в instruction_decoder),
    # разбить на ряд более простых функций. Здесь оставить только простой push.
    def push_to_stack(self, source_type=None, value=None, duplicate_top=False):
        """Помещает значение в стек, выбранное мультиплексором"""
        if duplicate_top and self.stack:
            self.stack.append(self.stack[-1])
        else:
            if source_type == "direct_value":
                self.latch.set_data(value)
            else:
                result = self.mux.select_for_stack(source_type)
                self.latch.set_data(result)
            self.stack.append(self.latch.get_data())
        self.sp.set_data(self.sp.get_data() + 1)

    def pop_from_stack(self):
        """Возвращает значение из стека"""
        if self.sp.get_data() > 0:
            self.sp.set_data(self.sp.get_data() - 1)
            return self.stack.pop()
        raise IndexError("Stack underflow")

    # Сделать что то с loop control
    def start_loop(self, initial, max_value, step):
        """Запуск цикла через return stack"""
        self.return_stack.append((initial, max_value, step))
        self.loop_counter.set_data(initial)

    def end_loop(self):
        """Проверка условия и остановка цикла"""
        if len(self.return_stack) == 0:
            raise Exception("No loop context in return stack")
        initial, max_value, step = self.return_stack[-1]
        current_value = self.loop_counter.get_data()
        next_value = current_value + step
        if next_value <= max_value:
            self.loop_counter.set_data(next_value)
            return True
        else:
            self.return_stack.pop()
            return False  # Завершить цикл

    # Переделать в instruction_decoder, как pop+oe
    def print_top(self):
        """Вывести верхний элемент стека"""
        if self.stack:
            print(self.pop_from_stack())
        else:
            raise Exception("Attempt to print from an empty stack")

    # Убрать\перенести в instruction_decoder (как набор простых операций).
    def perform_operation(self, opcode):
        """Выполнение операций через ALU"""
        a, b = self.mux.select_sources("ALU", opcode)
        self.alu.execute(opcode, a, b)
        self.push_to_stack("alu_result")

    def load(self):
        addr = self.pop_from_stack()
        val = self.memory[addr]
        self.push_to_stack("direct_value", val)

    def inp(self):
        """Чтение символа из input_buffer в стек"""
        if self.input_buffer:
            char = self.input_buffer.pop(0)
            if char == 0:
                self.push_to_stack("direct_value", 0)
            else:
                self.push_to_stack("direct_value", ord(char))
        else:
            self.push_to_stack("direct_value", 0)


class ControlUnit:
    def __init__(self, memory, input_data):
        self.memory = memory  # Общая память для данных и программы
        self.pc = Latch()  # Счётчик программ
        self.pc.set_data(0)
        self.halted = False
        self.data_path = DataPath(memory, input_data)  # Общая память
        self.instr_counter = 0  # Счетчик выполненных инструкций
        self.tick_counter = 0  # Счетчик тиков (модельного времени)
        self.instr_latch = Latch()
        self.decoder = InstructionDecoder(self)  # Декодер

    def fetch_instruction(self):
        if self.pc.get_data() < len(self.memory) and self.memory[self.pc.get_data()] is not None:
            self.instr_latch.set_data(self.memory[self.pc.get_data()])
        else:
            raise IndexError("Program counter out of bounds")

    def execute_instruction(self):
        instruction = self.instr_latch.get_data()
        # logging.debug(f"Executing instruction at PC={self.pc.get_data()}: {instruction}")
        self.decoder.decode(instruction)
        if self.data_path.jump_latch.get_data() == 0:  # По сути MUX
            self.pc.set_data(self.pc.get_data() + 1)
        self.data_path.jump_latch.set_data(0)

    def run(self):
        while not self.halted:
            self.fetch_instruction()
            self.execute_instruction()
            self.tick_counter += 1


def simulation(program, input_data, data_segment):
    memory = [0] * 1024
    for i, instruction in enumerate(program):
        memory[i] = instruction

    # Инициализация сегмента данных
    base_address = IOAddresses.STRING_STORAGE
    for offset, content in data_segment.items():
        address = base_address + int(offset)
        length = content[0]
        for i in range(length + 1):
            memory[address + i] = content[i]

    control_unit = ControlUnit(memory, input_data)
    control_unit.run()

    logs = log_stream.getvalue()
    return control_unit.instr_counter, control_unit.tick_counter, logs


def run_all_programs(directory, input_file):
    for file in os.listdir(directory):
        if file.endswith('.json'):
            code_file = os.path.join(directory, file)
            print(f"Processing {code_file}")
            print()
            main(code_file, input_file)


def main(code_file, input_file):
    machine_code = read_code(code_file)
    program = machine_code["program"]
    data_segment = machine_code["data"]
    with open(input_file, "r", encoding="utf-8") as file:
        input_data = file.read()
    instr_count, ticks, logs = simulation(program, input_data, data_segment)
    print()
    print(f"Instructions executed: {instr_count}, Ticks: {ticks}")


def parse_args():
    parser = argparse.ArgumentParser(description="Run FORTH machine code simulations.")
    parser.add_argument("-a", "--all", action="store_true",
                        help="Process all JSON files in the machine code directory.")
    parser.add_argument("input_file", type=str, nargs='?', default=None, help="Path to the input file for the machine.")
    parser.add_argument("machine_code_file", type=str, nargs='?', help="Path to a specific machine code file to run.")
    return parser.parse_args()


if __name__ == "__main__":
    args = parse_args()
    if args.all:
        if args.input_file is None:
            print("Please specify an input file.")
        else:
            machine_code_dir = './source/machine_code'
            run_all_programs(machine_code_dir, args.input_file)
    elif args.machine_code_file and args.input_file:
        main(args.machine_code_file, args.input_file)
    else:
        print("Invalid usage. Run 'python machine.py -h' for help.")
