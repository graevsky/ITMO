canvas.addEventListener('click', function(event) {
    handleCanvasClick(event);
});

function handleCanvasClick(event) {
    if (!rInput.value) {
        alert("Please select R before clicking on the graph.");
        return;
    }
    let rect = canvas.getBoundingClientRect();
    let x = event.clientX - rect.left - canvas.width / 2;
    let y = canvas.height / 2 - (event.clientY - rect.top);
    x = x / scaleFactor;
    y = y / scaleFactor;
    sendPointToServer(x, y);
}

const timezoneOffset = new Date().getTimezoneOffset();

function sendPointToServer(x, y) {
    let rValue = parseFloat(rInput.value);
    let queryString = `x=${x}&y=${y}&r=${rValue}&timezoneOffset=${timezoneOffset}`;

    fetch("controller?" + queryString, {
        method: "GET"
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 405) {
                    throw new Error("Method not allowed.");
                } else {
                    throw new Error("Server responded with a status: " + response.status);
                }
            }
            return response.text();
        })
        .then(data => {
            if (data.startsWith("Error:")) {
                alert(data);
            } else {
                updateGraph(x, y, rValue);
                document.getElementById("results_table").insertAdjacentHTML('afterbegin', data);
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert(error.message);
        });
}
