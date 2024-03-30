#!/bin/python3

import math


k = 0.4
a = 0.9


def first_function(args: []) -> float:
    return math.sin(args[0])


def second_function(args: []) -> float:
    return (args[0] * args[1]) / 2


def third_function(args: []) -> float:
    return math.tan(args[0] * args[1] + k) - pow(args[0], 2)


def fourth_function(args: []) -> float:
    return a * pow(args[0], 2) + 2 * pow(args[1], 2) - 1


def fifth_function(args: []) -> float:
    return pow(args[0], 2) + pow(args[1], 2) + pow(args[2], 2) - 1


def six_function(args: []) -> float:
    return 2 * pow(args[0], 2) + pow(args[1], 2) - 4 * args[2]


def seven_function(args: []) -> float:
    return 3 * pow(args[0], 2) - 4 * args[1] + pow(args[2], 2)


def default_function(args: []) -> float:
    return 0.0


# How to use this function:
# funcs = Result.get_functions(4)
# funcs[0](0.01)
def get_functions(n: int):
    if n == 1:
        return [first_function, second_function]
    elif n == 2:
        k = 0.4
        a = 0.9
        return [third_function, fourth_function]
    elif n == 3:
        k = 0
        a = 0.5
        return [third_function, fourth_function]
    elif n == 4:
        return [fifth_function, six_function, seven_function]
    else:
        return [default_function]


#
# Complete the 'solve_by_fixed_point_iterations' function below.
#
# The function is expected to return a DOUBLE_ARRAY.
# The function accepts following parameters:
#  1. INTEGER system_id
#  2. INTEGER number_of_unknowns
#  3. DOUBLE_ARRAY initial_approximations
#


def get_determinant(matrix):
    m_len = len(matrix)

    if not all(len(row) == m_len for row in matrix):
        print("Матрица не квадратная")
        return
    if m_len == 1:
        return matrix[0][0]
    elif m_len == 2:
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]

    det = 0

    for i in range(m_len):
        minor = get_minor(matrix, 0, i)
        det += matrix[0][i] * (-1) ** i * get_determinant(minor)
    return det


def get_minor(matrix, i, j):
    return [row[:j] + row[j+1:] for row in (matrix[:i] + matrix[i+1:])]


def matrix_inverse(matrix):
    determinant = get_determinant(matrix)
    if determinant == 0:
        #print("Определитель равен нулю => получить обратную матрицу невозможно")
        return None

    m_len = len(matrix)

    cofactor_matrix = []
    for i in range(m_len):
        cofactor_row = []
        for j in range(m_len):
            minor = get_minor(matrix, i, j)
            cofactor = get_determinant(minor) * ((-1) ** (i + j))
            cofactor_row.append(cofactor)
        cofactor_matrix.append(cofactor_row)

    transpose = list(map(list, zip(*cofactor_matrix)))

    inverse_matrix = [[transpose[i][j] / determinant for j in range(m_len)] for i in range(m_len)]

    return inverse_matrix


def jacobian_matrix(funcs, args):
    h = 1e-5
    n = len(args)
    jacobian = [[0.0 for _ in range(n)] for _ in range(n)]
    for i in range(n):
        for j in range(n):
            args_with_h = args.copy()
            args_with_h[j] += h
            jacobian[i][j] = (funcs[i](args_with_h) - funcs[i](args)) / h
    return jacobian


def solve_by_newton(system_id, number_of_unknowns, initial_approximations):
    funcs = get_functions(system_id)
    args = initial_approximations
    args_len = len(args)
    for _ in range(100):
        jacobian = jacobian_matrix(funcs, args)
        F = [-f(args) for f in funcs]
        jacobian_inverse = matrix_inverse(jacobian)
        if jacobian_inverse is None:
            result = [0 for _ in range(args_len)]
            return result
        delta = [sum(jacobian_inverse_row[j] * F[j] for j in range(len(F))) for jacobian_inverse_row in jacobian_inverse]
        args = [args[i] + delta[i] for i in range(args_len)]
        if max(abs(delta[i]) for i in range(len(delta))) < 1e-5:
            break
    return [round(arg, 5) for arg in args]


if __name__ == "__main__":
    system_id = int(input().strip())

    number_of_unknowns = int(input().strip())

    initial_approximations = []

    for _ in range(number_of_unknowns):
        initial_approximations_item = float(input().strip())
        initial_approximations.append(initial_approximations_item)

    result = solve_by_newton(system_id, number_of_unknowns, initial_approximations)

    print("\n".join(map(str, result)))
    print("\n")
