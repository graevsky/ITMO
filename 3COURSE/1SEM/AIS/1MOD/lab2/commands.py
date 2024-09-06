from command import Command

commands = [
    Command(
        r'Покажи моих друзей\.',
        "Покажи моих друзей.",
        "mutual_friends('{0}', X)"
    ),
    Command(
        r'Что у меня есть\?',
        "Что у меня есть?",
        "owns('{0}', X)"
    ),
    Command(
        r'Где я нахожусь\?',
        "Где я нахожусь?",
        "location('{0}', X)"
    ),
    Command(
        r'В опасности ли я\?',
        "В опасности ли я?",
        "in_danger('{0}')"
    ),
    Command(
        r'Вооружен ли я\?',
        "Вооружен ли я?",
        "armed('{0}')"
    ),
    Command(
        r'Подружимся ли мы с (\w+)\?',
        "Подружимся ли мы с *имя2*?",
        "enemy_of_my_enemy_is_friend('{0}', '{1}')"
    )
]
