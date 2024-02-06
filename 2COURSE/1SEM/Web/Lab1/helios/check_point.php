<?php
session_start();
header('Content-Type: application/json');
date_default_timezone_set('Europe/Moscow');

function isValidNumber($value) {
    return preg_match('/^-?\\d+(\\.\\d+)?$/', $value);
}

$x_str = isset($_GET['x']) ? $_GET['x'] : null;
$y_str = isset($_GET['y']) ? $_GET['y'] : null;
$r_str = isset($_GET['r']) ? $_GET['r'] : null;

if ($x_str === null || $y_str === null || $r_str === null) {
    echo json_encode(['error' => 'Не все параметры предоставлены']);
    exit;
}
if (!isValidNumber($x_str) || !isValidNumber($y_str) || !isValidNumber($r_str)) {
    echo json_encode(['error' => 'Числа некорректны']);
    exit;
}

$x = (float)$x_str;
$y = (float)$y_str;
$r = (float)$r_str;


if( !((-2 <= $x) && ($x <= 2)) || !((-3 <= $y) && ($y <= 3)) || !((1 <= $r) && ($r <= 4))){
    echo json_encode(['error' => 'Числа за пределами допустимых значений']);
    exit;
}

if ($x === null && $y === null && $r === null) {
    if (isset($_SESSION['results'])) {
        echo json_encode(['results' => $_SESSION['results']]);
    } else {
        echo json_encode(['results' => []]);
    }
    exit;
}

$start_time = microtime(true);


$hit = false;
if (
    ($x <= 0 && $x >= -$r/2 && $y <= 0  && $y >= -$r) ||
    ($x <= 0 && $y >= 0 && $x * $x + $y * $y <= $r * $r) ||
    ($x >= 0 && $y <= 0 && $x <= 0.5*$r + 0.5*$y)
) {
    $hit = true;
}

$execution_time = microtime(true) - $start_time;

$dateTime = new DateTime($_GET['currentTime']);
$result = [
    'x' => $x,
    'y' => $y,
    'r' => $r,
    'hit' => $hit,
    'current_time' => isset($_GET['currentTime']) ? $dateTime->format('Y-m-d H:i:s') : date("Y-m-d H:i:s"),
    'execution_time' => $execution_time,
    'client_time' => isset($_GET['currentTime']) ? date('Y-m-d H:i:s', strtotime($_GET['currentTime'])) : null,
    'server_time' => date('Y-m-d H:i:s')
];

if (!isset($_SESSION['results'])) {
    $_SESSION['results'] = [];
}

array_push($_SESSION['results'], $result);

echo json_encode($result);
?>
