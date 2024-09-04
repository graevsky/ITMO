% база знаний, персонаж - артур морган (red dead redemption 2)

% факты с 1 аргументом (20)
person(arthur).
person(javier).
person(john).
person(abigail).
person(dutch).
person(bill).
person(sadie).
person(micah).
person(charles).
person(hosea).
person(leny).
person(sean).
item(revolver).
item(horse).
item(rifle).
item(money).
item(gold).
item(tobacco).
item(hat).
item(letter).

% факты с 2 аргументами (10-15)
owns(arthur, revolver).
owns(arthur, horse).
owns(arthur, rifle).
owns(arthur, hat).
owns(arthur, money).
owns(john, revolver).
owns(john, horse).
owns(dutch, gold).
owns(micah, rifle).
owns(hosea, letter).

location(arthur, valentine).
location(john, blackwater).
location(dutch, annesburg).
location(micah, strawberry).
location(hosea, emerald_ranch).
location(treasure, cumberland_forest).

enemy(arthur, micah).
friend(arthur, john).
friend(arthur, hosea).
friend(arthur, sadie).

% правила (5-7)

% Правило: Артур является другом кого-то, если они друг для друга друзья.
mutual_friends(X, Y) :- friend(X, Y), friend(Y, X).

% Правило: Враг моего врага - мой друг.
enemy_of_my_enemy_is_friend(X, Z) :- enemy(X, Y), enemy(Y, Z), X \= Z.

% Правило: Артур находится в опасности, если он находится в том же месте, что и его враг.
in_danger(arthur) :- location(arthur, Location), location(Enemy, Location), enemy(arthur, Enemy).

% Правило: Артур богатый, если у него есть золото или много денег.
rich(arthur) :- owns(arthur, gold).
rich(arthur) :- owns(arthur, money), money_amount(arthur, Amount), Amount > 1000.

% Факт для суммы денег у Артура
money_amount(arthur, 1200).

% Правило: Артур вооружен, если у него есть хотя бы одно оружие.
armed(arthur) :- owns(arthur, Weapon), item(Weapon), (Weapon = revolver; Weapon = rifle).

% Правило: Если кто-то владеет лошадью, то они могут перемещаться между локациями.
can_travel(X) :- owns(X, horse).
