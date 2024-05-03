import random

cities = [
    [0, 1, 1, 5, 3],
    [1, 0, 3, 1, 5],
    [1, 3, 0, 11, 1],
    [5, 1, 11, 0, 1],
    [3, 5, 1, 1, 0]
]

def calculate_cost(path):
    """ Расчет стоимости пути """
    cost = 0
    for i in range(len(path)):
        cost += cities[path[i] - 1][path[(i + 1) % len(path)] - 1]
    return cost

def create_initial_population(size, num_cities):
    """ Создание начальной популяции """
    population = []
    for _ in range(size):
        path = list(range(1, num_cities + 1))
        random.shuffle(path)
        population.append((path, calculate_cost(path), "Initial"))
    return population

def crossover(parent1, parent2):
    """ Оператор скрещивания """
    n = len(parent1[0])
    point1, point2 = sorted(random.sample(range(1, n - 1), 2))
    middle1 = parent1[0][point1:point2]
    middle2 = parent2[0][point1:point2]

    def make_child(middle, parent):
        child = [-1] * n
        child[point1:point2] = middle
        added = set(middle)
        fill_idx = point2
        for city in parent[0]:
            if city not in added:
                while child[fill_idx] != -1:
                    fill_idx = (fill_idx + 1) % n
                child[fill_idx] = city
                added.add(city)
        return child

    child1 = make_child(middle2, parent1)
    child2 = make_child(middle1, parent2)
    return (child1, calculate_cost(child1), f"Child of {parent1[0]}, {parent2[0]}"), \
           (child2, calculate_cost(child2), f"Child of {parent1[0]}, {parent2[0]}")

def mutate(path, mutation_rate=0.01):
    """ Оператор мутации """
    if random.random() < mutation_rate:
        idx1, idx2 = random.sample(range(len(path[0])), 2)
        path[0][idx1], path[0][idx2] = path[0][idx2], path[0][idx1]
        path = (path[0], calculate_cost(path[0]), f"Mutated from {path[0]}")
    return path

def genetic_algorithm(cities, population_size=4, optimal_cost=5):
    num_cities = len(cities)
    population = create_initial_population(population_size, num_cities)
    print("Initial Population:")
    for path in population:
        print(path[0], path[1], path[2])

    generation = 0
    while True:
        print("\nGeneration", generation)
        new_population = []
        costs = []

        for i in range(0, len(population), 2):
            parent1, parent2 = population[i], population[i + 1]
            child1, child2 = crossover(parent1, parent2)
            child1 = mutate(child1)
            child2 = mutate(child2)
            new_population.extend([child1, child2])
            costs.append(child1[1])
            costs.append(child2[1])

        # Выводим детей и их стоимости и родителей
        print("Children and Costs:")
        for child in new_population:
            print(child[0], child[1], child[2])

        # Оператор редукции
        all_individuals = population + new_population
        all_individuals = sorted(all_individuals, key=lambda x: x[1])[:population_size]

        # Вывод текущей популяции
        print("Updated Population:")
        for ind in all_individuals:
            print(ind[0], ind[1], ind[2])

        population = all_individuals
        if any(ind[1] == optimal_cost for ind in population):
            break

        generation += 1

# Запуск генетического алгоритма
genetic_algorithm(cities)
