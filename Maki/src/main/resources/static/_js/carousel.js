
// Tomar todos los carousels
const carousels = Array.from(document.querySelectorAll(".carousel"))

for (const carousel of carousels) {
  const images = Array.from(carousel.querySelectorAll(".carousel_image"))

  const left_btn = carousel.querySelector(".carousel-left-btn")
  const right_btn = carousel.querySelector(".carousel-right-btn")

  const update_carousel = (active, next) => {
    // Animacion Entrada
    images[next].classList.remove("inactive")
    images[next].classList.add("active")

    // Animacion salida
    images[active].classList.add("inactive")
    images[active].classList.remove("active")
  }

  left_btn.addEventListener("click", () => {
    const active_index = images.findIndex(x => x.classList.contains("active"))
    const next_index = (active_index - 1 >= 0) ? (active_index - 1) : (images.length - 1)

    update_carousel(active_index, next_index)

  })

  right_btn.addEventListener("click", () => {
    const active_index = images.findIndex(x => x.classList.contains("active"))
    const next_index = (active_index + 1) % images.length

    update_carousel(active_index, next_index)
  })
}