#var 3
# Вывесили списки стипендиатов текущего семестра, которые представляют из себя
# список людей ФИО и номер группы этого человека. Вы решили подшутить над
# некоторыми из своих одногруппников и удалить их из списка.
# С помощью регулярного выражения найдите всех студентов своей группы, у которых
# инициалы начинаются на одну и туже букву и исключите их из списка.


import re

def BanStuds(studs, group):
    bannedStuds = []
    for student in studs:
        regExp = re.match(r'(\S)\S* (\S)\S* (\S+)', student)
        if regExp and regExp.group(1) == regExp.group(2) and regExp.group(3) == group:
            bannedStuds.append(student)
    return [student for student in studs if student not in bannedStuds]
