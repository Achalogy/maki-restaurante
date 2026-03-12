console.log(categorias);

categorias.forEach((categoria) => {
  const section = document.createElement("section");
  section.className = "menu-section grid grid-cols-2 gap-6";
  if (categoria.nombre.includes("None")) {
  section.innerHTML = `<h2 class="col-span-2">No categorizado</h2>`;
} else {
  section.innerHTML = `<h2 class="col-span-2">${categoria.nombre} ${categoria.id}</h2>`;
}
section.id = `cat-${categoria.id}`
section.setAttribute("id", `cat-${categoria.id}`)

  menu.forEach((plato) => {
    const storeElement = document.createElement("article");
    storeElement.className = "item-card";
    //Agrega datos de comida
    const itemInfo = document.createElement("div");
    itemInfo.className = "item-info";
    const image = document.createElement("img");
    image.src = plato.urlImage;
    image.alt = plato.nombre;
    storeElement.appendChild(image);
    const addButton = document.createElement("button");

    addButton.addEventListener("click", () => {
      window.location.href = `/Comidas/${plato.id}`;
    });
    addButton.className = "add-btn";

    /* Creamos el + con un span interno */
    const plus = document.createElement("span");
    plus.className = "plus-icon";

    addButton.appendChild(plus);
    storeElement.appendChild(addButton);
    const name = document.createElement("h3");
    name.className = "product-name";
    name.textContent = plato.nombre;
    itemInfo.appendChild(name);
    const desc = document.createElement("p");
    desc.textContent = plato.descripcion;
    itemInfo.appendChild(desc);
    const price = document.createElement("h3");
    price.textContent = `$${plato.precio.toLocaleString("en-US")}`;
    price.className = "price";
    itemInfo.appendChild(price);
    storeElement.appendChild(itemInfo);

    if (plato.categoria.id === categoria.id) {
      console.log(`Agregando ${plato.nombre} a la sección ${categoria.nombre}`);
      section.appendChild(storeElement);
    }
  });

  document.getElementById("menu-cards").appendChild(section);
});

// Safari es una kk :)
if (window.location.hash) {
  const target = document.querySelector(window.location.hash);
  if (target) {
    setTimeout(() => {
      target.scrollIntoView({ behavior: "smooth", block: "start" });
    }, 500);
  }
}