from command import Command

# Будущие команды: с кем может встретиться тот или иной человек, в опасности тот или иной человек, близкие друзья,
# вооружен ли человек, может ли случиться бойня (опасность и вооружен), потенциальный друг (друзья через одного)

commands = [
    Command(
        r'Кто дружит с (\w+)\?',
        "Кто дружит с *имя*?",
        "friend('{0}', X)"
    )
]