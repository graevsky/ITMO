from pyswip import Prolog
from commands import commands
import re

intro = "Для помощи введите help"
current_name = None


def help_list():
    txt = "\nСписок доступных команд:\n"
    for i, command in enumerate(commands, start=1):
        txt += f"{i}. {command.description}\n"
    txt += "exit для выхода"
    return txt


def init():
    try:
        prolog = Prolog()
        prolog.consult('mcKB.pl')
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
            else:
                formatted_results.append("Нет данных")
        elif isinstance(result, bool):
            formatted_results.append("Да" if result else "Нет")
        else:
            formatted_results.append(str(result))

    return ", ".join(formatted_results)


def main():
    global current_name
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

        match_name_and_query = re.match(r'Меня зовут (\w+)\.\s*(.*)', command)
        if match_name_and_query:
            current_name = match_name_and_query.group(1)
            command = match_name_and_query.group(2)

        if current_name is None:
            print("Пожалуйста, сначала представьтесь (например, 'Меня зовут arthur').")
            continue

        matched = False
        for cmd in commands:
            match = re.match(cmd.format, command)
            if match:
                matched = True
                if "enemy_of_my_enemy_is_friend" in cmd.query:
                    name2 = match.group(1)
                    query = cmd.query
                    results = request_from_kb(prolog, query, [current_name, name2])
                else:
                    query = cmd.query
                    results = request_from_kb(prolog, query, [current_name])

                formatted_output = format_results(results)
                print(f"Результат: {formatted_output}")
                break

        if not matched:
            print("Неизвестная команда. Пожалуйста, введите help для получения помощи.")


if __name__ == "__main__":
    main()
