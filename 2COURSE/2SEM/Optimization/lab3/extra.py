import numpy as np


def function(x):
    return round((1 / 3) * pow(x, 3) - 5 * x + x * np.log(x), 10)


x = 1.8591428571

print(function(x))


def comp(x1, x2):
    if x1 > x2:
        return "1 > 2"
    else:
        return "1 <= 2"


print(comp(-6.0009874371, -6.0009172916))


def min_f(x1, x2, x3):
    return min(x1, x2, x3)


print(min_f(-6.0009874371, -6.0009172916, -6.0008428917))


def bar_x(x1, x2, x3, f1, f2, f3):
    numerator = round(
        (x2**2 - x3**2) * f1 + (x3**2 - x1**2) * f2 + (x1**2 - x2**2) * f3, 10
    )
    denominator = round((x2 - x3) * f1 + (x3 - x1) * f2 + (x1 - x2) * f3, 10)

    if denominator == 0:
        return "error", "denom is 0"
    x_bar = round(0.5 * numerator / denominator, 10)
    print("Numer is ", numerator)
    print("Denom is ", denominator)
    return x_bar, function(x_bar)

x1 = 1.8571428571
x2 = 1.8581428571
x3 = 1.8591428571
x_res, f_res = bar_x(x1, x2, x3, function(x1), function(x2),function(x3))

print("Bar x res ", x_res, " F bar res ", f_res)


def x_in(x_bar, x1, x3):
    if x1 <= x_bar <= x3:
        return "Yes"
    else:
        return "No"


print("x in: ", x_in(1.8255813953, 1.8571428571, 1.8591428571))

def validation(F_min,f_over,x_min,x_over):
    res1 = abs((F_min-f_over)/f_over)
    res2 = abs((x_min-x_over)/x_over)

    if res1 >= 0.0001 or res2 >= 0.0001:
        print("Next iter")
        print("F res ", round(res1,10), " x res ", round(res2,10))

validation(-6.0009874371,-6.0010250215,1.8571428571,1.8255813953)


