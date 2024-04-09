#include <bits/stdc++.h>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

int main() {
  int n;
  cin >> n;         // n - кол-во учеников
  vector<int> a(n); // a - целевой рост

  for (int i = 0; i < n; i++) {
    cin >> a[i];
  }

  vector<int> b(n); // начальный порядок

  for (int i = 0; i < n; i++) {
    cin >> b[i];
  }

  vector<pair<int, int>> swaps;

  for (int i = 0; i < n; i++) {
    if (a[i] == b[i])
      continue;

    int position = find(b.begin() + i, b.end(), a[i]) - b.begin();

    for (int j = position; j > i; j--) {
      swap(b[j], b[j - 1]);
      swaps.emplace_back(j, j + 1);
    }
  }

  cout << swaps.size() << endl;
  for (auto &swap : swaps) {
    cout << swap.first << " " << swap.second << endl;
  }

  return 0;
}
