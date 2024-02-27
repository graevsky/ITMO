def interpolate_by_lagrange(x_axis, y_axis, x):
    result = 0

    for i in range(len(x_axis)):
        poly_i = 1

        for j in range(len(x_axis)):
            if i != j:
                poly_i *= (x-x_axis[j])/(x_axis[i]-x_axis[j])

        result += poly_i * y_axis[i]
    return result

if __name__ == '__main__':
    axis_count = int(input().strip())

    x_axis = list(map(float, input().rstrip().split()))

    y_axis = list(map(float, input().rstrip().split()))

    x = float(input().strip())

    result = interpolate_by_lagrange(x_axis, y_axis, x)

    print(str(result) + '\n')
