import math


class Result:
    error_message = ""
    has_discontinuity = False

    def first_function(x: float):
        return 1 / x

    def second_function(x: float):
        if x == 0:
            return (
                math.sin(Result.eps) / Result.eps + math.sin(-Result.eps) / -Result.eps
            ) / 2
        return math.sin(x) / x

    def third_function(x: float):
        return x * x + 2

    def fourth_function(x: float):
        return 2 * x + 2

    def five_function(x: float):
        return math.log(x)

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
        elif n == 5:
            return Result.five_function
        else:
            raise NotImplementedError(f"Function {n} not defined.")

    #
    # Complete the 'calculate_integral' function below.
    #
    # The function is expected to return a DOUBLE.
    # The function accepts following parameters:
    #  1. DOUBLE a
    #  2. DOUBLE b
    #  3. INTEGER f
    #  4. DOUBLE epsilon
    #

    def check_discontinuity(a, b, func):
        dx = (b - a) / 1000
        try:
            test_points = [a + dx * i for i in range(1001)]
            for point in test_points:
                func(point)
        except:
            Result.error_message = "Integrated function has discontinuity or does not defined in current interval"
            Result.has_discontinuity = True
        return Result.has_discontinuity

    def calculate_simpson(a, b, n, func):
        h = (b - a) / n

        first_sum = 0
        for i in range(2, n - 1, 2):
            first_sum += func(a + h * i)

        second_sum = 0
        for i in range(1, n, 2):
            second_sum += func(a + h * i)

        integral = (func(a) + 2 * first_sum + 4 * second_sum + func(b)) * (h / 3)
        return integral

    def calculate_integral(a, b, f, epsilon):
        if (epsilon <= 0):
            raise Exception("Epsilon should be greater than 0!")

        func = Result.get_function(f)
        if Result.check_discontinuity(a, b, func):
            return None

        n = 2
        result_previous = Result.calculate_simpson(a, b, n, func)
        while True:
            n *= 2
            result_next = Result.calculate_simpson(a, b, n, func)
            if abs(result_previous - result_next) < epsilon:
                break
            result_previous = result_next

        if a > b:
            return -result_previous
        return result_previous


if __name__ == "__main__":

    a = float(input().strip())
    # a = 1

    b = float(input().strip())
    # b = 6

    f = int(input().strip())
    # f = 3

    epsilon = float(input().strip())
    # epsilon = 0.001

    result = Result.calculate_integral(a, b, f, epsilon)
    if not Result.has_discontinuity:
        print(str(result) + "\n")
    else:
        print(Result.error_message + "\n")


