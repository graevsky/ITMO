const images = ["imgs/1.jpg", "imgs/2.jpg", "imgs/3.jpg"];
const comments = ["Комментарий к изображению 1", "Комментарий к изображению 2", "Комментарий к изображению 3"];
let currentImageIndex = 0;
let autoSlideInterval;

const imageElement = document.getElementById('image');
const commentElement = document.getElementById('comment');
const timingInput = document.getElementById('timing');
const audioPlayer = document.getElementById('audioPlayer');
const trackSelect = document.getElementById('trackSelect');
const canvas = document.getElementById('audioVisualizer');
const canvasCtx = canvas.getContext('2d');

// Инициализация первого трека при загрузке страницы
window.addEventListener('load', () => {
    audioPlayer.src = trackSelect.value;
    audioPlayer.load();  // Загрузим аудиофайл
    audioPlayer.play();  // Воспроизведем его
});

// Функция для переключения изображения
function updateImage() {
    imageElement.src = images[currentImageIndex];
    commentElement.textContent = comments[currentImageIndex];
}

// События кнопок переключения
document.getElementById('next').addEventListener('click', () => {
    currentImageIndex = (currentImageIndex + 1) % images.length;
    updateImage();
});

document.getElementById('prev').addEventListener('click', () => {
    currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
    updateImage();
});

// Автоматическое переключение
document.getElementById('startAuto').addEventListener('click', () => {
    clearInterval(autoSlideInterval);
    const timing = parseInt(timingInput.value) * 1000 || 3000;
    autoSlideInterval = setInterval(() => {
        currentImageIndex = (currentImageIndex + 1) % images.length;
        updateImage();
    }, timing);
});

// Выбор звуковой дорожки
trackSelect.addEventListener('change', () => {
    audioPlayer.src = trackSelect.value;
    audioPlayer.load();  // Загрузим новый аудиофайл
    audioPlayer.play();  // Воспроизведем его
});

// Аудио визуализация
function visualizeAudio() {
    const audioContext = new (window.AudioContext || window.webkitAudioContext)();
    const analyser = audioContext.createAnalyser();
    const source = audioContext.createMediaElementSource(audioPlayer);
    source.connect(analyser);
    analyser.connect(audioContext.destination);

    analyser.fftSize = 256;
    const bufferLength = analyser.frequencyBinCount;
    const dataArray = new Uint8Array(bufferLength);

    function draw() {
        requestAnimationFrame(draw);
        analyser.getByteFrequencyData(dataArray);

        canvasCtx.clearRect(0, 0, canvas.width, canvas.height);
        const barWidth = (canvas.width / bufferLength) * 2.5;
        let barHeight;
        let x = 0;

        for (let i = 0; i < bufferLength; i++) {
            barHeight = dataArray[i] / 2;
            canvasCtx.fillStyle = 'rgb(' + (barHeight + 100) + ',50,50)';
            canvasCtx.fillRect(x, canvas.height - barHeight / 2, barWidth, barHeight);
            x += barWidth + 1;
        }
    }

    draw();
}

audioPlayer.addEventListener('play', () => {
    visualizeAudio();
});
