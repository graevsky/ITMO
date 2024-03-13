import numpy as np

def func(x):
    result = (1 / 3) * (x ** 3) - 5 * x + x * np.log(x)
    return round(result,4)


def golden_ratio_optimization(a, b, e):
    # Константа золотого сечения
    phi = (1 + np.sqrt(5)) / 2

    # Начальные значения
    x1 = round(b - (b - a) / phi,4)
    x2 = round(a + (b - a) / phi,4)
    y1 = func(x1)
    y2 = func(x2)
    print("start vals: x1 ", x1, " y1 ", y1, " x2 ",x2, " y2 ", y2)

    # Основной цикл
    i = 1
    while abs(b - a) > e:

        if y1 < y2:
            b = x2
            x2 = x1
            y2 = y1
            x1 = round(b - (b - a) / phi,4)
            y1 = func(x1)
        else:
            a = x1
            x1 = x2
            y1 = y2
            x2 = round(a + (b - a) / phi,4)
            y2 = func(x2)


        print(" iter ", i)
        print(" x1 ",x1, " y1 ", y1, " x2 ", x2, " y2 ", y2)
        print("A is ", a, " B is ", b)
        print()
        print()
        i+=1

    # Возвращаем точку минимума
    return (a + b) / 2


# Пример использования
a = 1.5
b = 2
e = 0.02
minimum_point = golden_ratio_optimization(a, b, e)
minimum_value = func(minimum_point)
print("Точка минимума:", minimum_point)
print("Минимальное значение функции:", minimum_value)