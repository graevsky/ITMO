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
person(strawberry_sheriff).
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
owns(strawberry_sheriff, revolver).

location(arthur, strawberry).
location(john, blackwater).
location(dutch, annesburg).
location(micah, strawberry).
location(hosea, emerald_ranch).
location(strawberry_sheriff, strawberry).
location(treasure, cumberland_forest).
location(sadie, strawberry).

enemy(arthur, micah).
enemy(micah, arthur).
enemy(strawberry_sheriff, micah).
enemy(micah, strawberry_sheriff).
enemy(sadie, dutch).
friend(arthur, john).
friend(john, arthur).
friend(arthur, hosea).
friend(hosea, arthur).
friend(arthur, sadie).
friend(sadie, arthur).

% правила (5)

% Правило: Люди являются друзьями, если X дружит с Y и наоборот.
mutual_friends(X, Y) :- friend(X, Y), friend(Y, X).

% Правило: Враг моего врага - мой друг.
enemy_of_my_enemy_is_friend(X, Z) :- enemy(X, Y), enemy(Y, Z), X \= Z.

% Правило: Артур находится в опасности, если он находится в том же месте, что и его враг.
in_danger(X) :- location(X, Location), location(Enemy, Location), enemy(X, Enemy).

% Правило: Артур вооружен, если у него есть хотя бы одно оружие.
armed(X) :- owns(X, Weapon), item(Weapon), (Weapon = revolver; Weapon = rifle).

% Правило: Если кто-то владеет лошадью, то они могут перемещаться между локациями.
can_travel(X) :- owns(X, horse).


% враг моего друга
enemy_of_my_friend_enemy(X, Z) :- friend(X, Y), enemy(Y, Z), X \= Z.
