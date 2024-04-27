from ruamel.yaml import YAML
from ruamel.yaml.scalarstring import LiteralScalarString
import sys,json
from translator import translate
from machine import simulation, read_code




def capture_stdout(program, input_data):
    from io import StringIO
    old_stdout = sys.stdout
    sys.stdout = StringIO()
    simulation(program, input_data)
    output = sys.stdout.getvalue()
    sys.stdout = old_stdout
    return output

def run_simulation_and_capture_output(program, input_data):
    from io import StringIO
    old_stdout = sys.stdout
    sys.stdout = StringIO()

    # Выполняем симуляцию и одновременно захватываем стандартный вывод
    instr_count, ticks, execution_log = simulation(program, input_data)
    output = sys.stdout.getvalue()
    sys.stdout = old_stdout

    return output, instr_count, ticks, execution_log
def save_as_yaml(source_file, input_file,output_yaml):
    """ Сохранение входных файлов в формате YAML с сохранением форматирования """
    yaml = YAML()
    yaml.indent(sequence=4, offset=2)
    yaml.width = 500
    yaml.preserve_quotes = True

    with open(source_file, 'r', encoding='utf-8') as file:
        source_code = file.read()
    with open(input_file, 'r', encoding='utf-8') as file:
        input_data = file.read()

    machine_code = translate(source_code)
    output, instr_count, ticks, execution_log = run_simulation_and_capture_output(machine_code, input_data)


    data = {
        'in_source': LiteralScalarString(source_code),
        'in_stdin': LiteralScalarString(input_data),
        'out_code': LiteralScalarString('\n'.join(f"{item}" for item in machine_code)),
        'out_stdout': LiteralScalarString(f"Source instr: {len(machine_code)}\n============================================================\n{output}\nInstr counter: {instr_count}, Ticks: {ticks}"),
        'out_log': LiteralScalarString(execution_log)
    }

    with open(output_yaml, 'w', encoding='utf-8') as file:
        yaml.dump(data, file)

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: python logger.py <source_file> <input_file> <output_yaml>")
    else:
        _, source_file, input_file, output_yaml = sys.argv
        save_as_yaml(source_file, input_file, output_yaml)

"""1. Принимать входной файл (программу на форте)
2. Транслировать ее и сохранять результат
3. Принимать IO из input.txt
4. Сохранять результат с инфой (как в примере)
5. Формировать лог
6. Формировать yml отчет о работе как в примере
Если тебе такой подход кажется нелогичным, ты в праве его изменить"""