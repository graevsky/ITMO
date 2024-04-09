#include <bits/stdc++.h>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

set<string> prefixFind(const string &s) {
  set<string> prefixes;
  for (size_t i = 1; i <= s.length(); i++) {
    prefixes.insert(s.substr(0, i));
  }
  return prefixes;
}

int main() {
  string name, surname;
  cin >> name >> surname;

  set<string> name_p = prefixFind(name);
  set<string> surname_p = prefixFind(surname);

  set<string> logins;

  for (auto &namePref : name_p) {
    for (auto &surnamePref : surname_p) {
      logins.insert(namePref + surnamePref);
    }
  }

  cout << *logins.begin();

  return 0;
}
