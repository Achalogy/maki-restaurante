console.log(menu);

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
  price.textContent = `$${plato.precio.toLocaleString('en-US')}`;
  price.className = "price";
  itemInfo.appendChild(price);
  storeElement.appendChild(itemInfo);
  //Para ver donde entra cada plato
  if (plato.tag.includes("entrada")) {
    const entradaSection = document.querySelector(".entradas");
    entradaSection.appendChild(storeElement);
  }
  if (plato.tag.includes("pfuerte")) {
    const pfuerteSection = document.querySelector(".platos-fuertes");
    pfuerteSection.appendChild(storeElement);
  }
  if (plato.tag.includes("sushi")) {
    const sushiSection = document.querySelector(".sushi");
    sushiSection.appendChild(storeElement);
  }
  if (plato.tag.includes("postre")) {
    const postreSection = document.querySelector(".postres");
    postreSection.appendChild(storeElement);
  }
});
