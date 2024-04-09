#include <bits/stdc++.h>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

int main() {
  long long n;
  cin >> n;

  long long I; // размер, в байтах
  cin >> I;

  vector<long long> a(n); //  файл

  for (long long i = 0; i < n; i++) {
    cin >> a[i];
  }

  sort(a.begin(), a.end());

  long long avail_space = 8 * I / n; // доступное пространство в битах
  long long K = 1LL << avail_space;

  if (avail_space >= 31) {
    K = n + 1;
  }

  long long l = 0, r = 0, best = 0;
  map<long long, long long> unique_freqs;
  while (r < n) {
    unique_freqs[a[r]]++;

    while (unique_freqs.size() > K) {
      unique_freqs[a[l]]--;
      if (unique_freqs[a[l]] == 0) {
        unique_freqs.erase(a[l]);
      }
      l++;
    }

    best = max(best, r - l + 1);
    r++;
  }

  cout << n - best << endl;

  return 0;
}
