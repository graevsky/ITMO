#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include<bits/stdc++.h>

using namespace std;

struct Dot{
    int i;
    int x;
    int w;
};


int main() {

    int t;
    cin >> t;
    for(int i = 0;i < t;i++){


        int n, m;
        cin >> n >> m;

        vector<Dot> dots(m);

        for(int j = 0; j < m; j++){
            int x, w;
            cin >> x >> w;
            dots[j].x =x;
            dots[j].w = w;
            dots[j].i = j + 1;
        }

        sort(dots.begin(),dots.end(),[](const Dot& a, const Dot& b) {
            return a.w < b.w;
        });

        vector<Dot> bestDots(dots.begin(), dots.begin() + 2 * n);

        sort(bestDots.begin(),bestDots.end(),[](const Dot& a, const Dot& b) {
            return a.x < b.x;
        });

        int weight = 0 ;

        for(auto& dot : bestDots){
            weight+=dot.w;
        }

        cout << weight << endl;

        for(int j = 0; j < n;j++){
            cout << bestDots[j].i << " " << bestDots[2*n-j-1].i << endl;
        }

        cout << endl;


    }


    return 0;
}
