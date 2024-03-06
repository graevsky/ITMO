#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Table {
    int parent;
    int rank;
    int size;
};

int find(Table tables[], int i) {
    // Нахождение представителя множества с путём сжатия
    if (i != tables[i].parent) {
        tables[i].parent = find(tables, tables[i].parent);
    }
    return tables[i].parent;
}

void unionTables(Table tables[], int destination, int source, int& maxTableSize) {
    int realDestination = find(tables, destination);
    int realSource = find(tables, source);
    if (realDestination != realSource) {
        // Объединение множеств с учётом ранга
        if (tables[realDestination].rank > tables[realSource].rank) {
            tables[realSource].parent = realDestination;
            tables[realDestination].size += tables[realSource].size;
            maxTableSize = max(maxTableSize, tables[realDestination].size);
        } else {
            tables[realDestination].parent = realSource;
            tables[realSource].size += tables[realDestination].size;
            if (tables[realDestination].rank == tables[realSource].rank) {
                tables[realSource].rank++;
            }
            maxTableSize = max(maxTableSize, tables[realSource].size);
        }
    }
}

int aboba() {
    int n, m;
    cin >> n >> m;

    Table tables[n+1];
    int maxTableSize = 0;

    for (int i = 1; i <= n; i++) {
        cin >> tables[i].size;
        tables[i].parent = i;
        tables[i].rank = 0;
        maxTableSize = max(maxTableSize, tables[i].size);
    }

    for (int i = 0; i < m; i++) {
        int destination, source;
        cin >> destination >> source;
        unionTables(tables, destination, source, maxTableSize);
        cout << maxTableSize << endl;
    }

    return 0;
}
