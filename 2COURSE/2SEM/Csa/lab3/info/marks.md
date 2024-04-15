### Пример формата машкода
```json
[
    {
        "index": 0,
        "opcode": "CR",
        "arg": // не обязательно
    }
]
```

### pad

Адрес 0x0100 зарезервивован под pad, а адреса 0x0200 и выше доступны для i/o

### DataPath
```
             +------------------+    +----------+    +---------+
             |   Instruction    |    | Program  |    | Data    |
             |   Register (IR)  +--->| Counter  +--->| Memory  |
             +--------+---------+    | (PC)     |    |         |
                      |              +-----+----+    +----+----+
                      |                    |              |
                      v                    v              |
            +---------+---------+   +-----+-----+         |
            |    Decode &       |   | Address   |         |
            |    Control Logic  |   | Computation|         |
            +---------+---------+   +-----+-----+         |
                      |                    |              |
                      v                    v              |
          +-----------+----------+  +------+-------+      |
          | Stack Pointer (SP)   |  | ALU for Addr.|<-----+
          | and Stack Operations |  +------+-------+
          +-----------+----------+         |
                      |                    |
                      v                    v
             +--------+-------+     +------+------+
             | Stack Memory   |     | ALU for Data|
             | for Data       |<----+ Operations  |
             +--------+-------+     +-------------+
                      |
                      v
                +-----+----+
                | Registers|
                +----------+
```

### ControlUnit
```
   +------------+   +------------------+
   | Fetch Next |   | Instruction Fetch|
   | Instruction|<--+ from Memory      |
   +------+-----+   +---------+--------+
          |                     |
          v                     v
    +-----+-------+       +-----+--------+
    | Instruction |       | Decode        |
    | Register    |       | Instruction   |
    | (IR)        |       | & Generate    |
    +-----+-------+       | Control       |
          |               | Signals       |
          v               +------+--------+
    +-----+----------------------+-------+
    | Control Logic & State Machine     |
    | for Sequencing & Execution        |
    +----------+---------------+--------+
               |               |
               v               v
          +----+---+       +---+----+
          | PC Logic|      | ALU Ctrl|
          +---------+      +---------+

```

