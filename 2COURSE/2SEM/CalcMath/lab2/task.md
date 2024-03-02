**Title: Разложение Холецкого**

Description: [ДИСКЛЕЙМЕР: Это не всё, что вам нужно сделать для сдачи лабораторной работы по курсу, но этого достаточно, чтобы получить вариант на следующую работу. Проверьте требования и вариант в методическом пособии в файлах Teams.]

Решите систему линейных алгебраических уравнений, реализуя метод разложения Холецкого. Также выведите полученные промежуточные значения y.
Формат входных данных:

n

a11 a12 ... a1n b1

a21 a22 ... a2n b2

...

an1 an2 ... ann bn

Формат вывода:

x1

x2 

...

xn

y1

y2

...

yn

, где x1..xn - значения неизвестных, а y1..yn - значения y.

Для систем, которые не имеют решений или имеют неограниченное количество решений, должно быть напечатано только следующее сообщение:
"The system has no roots of equations or has an infinite set of them.". Для этого задайте значение переменной isSolutionExists и сообщение об ошибке.

Max score: 100

Min score: 60

Code before
```python
#!/bin/python3 
 
import math 
import os 
import random 
import re 
import sys
```
Code edit:
```python
class Solution: 
    isSolutionExists = True 
    errorMessage = "" 
 
    # 
    # Complete the 'solveByCholeskyDecomposition' function below. 
    # 
    # The function is expected to return a DOUBLE_ARRAY. 
    # The function accepts following parameters: 
    #  1. INTEGER n 
    #  2. 2D_DOUBLE_ARRAY matrix 
    # 
    def solveByCholeskyDecomposition(n, matrix): 
        # Write your code here
```
Code after:
```python
if name == '__main__': 
    n = int(input().strip()) 
 
    matrix_rows = n 
    matrix_columns = n + 1 
 
    matrix = [] 
 
    for _ in range(matrix_rows): 
        matrix.append(list(map(float, input().rstrip().split()))) 
 
    result = Solution.solveByCholeskyDecomposition(n, matrix) 
    if Solution.isSolutionExists: 
        print('\n'.join(map(str, result))) 
    else: 
        print(f"{Solution.errorMessage}") 
    print("")
```