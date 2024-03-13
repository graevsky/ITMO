import numpy as np
def func(x):
    result = (1 / 3) * (x ** 3) - 5 * x + x * np.log(x)
    return result
def solution(a, b, e):
    i = 1
    while b - a >= 2 * e:
        x1 = (a + b - e) / 2
        x2 = (a + b + e) / 2

        y1 = func(x1)
        y2 = func(x2)

        if y1 > y2:
            a = x1
        else:
            b = x2

        i += 1
    return a, b

# f(x) = 1/3 * x**3-5*x+x*ln(x) a = 1.5 b = 2 e =0.02

print("For f(x) = (1/3)*x^3-5*x+x*ln(x) enter a, b ([a,b]) and epsilon: ")

inp_a = float(input())
inp_b = float(input())
inp_e = float(input())

res_a, res_b = solution(inp_a,inp_b,inp_e)
xm = (res_a+res_b)/2

print("Result is: xm = ", round(xm,5), ", ym = ", round(func(xm),5))


