import math
def func(x1, x2, x3):
    return x1**3 + x2**2 + 2 * x3**2 - x2 * x3 - x2


def func_dx1(x1, x2, x3):
    return 3 * x1**2


def func_dx2(x1, x2, x3):
    return 2 * x2 - x3 - 1


def func_dx3(x1, x2, x3):
    return 4 * x3 - x2


x1,x2,x3 = (0,0.5,0.125)

print("Func ",func(x1, x2, x3))

print("grad vector ", func_dx1(x1, x2, x3), func_dx2(x1, x2, x3),
      func_dx3(x1, x2, x3))

grad_f = lambda x_1, x_2, x_3: (
        func_dx1(x_1, x_2, x_3),
        func_dx2(x_1, x_2, x_3),
        func_dx3(x_1, x_2, x_3),
    )
dx1, dx2, dx3 = grad_f(x1, x2, x3)

print("Stop? ", math.sqrt(dx1**2 + dx2**2 + dx3**2))

# x2 = 0.5
# x3 = 0.125
# df2 = 1-0.125-1
# df3 = 0.5-0.5