import tasks.n1
import tasks.n2
import tasks.n3
print("Task 1, 2, 3 examples:")
print("Task 1:")
inp = ';<{O'
print("Smile: " + inp)
print("Result: " + str(tasks.n1.smiles(inp)))
print("Task 2:")
inp = 'Студент Вася вспомнил, что на своей лекции Балакшин П.В. упоминал про старшекурсников, которые будут ему помогать: Анищенко А.А. и Машина Е.А. '
print("Text: " + inp)
print("Result: " + tasks.n2.sort_surnames(inp))
print("Task 3:")
inp = [
            'Петров П.П. P000',
            'Анищенко А.А. P33113',
            'Примеров Е.В. P000',
            'Иванов И.И. P000'
]
print("List: " + str(inp))
group = 'P000'
print("Group: " + group)
print("Result: " + str(tasks.n3.BanStuds(inp,group)))
