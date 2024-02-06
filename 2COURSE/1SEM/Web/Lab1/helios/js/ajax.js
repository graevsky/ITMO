function sendDataToServer() {
    let xValue = parseFloat(xSelect.value);
    let yValue = parseFloat(yInput.value.replace(",", "."));
    let rValue = parseFloat(rInput.value.replace(",", "."));
    let xhr = new XMLHttpRequest();
    let localTime = new Date();
    let formattedLocalTime = `${localTime.getFullYear()}-${String(localTime.getMonth() + 1)
        .padStart(2, '0')}-${String(localTime.getDate()).padStart(2, '0')} ${String(localTime.getHours())
        .padStart(2, '0')}:${String(localTime.getMinutes()).padStart(2, '0')}:${String(localTime.getSeconds()).padStart(2, '0')}`;
    xhr.open('GET', `check_point.php?x=${xValue}&y=${yValue}&r=${rValue}&currentTime=${formattedLocalTime}`, true);
    xhr.onload = function () {
        if (this.status === 200) {
            let response = JSON.parse(this.responseText);
            if (response.error) {
                alert(response.error);
            } else {
                addToResultsTable(response);
                updateGraph(xValue, yValue, rValue);
                if (response.results) {
                    updateAllResults(response.results);
                }
            }
        } else if (this.status === 404) {
            alert("Страница не найдена");
        } else if (this.status === 500) {
            alert("На сервере произошла ошибка");
        } else {
            alert("Произошла неизвестная ошибка: " + this.status);
        }
    };
    xhr.send();
}

document.getElementById("data_form").addEventListener("submit", function (event) {
    event.preventDefault();
    if (!validateX() || !validateY() || !validateR()) {
        alert("Пожалуйста, введите корректные значения для X, Y и R.");
    } else {
        sendDataToServer();
    }
});


function updateAllResults(results) {
    results.forEach(result => {
        addToResultsTable(result);
    });
}


drawGraph(2);