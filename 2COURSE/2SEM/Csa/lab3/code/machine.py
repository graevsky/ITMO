from isa import Opcode,read_code





class DataPath:
    def __init__(self, memory_size):
        self.memory = [0] * memory_size
        self.stack = []
        self.acc = 0  # Аккумулятор
        self.sp = 0   # Указатель стека
        self.input_buffer = ''  # Буфер для хранения входных данных
        self.input_pointer = 0  # Указатель на текущую позицию чтения в буфере

    def accept_input(self, size):
        """ Эмулирует ввод данных из буфера """
        if self.input_pointer < len(self.input_buffer):
            input_data = self.input_buffer[self.input_pointer:self.input_pointer + size]
            self.input_pointer += size
        else:
            input_data = ''

        for char in input_data:
            self.push_to_stack(ord(char))  # Кладём каждый символ в стек как целое число

    def set_input_buffer(self, data):
        """ Загружает входные данные в буфер """
        self.input_buffer = data
        self.input_pointer = 0


    def push_to_stack(self, value):
        """ Помещает значение в стек """
        self.stack.append(value)
        self.sp += 1

    def pop_from_stack(self):
        """ Возвращает значение из стека """
        if self.sp > 0:
            self.sp -= 1
            return self.stack.pop()
        raise IndexError("Stack underflow")

    def perform_arithmetic(self, op_type):
        """ Выполнение арифметических операций над двумя верхними значениями стека """
        if self.sp < 2:
            raise Exception("Insufficient values in stack")
        b = self.pop_from_stack()
        a = self.pop_from_stack()

        if op_type == 'ADD':
            result = a + b
        elif op_type == 'SUB':
            result = a - b
        elif op_type == 'MUL':
            result = a * b
        elif op_type == 'DIV':
            result = a // b  # Деление нацело для простоты

        self.push_to_stack(result)
        self.acc = result  # Обновление аккумулятора последним результатом

    def load_to_acc(self, address):
        """ Загружает значение из памяти в аккумулятор """
        #address = int(address, 16)  # Преобразование шестнадцатеричной строки в целое число
        self.acc = self.memory[address]

    def store_from_acc(self, address):
        """ Сохраняет значение из аккумулятора в память """
        self.memory[address] = self.acc



    def signal_output(self):
        """ Выводит все данные из стека до его опустошения """
        output_string = ''
        while self.stack:
            char = chr(self.pop_from_stack())
            output_string = char + output_string  # Приписываем символы в начало строки
        print(output_string, end='')

    def swap_stack(self):
        """ Меняет местами два верхних значения стека """
        if len(self.stack) < 2:
            raise Exception("Not enough data in stack to perform swap")
        a = self.pop_from_stack()
        b = self.pop_from_stack()
        self.push_to_stack(a)
        self.push_to_stack(b)


class ControlUnit:
    def __init__(self, program, data_path):
        self.program = program  # Память программы, список инструкций
        self.pc = 0  # Счётчик программ
        self.data_path = data_path  # Ссылка на DataPath для доступа к данным и выполнения операций
        self.halted = False  # Флаг остановки процессора

    def fetch_instruction(self):
        """ Загружает инструкцию из памяти программ по адресу PC """
        if self.pc < len(self.program):
            return self.program[self.pc]
        else:
            raise IndexError("Program counter out of bounds")

    def execute_instruction(self, instruction):
        opcode = instruction.get('opcode')
        arg = instruction.get('arg')

        #print("Current instruction " + str(instruction))

        if opcode == Opcode.CR.value:
            self.data_path.push_to_stack(ord('\n'))
            self.data_path.signal_output()



        elif opcode == Opcode.LOAD_ADDR.value:
            # Убедимся, что адрес корректно интерпретируется
            if isinstance(arg, str):
                address = int(arg, 16)
            else:
                address = int(str(arg), 16)  # Преобразуем arg в строку перед конвертацией
            self.data_path.load_to_acc(address)


        elif opcode == Opcode.ACCEPT.value:

            self.data_path.accept_input(int(arg))

        elif opcode == Opcode.SWAP.value:
            self.data_path.swap_stack()

        elif opcode == Opcode.TYPE.value:
            self.data_path.signal_output()

        elif opcode == Opcode.PRINT_STRING.value:

            for char in arg:
                self.data_path.push_to_stack(ord(char))
            self.data_path.signal_output()

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
        """ Запускает выполнение программы до остановки """
        while not self.halted:
            instr = self.fetch_instruction()
            self.execute_instruction(instr)
def simulation(program, input_data):
    data_path = DataPath(1024)
    data_path.set_input_buffer(input_data)
    control_unit = ControlUnit(program, data_path)

    # Запуск выполнения программы
    # try:
    control_unit.run()
    #except Exception as e:
    #    print("Simulation error:", e)

    # Вывод результатов
    print("Output data:", ''.join(chr(x) for x in data_path.memory if x != 0))  # Простой вывод ненулевых значений памяти


import sys

def main(code_file, input_file):
    # Читаем машинный код
    program = read_code(code_file)

    # Читаем входные данные
    with open(input_file, 'r', encoding='utf-8') as file:
        input_data = file.read()

    # Запускаем симуляцию
    simulation(program, input_data)

if __name__ == "__main__":
    #if len(sys.argv) != 3:
    #    print("Usage: python machine.py <machine_code_file> <input_file>")
    #else:
        main("greet.json","input.txt")
        #main(sys.argv[1], sys.argv[2])

