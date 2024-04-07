function validateX() {
    const xRadios = document.querySelector('#j_idt8\\:xCoord');
    const xSelected = xRadios.querySelector('input:checked');

    if (!xSelected) {
        alert('Пожалуйста, выберите значение X!');
        return false;
    }
    return true;
}

function validateY() {
    const yInput = document.querySelector('#j_idt8\\:yCoord');

    let yValueStr = yInput.value.trim();
    const commaCount = (yValueStr.match(/,/g) || []).length;
    if (commaCount > 1) {
        return false;
    }
    yValueStr = yValueStr.replace(",", ".");
    let yValue = parseFloat(yValueStr);
    if (!isNaN(yValue) && yValueStr === yValue.toString() && yValue >= -5 && yValue <= 5) {
        return true;
    }
    alert('Значение y должно быть в диапазоне от -5 до 3!');
    return false;

}

function validateR() {
    const rRadios = document.querySelector('#j_idt8\\:rValue');
    const rSelected = rRadios.querySelector('input:checked');

    if (!rSelected) {
        alert('Пожалуйста, выберите значение R!');
        return false;
    }
    return true;
}

function validateForm() {
    if (!validateX() || !validateY() || !validateR()) {
        return false;
    }

    const timezoneOffsetField = document.querySelector('#j_idt8\\:timezoneOffset');
    timezoneOffsetField.value = new Date().getTimezoneOffset();


    return true;
}
