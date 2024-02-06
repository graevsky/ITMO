function validateX() {
    let xValue = parseFloat(xSelect.value);
    return xValue >= -5 && xValue <= 5;
}

function validateY() {
    let yValueStr = yInput.value.trim().replace(",", ".");
    let yValue = parseFloat(yValueStr);
    return !isNaN(yValue) && yValueStr === yValue.toString() && yValue >= -3 && yValue <= 3;
}


function validateR() {
    let rValueSTR =rInput.value.trim().replace(",",".");
    let rValue = parseFloat(rValueSTR)
    return !isNaN(rValue) && rValueSTR === rValue.toString() && rValue >= 1 && rValue <= 4;
}

