let yInput, rInput, rButtons, resultsTable;

document.addEventListener("DOMContentLoaded", function() {
    yInput = document.getElementById("y_input");
    rInput = document.getElementById("r_input");
    rButtons = document.querySelectorAll('.r-button');
    canvas = document.getElementById("graph");
    ctx = canvas.getContext("2d");
    resultsTable = document.getElementById("results_table");

    rButtons.forEach(button => {
        button.addEventListener('click', function() {
            rInput.value = this.getAttribute("data-value");
            rButtons.forEach(btn => btn.classList.remove('selected'));
            this.classList.add('selected');
            drawGraph(parseFloat(rInput.value));

        });
    });
    drawGraph(2);
});
