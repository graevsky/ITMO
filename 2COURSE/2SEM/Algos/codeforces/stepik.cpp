#include <algorithm>
#include <iostream>
#include <map>
#include <vector>

using namespace std;


class Node {
public:
  int val;
  Node *parent;
  Node *l;
  Node *r;
  long long sum;

  Node(int value)
      : val(value), parent(nullptr), l(nullptr), r(nullptr), sum(value) {}

  void setL(Node *node) {
    l = node;
    //
    if (node) {
      node->parent = this;
    }
  }

  void setR(Node *node) {
    r = node;
    //
    if (node) {
      node->parent = this;
    }
  }
};

class Tree {
private:
  Node *treeRoot;

public:
  Tree() : treeRoot(nullptr) {}

  void setRoot(Node *node) {
    treeRoot = node;
    //
    if (node) {
      node->parent = nullptr;
    }
  }

  void moveLeft(Node *node) {
    Node *parent = node->parent;
    Node *pParent = parent ? parent->parent : nullptr;

    node->sum += parent->val + (parent->r ? parent->r->sum : 0);
    parent->sum -= node->val + (node->l ? node->l->sum : 0);

    parent->setL(node->r);
    node->setR(parent);

    if (!pParent) {
      setRoot(node);
    } else {
      if (pParent->l == parent) {
        pParent->setL(node);
      } else {
        pParent->setR(node);
      }
    }
  }

  void moveRight(Node *node) {
    Node *parent = node->parent;
    Node *pParent = parent ? parent->parent : nullptr;

    node->sum += parent->val + (parent->l ? parent->l->sum : 0);
    parent->sum -= node->val + (node->r ? node->r->sum : 0);

    parent->setR(node->l);
    node->setL(parent);

    if (!pParent) {
      setRoot(node);
    } else {
      if (pParent->l == parent) {
        pParent->setL(node);
      } else {
        pParent->setR(node);
      }
    }
  }

  void action(Node *node) {
    while (node->parent) {
      Node *parent = node->parent;
      Node *pParent = parent->parent;
      if (!pParent) {
        if (parent->l == node)
          moveLeft(node);
        else
          moveRight(node);
      } else {
        if (pParent->l == parent) {
          if (parent->l == node) {
            moveLeft(parent);
            moveLeft(node);
          } else {
            moveRight(node);
            moveLeft(node);
          }
        } else {
          if (parent->r == node) {
            moveRight(parent);
            moveRight(node);
          } else {
            moveLeft(node);
            moveRight(node);
          }
        }
      }
    }
  }

  Node *get_max(Node *node) {
    while (node->r)
      node = node->r;
    return node;
  }

  void add(int value) {
    if (!treeRoot) {
      setRoot(new Node(value));
      return;
    }
    Node *current = treeRoot, *max_node = nullptr;
    while (current) {
      if (current->val == value)
        return;
      if (current->val > value) {
        current = current->l;
      } else {
        max_node = current;
        current = current->r;
      }
    }
    Node *new_node = new Node(value);
    if (!max_node) {
      new_node->setR(treeRoot);
      new_node->sum += treeRoot->sum;
      setRoot(new_node);
      return;
    }

    action(max_node);
    new_node->sum += treeRoot->sum;
    Node *r_child = treeRoot->r;
    if (r_child)
      treeRoot->sum -= r_child->sum;
    treeRoot->r = nullptr;

    new_node->setL(treeRoot);
    new_node->setR(r_child);

    setRoot(new_node);
  }

  void remove(int value) {
    Node *cur = find(value);
    if (!cur)
      return;

    action(cur);
    if (!cur->l) {
      setRoot(cur->r);
    } else {
      Node *max_left = get_max(cur->l);
      action(max_left);
      max_left->sum -= cur->val;
      max_left->setR(cur->r);
      setRoot(max_left);
    }
  }

  Node *find(int value) {
    Node *cur = treeRoot, *prev = nullptr;
    while (cur) {
      prev = cur;
      if (cur->val == value) {
        action(cur);
        return cur;
      }
      if (cur->val > value)
        cur = cur->l;
      else
        cur = cur->r;
    }
    if (prev)
      action(prev);
    return nullptr;
  }

  long long sum(int left_value, int right_value) {
    if (!treeRoot)
      return 0;
    Node *min_node = nullptr, *max_node = nullptr;
    long long s = 0;
    Node *cur = treeRoot;

    while (cur) {
      if (cur->val >= left_value) {
        min_node = cur;
        cur = cur->l;
      } else {
        cur = cur->r;
      }
    }

    cur = treeRoot;
    while (cur) {
      if (cur->val <= right_value) {
        max_node = cur;
        cur = cur->r;
      } else {
        cur = cur->l;
      }
    }

    if (!min_node || !max_node)
      return 0;
    action(min_node);
    s += min_node->val;
    if (min_node->r)
      s += min_node->r->sum;

    action(max_node);
    if (max_node->r)
      s -= max_node->r->sum;

    return s;
  }
};

int modFun(int x, int sum) { return (x + sum) % 1000000001; }

int main() {
  int n, x;
  cin >> n;
  Tree tree;
  long long last_sum = 0;
  string inp;
  for (int i = 0; i < n; i++) {
    cin >> inp;
    if (inp == "+") {
      cin >> x;
      tree.add(modFun(x,last_sum));
    } else if (inp == "-") {
      cin >> x;
      tree.remove(modFun(x,last_sum));
    } else if (inp == "?") {
      cin >> x;
      Node *node = tree.find(modFun(x,last_sum));
      cout << (node ? "Found" : "Not found") << endl;
    } else if (inp == "s") {
      int l, r;
      cin >> l >> r;
      l = modFun(l,last_sum);
      r = modFun(r,last_sum);
      if (l > r)
        swap(l, r);
      last_sum = tree.sum(l, r);
      cout << last_sum << endl;
    }
  }
  return 0;
}
