// ── Slideshow de fondo ──
const slides = document.querySelectorAll('.bg-slide');
let actual = 0;

setInterval(() => {
  // Quita la clase activo al slide actual
  slides[actual].classList.remove('activo');

  // Pasa al siguiente (vuelve al primero si llega al final)
  actual = (actual + 1) % slides.length;

  // Activa el nuevo slide
  slides[actual].classList.add('activo');
}, 4000); // Cambia cada 4 segundos