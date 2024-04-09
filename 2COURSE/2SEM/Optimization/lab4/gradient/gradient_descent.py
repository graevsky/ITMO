import math


def func(x1, x2, x3):
    return x1**3 + x2**2 + 2 * x3**2 - x2 * x3 - x2


def func_dx1(x1, x2, x3):
    return 3 * x1**2


def func_dx2(x1, x2, x3):
    return 2 * x2 - x3 - 1


def func_dx3(x1, x2, x3):
    return 4 * x3 - x2


def gradient_descent(f, f_dx1, f_dx2, f_dx3, m0, eps, step):
    x1, x2, x3 = m0

    grad_f = lambda x_1, x_2, x_3: (
        f_dx1(x_1, x_2, x_3),
        f_dx2(x_1, x_2, x_3),
        f_dx3(x_1, x_2, x_3),
    )

    while True:
        dx1, dx2, dx3 = grad_f(x1, x2, x3)

        if (math.sqrt(dx1**2 + dx2**2 + dx3**2)) <= eps:
            break

        x1 -= step * dx1
        x2 -= step * dx2
        x3 -= step * dx3
    return x1, x2, x3


M0 = (0, 0, 0)
epsilon = 0.001
h = 0.05

result = gradient_descent(func, func_dx1, func_dx2, func_dx3, M0, epsilon, h)
print(result)
print(func(*result))
