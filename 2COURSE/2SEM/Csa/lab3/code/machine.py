from isa import Opcode, read_code


class DataPath:
    def __init__(self, memory):
        self.memory = memory  # Общая память
        self.stack = []
        self.acc = 0  # Аккумулятор
        self.sp = 0  # Указатель стека
        self.input_buffer = ""  # Буфер для входных данных
        self.input_pointer = 0

    def accept_input(self, size):
        """Эмулирует ввод данных из буфера"""
        end_pos = min(self.input_pointer + size, len(self.input_buffer))
        input_data = self.input_buffer[self.input_pointer : end_pos]
        self.input_pointer = end_pos
        for char in input_data:
            self.push_to_stack(ord(char))

    def set_input_buffer(self, data):
        """Загружает входные данные в буфер"""
        self.input_buffer = data
        self.input_pointer = 0

    def push_to_stack(self, value):
        """Помещает значение в стек"""
        self.stack.append(value)
        self.sp += 1

    def pop_from_stack(self):
        """Возвращает значение из стека"""
        if self.sp > 0:
            self.sp -= 1
            return self.stack.pop()
        raise IndexError("Stack underflow")

    def perform_arithmetic(self, op_type):
        """Выполнение арифметических операций над двумя верхними значениями стека"""
        if self.sp < 2:
            raise Exception("Insufficient values in stack")
        b = self.pop_from_stack()
        a = self.pop_from_stack()

        if op_type == "ADD":
            result = a + b
        elif op_type == "SUB":
            result = a - b
        elif op_type == "MUL":
            result = a * b
        elif op_type == "DIV":
            result = a // b  # Деление нацело для простоты

        self.push_to_stack(result)
        self.acc = result  # Обновление аккумулятора последним результатом

    def load_to_acc(self, address):
        """Загружает значение из памяти в аккумулятор"""
        self.acc = self.memory[address]

    def store_from_acc(self, address):
        """Сохраняет значение из аккумулятора в память"""
        self.memory[address] = self.acc

    def signal_output(self):
        """Выводит все данные из стека до его опустошения"""
        while self.stack:
            char = chr(self.pop_from_stack())
            print(char, end="")

    def swap_stack(self):
        """Меняет местами два верхних значения стека"""
        if len(self.stack) < 2:
            raise Exception("Not enough data in stack to perform swap")
        a = self.pop_from_stack()
        b = self.pop_from_stack()
        self.push_to_stack(a)
        self.push_to_stack(b)


class ControlUnit:
    def __init__(self, memory):
        self.memory = memory  # Общая память для данных и программы
        self.pc = 0  # Счётчик программ
        self.halted = False
        self.data_path = DataPath(memory)  # Общая память

    def fetch_instruction(self):
        if self.pc < len(self.memory):
            return self.memory[self.pc]
        else:
            raise IndexError("Program counter out of bounds")

    def execute_instruction(self, instruction):
        opcode = instruction.get("opcode")
        arg = instruction.get("arg")

        if opcode == Opcode.CR.value:
            print()
        elif opcode == Opcode.LOAD_ADDR.value:
            address = int(arg, 16) if isinstance(arg, str) else int(arg)
            self.data_path.load_to_acc(address)
        elif opcode == Opcode.ACCEPT.value:
            self.data_path.accept_input(int(arg))
        elif opcode == Opcode.SWAP.value:
            self.data_path.swap_stack()
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
            instr = self.fetch_instruction()
            self.execute_instruction(instr)


def simulation(program, input_data):
    memory = [0] * 1024  # Создание общей памяти
    for i, instruction in enumerate(program):
        memory[i] = instruction  # Убедимся, что instruction - это словарь, а не `int`

    control_unit = ControlUnit(memory)
    control_unit.data_path.set_input_buffer(input_data)
    try:
        control_unit.run()
    except Exception as e:
        print("Simulation error:", e)


def main(code_file, input_file):
    program = read_code(code_file)
    with open(input_file, "r", encoding="utf-8") as file:
        input_data = file.read()
    simulation(program, input_data)


if __name__ == "__main__":
    # if len(sys.argv) != 3:
    #    print("Usage: python machine.py <machine_code_file> <input_file>")
    # else:
    main("cat.json", "input.txt")
    # main(sys.argv[1], sys.argv[2])
