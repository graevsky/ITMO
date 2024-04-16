import math


class Result:

    def first_function(x: float, y: float):
        return math.sin(x)

    def second_function(x: float, y: float):
        return (x * y) / 2

    def third_function(x: float, y: float):
        return y - (2 * x) / y

    def fourth_function(x: float, y: float):
        return x + y

    def default_function(x: float, y: float):
        return 0.0

    # How to use this function:
    # func = Result.get_function(4)
    # func(0.01)

    def get_function(n: int):
        if n == 1:
            return Result.first_function
        elif n == 2:
            return Result.second_function
        elif n == 3:
            return Result.third_function
        elif n == 4:
            return Result.fourth_function
        else:
            return Result.default_function

    #
    # Complete the 'solveByAdams' function below.
    #
    # The function is expected to return a DOUBLE.
    # The function accepts following parameters:
    #  1. INTEGER f
    #  2. DOUBLE epsilon
    #  3. DOUBLE a
    #  4. DOUBLE y_a
    #  5. DOUBLE b
    #

    def rungeKutta(func, x, y, h):
        k1 = func(x, y)
        k2 = func(x + 0.5 * h, y + 0.5 * k1 * h)
        k3 = func(x + 0.5 * h, y + 0.5 * h * k2)
        k4 = func(x + h, y + h * k3)
        return y + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4)

    def solveByAdams(f, epsilon, a, y_a, b):
        counter = 1
        if a > b:
            raise ValueError("Interval is incorrect, solution is not possible")

        func = Result.get_function(f)
        h = (b - a) * epsilon

        x_val = [a + i * h for i in range(4)]
        y_val = [y_a]
        for i in range(3):
            y_val.append(Result.rungeKutta(func, x_val[i], y_val[i], h))

        while x_val[-1] < b:

            y_next = y_val[-1] + (h / 24) * (
                55 * func(x_val[-1], y_val[-1])
                - 59 * func(x_val[-2], y_val[-2])
                + 37 * func(x_val[-3], y_val[-3])
                - 9 * func(x_val[-4], y_val[-4])
            )
            x_next = x_val[-1] + h

            y_val.append(y_next), x_val.append(x_next)
            if abs(x_val[-1] - b) < h:
                h = b - x_val[-1]

            counter += 1

        print("Counter " + str(counter))
        return y_val[-1]


if __name__ == "__main__":
    f = [1, 4, 2, 4, 3]

    epsilon = [0.001, 0.0001, 0.01, 0.000001, 0.01]

    a = [0, 1, 0, 0, 0]

    y_a = [0, 1, 1, 1, 0]

    b = [math.pi, 1.001, 10, 1, 1]

    for i in range(5):
        print("Iteration " + str(i))
        print(f[i])
        print(epsilon[i])
        print(a[i])
        print(y_a[i])
        print(b[i])
        print(
            str(Result.solveByAdams(f[i], epsilon[i], a[i], y_a[i], b[i]))
            + " # result \n\n\n"
        )

    # result = Result.solveByAdams(f, epsilon, a, y_a, b)

    # print(str(result) + "\n")
