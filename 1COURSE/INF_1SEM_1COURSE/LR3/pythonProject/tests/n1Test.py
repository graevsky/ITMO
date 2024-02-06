# variant-1 3 2
# ; <{ O
import n1
import unittest


class Test1(unittest.TestCase):
    def test_try1(self):
        inp = 'amogus'
        ans = 0
        self.assertEquals(ans, n1.smiles(inp))
    def test_try2(self):
        inp = ';<{O'
        ans = 1
        self.assertEquals(ans, n1.smiles(inp))
    def test_try3(self):
        inp = ';<{O;<{O;<{O;<{O'
        ans = 4
        self.assertEquals(ans, n1.smiles(inp))
    def test_try4(self):
        inp = 'AM;;;<{OM;<<{{OOGUS ;<{O'
        ans = 2
        self.assertEquals(ans, n1.smiles(inp))
    def test_try5(self):
        inp = ';<{  O ;<{O;<{O; <{O'
        ans = 2
        self.assertEquals(ans, n1.smiles(inp))
    def test_try6(self):
        inp = '[;<{]O galaxy z fold ;<{O3 ;<{O'
        ans = 2
        self.assertEquals(ans, n1.smiles(inp))
