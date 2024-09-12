import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

data = pd.read_csv("Student_Performance.csv", delimiter=',')


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

numerical_columns = data_encoded.select_dtypes(include=[np.number]).columns

for column in numerical_columns:
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


def linear_regression(X_train, y_train, epochs=1000, learning_rate=0.01):
    X_train = X_train.astype(np.float64)
    y_train = y_train.astype(np.float64)
    n_samples, n_features = X_train.shape

    weights = np.zeros(n_features)
    bias = 0

    history = []

    for epoch in range(epochs):
        y_predicted = np.dot(X_train, weights) + bias

        error = y_predicted - y_train
        mse = (1 / n_samples) * np.sum(error ** 2)
        history.append(mse)

        dw = (1 / n_samples) * np.dot(X_train.T, error)
        db = (1 / n_samples) * np.sum(error)

        weights -= learning_rate * dw
        bias -= learning_rate * db

        if epoch % 100 == 0:
            print(f'Epoch {epoch}, MSE: {mse:.4f}')

    return weights, bias, history


def r_squared(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred) ** 2)
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)
    r2 = 1 - (ss_res / ss_tot)
    return r2

def train_and_evaluate(X_train, X_test, y_train, y_test):
    weights, bias, _ = linear_regression(X_train, y_train)

    y_train_pred = np.dot(X_train, weights) + bias
    y_test_pred = np.dot(X_test, weights) + bias

    r2_train = r_squared(y_train, y_train_pred)
    r2_test = r_squared(y_test, y_test_pred)

    return r2_train, r2_test


X_train_model_1 = X_train[['Hours Studied', 'Previous Scores', 'Sample Question Papers Practiced']]
X_test_model_1 = X_test[['Hours Studied', 'Previous Scores', 'Sample Question Papers Practiced']]

r2_train_1, r2_test_1 = train_and_evaluate(X_train_model_1.to_numpy(), X_test_model_1.to_numpy(), y_train.to_numpy(), y_test.to_numpy())
print(f'Model 1 - R² Train: {r2_train_1:.4f}, R² Test: {r2_test_1:.4f}')


X_train_model_2 = X_train[['Sleep Hours', 'Extracurricular Activities_Yes']].copy()
X_test_model_2 = X_test[['Sleep Hours', 'Extracurricular Activities_Yes']].copy()
X_train_model_2['Previous_Sleep_Interaction'] = X_train['Previous Scores'] * X_train['Sleep Hours']
X_test_model_2['Previous_Sleep_Interaction'] = X_test['Previous Scores'] * X_test['Sleep Hours']

r2_train_2, r2_test_2 = train_and_evaluate(X_train_model_2.to_numpy(), X_test_model_2.to_numpy(), y_train.to_numpy(), y_test.to_numpy())
print(f'Model 2 - R² Train: {r2_train_2:.4f}, R² Test: {r2_test_2:.4f}')


X_train_model_3 = X_train
X_test_model_3 = X_test

r2_train_3, r2_test_3 = train_and_evaluate(X_train_model_3.to_numpy(), X_test_model_3.to_numpy(), y_train.to_numpy(), y_test.to_numpy())
print(f'Model 3 - R² Train: {r2_train_3:.4f}, R² Test: {r2_test_3:.4f}')

print(f"Модель 1 (Академические признаки): R² Train = {r2_train_1:.4f}, R² Test = {r2_test_1:.4f}")
print(f"Модель 2 (Неакадемические признаки + синтетика): R² Train = {r2_train_2:.4f}, R² Test = {r2_test_2:.4f}")
print(f"Модель 3 (Все признаки): R² Train = {r2_train_3:.4f}, R² Test = {r2_test_3:.4f}")

