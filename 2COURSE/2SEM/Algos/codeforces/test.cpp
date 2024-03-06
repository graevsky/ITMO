#include <iostream>
#include <string>

std::string ropeOperation(std::string& s, int i, int j, int k) {
    std::string cut = s.substr(i, j - i + 1); // Вырезаем подстроку
    s.erase(i, j - i + 1); // Удаляем вырезанную часть из исходной строки
    if (k == 0) {
        s = cut + s; // Вставляем вырезанное в начало, если k == 0
    } else {
        s.insert(k, cut); // Вставляем вырезанное после k-го символа
    }
    return s;
}

int mainasd() {
    std::string s;
    std::cin >> s;
    int q;
    std::cin >> q;
    while (q--) {
        int i, j, k;
        std::cin >> i >> j >> k;
        ropeOperation(s, i, j, k == 0 ? 0 : k);
    }
    std::cout << s << std::endl;
    return 0;
}
