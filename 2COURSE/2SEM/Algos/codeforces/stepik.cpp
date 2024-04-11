#include <iostream>
#include <map>
#include <vector>
#include <algorithm>

using namespace std;

const int MOD = 1000000001;
map<long long, int> comp; // Для сжатия координат
map<long long, int> counter; // Для отслеживания количества каждого элемента
vector<long long> raw_nums; // Числа для маппинга без применения функции f
vector<long long> nums; // Вектор для хранения уникальных чисел после применения f
vector<pair<char, pair<long long, long long>>> queries; // Для хранения запросов
long long lastSum = 0;

// Функция f
long long f(long long x) {
  return (x + lastSum) % MOD;
}

// Дерево Фенвика
class FenwickTree {
private:
  vector<long long> tree;
  int size;

public:
  FenwickTree(int n) : size(n), tree(n + 1, 0) {}

  void update(int idx, long long val) {
    while (idx <= size) {
      tree[idx] += val;
      idx += idx & (-idx);
    }
  }

  long long sum(int idx) {
    long long result = 0;
    while (idx > 0) {
      result += tree[idx];
      idx -= idx & (-idx);
    }
    return result;
  }

  long long rangeSum(int l, int r) {
    return sum(r) - sum(l - 1);
  }
};

int main() {
  int n;
  cin >> n;

  for (int i = 0; i < n; ++i) {
    char type;
    cin >> type;
    if (type == '+' || type == '-' || type == '?') {
      long long x;
      cin >> x;
      queries.push_back({type, {x, 0}});
      raw_nums.push_back(x);
    } else if (type == 's') {
      long long l, r;
      cin >> l >> r;
      queries.push_back({type, {l, r}});
      raw_nums.push_back(l);
      raw_nums.push_back(r);
    }
  }

  for (auto& num : raw_nums) {
    nums.push_back(f(num));
  }

  sort(nums.begin(), nums.end());
  nums.erase(unique(nums.begin(), nums.end()), nums.end());
  for (int i = 0; i < nums.size(); ++i) {
    comp[nums[i]] = i + 1;
  }

  FenwickTree ft(nums.size());

  for (auto &q : queries) {
    char type = q.first;
    long long x = q.second.first, y = q.second.second;

    if (type == 's') {
      x = f(q.second.first);
      y = f(q.second.second);
    } else {
      x = f(x);
    }

    if (type == '+') {
      if (counter[x]++ == 0) {
        ft.update(comp[x], 1);
      }
    } else if (type == '-') {
      if (--counter[x] == 0) {
        ft.update(comp[x], -1);
      }
    } else if (type == '?') {
      cout << (counter[x] > 0 ? "Found\n" : "Not found\n");
    } else if (type == 's') {
      lastSum = ft.rangeSum(comp[x], comp[y]);
      cout << lastSum << endl;
    }
  }

  return 0;
}
