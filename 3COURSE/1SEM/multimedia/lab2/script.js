const images = ["imgs/1.jpg", "imgs/2.jpg", "imgs/3.jpg"];
const comments = [
  "Город будущего",
  "Еще одна картинка...",
  "Третья картинка...",
];
let currentImageIndex = 0;
let autoSlideInterval;
let defaultTracks = [
  { name: "Over50", src: "audio/track1.mp3" },
  { name: "Intense Electro Music", src: "audio/track2.mp3" },
  { name: "BLACK BOX - The Crew", src: "audio/track3.mp3" },
];
let customTracks = [];
let currentAudioIndex = 0;
let isPlaying = false;
let isAutoSliding = false;

const imageElement = document.getElementById("image");
const commentElement = document.getElementById("comment");
const timingInput = document.getElementById("timing");
const audioUpload = document.getElementById("audioUpload");
const audioList = document.getElementById("audioList");
const playPauseButton = document.getElementById("playPauseAudio");
const audioPlayer = new Audio();
const currentTrackElement = document.getElementById("currentTrack");
const canvas = document.getElementById("audioVisualizer");
const canvasCtx = canvas.getContext("2d");
const progressBar = document.getElementById("progressBar");

canvas.width = canvas.clientWidth;
canvas.height = canvas.clientHeight;

let audioContext;
let analyser;
let dataArray;
let bufferLength;

function getAllTracks() {
  return [...defaultTracks, ...customTracks];
}

function updateTrackList() {
  audioList.innerHTML = "";
  getAllTracks().forEach((track, index) => {
    const li = document.createElement("li");
    li.textContent = track.name;
    li.addEventListener("click", () => {
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

document.getElementById("next").addEventListener("click", () => {
  currentImageIndex = (currentImageIndex + 1) % images.length;
  updateImage();
});

document.getElementById("prev").addEventListener("click", () => {
  currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
  updateImage();
});

document.getElementById("startAuto").addEventListener("click", () => {
  if (isAutoSliding) {
    clearInterval(autoSlideInterval);
    document.getElementById("startAuto").textContent = "Старт пролистывания";
    isAutoSliding = false;
  } else {
    const timing = parseInt(timingInput.value) * 1000 || 2000;
    autoSlideInterval = setInterval(() => {
      currentImageIndex = (currentImageIndex + 1) % images.length;
      updateImage();
    }, timing);
    document.getElementById("startAuto").textContent = "Пауза пролистывания";
    isAutoSliding = true;
  }
});

audioUpload.addEventListener("change", (event) => {
  const newTracks = Array.from(event.target.files).map((file) => ({
    name: file.name,
    src: URL.createObjectURL(file),
  }));
  customTracks = customTracks.concat(newTracks);
  updateTrackList();
  updateCurrentTrack();
});

function playAudio() {
  const allTracks = getAllTracks();
  if (allTracks.length > 0) {
    audioPlayer.src = allTracks[currentAudioIndex].src;
    audioPlayer.play();
    isPlaying = true;
    playPauseButton.textContent = "Пауза";
    updateCurrentTrack();
    initializeVisualizer();
  }
}

playPauseButton.addEventListener("click", () => {
  if (isPlaying) {
    audioPlayer.pause();
    isPlaying = false;
    playPauseButton.textContent = "Плей";
  } else {
    audioPlayer.play();
    isPlaying = true;
    playPauseButton.textContent = "Пауза";
    updateCurrentTrack();
    initializeVisualizer();
  }
});

const volumeSlider = document.getElementById("volumeSlider");
volumeSlider.addEventListener("input", (event) => {
  audioPlayer.volume = event.target.value;
});

document.getElementById("prevTrack").addEventListener("click", () => {
  currentAudioIndex =
    (currentAudioIndex - 1 + getAllTracks().length) % getAllTracks().length;
  playAudio();
});

document.getElementById("nextTrack").addEventListener("click", () => {
  currentAudioIndex = (currentAudioIndex + 1) % getAllTracks().length;
  playAudio();
});

audioPlayer.addEventListener("timeupdate", () => {
  progressBar.max = audioPlayer.duration;
  progressBar.value = audioPlayer.currentTime;
});

progressBar.addEventListener("input", () => {
  audioPlayer.currentTime = progressBar.value;
});

function updateCurrentTrack() {
  const allTracks = getAllTracks();
  currentTrackElement.textContent = `Текущий трек: ${allTracks[currentAudioIndex] ? allTracks[currentAudioIndex].name : "нет"}`;
}

function initializeVisualizer() {
  if (!audioContext) {
    audioContext = new (window.AudioContext || window.webkitAudioContext)();
  }

  const source = audioContext.createMediaElementSource(audioPlayer);
  analyser = audioContext.createAnalyser();
  source.connect(analyser);
  analyser.connect(audioContext.destination);

  analyser.fftSize = 256;
  bufferLength = analyser.frequencyBinCount;
  dataArray = new Uint8Array(bufferLength);

  drawVisualizer();
}

function drawVisualizer() {
  if (!isPlaying) {
    return;
  }

  requestAnimationFrame(drawVisualizer);

  analyser.getByteFrequencyData(dataArray);

  canvasCtx.clearRect(0, 0, canvas.width, canvas.height);
  const barWidth = (canvas.width / bufferLength) * 2.5;
  let barHeight;
  let x = 0;

  for (let i = 0; i < bufferLength; i++) {
    barHeight = dataArray[i];
    canvasCtx.fillStyle = `rgb(${barHeight + 100},50,50)`;
    canvasCtx.fillRect(
      x,
      canvas.height - barHeight / 2,
      barWidth,
      barHeight / 2,
    );

    x += barWidth + 1;
  }
}

window.addEventListener("load", () => {
  updateImage();
  updateTrackList();
});
