#variant-1 3 2
#; <{ O
import re

def smiles(input):
    regExp = r';<\{O'
    return len(re.findall(regExp,input))