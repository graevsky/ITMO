#include <iostream>
#include <vector>
#include <queue>
#include <map>
#include <climits>

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

  priority_queue<pair<long long, int>, vector<pair<long long, int>>, greater<>> pq;
  pq.emplace(0, 1);

  while (!pq.empty()) {
    auto [d, u] = pq.top();
    pq.pop();

    if (d > dist[u]) continue;

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
