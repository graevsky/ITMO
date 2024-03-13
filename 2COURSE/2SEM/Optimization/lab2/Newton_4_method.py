import numpy as np

def func(x):
    result = (1 / 3) * (x ** 3) - 5 * x + x * np.log(x)
    return round(result,5)

def func_pr(x):
    result = np.log(x)+x**2-4
    return round(result,5)


def func_pr2(x):
    result = 2*x+(1/x)
    return round(result,5)


def F(x,x0):
    return round(func_pr(x0)+func_pr2(x0)*(x-x0),5)


