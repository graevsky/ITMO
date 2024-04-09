import math
import numpy as np


def f(x1, x2, x3):
    return x1**3 + x2**2 + 2 * x3**2 - x2 * x3 - x2


def f_dx1(x1, x2, x3):
    return 3 * math.pow(x1, 2)


def f_dx2(x1, x2, x3):
    return 2 * x2 - x3 - 1


def f_dx3(x1, x2, x3):
    return 4 * x3 - x2


grad_f = lambda x1, x2, x3: np.array(
    [f_dx1(x1, x2, x3), f_dx2(x1, x2, x3), f_dx3(x1, x2, x3)]
)


def golden_ratio(func, a, b, e):
    phi = (1 + math.sqrt(5)) / 2
    x1 = b - (b - a) / phi
    x2 = a + (b - a) / phi
    y1 = func(x1)
    y2 = func(x2)

    while abs(b - a) > e:
        if y1 < y2:
            b = x2
            x2 = x1
            y2 = y1
            x1 = b - (b - a) / phi
            y1 = func(x1)
        else:
            a = x1
            x1 = x2
            y1 = y2
            x2 = a + (b - a) / phi
            y2 = func(x2)

    return (a + b) / 2


def fastest_descent(function, gradient_f, m0, eps, a, b):
    x = np.array(m0)
    k = 1

    while True:
        gradient = gradient_f(*x)
        norm_grad = np.linalg.norm(gradient)

        if norm_grad < eps:
            break

        direction = -gradient / norm_grad

        func_lambda = lambda h: function(*(x + h * direction))
        lambda_k = golden_ratio(func_lambda, a, b, eps)

        x = x + lambda_k * direction
        k += 1

    return x


M0 = (0, 0, 0)
epsilon = 0.001
l, r = -10, 10  # for golden cut


result = fastest_descent(f, grad_f, M0, epsilon, l, r)
print(result)
print(f(*result))
