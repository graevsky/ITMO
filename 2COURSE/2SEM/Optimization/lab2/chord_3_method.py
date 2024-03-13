import numpy as np

def func(x):
    return round((1 / 3) * x ** 3 - 5 * x + x * np.log(x),5)

def func_derivative(x):
    return round(np.log(x) + x ** 2 - 4,5)

def chord(a, b, e):
    x = a - (func_derivative(a)/(func_derivative(a)-func_derivative(b)))*(a-b)

    while(func_derivative(x) > e):
        if (func_derivative(x) < 0):
            a = x
        else:
            b = x

        x = a - (func_derivative(a) / (func_derivative(a) - func_derivative(b))) * (a - b)

    return round(x,5)

#inp_a = 1.5
#inp_b = 2
#inp_e = 0.02

# f(x) = 1/3 * x**3-5*x+x*ln(x) a = 1.5 b = 2 e =0.02

print("For f(x) = (1/3)*x^3-5*x+x*ln(x) enter a, b ([a,b]) and epsilon: ")

inp_a = float(input())
inp_b = float(input())
inp_e = float(input())

xm = chord(inp_a,inp_b,inp_e)

fm = func(xm)

print("Result is: xm = ",xm, ", ym = ", fm)
