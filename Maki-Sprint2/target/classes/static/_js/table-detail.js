document.querySelectorAll(".edit-btn").forEach(button => {
  button.addEventListener("click", () => {
    const row = button.closest("tr");
    const id = row.dataset.id;
    window.location.href = `/Comidas/${id}`;
  });
});