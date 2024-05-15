import matplotlib.pyplot as plt
import numpy as np


def y(x):
    return 11.697 + 0.922 * x


x_full_range = np.linspace(0, 100, 1000)

x_start = 40
x_end = 70
x_display_range = np.linspace(x_start, x_end, 400)

y_full_range = y(x_full_range)
y_display_range = y(x_display_range)

points_x = [44, 54, 64]
points_y = [52.043, 62.214, 69]

table_data = {
    (44, 49): 15,
    (44, 59): 15,
    (44, 64): 0,
    (44, 69): 0,
    (54, 49): 0,
    (54, 59): 30,
    (54, 64): 30,
    (54, 69): 0,
    (64, 49): 0,
    (64, 59): 0,
    (64, 64): 0,
    (64, 69): 15
}

x_points = []
y_points = []
for (x, y), count in table_data.items():
    x_points.extend(np.random.uniform(x - 4, x + 4, count))
    y_points.extend(np.random.uniform(y - 4, y + 4, count))

plt.figure(figsize=(10, 6))
plt.plot(x_full_range, y_full_range)
plt.plot(x_display_range, y_display_range, label='y(x) = 11.697 + 0.922x', color='blue')

plt.scatter(points_x, points_y, color='green', zorder=5)
plt.plot(points_x, points_y, color='green', linestyle='--', label='Линия через точки математических ожиданий', zorder=4)

plt.scatter(x_points, y_points, color='red', zorder=5, label='Случайные точки')

plt.xlim(x_start, x_end)
plt.ylim(30, 90)
plt.xticks(np.arange(x_start, x_end + 1, step=5))
plt.yticks(np.arange(30, 91, step=10))

plt.title('График функции линейной регрессии и случайные точки')
plt.xlabel('x')
plt.ylabel('y')
plt.legend()
plt.grid(True)

plt.show()
