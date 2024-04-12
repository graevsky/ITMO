document.addEventListener("DOMContentLoaded", function () {
    window.canvas = document.getElementById('graph');
    window.ctx = canvas.getContext('2d');

    drawGraph(2);
});
