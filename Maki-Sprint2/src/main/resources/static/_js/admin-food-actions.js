const delbtn = document.getElementById("del-button");

delbtn.addEventListener("click", function(){
    if (confirm("¿Estás seguro de que deseas eliminar este plato?")) {
        const id = delbtn.getAttribute("data-id");
        window.location.href = `/Comidas/deleteMenuItem/${id}`;
        alert("Plato eliminado");
    }
});