: read_input
    1 0 do
        inp
        dup
        if
            dec_i
        then
        store
    loop
;

: print_output
    1 0 do
        load
        out
        if
            dec_i
        then
    loop
;


: greet
    cr
    ."Hello, what is your name?"
    cr
    read_input
    cr
    ."Hello, "
    print_output
;

greet