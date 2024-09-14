import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from collections import Counter

data = pd.read_csv("diabetes.csv", delimiter=',')


def visualise():
    for column in data.select_dtypes(include=['int64', 'float64']).columns:
        plt.figure(figsize=(12, 10))

        data[column].hist(bins=30, edgecolor='black', alpha=0.7)

        plt.title(f'Distribution of {column}')
        plt.xlabel(column)
        plt.ylabel('Frequency')
        plt.tight_layout()

        legend = (f'Count: {data[column].count()}\n'
                  f'Mean: {data[column].mean():.2f}\n'
                  f'Std: {data[column].std():.2f}\n'
                  f'Min: {data[column].min():.2f}\n'
                  f'25%: {data[column].quantile(0.25):.2f}\n'
                  f'50% (Median): {data[column].median():.2f}\n'
                  f'75%: {data[column].quantile(0.75):.2f}\n'
                  f'Max: {data[column].max():.2f}')

        plt.gcf().text(0.8, 0.5, legend, fontsize=12, verticalalignment='center',
                       bbox=dict(facecolor='white', alpha=0.5))

        plt.show()

def visualise_3d():
    feature_1 = 'Glucose'
    feature_2 = 'BMI'
    feature_3 = 'Age'

    fig = plt.figure(figsize=(10, 7))
    ax = fig.add_subplot(111, projection='3d')

    ax.scatter(data[feature_1], data[feature_2], data[feature_3], c='blue', marker='o')

    ax.set_xlabel(feature_1)
    ax.set_ylabel(feature_2)
    ax.set_zlabel(feature_3)

    plt.show()


empty_vals = data.isnull().sum()
#print(empty_vals)

# no categorical

#scale
for column in data:
    mean = data[column].mean()
    std = data[column].std()
    data[column] = (data[column] - mean) / std




X = data.drop(columns=['Outcome'])
y = data['Outcome']

test_size = 0.2
train_size = 1 - test_size

n_train = int(train_size * len(data))

indices = np.random.permutation(len(data))

train_indices = indices[:n_train]
test_indices = indices[n_train:]


X_train = X.iloc[train_indices].to_numpy()
X_test = X.iloc[test_indices].to_numpy()
y_train = y.iloc[train_indices].to_numpy()
y_test = y.iloc[test_indices].to_numpy()


def distance(x1, x2):
    return np.sqrt(np.sum((x1 - x2) ** 2))

def kNN(X_train, y_train, X_test, k=3):
    y_pred = []
    for x_test in X_test:
        distances = []
        for i in range(len(X_train)):
            dist = distance(X_train[i], x_test)
            distances.append((dist, y_train[i]))
        distances.sort(key=lambda x: x[0])

        neighbors = distances[:k]
        neighbor_labels = [neighbor[1] for neighbor in neighbors]
        most_common = Counter(neighbor_labels).most_common(1)[0][0]
        y_pred.append(most_common)

    return np.array(y_pred)

def confusion_matrix(y_true, y_pred):
    cm = np.zeros((2, 2), dtype=int)
    for true, pred in zip(y_true, y_pred):
        cm[int(true), int(pred)] += 1
    return cm

def accuracy_score(cm):
    TP, TN, FP, FN = cm[1, 1], cm[0, 0], cm[0, 1], cm[1, 0]
    return (TP + TN) / (TP + TN + FP + FN)

def f1_score(cm):
    TP, TN, FP, FN = cm[1, 1], cm[0, 0], cm[0, 1], cm[1, 0]
    precision = TP / (TP + FP) if (TP + FP) != 0 else 0
    recall = TP / (TP + FN) if (TP + FN) != 0 else 0
    return 2 * (precision * recall) / (precision + recall) if (precision + recall) != 0 else 0

def plot_confusion_matrix(cm, accuracy, f1, model_name, k, features=None):
    fig, ax = plt.subplots(figsize=(6, 6))
    ax.matshow(cm, cmap=plt.cm.Blues, alpha=0.6)
    for i in range(cm.shape[0]):
        for j in range(cm.shape[1]):
            ax.text(x=j, y=i, s=cm[i, j], va='center', ha='center', size='xx-large')

    plt.xlabel('Predicted labels', fontsize=14)
    plt.ylabel('True labels', fontsize=14)

    if features is not None:
        features_str = ', '.join(features)
        title = f'{model_name} (k={k})\nFeatures: {features_str}\nAccuracy: {accuracy:.4f}, F1 Score: {f1:.4f}'
    else:
        title = f'{model_name} (k={k})\nAccuracy: {accuracy:.4f}, F1 Score: {f1:.4f}'

    plt.title(title, fontsize=16)
    plt.show()

# Модель 1: случайные признаки
np.random.seed(0) # для повторяемости
random_features = np.random.choice(X.columns, 3, replace=False)
X_train_model_1 = X_train[:, [X.columns.get_loc(col) for col in random_features]]
X_test_model_1 = X_test[:, [X.columns.get_loc(col) for col in random_features]]

# Модель 2: фиксированные признаки
fixed_features = ['Glucose', 'BMI', 'Age']
X_train_model_2 = X_train[:, [X.columns.get_loc(col) for col in fixed_features]]
X_test_model_2 = X_test[:, [X.columns.get_loc(col) for col in fixed_features]]

ks = [3, 5, 10, 15, 30]

for k in ks:
    y_pred_model_1 = kNN(X_train_model_1, y_train, X_test_model_1, k)
    cm_model_1 = confusion_matrix(y_test, y_pred_model_1)
    accuracy_1 = accuracy_score(cm_model_1)
    f1_1 = f1_score(cm_model_1)
    plot_confusion_matrix(cm_model_1, accuracy_1, f1_1, "Random Features", k, features=random_features)

    y_pred_model_2 = kNN(X_train_model_2, y_train, X_test_model_2, k)
    cm_model_2 = confusion_matrix(y_test, y_pred_model_2)
    accuracy_2 = accuracy_score(cm_model_2)
    f1_2 = f1_score(cm_model_2)
    plot_confusion_matrix(cm_model_2, accuracy_2, f1_2, "Fixed Features", k)

