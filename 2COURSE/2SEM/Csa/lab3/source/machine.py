import argparse
import os

from isa import read_code, IOAddresses
import logging
from io import StringIO
from ALU import ALU
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
        self.stack = []  # Стек
        self.stack_pointer = 0  # Указатель стека
        self.input_buffer = list(inp_data) + [0]  # Буфер для входных данных
        self.output_buffer = list()  # Буфер для выходных данных

        """Инициализация ALU"""
        self.alu_latch = 0
        self.alu = ALU(self)

    # Можно ли оставить?
    def write_io(self, value, tochar=False):
        """Вывод IO"""
        if tochar:
            print(chr(value), end="")  # Заменить на лог
        else:
            print(value, end="")  # Заменить на лог
        self.output_buffer.append(value)

    """Помещает значение в стек и увеличивает счетчик стека на 1"""

    def push_to_stack(self, value):
        self.stack.append(value)
        self.stack_pointer = self.stack_pointer + 1

    """Возвращает значение из стека и уменьшает счетчик стека на 1"""

    def pop_from_stack(self):
        if self.stack_pointer > 0:
            self.stack_pointer = self.stack_pointer - 1
            return self.stack.pop()
        raise IndexError("Stack underflow")

    """Загружает значение из памяти в стек"""

    def load(self):
        addr = self.pop_from_stack()  # Адрес для загрузки берется из стека
        if addr == IOAddresses.INP_ADDR:  # Если это адрес ввода, то значение в стек попадает из input buffer
            if self.input_buffer:
                value = self.input_buffer.pop(0)
                if isinstance(value, str):
                    value = ord(value)
                self.push_to_stack(value)
            else:
                self.push_to_stack(0)
        else:  # Если это просто адрес из памяти, то значение из него помещается в стек
            value = self.memory[addr]
            self.push_to_stack(value)

    """Сохраняет значение из стека в память"""

    def save(self, tochar=True):
        addr = self.pop_from_stack()  # Адрес для сохранения берется из стека
        val = self.pop_from_stack()  # Значение для сохранения берется из стека
        if addr == IOAddresses.OUT_ADDR:  # Если это адрес вывода, то значение из стека помещается в output buffer
            self.write_io(val, tochar)
        else:  # Если это просто адрес из памяти, то по этому адресу записывается значение
            self.memory[addr] = val


class ControlUnit:
    def __init__(self, memory, input_data):
        self.memory = memory  # Общая память
        self.pc = 0  # Счётчик программ
        self.halted = False  # Состояние остановки модели
        self.data_path = DataPath(memory, input_data)  # Инициализация DataPath
        self.instr_counter = 0  # Счетчик выполненных инструкций
        self.tick_counter = 0  # Счетчик тиков (модельного времени)
        self.instr_latch = 0  # Регистр хранения инструкции
        self.decoder = InstructionDecoder(self)  # Декодер инструкций
        self.mem_inp_pointer = IOAddresses.INPUT_STORAGE  # Указатель для сохранения символов в память ввода
        self.mem_out_pointer = IOAddresses.INPUT_STORAGE  # Указатель для загрузки символов из памяти вывода

        """Инициализация регистра и returnstack для управления циклами"""
        self.loop_counter = 0
        self.return_stack = []

        """Регистр для хранения информации о переходе"""
        self.jump_latch = 0

    """Загрузка информации о цикле в return stack"""

    def start_loop(self, initial, max_value, step):
        self.return_stack.append((initial, max_value, step))
        self.loop_counter = initial

    """Проверка условия и остановка цикла"""

    def end_loop(self):
        if len(self.return_stack) == 0:
            raise Exception("No loop context in return stack")
        initial, max_value, step = self.return_stack[-1]
        current_value = self.loop_counter
        next_value = current_value + step
        if next_value <= max_value:
            self.loop_counter = next_value
            return True
        else:
            self.return_stack.pop()
            return False  # Завершить цикл

    """Загрузка инструкции в регистр"""

    def fetch_instruction(self):
        if self.pc < len(self.memory) and self.memory[self.pc] is not None:
            self.instr_latch = self.memory[self.pc]
        else:
            raise IndexError("Program counter out of bounds")

    """Обработка инструкции из регистра"""

    def execute_instruction(self):
        instruction = self.instr_latch
        self.decoder.decode(instruction)
        if self.jump_latch == 0:
            self.pc = self.pc + 1
        self.jump_latch = 0

    def run(self):
        while not self.halted:
            self.fetch_instruction()
            self.execute_instruction()
            self.tick_counter += 1

"""Запуск симуляции"""
def simulation(program, input_data, data_segment):
    memory = [0] * 1024  # Инициализация памяти
    for i, instruction in enumerate(program):  # Загрузка инструкций в память
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
