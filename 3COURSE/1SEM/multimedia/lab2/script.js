const images = ["imgs/1.jpg", "imgs/2.jpg", "imgs/3.jpg"];
const comments = ["Комментарий к изображению 1", "Комментарий к изображению 2", "Комментарий к изображению 3"];
let currentImageIndex = 0;
let autoSlideInterval;
let defaultTracks = [
    { name: 'Track 1', src: 'audio/track1.mp3' },
    { name: 'Track 2', src: 'audio/track2.mp3' },
    { name: 'Track 3', src: 'audio/track3.mp3' }
];
let customTracks = [];
let currentAudioIndex = 0;
let isPlaying = false;
let isAutoSliding = false;


const imageElement = document.getElementById('image');
const commentElement = document.getElementById('comment');
const timingInput = document.getElementById('timing');
const audioUpload = document.getElementById('audioUpload');
const audioList = document.getElementById('audioList');
const playPauseButton = document.getElementById('playPauseAudio');
const audioPlayer = new Audio();
const currentTrackElement = document.getElementById('currentTrack');

function getAllTracks() {
    return [...defaultTracks, ...customTracks];
}

function updateTrackList() {
    audioList.innerHTML = '';
    getAllTracks().forEach((track, index) => {
        const li = document.createElement('li');
        li.textContent = track.name;
        li.addEventListener('click', () => {
            currentAudioIndex = index;
            playAudio();
        });
        audioList.appendChild(li);
    });
}

function updateImage() {
    imageElement.src = images[currentImageIndex];
    commentElement.textContent = comments[currentImageIndex];
}

document.getElementById('next').addEventListener('click', () => {
    currentImageIndex = (currentImageIndex + 1) % images.length;
    updateImage();
});

document.getElementById('prev').addEventListener('click', () => {
    currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
    updateImage();
});

document.getElementById('startAuto').addEventListener('click', () => {
    if (isAutoSliding) {
        clearInterval(autoSlideInterval);
        document.getElementById('startAuto').textContent = 'Старт авто';
        isAutoSliding = false;
    } else {
        const timing = parseInt(timingInput.value) * 1000 || 2000;
        autoSlideInterval = setInterval(() => {
            currentImageIndex = (currentImageIndex + 1) % images.length;
            updateImage();
        }, timing);
        document.getElementById('startAuto').textContent = 'Пауза авто';
        isAutoSliding = true;
    }
});

audioUpload.addEventListener('change', (event) => {
    customTracks = Array.from(event.target.files).map(file => ({
        name: file.name,
        src: URL.createObjectURL(file)
    }));
    updateTrackList();
    updateCurrentTrack();
});



function playAudio() {
    const allTracks = getAllTracks();
    if (allTracks.length > 0) {
        audioPlayer.src = allTracks[currentAudioIndex].src;
        audioPlayer.play();
        isPlaying = true;
        playPauseButton.textContent = 'Пауза';
        updateCurrentTrack();
    }
}

playPauseButton.addEventListener('click', () => {
    if (isPlaying) {
        audioPlayer.pause();
        isPlaying = false;
        playPauseButton.textContent = 'Плей';
    } else {
        playAudio();
    }
});

document.getElementById('prevTrack').addEventListener('click', () => {
    currentAudioIndex = (currentAudioIndex - 1 + getAllTracks().length) % getAllTracks().length;
    playAudio();
});

document.getElementById('nextTrack').addEventListener('click', () => {
    currentAudioIndex = (currentAudioIndex + 1) % getAllTracks().length;
    playAudio();
});

function updateCurrentTrack() {
    const allTracks = getAllTracks();
    currentTrackElement.textContent = `Текущий трек: ${allTracks[currentAudioIndex] ? allTracks[currentAudioIndex].name : 'нет'}`;
}

window.addEventListener('load', () => {
    updateImage();
    updateTrackList();
});

