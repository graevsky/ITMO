: greet
  cr
  ."Hello, what is your name?"
  cr
  1 0 do
    inp
    if
        dec_i
    then
  loop
  cr
  ."Hello,"
  1 0 do
    out
    dup
    if
        dec_i
    then
  loop
;

greet