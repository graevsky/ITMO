import math
def func(x, y):
    return x**2-y**2-4*x+6*y


def func_dx1(x, y):
    return 2*x-4


def func_dx2(x, y):
    return -2*y+6


x1,x2=(0,0)
print("Func ",func(x1, x2))

print("grad vector ", func_dx1(x1, x2), func_dx2(x1, x2))

grad_f = lambda x_1, x_2: (
        func_dx1(x_1, x_2),
        func_dx2(x_1, x_2),
    )
dx1, dx2 = grad_f(x1, x2)

print(dx1,dx2)
print("lambda" )
print(math.sqrt(dx1**2+dx2**2))

print("S")
print(dx1/math.sqrt(dx1**2+dx2**2), dx2/math.sqrt(dx1**2+dx2**2))

