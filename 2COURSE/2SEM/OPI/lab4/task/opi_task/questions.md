### Вопросы и ответы
1. Отличия мониторинга от профилирования
- Мониторинг - просмотр общего состояния приложения без подробностей, идет длительное время, по сути - статистика. (JConsole)
- Профилирование - отслеживание поведения конкретной части программы. Позволяет залезть в кучу, посмотреть количество экземпляров, ссылки. (VisualVM)
- MBean доступны и там, и там тк доступ к нему через JMX.
- 
2. Каким образом можно узнать внутреннее состояние приложения без профилирования (альтернативы):
- Сбор логов, метрик
- Сбор ошибок

3. Что можно сделать с помощью профилирования, но нельзя с помощью логгинга:
- Логгинг покажет, что случилось
- Профайлинг покажет, как случилось и что конкретно, сколько времени потрачено было, количество вызовов. Так же позволяет просмотреть память (но логгинг так же позволяет залезть в некоторую память)

4. Как определить, что есть гарантированная утечка памяти без просмотра кода
- Смотрим дамп или набор дампов, сравниваем состояние памяти, количества созданных экземпляров, связи между ними
- Находим самого большого потребителя
- Определить, кто держит память
- Понять почему (Eclipse MAT сравнивает утечку с популярными шаблонами)

5. Что делать с 4 лабой
- Понять, какая страница медленно работает
- Понять, что с чем она взаимодействует в бекенде
- Смотрим логи
- Если проблема в стороннем сервисе - пинаем поставщика
- Если проблема у нас - смотрим бд, скорее всего дело в ней. Самый простой способ - проверить и оптимизировать запросы (для hibernate есть утилита, позволяющая посмотреть формируемые запросы). Можно так же прикрутить пул соединений (сложно).
- Если проблема не в бд - еще раз логи
- Потом уже мониторинг. Определили куда копать +-.
- Профилирование