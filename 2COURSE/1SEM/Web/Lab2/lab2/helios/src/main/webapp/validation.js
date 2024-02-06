const VALID_R_VALUES = [1.0, 1.5, 2.0, 2.5, 3.0];

function getXValueFromCheckboxes() {
    let checkboxes = document.querySelectorAll('input[name="x"]:checked');
    if (checkboxes.length > 1) {
        alert("Only one x can be used!");
        return null;
    } else if (checkboxes.length === 0) {
        alert("Please select x.");
        return null;
    }
    return parseFloat(checkboxes[0].value);
}

function validateX() {
    let xValue = getXValueFromCheckboxes();
    return xValue !== null && xValue >= -3 && xValue <= 5;
}

function validateY() {
    let yValueStr = yInput.value.trim();
    const commaCount = (yValueStr.match(/,/g) || []).length;
    if (commaCount > 1) {
        return false;
    }
    yValueStr = yValueStr.replace(",", ".");
    let yValue = parseFloat(yValueStr);
    return !isNaN(yValue) && yValueStr === yValue.toString() && yValue >= -5 && yValue <= 5;
}

function validateR() {
    let rValue = parseFloat(document.getElementById("r_input").value);
    return VALID_R_VALUES.includes(rValue);
}

document.getElementById("data_form").addEventListener("submit", function (event) {
    if (!validateX() || !validateY() || !validateR()) {
        alert("Please use correct x, y, r.");
        event.preventDefault();
        return;
    }

    event.preventDefault();

    let xValue = getXValueFromCheckboxes();
    let yValue = parseFloat(yInput.value.trim().replace(",", "."));
    let rValue = parseFloat(document.getElementById("r_input").value);

    let formData = new FormData(this);
    let queryString = new URLSearchParams(formData).toString() + "&timezoneOffset=" + timezoneOffset;

    fetch(this.action + "?" + queryString, {
        method: this.method
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
                updateGraph(xValue, yValue, rValue);
                document.getElementById("results_table").insertAdjacentHTML('afterbegin', data);
            }
        })
        .catch(error => {
            console.error("There was an error:", error);
            alert(error.message);
        });

});
