# Вар 93456
### 1. Для своей программы из лабораторной работы #3 по дисциплине "Веб-программирование" реализовать:

* MBean, считающий общее число установленных пользователем точек, а также число точек, попадающих в область. В случае, если количество установленных пользователем точек стало кратно 5, разработанный MBean должен отправлять оповещение об этом событии.
* MBean, определяющий средний интервал между кликами пользователя по координатной плоскости.

### 2. С помощью утилиты JConsole провести мониторинг программы:

* Снять показания MBean-классов, разработанных в ходе выполнения задания 1.
* Определить значение переменной classpath для данной JVM.

### 3. С помощью утилиты VisualVM провести мониторинг и профилирование программы:

* Снять график изменения показаний MBean-классов, разработанных в ходе выполнения задания 1, с течением времени.
* Определить имя потока, потребляющего наибольший процент времени CPU.

### 4. С помощью утилиты VisualVM и профилировщика IDE NetBeans, Eclipse или Idea локализовать и устранить проблемы с производительностью в программе. По результатам локализации и устранения проблемы необходимо составить отчёт, в котором должна содержаться следующая информация:

* Описание выявленной проблемы.
* Описание путей устранения выявленной проблемы.
* Подробное (со скриншотами) описание алгоритма действий, который позволил выявить и локализовать проблему.
* Студент должен обеспечить возможность воспроизведения процесса поиска и локализации проблемы по требованию преподавателя.

## Отчёт по работе должен содержать:

* Текст задания.
* Исходный код разработанных MBean-классов и сопутствующих классов.
* Скриншоты программы JConcole со снятыми показаниями, выводы по результатам мониторинга.
* Скриншоты программы VisualVM со снятыми показаниями, выводы по результатам профилирования.
* Скриншоты программы VisualVM с комментариями по ходу поиска утечки памяти.
* Выводы по работе.

## Вопросы к защите лабораторной работы:

* Мониторинг и профилирование. Основные понятия. Отличия мониторинга от профилирования.
* Инфраструктура для организации мониторинга и профилирования в составе JDK. JMX.
* MBeans. Основные понятия. Архитектура фреймворка.
* Утилита JConsole. Возможности, область применения.
* Утилита Visual VM. Возможности, область применения.
* Удалённый мониторинг и профилирование приложений на платформе Java.