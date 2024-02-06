#var 3
# Дан текст. Требуется найти в тексте все фамилии, отсортировав их по алфавиту.
# Фамилией для простоты будем считать слово с заглавной буквой, после которого идут
# инициалы
import re


import re

def sort_surnames(text):
    pattern = r'\b[А-ЯЁ][а-яё]+\s[А-ЯЁ]\.[А-ЯЁ]\.'
    surnames = sorted(set(re.findall(pattern, text)))
    return '\n'.join(surnames)