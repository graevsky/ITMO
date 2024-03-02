#include <iostream>
#include <cmath>

using namespace std;

bool checkNine(long long a){
    while (a){
        long long temp = a % 10;
        if(temp != 9){
            return false;
        }
        a /= 10;
    }
    return true;
}

long long numLen(long long a){
    long long digits = 0;
    while (a){
        a /= 10;
        digits++;
    }
    return digits;
}

int main() {
    long long n;
    cin >> n;

    long long sum = n + (n - 1);
    bool flag = checkNine(sum);


    if (sum < 9){
        cout << n*(n-1)/2;
    } else if(flag){
        cout << 1 << endl;
    }else{
        long long result = 0;

        long long digits = numLen(sum)-1;
        long long num = pow(10,digits)-1;

        while(num <= sum){
            if (n >= num){
                result += num/2;
            }else{
                result+= (n-num /2);
            }
            num += pow(10, digits);
        }
        cout << result << endl;
    }
    return 0;






}