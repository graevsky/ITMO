from pyswip import Prolog
from commands import commands
import re

intro = "Для помощи введите help"


def help_list():
    txt = "Список доступных команд:\n"
    for command in commands:
        txt += command.description + "\n"
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
        return "Ничего не найдено."

    if len(results) == 1:
        result = results[0]
        if isinstance(result, dict):
            return result.get("X", "Нет данных")
        elif isinstance(result, bool):
            return "Да" if result else "Нет"

    formatted_results = []
    for result in results:
        if isinstance(result, dict):
            formatted_results.append(result.get("X", "Нет данных"))
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
                name = match.group(1)
                query = cmd.querry
                results = request_from_kb(prolog, query, [name])
                formatted_output = format_results(results)
                print(f"Результат: {formatted_output}")
                break

        if not matched:
            print("Неизвестная команда. Пожалуйста, введите help для получения помощи.")


if __name__ == "__main__":
    main()
