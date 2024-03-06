#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include<bits/stdc++.h>

using namespace std;


int main() {
    char inp[100];
    int add = 1, sub = 0;

    char sign, s;
    int i = 0;
    while (cin >> s){
        cin >> sign;

        //cout << endl << sign << endl;
        if (sign == '=') break;
        else if (sign == '+') add++;
        else if(sign == '-') sub++;

        inp[i] = sign;
        i++;
    }

    int n;
    cin >> n;

    int min = add - n*sub;
    int max = n*add - sub;

    if(n < min || max < n){
        cout << "Impossible";
        return 0;
    }else{
        cout << "Possible" << endl;
    }

    vector<pair<char,int>> result;
    int sum = 0;
    for (int j = 0; j < i; j++ ){
        int sign;
        if (j>0 && inp[j-1] == '-'){
            sign = -1;
        }else{
            sign = 1;
        }

        if(sign == 1){
            add--;
        }else{
            sub--;
        }


        for (int x = 1; x <= n; x++) {
            int add_left = sum + x*sign + n*add - sub;
            int sub_left = sum + sign*x + add - n*sub;
            if(sub_left <= n && n <= add_left){
                result.emplace_back(inp[j], x);
                sum += x * sign;
                break;
            }
        }

    }
    for(auto& x : result){
        cout << x.second << " " << x.first << " ";
    }
    cout << abs(sum - n) << " = " << n;


    return 0;
}
