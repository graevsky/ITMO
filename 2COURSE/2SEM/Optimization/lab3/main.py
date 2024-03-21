import math
import numpy as np


def f(x):
    return (1 / 3) * pow(x, 3) - 5 * x + x * np.log(x)


def solution(x1, del_x, e):
    i = 1
    while True:
        i+=1
        x2 = x1 + del_x
        f1 = f(x1)
        f2 = f(x2)

        if f1 > f2:
            x3 = x1 + 2 * del_x
        else:
            x3 = x1 - del_x

        f3 = f(x3)

        f_min = min(f1, f2, f3)
        if f_min == f1:
            x_min = x1
        elif f_min == f2:
            x_min = x2
        else:
            x_min = x3
        #print("X1 ",x1, " X2 ", x2, " X3 ", x3)
        #print("Y1 ", f1, " Y2 ", f2, " Y3 ", f3)
        num = (x2**2 - x3**2) * f1 + (x3**2 - x1**2) * f2 + (x1**2 - x2**2) * f3
        den = (x2 - x3) * f1 + (x3 - x1) * f2 + (x1 - x2) * f3

        if den == 0:
            x1 = x_min
            continue

        x_over = 0.5 * num / den
        #print("X over ", x_over)
        f_over = f(x_over)

        if abs((f_min - f_over) / f_over) < e and abs((x_min - x_over) / x_over) < e:
            #print("Iter amount: ",i)
            return round(x_over, 5)
        if x_over not in [min(x1, x3), max(x1, x3)]:
            x1 = x_over
            continue
        if f_over < f_min:
            if x_over < x_min:
                x1 = x_over
            else:
                x1 = x_min
        else:
            if x_min == x1:
                x1 = x_min - del_x
            else:
                x1 = x_min


# f(x) = (1 / 3) * pow(x, 3) - 5 * x + x * np.log(x)

#inp_x = 1.5
#inp_dx = 0.001
#inp_e = 0.00001

print(
    "For f(x) = (1 / 3) * pow(x, 3) - 5 * x + x * np.log(x) enter starting x, delta x, epsilon:"
)

inp_x = input()
inp_dx = input()
inp_e = input()


res_x = solution(inp_x,inp_dx,inp_e)

print("Min x: ", res_x, " and min f(x): ", round(f(res_x), 5))
