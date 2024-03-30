# Решение систем нелинейных уравнений. Метод Ньютона.


Description: [ДИСКЛЕЙМЕР: Это не всё, что вам нужно сделать для сдачи лабораторной работы по курсу, но этого достаточно, чтобы получить вариант на следующую работу. Проверьте требования и вариант в методическом пособии в файлах Teams.]

Дана система нелинейных уравнений. По заданному начальному приближению необходимо найти решение системы с точностью до 5 верного знака после запятой при помощи метода Ньютона.

Формат входных данных:
k
n
x0
y0
...

где k - номер системы, n - количество уравнений и количество неизвестных, а остальные значения - начальные приближения для соответствующих неизвестных.

Формат выходных данных: список такого же типа данных, как списки входных данных, содержащие значения корня для каждой из неизвестных с точностью до 5 верного знака.

Max score: 100

Min score: 60

Code before:
```python
#!/bin/python3

import math
import os
import random
import re
import sys


k = 0.4
a = 0.9

def first_function(args: []) -> float:
    return math.sin(args[0])


def second_function(args: []) -> float:
    return (args[0] * args[1]) / 2


def third_function(args: []) -> float:
    return math.tan(args[0]*args[1] + k) - pow(args[0], 2)


def fourth_function(args: []) -> float:
    return a * pow(args[0], 2) + 2 * pow(args[1], 2) - 1


def fifth_function(args: []) -> float:
    return pow(args[0], 2) + pow(args[1], 2) + pow(args[2], 2) - 1


def six_function(args: []) -> float:
    return 2 * pow(args[0], 2) + pow(args[1], 2) - 4 * args[2]


def seven_function(args: []) -> float:
    return 3 * pow(args[0], 2) - 4 * args[1] + pow(args[2], 2)


def default_function(args: []) -> float:
    return 0.0


# How to use this function:
# funcs = Result.get_functions(4)
# funcs[0](0.01)
def get_functions(n: int):
    if n == 1:
        return [first_function, second_function]
    elif n == 2:
        k = 0.4
        a = 0.9
        return [third_function, fourth_function]
    elif n == 3:
        k = 0
        a = 0.5
        return [third_function, fourth_function]
    elif n == 4:
        return [fifth_function, six_function, seven_function]
    else:
        return [default_function]


#
# Complete the 'solve_by_fixed_point_iterations' function below.
#
# The function is expected to return a DOUBLE_ARRAY.
# The function accepts following parameters:
#  1. INTEGER system_id
#  2. INTEGER number_of_unknowns
#  3. DOUBLE_ARRAY initial_approximations
```

Code edit:
```python
def solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations):
    ...
```


Code after:
```python
if __name__ == '__main__':
    system_id = int(input().strip())

    number_of_unknowns = int(input().strip())

    initial_approximations = []

    for _ in range(number_of_unknowns):
        initial_approximations_item = float(input().strip())
        initial_approximations.append(initial_approximations_item)

    result = solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations)

    print('\n'.join(map(str, result)))
    print('\n')
```

```python
stdout:
Traceback (most recent call last):
  File "/src/main.py", line 182, in <module>
    result = solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations)
             ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  File "/src/main.py", line 164, in solve_by_fixed_point_iterations
    jacobian_inverse = matrix_inverse(jacobian)
                       ^^^^^^^^^^^^^^^^^^^^^^^^
  File "/src/main.py", line 122, in matrix_inverse
    inverse_matrix = [[a / determinant for a in row] for row in transpose]
                     ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  File "/src/main.py", line 122, in <listcomp>
    inverse_matrix = [[a / determinant for a in row] for row in transpose]
                      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  File "/src/main.py", line 122, in <listcomp>
    inverse_matrix = [[a / determinant for a in row] for row in transpose]
                       ~~^~~~~~~~~~~~~
ZeroDivisionError: float division by zero
```