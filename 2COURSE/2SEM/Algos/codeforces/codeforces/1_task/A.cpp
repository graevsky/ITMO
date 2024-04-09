#include <climits>
#include <cstdio>
#include <ext/rope>
#include <iostream>
#include <map>
#include <set>
#include <stack>
#include <vector>

using namespace std;
using namespace __gnu_cxx;

int main() {

  int n;
  cin >> n;
  cin.ignore();

  for (int i = 0; i < n; i++) {
    int len;
    cin >> len;
    cin.ignore();

    string input;
    getline(cin, input);
    stack<char> s1;
    int count = 0;

    for (char c : input) {
      if (c == '(')
        s1.push(c);
      else if (c == ')') {
        if (!s1.empty() && s1.top() == '(')
          s1.pop();
        else
          count++;
      }
    }

    cout << (count + s1.size()) / 2 << endl;
  }
  return 0;
}