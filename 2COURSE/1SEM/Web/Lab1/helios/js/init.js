document.addEventListener("DOMContentLoaded", function() {
    window.xSelect = document.getElementById("x_select");
    window.yInput = document.getElementById("y_input");
    window.rInput = document.getElementsByName("r_input");
    window.canvas = document.getElementById("graph");
    window.ctx = canvas.getContext("2d");
    window.resultsTable = document.getElementById("results_table");

    fetch('check_point.php')
        .then(response => response.json())
        .then(data => {
            data.results.forEach(result => {
                addToResultsTable(result);
            });
        });
});

function addToResultsTable(result) {
    let row = resultsTable.insertRow(0);
    row.insertCell(0).innerHTML = result.x;
    row.insertCell(1).innerHTML = result.y;
    row.insertCell(2).innerHTML = result.r;
    row.insertCell(3).innerHTML = result.hit ? 'Да' : 'Нет';
    row.insertCell(4).innerHTML = result.current_time;
    row.insertCell(5).innerHTML = result.execution_time;
}