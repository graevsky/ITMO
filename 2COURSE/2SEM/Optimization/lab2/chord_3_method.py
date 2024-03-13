import numpy as np

def func(x):
    result = (1 / 3) * (x ** 3) - 5 * x + x * np.log(x)
    return round(result,5)

def func_pr(x):
    result = np.log(x)+x**2-4
    return result

a = 1.84075
b = 2
e = 0.02

def calc(a_i,b_i):
    return round((a-(func_pr(a)/(func_pr(a)-func_pr(b)))*(a-b)),5)
#f'(x) = 0 x in a,b

#print("f'(a) " ,func_pr(a))
#print("f'(b) " ,func_pr(b))
#print("f'(over x) ", func_pr(1.82992))

print("over x with a ", a, " b ", b, " is ", calc(a,b), " and f'(over x) is ", func_pr(calc(a,b)))