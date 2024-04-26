#include <iostream>
using namespace std;

class Node {
public:
  int value;
  Node* parent;
  Node* left;
  Node* right;
  long long sum;

  Node(int val) : value(val), parent(nullptr), left(nullptr), right(nullptr), sum(val) {}

  void setLeft(Node* node) {
    left = node;
    if (node) {
      node->parent = this;
    }
  }

  void setRight(Node* node) {
    right = node;
    if (node) {
      node->parent = this;
    }
  }
};

class Tree {
private:
  Node* root;

public:
  Tree() : root(nullptr) {}

  void setRoot(Node* node) {
    root = node;
    if (node) {
      node->parent = nullptr;
    }
  }

  void zig(Node* node) {
    Node* dad = node->parent;
    Node* grand = dad ? dad->parent : nullptr;

    // Update sums
    node->sum += dad->value + (dad->right ? dad->right->sum : 0);
    dad->sum -= node->value + (node->left ? node->left->sum : 0);

    // Rearrange nodes
    dad->setLeft(node->right);
    node->setRight(dad);

    if (!grand) {
      setRoot(node);
    } else {
      if (grand->left == dad) {
        grand->setLeft(node);
      } else {
        grand->setRight(node);
      }
    }
  }

  void zag(Node* node) {
    Node* dad = node->parent;
    Node* grand = dad ? dad->parent : nullptr;

    // Update sums
    node->sum += dad->value + (dad->left ? dad->left->sum : 0);
    dad->sum -= node->value + (node->right ? node->right->sum : 0);

    // Rearrange nodes
    dad->setRight(node->left);
    node->setLeft(dad);

    if (!grand) {
      setRoot(node);
    } else {
      if (grand->left == dad) {
        grand->setLeft(node);
      } else {
        grand->setRight(node);
      }
    }
  }

  void splay(Node* node) {
    while (node->parent) {
      Node* dad = node->parent;
      Node* grand = dad->parent;
      if (!grand) {
        if (dad->left == node) zig(node);
        else zag(node);
      } else {
        if (grand->left == dad) {
          if (dad->left == node) { zig(dad); zig(node); }
          else { zag(node); zig(node); }
        } else {
          if (dad->right == node) { zag(dad); zag(node); }
          else { zig(node); zag(node); }
        }
      }
    }
  }

  Node* get_max_node(Node* node) {
    while (node->right) node = node->right;
    return node;
  }

  void add(int value) {
    if (!root) {
      setRoot(new Node(value));
      return;
    }
    Node *cur = root, *max_node = nullptr;
    while (cur) {
      if (cur->value == value) return;
      if (cur->value > value) {
        cur = cur->left;
      } else {
        max_node = cur;
        cur = cur->right;
      }
    }
    Node* new_node = new Node(value);
    if (!max_node) {
      new_node->setRight(root);
      new_node->sum += root->sum;
      setRoot(new_node);
      return;
    }

    splay(max_node);
    new_node->sum += root->sum;
    Node* r_child = root->right;
    if (r_child) root->sum -= r_child->sum;
    root->right = nullptr;

    new_node->setLeft(root);
    new_node->setRight(r_child);

    setRoot(new_node);
  }

  void remove(int value) {
    Node* cur = find(value);
    if (!cur) return;  // Node not found

    splay(cur);
    if (!cur->left) {
      setRoot(cur->right);
    } else {
      Node* max_left = get_max_node(cur->left);
      splay(max_left);
      max_left->sum -= cur->value;
      max_left->setRight(cur->right);
      setRoot(max_left);
    }
  }

  Node* find(int value) {
    Node *cur = root, *prev = nullptr;
    while (cur) {
      prev = cur;
      if (cur->value == value) {
        splay(cur);
        return cur;
      }
      if (cur->value > value) cur = cur->left;
      else cur = cur->right;
    }
    if (prev) splay(prev);
    return nullptr;
  }

  long long sum(int left_value, int right_value) {
    if (!root) return 0;
    Node *min_node = nullptr, *max_node = nullptr;
    long long s = 0;
    Node* cur = root;

    while (cur) {
      if (cur->value >= left_value) {
        min_node = cur;
        cur = cur->left;
      } else {
        cur = cur->right;
      }
    }

    cur = root;
    while (cur) {
      if (cur->value <= right_value) {
        max_node = cur;
        cur = cur->right;
      } else {
        cur = cur->left;
      }
    }

    if (!min_node || !max_node) return 0;
    splay(min_node);
    s += min_node->value;
    if (min_node->right) s += min_node->right->sum;

    splay(max_node);
    if (max_node->right) s -= max_node->right->sum;

    return s;
  }
};

int main() {
  int n, x;
  cin >> n;
  Tree tree;
  long long last_sum = 0;
  string command;
  for (int i = 0; i < n; i++) {
    cin >> command;
    if (command == "+") {
      cin >> x;
      tree.add((x + last_sum) % 1000000001);
    } else if (command == "-") {
      cin >> x;
      tree.remove((x + last_sum) % 1000000001);
    } else if (command == "?") {
      cin >> x;
      Node* node = tree.find((x + last_sum) % 1000000001);
      cout << (node ? "Found" : "Not found") << endl;
    } else if (command == "s") {
      int l, r;
      cin >> l >> r;
      l = (l + last_sum) % 1000000001;
      r = (r + last_sum) % 1000000001;
      if (l > r) swap(l, r);
      last_sum = tree.sum(l, r);
      cout << last_sum << endl;
    }
  }
  return 0;
}
