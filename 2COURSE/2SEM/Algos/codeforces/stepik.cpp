#include <iostream>
#include <algorithm>
using namespace std;

struct Node {
    int key, height;
    long long sum;
    Node *left, *right;

    Node(int k) : key(k), height(1), sum(k), left(nullptr), right(nullptr) {}
};

int height(Node* node) {
    return node ? node->height : 0;
}

long long sum(Node* node) {
    return node ? node->sum : 0;
}

void updateNode(Node* &node) {
    if (node) {
        node->height = max(height(node->left), height(node->right)) + 1;
        node->sum = node->key + sum(node->left) + sum(node->right);
    }
}

Node* rotateRight(Node* y) {
    Node* x = y->left;
    Node* T2 = x->right;
    x->right = y;
    y->left = T2;
    updateNode(y);
    updateNode(x);
    return x;
}

Node* rotateLeft(Node* x) {
    Node* y = x->right;
    Node* T2 = y->left;
    y->left = x;
    x->right = T2;
    updateNode(x);
    updateNode(y);
    return y;
}

int getBalance(Node* node) {
    if (!node) return 0;
    return height(node->left) - height(node->right);
}

Node* insert(Node* node, int key) {
    if (!node) return new Node(key);
    if (key < node->key) {
        node->left = insert(node->left, key);
    } else if (key > node->key) {
        node->right = insert(node->right, key);
    } else {
        return node;
    }

    updateNode(node);

    int balance = getBalance(node);
    if (balance > 1 && key < node->left->key) {
        return rotateRight(node);
    }
    if (balance < -1 && key > node->right->key) {
        return rotateLeft(node);
    }
    if (balance > 1 && key > node->left->key) {
        node->left = rotateLeft(node->left);
        return rotateRight(node);
    }
    if (balance < -1 && key < node->right->key) {
        node->right = rotateRight(node->right);
        return rotateLeft(node);
    }

    return node;
}

Node* minValueNode(Node* node) {
    Node* current = node;
    while (current->left != nullptr) current = current->left;
    return current;
}

Node* deleteNode(Node* root, int key) {
    if (root == nullptr) return root;

    if (key < root->key) {
        root->left = deleteNode(root->left, key);
    } else if (key > root->key) {
        root->right = deleteNode(root->right, key);
    } else {
        if ((root->left == nullptr) || (root->right == nullptr)) {
            Node* temp = root->left ? root->left : root->right;
            if (temp == nullptr) {
                temp = root;
                root = nullptr;
            } else {
                *root = *temp;
            }
            delete temp;
        } else {
            Node* temp = minValueNode(root->right);
            root->key = temp->key;
            root->right = deleteNode(root->right, temp->key);
        }
    }

    if (root == nullptr) return root;

    updateNode(root);

    int balance = getBalance(root);
    if (balance > 1 && getBalance(root->left) >= 0) {
        return rotateRight(root);
    }
    if (balance > 1 && getBalance(root->left) < 0) {
        root->left = rotateLeft(root->left);
        return rotateRight(root);
    }
    if (balance < -1 && getBalance(root->right) <= 0) {
        return rotateLeft(root);
    }
    if (balance < -1 && getBalance(root->right) > 0) {
        root->right = rotateRight(root->right);
        return rotateLeft(root);
    }

    return root;
}

bool find(Node* node, int key) {
    if (node == nullptr) return false;
    if (node->key == key) return true;
    if (node->key < key) return find(node->right, key);
    return find(node->left, key);
}

long long sumRange(Node* node, int L, int R) {
    if (!node) return 0;
    if (node->key > R) return sumRange(node->left, L, R);
    if (node->key < L) return sumRange(node->right, L, R);
    return node->key + sumRange(node->left, L, R) + sumRange(node->right, L, R);
}

class AVLTree {
private:
    Node* root = nullptr;
    long long lastSum = 0;
    const int MOD = 1000000001;

    int f(int x) {
        return (x + lastSum) % MOD;
    }

public:
    void process(char type, int x) {
        if (type == '+') {
            root = insert(root, f(x));
        } else if (type == '-') {
            root = deleteNode(root, f(x));
        } else if (type == '?') {
            cout << (find(root, f(x)) ? "Found" : "Not found") << "\n";
        } else if (type == 's') {
            int l = f(x);
            cin >> x;
            int r = f(x);
            lastSum = sumRange(root, l, r);
            cout << lastSum << "\n";
        }
    }
};

int main() {

    AVLTree tree;
    int n;
    cin >> n;
    char type;
    int x;

    for (int i = 0; i < n; ++i) {
        cin >> type >> x;
        tree.process(type, x);
    }

    return 0;
}
