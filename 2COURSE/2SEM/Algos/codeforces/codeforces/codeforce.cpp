#include <vector>
#include <algorithm>
#include <iostream>
#include<bits/stdc++.h>

using namespace std;

std::vector<double> calculateMedians(const std::vector<int>& arr, int k) {
    std::vector<double> medians;
    if (k <= 0 || arr.size() < k) return medians; // Возврат пустого вектора, если k невалидно или массив слишком мал

    for (size_t start = 0; start <= arr.size() - k; ++start) {
        for (size_t end = start + k; end <= arr.size(); ++end) {
            std::vector<int> subArray(arr.begin() + start, arr.begin() + end);
            std::sort(subArray.begin(), subArray.end());
            int n = subArray.size();
            double median = n % 2 == 0 ? (subArray[(n/2) - 1] + subArray[n/2]) / 2.0 : subArray[n/2];
            int med = floor(median);
            medians.push_back(med);
        }
    }
    return medians;
}

int main() {
    std::vector<int> arr = {1,2,3,4}; // Пример массива
    int k = 2; // Минимальная длина подмассива
    std::vector<double> medians = calculateMedians(arr, k);

    std::cout << "Medians of all subarrays of length at least " << k << ": ";
    for (double median : medians) {
        std::cout << median << " ";
    }
    std::cout << std::endl;

    return 0;
}
