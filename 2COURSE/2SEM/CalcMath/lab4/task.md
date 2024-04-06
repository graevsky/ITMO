# Title: Метод Симпсона

Реализуйте метод Симпсона для вычисления интеграла от выбранной функции на интервале от a до b.
<ul>
<li> Если функция имеет разрыв второго рода или "скачок", или если функция не определена какой-либо частью в интервале от a до b, то вам следует указать переменные error_message и hasDiscontinuity.</li>
<li> Сообщение об ошибке, которое вы должны указать: "Integrated function has discontinuity or does not defined in current interval".</li>
<li> Если функция имеет устранимый разрыв первого рода, то вы должны уметь вычислить интеграл. </li>
<li> Если a > b, то интеграл должен иметь отрицательное значение.</li>
</ul>
Формат ввода:
<ul>
<li>a</li>
<li>b</li>
<li>f</li>
<li>epsilon</li>
</ul>
, где a и b - границы интеграла, f - номер функции, epsilon - максимальная разница между двумя вашими итерациями (итерация - это некоторое разбиение на отрезки).
Формат вывода:
<ul>
<li>I</li>
</ul>
, где I - ваш вычисленный интеграл для текущего количества разбиений.

## Code before:
```python
#!/bin/python3

import math
import os
import random
import re
import sys
```

## Code edit:
```python
class Result:
    error_message = ""
    has_discontinuity = False
    
    def first_function(x: float):
        return 1 / x


    def second_function(x: float):
        if x == 0:
            return (math.sin(Result.eps)/Result.eps + math.sin(-Result.eps)/-Result.eps)/2 
        return math.sin(x)/x


    def third_function(x: float):
        return x*x+2


    def fourth_function(x: float):
        return 2*x+2


    def five_function(x: float):
        return math.log(x)

    # How to use this function:
    # func = Result.get_function(4)
    # func(0.01)
    def get_function(n: int):
        if n == 1:
            return Result.first_function
        elif n == 2:
            return Result.second_function
        elif n == 3:
            return Result.third_function
        elif n == 4:
            return Result.fourth_function
        elif n == 5:
            return Result.five_function
        else:
            raise NotImplementedError(f"Function {n} not defined.")

    #
    # Complete the 'calculate_integral' function below.
    #
    # The function is expected to return a DOUBLE.
    # The function accepts following parameters:
    #  1. DOUBLE a
    #  2. DOUBLE b
    #  3. INTEGER f
    #  4. DOUBLE epsilon
    #

    def calculate_integral(a, b, f, epsilon):
        # Write your code here
```

## Code after:
```python
if __name__ == '__main__':

    a = float(input().strip())

    b = float(input().strip())

    f = int(input().strip())

    epsilon = float(input().strip())

    result = Result.calculate_integral(a, b, f, epsilon)
    if not Result.has_discontinuity:
        print(str(result) + '\n')
    else:
        print(Result.error_message + '\n')
```