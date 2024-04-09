import math
import numpy as np

def f(x1, x2, x3):
    return x1**3 + x2**2 + 2*x3**2 - x2*x3 - x2

def f_dx1(x1, x2, x3):
    return 3*math.pow(x1, 2)

def f_dx2(x1, x2, x3):
    return 2*x2 - x3 - 1

def f_dx3(x1, x2, x3):
    return 4*x3 - x2

grad_f = lambda x1, x2, x3: np.array([f_dx1(x1, x2, x3), f_dx2(x1, x2, x3), f_dx3(x1, x2, x3)])

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
def adjust_bounds(func, initial_l=1.0, factor=2, max_iter=10):
    """
        Корректировка границ золотого сечения
    """
    a, b = -initial_l, initial_l
    for _ in range(max_iter):
        if func(a) > func(0) and func(b) > func(0):
            return a, b
        a *= factor
        b *= factor
    return a, b

def fastest_descent_with_auto_bounds(f, grad_f, M0, epsilon, e):
    x = np.array(M0)
    k = 1

    while True:
        gradient = grad_f(*x)
        norm_grad = np.linalg.norm(gradient)

        if norm_grad < epsilon:
            break

        direction = -gradient / norm_grad

        func_lambda = lambda l: f(*(x + l * direction))
        a, b = adjust_bounds(func_lambda)
        lambda_k = golden_ratio(func_lambda, a, b, e)

        x = x + lambda_k * direction
        k += 1

    return x

M0 = (0, 0, 0)
epsilon = 0.001
e = 0.001

result = fastest_descent_with_auto_bounds(f, grad_f, M0, epsilon, e)
print(result)
