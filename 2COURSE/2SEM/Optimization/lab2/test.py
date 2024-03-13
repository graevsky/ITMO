import numpy as np

def func(x):
    return (1/3)*x**3 - 5*x + x*np.log(x)

def func_derivative(x):
    return np.log(x) + x**2 - 4

def chord_method(a, b, e):
    i = 1
    while abs(b - a) >= e:
        x = b - (func(b) * (b - a)) / (func(b) - func(a))
        if func_derivative(x) == 0:
            print("Производная равна нулю, метод хорд не может продолжаться.")
            return None
        if func_derivative(x) > 0:
            b = x
        else:
            a = x
        print("Iteration " + str(i))
        print("a now " + str(a) + " and b is " + str(b))
        i += 1
    return (a + b) / 2

# Пример использования
a = 1.5
b = 2
e = 0.02
minimum_point = chord_method(a, b, e)
minimum_value = func(minimum_point)
print("Точка минимума:", minimum_point)
print("Минимальное значение функции:", minimum_value)
