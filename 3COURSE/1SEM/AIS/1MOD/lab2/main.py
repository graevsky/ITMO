from pyswip import Prolog
from commands import commands
import re

intro = "Для помощи введите help"


def help_list():
    txt = "\nСписок доступных команд:\n"
    for i, command in enumerate(commands, start=1):
        txt += f"{i}. {command.description}\n"
    txt += "exit для выхода"
    return txt


def init():
    try:
        prolog = Prolog()
        prolog.consult("kb.pl")
        return prolog
    except FileNotFoundError:
        print("Ошибка при подключении к базе знаний")
    return


def request_from_kb(prolog, query, vals):
    formatted_query = query.format(*vals)
    results = list(prolog.query(formatted_query))
    return results


def format_results(results):
    if not results:
        return "Информация отсутствует."

    if all(result == {} for result in results):
        return "Да"

    formatted_results = []
    for result in results:
        if isinstance(result, dict):
            if 'X' in result:
                formatted_results.append(result['X'])
            elif 'What' in result:
                formatted_results.append(result['What'])
            else:
                formatted_results.append("Нет данных")
        elif isinstance(result, bool):
            formatted_results.append("Да" if result else "Нет")
        else:
            formatted_results.append(str(result))

    return ", ".join(formatted_results)


def main():
    prolog = init()

    print(intro)

    while True:
        command = input().strip()

        if command == "exit":
            print("Выход из программы.")
            break

        if command == "help":
            print(help_list())
            continue

        matched = False
        for cmd in commands:
            match = re.match(cmd.format, command)
            if match:
                matched = True
                # Проверяем, сколько групп было захвачено в команде
                if len(match.groups()) == 1:
                    name = match.group(1)
                    query = cmd.query
                    results = request_from_kb(prolog, query, [name])
                elif len(match.groups()) == 2:
                    name1 = match.group(1)
                    name2 = match.group(2)
                    query = cmd.query
                    results = request_from_kb(prolog, query, [name1, name2])

                formatted_output = format_results(results)
                print(f"Результат: {formatted_output}")
                break

        if not matched:
            print("Неизвестная команда. Пожалуйста, введите help для получения помощи.")

if __name__ == "__main__":
    main()
