
import math
import os
import random
import re
import sys


class Solution:
    isSolutionExists = True
    errorMessage = "The system has no roots of equations or has an infinite set of them."

    #
    # Complete the 'solveByCholeskyDecomposition' function below.
    #
    # The function is expected to return a DOUBLE_ARRAY.
    # The function accepts following parameters:
    #  1. INTEGER n
    #  2. 2D_DOUBLE_ARRAY matrix
    #
    def solveByCholeskyDecomposition(n, matrix):
        L = [[0.0] * n for _ in range(n)]

        for i in range(n):
            for j in range(i+1):
                sum = 0
                for k in range(j):
                    sum+= L[i][k]*L[j][k]
                if (i == j):
                    if (matrix[i][i] - sum <= 0):
                        Solution.isSolutionExists = False
                        return
                    L[i][j] = math.sqrt(matrix[i][i]-sum)
                else:
                    if (L[j][j] == 0):
                        Solution.isSolutionExists = False
                        return
                    L[i][j] = (matrix[i][j]-sum) / L[j][j]
        #print("L: \n")
        #print(L)

        y = [0 for _ in range(n)]
        for i in range (n):
            sum = 0
            for j in range(i):
                sum += L[i][j] * y[j]
            y[i] = (matrix[i][-1]-sum) /L[i][i]

        x = [0 for _ in range(n)]
        for i in range(n-1,-1,-1):
            sum = 0
            for j in range(i+1,n):
                sum += L[j][i] *x[j]
            x[i] = (y[i]-sum)/L[i][i]

        result = x+y
        return result


if __name__ == '__main__':
    n = int(input().strip())

    matrix_rows = n
    matrix_columns = n + 1

    matrix = []

    for _ in range(matrix_rows):
        matrix.append(list(map(float, input().rstrip().split())))

    result = Solution.solveByCholeskyDecomposition(n, matrix)
    if Solution.isSolutionExists:
        print('\n'.join(map(str, result)))
    else:
        print(f"{Solution.errorMessage}")
    print("")