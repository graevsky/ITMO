import math

def f(x1, x2, x3):
    return x1**3 + x2**2 + 2*x3**2 - x2*x3 - x2

def f_dx1(x1, x2, x3):
    return 3 * math.pow(x1, 2)

def f_dx2(x1, x2, x3):
    return 2 * x2 - x3 - 1

def f_dx3(x1, x2, x3):
    return 4 * x3 - x2

def grad_f(x1, x2, x3):
    return [f_dx1(x1, x2, x3), f_dx2(x1, x2, x3), f_dx3(x1, x2, x3)]

def norm(vector):
    return math.sqrt(sum(x**2 for x in vector))

def add_vectors(v1, v2):
    return [x + y for x, y in zip(v1, v2)]

def scalar_multiply(scalar, vector):
    return [scalar * x for x in vector]

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

def fastest_descent(f, grad_f, M0, epsilon, a, b, e):
    x = list(M0)
    k = 1

    while True:
        gradient = grad_f(*x)
        norm_grad = norm(gradient)

        if norm_grad < epsilon:
            break

        direction = scalar_multiply(-1 / norm_grad, gradient)

        func_lambda = lambda l: f(*(add_vectors(x, scalar_multiply(l, direction))))
        lambda_k = golden_ratio(func_lambda, a, b, e)

        x = add_vectors(x, scalar_multiply(lambda_k, direction))
        k += 1

    return x

M0 = (0, 0, 0)
epsilon = 0.001
a, b = -10, 10
e = 0.001

result = fastest_descent(f, grad_f, M0, epsilon, a, b, e)
print(result)
