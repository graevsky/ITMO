import numpy as np
def func(x):
    result = (1 / 3) * (x ** 3) - 5 * x + x * np.log(x)
    return round(result,5)
def solution(a, b, e):
    i = 1
    while b - a >= 2 * e:

        print("Before iteration ", i)
        print("A ", a, " B ", b)

        x1 = round((a + b - e) / 2,3)
        x2 = round((a + b + e) / 2,3)

        y1 = func(x1)
        y2 = func(x2)
        print("X1 ", x1, " X2 ", x2, " Y1 ", y1, " Y2 ", y2, "\n")

        if y1 > y2:
            a = x1
        else:
            b = x2
        a = round(a,5)
        b = round(b,5)

        print("After iteration ", i)
        print("A ", a, " B ", b, " X1 ", x1, " X2 ", x2, " Y1 ", y1, " Y2 ", y2, "\n\n\n")

        i += 1
    result = round((a+b)/2,5)
    return result

# f(x) = 1/3 * x**3-5*x+x*ln(x) a = 1.5 b = 2 e =0.02

#print("For f(x) = (1/3)*x^3-5*x+x*ln(x) enter a, b ([a,b]) and epsilon: ")

inp_a = 1.5
inp_b = 2
inp_e = 0.02
#inp_a = float(input())
#inp_b = float(input())
#inp_e = float(input())

xm = solution(inp_a,inp_b,inp_e)

print("Result is: xm = ", round(xm,5), ", ym = ", round(func(xm),5))


