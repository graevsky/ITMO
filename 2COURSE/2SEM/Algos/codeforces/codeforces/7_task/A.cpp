// Алгоритм Дейкстры?

// Задан неориентированный взвешенный граф,
//  вершины которого пронумерованы от 1 до
//  n. Ваша задача найти кратчайший путь из вершины 1 в вершину n.

// Входные данные
//  В первой строке содержатся целые числа n и
//  m(2≤n≤10^5,0≤m≤10^5),где n — количество вершин, а m — количество ребер в
//  графе. Далее в m строках содержатся сами ребра, по одному в строке. Каждое
//  ребро задается тремя числами a_i, b_i, w_i(1≤a_i,b_i≤_n,1≤w_i≤10^6), где
//  a_i, b_i — это концы ребра, а w_i — его длина. Граф может содержать кратные
//  ребра и петли.

// Выходные данные
//  Выведите число - 1 если пути нет или сам кратчайший путь, если он
//  существует.

// Примеры
//
//  1
//  Входные данные
//  5 6
//  1 2 2
//  2 5 5
//  2 3 4
//  1 4 1
//  4 3 3
//  3 5 1
//
//  Выходные данные
//  4 3 5
//
//  2
//  Входные данные
//  5 6
//  1 2 2
//  2 5 5
//  2 3 4
//  1 4 1
//  4 3 3
//  3 5 1
//
//  Выходные данные
//  1 4 3 5
#include <climits>
#include <iostream>
#include <map>
#include <queue>
#include <vector>
using namespace std;

const int MAX_N = 100005;
const long long INFTY = LLONG_MAX;

vector<int> graph[MAX_N];
map<pair<int, int>, int> cost;
long long dist[MAX_N];
int parent[MAX_N];

void print_path(int n) {
  if (n == 1) {
    cout << 1 << " ";
    return;
  }
  print_path(parent[n]);
  cout << n << " ";
}

int main() {
  ios::sync_with_stdio(false);
  cin.tie(0);

  int n, m;
  cin >> n >> m;

  for (int i = 0; i < m; ++i) {
    int u, v, w;
    cin >> u >> v >> w;
    graph[u].push_back(v);
    graph[v].push_back(u);
    cost[{u, v}] = w;
    cost[{v, u}] = w;
  }

  for (int i = 1; i <= n; ++i) {
    dist[i] = INFTY;
  }
  dist[1] = 0;

  priority_queue<pair<long long, int>, vector<pair<long long, int>>, greater<>>
      pq;
  pq.emplace(0, 1);

  while (!pq.empty()) {
    auto [d, u] = pq.top();
    pq.pop();

    if (d > dist[u])
      continue;

    for (int v : graph[u]) {
      if (dist[u] + cost[{u, v}] < dist[v]) {
        dist[v] = dist[u] + cost[{u, v}];
        parent[v] = u;
        pq.emplace(dist[v], v);
      }
    }
  }

  if (dist[n] == INFTY) {
    cout << -1 << endl;
  } else {
    print_path(n);
    cout << endl;
  }

  return 0;
}
