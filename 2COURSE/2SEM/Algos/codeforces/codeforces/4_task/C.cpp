#include <bits/stdc++.h>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

bool median_check(vector<int> a, int k, int m, int n) {

  vector<int> prefix(n + 1, 0);
  for (int i = 0; i < n; i++) {
    if (a[i] >= m) {
      prefix[i + 1] = prefix[i] + 1;
    } else {
      prefix[i + 1] = prefix[i] - 1;
    }
  }

  int min_prefix = 0;
  for (int i = k; i <= n; i++) {

    min_prefix = min(min_prefix, prefix[i - k]);
    if (prefix[i] - min_prefix > 0) {
      return true;
    }
  }
  return false;
}

int main() {
  int n, k;

  cin >> n >> k;

  vector<int> a(n);
  for (int i = 0; i < n; i++) {
    cin >> a[i];
  }

  int l = 1, r = n;

  while (l < r) {
    int m = l + (r - l + 1) / 2;
    if (median_check(a, k, m, n)) {
      l = m;
    } else {
      r = m - 1;
    }
  }
  cout << l;

  return 0;
}
