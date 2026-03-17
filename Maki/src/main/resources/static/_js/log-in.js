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

const error_container = document.getElementById("log-in-errors")
const url = new URL(window.location.href)
const params = url.searchParams
const msg = params.get("msg");

if(msg) {
  const error_message = document.createElement("p")
  error_message.classList.add("p-error")
  error_message.textContent = msg
  error_container.appendChild(
    error_message
  )
}