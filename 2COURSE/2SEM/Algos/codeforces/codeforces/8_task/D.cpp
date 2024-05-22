// Ярослав и время
// Ограничение по времени на тест - 2 секунды
// Ограничение по памяти на тест - 256 мегабайт
// Ввод стандартный ввод
// Вывод стандартный вывод
//
// Ярослав играет в игру «Время». В игре у него есть таймер, на котором
// изображено время, которое ему осталось жить. Как только таймер показывает 0,
// игровой персонаж Ярослава умирает и игра заканчивается. Так же в игре
// существует n часовых станций, станция номер i находится в точке (xi,yi)
// плоскости. Зайдя на станцию номер i, игрок увеличивает текущее время на своем
// таймере на ai. Станции одноразовые, то есть если игрок второй раз зайдет на
// какую-нибудь станцию, то время на его таймере не увеличится.
//
// На перемещение между станциями игрок тратит d·dist единиц времени, где dist —
// расстояние, пройденное игроком, а d — некоторая константа. Расстояние между
// станциями i и j определяется как |xi-xj|+|yi-yj|.
//
// Изначально игрок находится на станции номер 1, и у игрока осталось строго
// больше нуля и меньше одной единицы времени. На станции номер 1 за единицу
// денег можно увеличить время на таймере на единицу времени (можно покупать
// только целое количество единиц времени). Сейчас Ярослава интересует вопрос:
// сколько единиц денег нужно ему, чтобы попасть на станцию номер n? Помогите
// Ярославу. Считайте, что время покупки и увеличения времени на таймере
// пренебрежимо мало.
//
// Входные данные
// В первой строке содержатся целые числа n и d (3≤n≤100,10^3≤d≤10^5) —
// количество станций и константа из условия.
// Во второй строке заданы n-2 целых числа:
// a2,a3,...,an-1(1≤ai≤10^3). В следующих n строках содержатся координаты
// станций. В i- той из них записаны два целых чисел xi, yi(-100≤xi,yi≤100).
//
// Гарантируется, что никакие две станции не находятся в одной точке.
//
// Выходные данные В единственную строку выведите целое число — ответ на задачу.
//
// Примеры
//
// 1
// Входные данные
// 3 1000 1000 0 0 0 1 0 3
// Выходные данные
// 2000
//
// 2
// Входные данные
// 3 1000 1000 1 0 1 1 1 2
// Выходные данные
// 1000

#include <algorithm>
#include <cmath>
#include <iostream>
#include <queue>
#include <vector>

using namespace std;

struct State {
  int station;
  long long time;
  bool operator>(const State &second) const { return time > second.time; }
};

struct Coordinate {
  int x, y;
};

long long delta_distances(Coordinate &first, Coordinate &second) {
  return abs(first.x - second.x) + abs(first.y - second.y);
}

int main() {
  int n, d;
  cin >> n >> d;

  vector<int> time_bonuses(n + 1, 0);
  for (int i = 2; i < n; i++) {
    cin >> time_bonuses[i];
  }

  vector<Coordinate> stations(n + 1);
  for (int i = 1; i <= n; ++i) {
    cin >> stations[i].x >> stations[i].y;
  }

  priority_queue<State, vector<State>, greater<>> pq;
  vector<long long> distances(n + 1, LLONG_MAX);
  distances[1] = 0;

  pq.push({1, 0});

  while (!pq.empty()) {
    State cur = pq.top();
    pq.pop();

    int cur_station = cur.station;
    long long cur_time = cur.time;

    if (cur_time > distances[cur_station]) {
      continue;
    }

    for (int i = 1; i <= n; i++) {
      if (i != cur_station) {
        long long time =
            d * delta_distances(stations[cur_station], stations[i]);
        long long new_time = cur_time + time;
        if (i != n) {
          new_time -= time_bonuses[i];
        }

        if (new_time < distances[i]) {
          distances[i] = new_time;
          pq.push({i, new_time});
        }
      }
    }
  }
  long long res_time = distances[n];
  long long result = max(0LL, res_time - 1) + 1;
  cout << result << endl;

  return 0;
}