const MAX_R = 5;
const MAX_X = 4;

//const scaleFactor = canvas.width / (2 * (MAX_R + MAX_X));

function drawGraph(rValue) {
    const scaleFactor = canvas.width / (2 * (MAX_R + MAX_X));

    ctx.clearRect(0, 0, canvas.width, canvas.height);


    ctx.strokeStyle = "black";
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(0, canvas.height / 2);
    ctx.lineTo(canvas.width, canvas.height / 2);
    ctx.moveTo(canvas.width / 2, 0);
    ctx.lineTo(canvas.width / 2, canvas.height);
    ctx.stroke();

    ctx.fillStyle = "rgba(91,95,201,0.58)";

    ctx.fillRect(canvas.width / 2, canvas.height / 2, -rValue  * scaleFactor, -rValue / 2  * scaleFactor);//rect


    ctx.beginPath();//circle
    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.arc(canvas.width / 2, canvas.height / 2, rValue / 2 * scaleFactor, -Math.PI / 2, 0, false);
    ctx.closePath();
    ctx.fill();




    ctx.beginPath();//triangl
    ctx.moveTo(canvas.width / 2, canvas.height / 2);
    ctx.lineTo(canvas.width / 2 - rValue * scaleFactor, canvas.height / 2);
    ctx.lineTo(canvas.width / 2, canvas.height / 2 + rValue * scaleFactor);
    ctx.closePath();
    ctx.fill();


    ctx.fillStyle = "black";
    ctx.font = "14px Arial";
    ctx.fillText("R", canvas.width / 2 + rValue * scaleFactor, canvas.height / 2 + 20);
    ctx.fillText("R/2", canvas.width / 2 + rValue * 0.5 * scaleFactor, canvas.height / 2 + 20);
    ctx.fillText("-R", canvas.width / 2 - rValue * scaleFactor, canvas.height / 2 + 20);
    ctx.fillText("-R/2", canvas.width / 2 - rValue * 0.5 * scaleFactor, canvas.height / 2 + 20);
    ctx.fillText("R", canvas.width / 2 - 10, canvas.height / 2 - rValue * scaleFactor);
    ctx.fillText("R/2", canvas.width / 2 - 10, canvas.height / 2 - rValue * 0.5 * scaleFactor);
    ctx.fillText("-R", canvas.width / 2 - 10, canvas.height / 2 + rValue * scaleFactor);
    ctx.fillText("-R/2", canvas.width / 2 - 10, canvas.height / 2 + rValue * 0.5 * scaleFactor);

}

function drawPoint(x, y, isHit) {
    const scaleFactor = canvas.width / (2 * (MAX_R + MAX_X));

    let pixelX = canvas.width / 2 + x * scaleFactor;
    let pixelY = canvas.height / 2 - y * scaleFactor;

    ctx.fillStyle = isHit ? "green" : "red";
    ctx.beginPath();
    ctx.arc(pixelX, pixelY, 5, 0, 2 * Math.PI);
    ctx.fill();
}


function updateGraph(r, points) {
    drawGraph(r);
    points.forEach(point => {
        drawPoint(point.x, point.y, point.hit);
    });
}



function updateAfterSubmit(data) {
    if (document.getElementById('resultsTable')) {
        window.canvas = document.getElementById('graph');
        window.ctx = canvas.getContext('2d');

        const rValue = parseFloat(document.querySelector('.rRadios input:checked').value);
        const resultsTable = document.querySelectorAll('#resultsTable tbody tr');
        const points = Array.from(resultsTable).map(row => {
            return {
                x: parseFloat(row.querySelector('td:nth-child(1)').textContent),
                y: parseFloat(row.querySelector('td:nth-child(2)').textContent),
                hit: row.querySelector('td:nth-child(4)').textContent.trim().toLowerCase() === 'да'
            };
        });

        updateGraph(rValue, points);
    }
}



function redrawGraph(data) {
    if (data.status === "success") {
        window.canvas = document.getElementById('graph');
        window.ctx = canvas.getContext('2d');
        const rValue = parseFloat(document.querySelector('.rRadios input:checked').value);
        drawGraph(rValue);
    }
}
