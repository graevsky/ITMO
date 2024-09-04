% ���� ������, �������� - ����� ������ (red dead redemption 2)

% ����� � 1 ���������� (20)
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

% ����� � 2 ����������� (10-15)
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

% ������� (5-7)

% �������: ����� �������� ������ ����-��, ���� ��� ���� ��� ����� ������.
mutual_friends(X, Y) :- friend(X, Y), friend(Y, X).

% �������: ���� ����� ����� - ��� ����.
enemy_of_my_enemy_is_friend(X, Z) :- enemy(X, Y), enemy(Y, Z), X \= Z.

% �������: ����� ��������� � ���������, ���� �� ��������� � ��� �� �����, ��� � ��� ����.
in_danger(arthur) :- location(arthur, Location), location(Enemy, Location), enemy(arthur, Enemy).

% �������: ����� �������, ���� � ���� ���� ������ ��� ����� �����.
rich(arthur) :- owns(arthur, gold).
rich(arthur) :- owns(arthur, money), money_amount(arthur, Amount), Amount > 1000.

% ���� ��� ����� ����� � ������
money_amount(arthur, 1200).

% �������: ����� ��������, ���� � ���� ���� ���� �� ���� ������.
armed(arthur) :- owns(arthur, Weapon), item(Weapon), (Weapon = revolver; Weapon = rifle).

% �������: ���� ���-�� ������� �������, �� ��� ����� ������������ ����� ���������.
can_travel(X) :- owns(X, horse).
