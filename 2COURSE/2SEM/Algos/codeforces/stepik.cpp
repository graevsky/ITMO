class Node:
def __init__(self, val: int, parent, left, right):
    self.val = val
    self.parent = parent
    self.left = left
    self.right = right
    self.sum = val

def set_child(self, node, sel):
    if sel == "left":
        self.left = node
        if node:
            node.parent = self
    else:
        self.right = node
        if node:
            node.parent = self


class Tree:
def __init__(self):
    self.root = None

def set_root(self, node):
    self.root = node
    if node:
        node.parent = None

def move_left(self, node: Node):
    parent = node.parent
    pParent = parent.parent if parent else None

    node.sum += parent.val
    if parent.right:
        node.sum += parent.right.sum

    parent.sum -= node.val
    if node.left:
        parent.sum -= node.left.sum

    parent.set_child(node.right, "left")
    node.set_child(parent, "right")

    if pParent is None:
        self.set_root(node)
        return

    if pParent.left == parent:
        pParent.set_child(node, "left")
    else:
        pParent.set_child(node, "right")

def move_right(self, node: Node):
    parent = node.parent
    pParent = parent.parent if parent else None

    node.sum += parent.val
    if parent.left:
        node.sum += parent.left.sum

    parent.sum -= node.val
    if node.right:
        parent.sum -= node.right.sum

    parent.set_child(node.left, "right")
    node.set_child(parent, "left")

    if pParent is None:
        self.set_root(node)
        return

    if pParent.left == parent:
        pParent.set_child(node, "left")
    else:
        pParent.set_child(node, "right")

def splay(self, node: Node):
    while True:
        parent = node.parent
        pParent = parent.parent if parent else None

        if parent is None:
            return

        if pParent is None:
            if parent.left == node:
                self.move_left(node)
            else:
                self.move_right(node)
        else:
            if pParent.left == parent and parent.left == node:
                self.move_left(parent)
                self.move_left(node)
            elif pParent.right == parent and parent.right == node:
                self.move_right(parent)
                self.move_right(node)
            elif pParent.left == parent and parent.right == node:
                self.move_right(node)
                self.move_left(node)
            elif pParent.right == parent and parent.left == node:
                self.move_left(node)
                self.move_right(node)

def add_value(self, val):
    if self.root is None:
        self.set_root(Node(val, None, None, None))
        return
    max_node = None
    current = self.root
    while current:
        if current.val == val:
            return
        if current.val > val:
            current = current.left
        else:
            max_node = current
            current = current.right
    new_node = Node(val, None, None, None)
    if max_node is None:
        new_node.set_child(self.root, "right")
        new_node.sum += self.root.sum
        self.set_root(new_node)
        return

    self.splay(max_node)
    new_node.sum += self.root.sum
    right_child = self.root.right
    max_node.right = None
    if right_child:
        self.root.sum -= right_child.sum

    new_node.set_child(self.root, "left")
    new_node.set_child(right_child, "right")

    self.set_root(new_node)

def remove_value(self, val):
    current = self.find_value(val)
    if not current:
        return
    self.splay(current)
    if current.left is None:
        tr.set_root(current.right)
        return

    max_fo_find = current.left
    while max_fo_find.right:
        max_fo_find = max_fo_find.right
    max_left = max_fo_find

    self.splay(max_left)
    max_left.sum -= current.val
    max_left.set_child(current.right, "right")
    tr.set_root(max_left)

def find_value(self, val):
    if self.root is None:
        return None
    previous = None
    current = self.root
    while current:
        previous = current
        if current.val == val:
            return current
        elif current.val > val:
            current = current.left
        else:
            current = current.right
    self.splay(previous)
    return None

def sum(self, left_val, right_val):
    if self.root is None:
        return 0
    min_node = None
    max_node = None
    res_sum = 0

    current = self.root
    while current:
        if current.val >= left_val:
            min_node = current
            current = current.left
        else:
            current = current.right

    current = self.root
    while current:
        if current.val <= right_val:
            max_node = current
            current = current.right
        else:
            current = current.left

    if min_node is None or max_node is None:
        return 0
    self.splay(min_node)
    res_sum += min_node.val
    if min_node.right:
        res_sum += min_node.right.sum

    self.splay(max_node)
    if max_node.right:
        res_sum -= max_node.right.sum

    return res_sum


def modFun(x, last_sum):
return (x + last_sum) % 1_000_000_001


n = input()
tr = Tree()
res = 0
for i in range(int(n)):
operation = input().split()

if operation[0] == "+":
    num_to_add = int(operation[1])
    tr.add_value(modFun(num_to_add, res))
elif operation[0] == "-":
    num_to_add = int(operation[1])
    tr.remove_value(modFun(num_to_add, res))
elif operation[0] == "?":
    num_to_add = int(operation[1])
    node = tr.find_value(modFun(num_to_add, res))
    if node:
        print("Found")
    else:
        print("Not found")
elif operation[0] == "s":
    left = int(operation[1])
    right = int(operation[2])

    left = modFun(left, res)
    right = modFun(right, res)

    if left > right:
        left, right = right, left
    res = tr.sum(left, right)
    print(res)
