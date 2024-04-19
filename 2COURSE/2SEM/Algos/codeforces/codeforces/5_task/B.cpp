// Победитель популярной в Берляндии карточной игры «Берлоггинг» определяется по
// следующим правилам.

//  Если на момент окончания игры существует только один игрок, набравший
//  максимальное количество очков, то он и становится победителем. Ситуация
//  осложняется, если таких игроков несколько. Каждый кон игры некоторый игрок
//  выигрывает или проигрывает некоторое количество очков. В записи о ходе игры
//  это обозначается строкой «namescore», где name это имя игрока, а score целое
//  число обозначающее количество заработанных очков данным игроком. Если score
//  — отрицательное число, это обозначает, что игрок проиграл в этом коне. Так
//  вот, если на конец игры несколько игроков набрали максимум очков(пусть это
//  число равно m), то выигрывает тот из них, кто первым набрал как минимум
//  m очков. Перед началом игры у каждого игрока 0 очков. Гарантируется, что на
//  момент окончания игры хотя бы у одного игрока положительное число очков.

// Входные данные
// В первой строке записано целое число n (1 <= n <= 1000), n —
// количество конов сыгранной игры. Далее в n строках идут описания конов, в
// формате «name score» в хронологическом порядке, где name это строка из
// строчных букв латинского алфавита длины от 1 до 32, а score это целое число
// от -1000 до 1000 включительно.

// Выходные данные
// Выведите имя победителя игры «Берлоггинг».

#include <bits/stdc++.h>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

struct Player {
  string name;
  int points;
};

int main() {
  int n;
  cin >> n;
  unordered_map<string, int> players;
  unordered_map<string, int> winners;

  string name;
  int score;
  int max_score = INT_MIN;
  vector<pair<string, int>> cons;
  string winner;

  for (int i = 0; i < n; ++i) {
    cin >> name >> score;
    cons.emplace_back(name, score);
    players[name] += score;
  }

  for (auto &player : players) {
    if (player.second > max_score) {
      max_score = player.second;
    }
  }

  for (auto &player : players) {
    if (player.second == max_score) {
      winners[player.first] = 0;
    }
  }

  for (auto &pair : cons) {
    if (winners.find(pair.first) == winners.end())
      continue;

    winners[pair.first] += pair.second;
    if (winners[pair.first] >= max_score) {
      winner = pair.first;
      break;
    }
  }

  cout << winner << endl;
  return 0;
}
