#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include<bits/stdc++.h>

using namespace std;


void get_mn(int a, map<int, int> &mn) {
    for (int i = 2; i <= sqrt(a); i++) {
        while (a % i == 0) {
            mn[i]++;
            a /= i;
        }
    }
    if (a != 1) {
        mn[a]++;
    }
}

int main() {
    int t;
    cin >> t;


    for (int i = 0; i < t; i++) {
        int n;
        cin >> n;
        vector<int> inp;
        map<int, int> mn;
        for (int j = 0; j < n; j++) {
            int a;
            cin >> a;
            inp.push_back(a);
            get_mn(a, mn);
        }

        bool flag = true;

        for (auto &iter: mn) {
            if (iter.second % n != 0) {
                flag = false;
                break;
            }
        }

        if (flag) {
            cout << "YES" << endl;
        } else {
            cout << "NO" << endl;
        }
    }
    return 0;
}
