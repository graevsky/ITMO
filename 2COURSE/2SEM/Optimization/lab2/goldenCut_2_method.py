import numpy as np

def func(x):
    result = (1 / 3) * (x ** 3) - 5 * x + x * np.log(x)
    return round(result,5)


def golden_ratio(a, b, e):
    phi = (1 + np.sqrt(5)) / 2

    x1 = round(b - (b - a) / phi,5)
    x2 = round(a + (b - a) / phi,5)
    y1 = func(x1)
    y2 = func(x2)

    while abs(b - a) > e:

        if y1 < y2:
            b = x2
            x2 = x1
            y2 = y1
            x1 = round(b - (b - a) / phi,5)
            y1 = func(x1)
        else:
            a = x1
            x1 = x2
            y1 = y2
            x2 = round(a + (b - a) / phi,5)
            y2 = func(x2)

    return (a + b) / 2


#a = 1.5
#b = 2
#e = 0.02

# f(x) = 1/3 * x**3-5*x+x*ln(x) a = 1.5 b = 2 e =0.02

print("For f(x) = (1/3)*x^3-5*x+x*ln(x) enter a, b ([a,b]) and epsilon: ")

inp_a = float(input())
inp_b = float(input())
inp_e = float(input())

xm = golden_ratio(inp_a,inp_b,inp_e)

fm = func(xm)

print("Result is: xm = ",xm, ", ym = ", fm)