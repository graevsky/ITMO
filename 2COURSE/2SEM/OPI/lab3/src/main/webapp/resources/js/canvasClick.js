function onCanvasClick(event) {

    if (!validateR()) {
        alert('Пожалуйста, выберите значение R перед отправкой точки на сервер!');
        return;
    }

    const rect = canvas.getBoundingClientRect();
    const scaleFactor = canvas.width / (2 * (MAX_R + MAX_X));
    const rValue = parseFloat(document.querySelector('.rRadios input:checked').value);

    let x = (event.clientX - rect.left - canvas.width / 2) / scaleFactor;
    let y = -(event.clientY - rect.top - canvas.height / 2) / scaleFactor;

    if (x < -3 || x > 4 || y < -5 || y > 3) {
        alert('x и y за пределами допустимых значений: -3 \u2264 x \u2264 4; -5 \u2264 y \u2264 3!');
        return;
    }

    const timezoneOffset = new Date().getTimezoneOffset();


    sendPointToServer(x, y, rValue, timezoneOffset);
}

function sendPointToServer(x, y, r, timezoneOffset) {
    sendCanvasPoint([{name: 'xValue', value: x}, {name: 'yValue', value: y}, {
        name: 'rValue',
        value: r
    }, {name: 'timezoneOffset', value: timezoneOffset}]);


}



