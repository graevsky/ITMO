import numpy as np

def func(x):
    result = (1 / 3) * (x ** 3) - 5 * x + x * np.log(x)
    return round(result,5)

def func_der(x):
    result = np.log(x)+x**2-4
    return round(result,5)

def func_der2(x):
    result = 2*x+(1/x)
    return round(result,5)

i = 1
def newton(a,b,e):
    x0 = (a + b ) / 2
    while True:
        f_prime = func_der(x0)
        f_dprime = func_der2(x0)

        if(abs(f_prime) <= e):
            break

        x0 = round(x0 - (f_prime/f_dprime),5)
        print("Iter ",i,"\n\n")

    return round(x0,7)

inp_a = 1.5
inp_b = 2
inp_e = 0.02

# f(x) = 1/3 * x**3-5*x+x*ln(x) a = 1.5 b = 2 e =0.02

#print("For f(x) = (1/3)*x^3-5*x+x*ln(x) enter a, b ([a,b]) and epsilon: ")

#inp_a = float(input())
#inp_b = float(input())
#inp_e = float(input())

xm = newton(inp_a,inp_b,inp_e)

fm = func(xm)

print("Result is: xm = ",xm, ", ym = ", fm)

