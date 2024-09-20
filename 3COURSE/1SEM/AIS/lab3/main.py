import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


data = pd.read_csv(r"C:\Users\graev\OneDrive\Рабочий стол\ITMO\3COURSE\1SEM\AIS\lab3\Student_Performance.csv", delimiter=',')


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

    for column in data.select_dtypes(include=['object']).columns:
        plt.figure(figsize=(12, 10))

        data[column].value_counts().plot(kind='bar', edgecolor='black', alpha=0.7)

        plt.title(f'Distribution of {column}')
        plt.xlabel(column)
        plt.ylabel('Count')
        plt.tight_layout()

        mode_value = data[column].mode()[0]
        value_counts = data[column].value_counts()
        total_count = data[column].count()

        legend = (f'Total Count: {total_count}\n'
                  f'Most Frequent (Mode): {mode_value}\n'
                  f'Frequency of Mode: {value_counts[mode_value]} ({(value_counts[mode_value] / total_count) * 100:.2f}%)\n'
                  f'Unique Values: {dict(value_counts)}')

        plt.gcf().text(0.6, 0.5, legend, fontsize=12, verticalalignment='center',
                       bbox=dict(facecolor='white', alpha=0.5))

        plt.show()


empty_vals = data.isnull().sum()
# print(empty_vals)


categorical_columns = data.select_dtypes(include=['object']).columns
data_encoded = pd.get_dummies(data, columns=categorical_columns)

for column in data_encoded:
    mean = data_encoded[column].mean()
    std = data_encoded[column].std()
    data_encoded[column] = (data_encoded[column] - mean) / std

X = data_encoded.drop(columns=['Performance Index'])
y = data_encoded['Performance Index']

test_size = 0.2
train_size = 1 - test_size
n_train = int(train_size * len(data_encoded))

indices = np.random.permutation(len(data_encoded))
train_indices = indices[:n_train]
test_indices = indices[n_train:]

X_train = X.iloc[train_indices]
X_test = X.iloc[test_indices]
y_train = y.iloc[train_indices]
y_test = y.iloc[test_indices]

def least_squares(X, y):
    X_b = np.c_[np.ones((X.shape[0], 1)), X]
    theta_best = np.linalg.inv(X_b.T.dot(X_b)).dot(X_b.T).dot(y)
    return theta_best

def predict(X, theta):
    X_b = np.c_[np.ones((X.shape[0], 1)), X]
    return X_b.dot(theta)

def r_squared(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred) ** 2)
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)
    return 1 - (ss_res / ss_tot)

def train_and_evaluate(X_train, X_test, y_train, y_test):
    theta = least_squares(X_train.to_numpy(), y_train.to_numpy())

    y_train_pred = predict(X_train.to_numpy(), theta)
    y_test_pred = predict(X_test.to_numpy(), theta)

    r2_train = r_squared(y_train.to_numpy(), y_train_pred)
    r2_test = r_squared(y_test.to_numpy(), y_test_pred)

    return r2_train, r2_test, theta

# Модель 1: Только академические признаки
X_train_model_1 = X_train[['Hours Studied', 'Previous Scores', 'Sample Question Papers Practiced']]
X_test_model_1 = X_test[['Hours Studied', 'Previous Scores', 'Sample Question Papers Practiced']]

r2_train_1, r2_test_1, theta_1 = train_and_evaluate(X_train_model_1, X_test_model_1, y_train, y_test)
print(f'Model 1 - R² Train: {r2_train_1:.4f}, R² Test: {r2_test_1:.4f}')

# Модель 2: Неакадемические признаки + синтетический признак
X_train_model_2 = X_train[['Sleep Hours', 'Extracurricular Activities_Yes']].copy()
X_test_model_2 = X_test[['Sleep Hours', 'Extracurricular Activities_Yes']].copy()
X_train_model_2['Previous_Sleep_Interaction'] = X_train['Previous Scores'] * X_train['Sleep Hours']
X_test_model_2['Previous_Sleep_Interaction'] = X_test['Previous Scores'] * X_test['Sleep Hours']

r2_train_2, r2_test_2, theta_2 = train_and_evaluate(X_train_model_2, X_test_model_2, y_train, y_test)
print(f'Model 2 - R² Train: {r2_train_2:.4f}, R² Test: {r2_test_2:.4f}')

# Модель 3: Все признаки
X_train_model_3 = X_train
X_test_model_3 = X_test

r2_train_3, r2_test_3, theta_3 = train_and_evaluate(X_train_model_3, X_test_model_3, y_train, y_test)
print(f'Model 3 - R² Train: {r2_train_3:.4f}, R² Test: {r2_test_3:.4f}')

# Вывод результатов
print(f"Модель 1 (Академические признаки): R² Train = {r2_train_1:.4f}, R² Test = {r2_test_1:.4f}")
print(f"Модель 2 (Неакадемические признаки + синтетика): R² Train = {r2_train_2:.4f}, R² Test = {r2_test_2:.4f}")
print(f"Модель 3 (Все признаки): R² Train = {r2_train_3:.4f}, R² Test = {r2_test_3:.4f}")