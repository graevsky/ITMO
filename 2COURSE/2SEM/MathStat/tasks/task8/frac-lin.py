import matplotlib.pyplot as plt
import numpy as np

x_points = np.array([0.5, 1, 1.5, 2, 2.5, 3, 3.5])
y_points = np.array([0.65, 0.7, 0.83, 0.98, 1.12, 1.48, 1.96])


def func(x):
    return x/(0.649+0.526*x)


x_vals = np.linspace(-1, 4, 400)
y_vals = func(x_vals)

plt.figure(figsize=(10, 6))
plt.plot(x_vals, y_vals, label='y=x/(0.649+0.526x)}', color='blue')
plt.scatter(x_points, y_points, color='red', label='Исходные точки')

plt.xlabel('x')
plt.ylabel('y')
plt.title('График функции и точек')
plt.legend()

plt.grid(True)
plt.show()