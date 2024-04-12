document.addEventListener("DOMContentLoaded", function () {
    const clockElement = document.getElementById('clock');
    const clockContainerElement = document.getElementById('clockContainer');
    const clockVideoElement = document.getElementById('clockVideo');

    function updateClock() {
        const now = new Date();
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const seconds = String(now.getSeconds()).padStart(2, '0');
        clockElement.textContent = `${hours}:${minutes}:${seconds}`;
    }

    function showClock() {
        clockElement.style.display = 'block';
        clockVideoElement.style.display = 'none';
        clockContainerElement.style.backgroundColor = '#fff';
        clockElement.style.opacity = '1';
    }

    setTimeout(showClock, 9000);

    setInterval(updateClock, 9000);
});
