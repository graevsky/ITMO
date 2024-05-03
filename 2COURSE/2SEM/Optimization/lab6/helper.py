cities = [[0, 1, 1, 5, 3],
          [1, 0, 3, 1, 5],
          [1, 3, 0, 11, 1],
          [5, 1, 11, 0, 1],
          [3, 5, 1, 1, 0]]

def calculate_distance(sequence):
    total_distance = 0
    for i in range(len(sequence) - 1):
        from_city = sequence[i] - 1
        to_city = sequence[i+1] - 1
        total_distance += cities[from_city][to_city]
    return total_distance

while True:
    input_sequence = input()
    sequence = list(map(int, input_sequence.split('-')))
    distance = calculate_distance(sequence)
    print("Общее расстояние для последовательности", input_sequence, ":", distance)
