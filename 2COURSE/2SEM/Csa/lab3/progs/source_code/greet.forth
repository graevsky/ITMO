: greet                           \\ Программа приветсвия
  cr
  ." Hello, what is your name?"
  cr
  pad 256 accept
  dup
  pad swap
  cr
  ." Hello, "
  type
  ." !"
  cr
;

greet