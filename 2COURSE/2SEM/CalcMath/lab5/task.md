# Title: Метод Адамса

Реализуйте метод Адамса для решения обыкновенных дифференциальных уравнений по начальному значению (задача Коши) в интервале от a до b [a,b].
<ul>
<li>f</li>
<li>epsilon</li>
<li>a</li>
<li>y(a)</li>
<li>b</li>
</ul>
f - номер уравнения, где уравнение в виде y'=f(x,y). Вы должны получить функцию по номеру из входных данных в методе get_function.
Вы должны определить и пересчитать шаг h самостоятельно.
Вы должны вычислить и вернуть y(b) с разницей, не превышающей epsilon.

Подсказка: Чтобы использовать метод Адамса для решения задачи Коши, вам нужно больше 1 начальной точки. Поэтому вам также необходимо реализовать еще один метод для вычисления 3 дополнительных разгоночных точек. Вы можете выбрать этот метод самостоятельно, но если вы неправильно рассчитаете начальный набор точек, то это может повлиять на все решение.
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
    
    def first_function(x: float, y: float):
        return math.sin(x)


    def second_function(x: float, y: float):
        return (x * y)/2


    def third_function(x: float, y: float):
        return y - (2 * x)/y


    def fourth_function(x: float, y: float):
        return x + y

    
    def default_function(x:float, y: float):
        return 0.0

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
        else:
            return Result.default_function

    #
    # Complete the 'solveByAdams' function below.
    #
    # The function is expected to return a DOUBLE.
    # The function accepts following parameters:
    #  1. INTEGER f
    #  2. DOUBLE epsilon
    #  3. DOUBLE a
    #  4. DOUBLE y_a
    #  5. DOUBLE b
    #
    def solveByAdams(f, epsilon, a, y_a, b):
        # Write your code here
```

## Code after:
```python
if __name__ == '__main__':
    f = int(input().strip())

    epsilon = float(input().strip())

    a = float(input().strip())

    y_a = float(input().strip())

    b = float(input().strip())

    result = Result.solveByAdams(f, epsilon, a, y_a, b)

    print(str(result) + '\n')
```