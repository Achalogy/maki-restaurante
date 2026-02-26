package com.maki.web.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.maki.web.entities.Plato;

@Repository
public class MenuRepository {
        private Map<Integer, Plato> menu = new HashMap<>();

        public MenuRepository() {
                menu.put(1, new Plato(1, "Sushi Variado", 51960,
                                "Una hermosa combinación de nigiri y rollos con los mejores ingredientes. Cada pieza es cuidadosamente elaborada por nuestros chefs expertos de sushi con precisión y artesanía.",
                                "https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500", true, "sushi"));
                menu.put(2, new Plato(2, "Ramen", 47960,
                                "Un tazón abundante con un caldo tonkotsu rico y tallarines tiernos. Cubierto con huevos cocidos, brotes de bambú y barriga de cerdo marinada.",
                                "https://images.unsplash.com/photo-1638866281450-3933540af86a?w=500", true, "pfuerte"));
                menu.put(3, new Plato(3, "Tempura", 43960,
                                "Verduras rebozadas crujientes y camarones suculentos fritos hasta obtener una perfección dorada. Servido con una salsa tradicional para mojar.",
                                "https://images.unsplash.com/photo-1677743537607-f7fc9273ec4d?w=500", true, "entrada"));
                menu.put(4, new Plato(4, "Tonkatsu", 55960,
                                "Una chuleta de cerdo premium rebozada en panko y frita profundamente hasta quedar dorada y crujiente. La carne en el interior permanece tierna y jugosa, cubierta con salsa sabrosa.",
                                "https://images.unsplash.com/photo-1734775373504-ff24ea8419b2?w=500", true, "pfuerte"));
                menu.put(5, new Plato(5, "Gyoza", 31960,
                                "Delicadas empanadillas fritas en sartén rellenas de cerdo sazonado y verduras. Cada empanadilla está hecha a mano para asegurar el equilibrio ideal entre relleno y masa.",
                                "https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500", true, "entrada"));
                menu.put(6, new Plato(6, "Edamame", 23960,
                                "Frijoles de soya jóvenes y frescos cocidos al vapor hasta quedar tiernos y ligeramente salados. Ricos en proteína, estos son un aperitivo saludable y delicioso.",
                                "https://images.unsplash.com/photo-1575262599410-837a72005862?w=500", true, "entrada"));
                menu.put(7, new Plato(7, "Sopa Miso", 15960,
                                "Una sopa japonesa tradicional hecha con pasta miso fermentada y caldo dashi sabroso. Tipicamente servida con tofu suave y alga marina.",
                                "https://images.unsplash.com/photo-1610393069309-2607fcf74146?w=500", true, "entrada"));
                menu.put(8, new Plato(8, "Rollo California", 39960,
                                "Rollo de sushi al revés con jurel imitado, aguacate cremoso y pepino fresco envuelto en arroz sazonado. Este rollo icónico ofrece un equilibrio perfecto de sabores y texturas.",
                                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=500", true, "sushi"));
                menu.put(9, new Plato(9, "Rollo Dragón", 59960,
                                "Un rollo especializado con anguila tierna y pepino crujiente envueltos en arroz con aguacate en la parte superior. La dulzura de la anguila combina hermosamente con el aguacate cremoso.",
                                "https://images.unsplash.com/photo-1712192674556-4a89f20240c1?w=500", true, "sushi"));
                menu.put(10, new Plato(10, "Rollo Philadelphia", 47960,
                                "Un rollo premium que combina salmón ahumado delicado con queso crema suave y pepino fresco. La combinación crea una mezcla lujosa que une las cocinas japonesa y occidental.",
                                "https://images.unsplash.com/photo-1759646828324-c215a83828ae?w=500", true, "sushi"));
                menu.put(11, new Plato(11, "Yakitori", 43960,
                                "Piezas de pollo tiernas a la parrilla en pinchos sobre carbón hasta quedar perfectamente cocidas y caramelizadas. Cubierto con un glaseado sabroso y dulce.",
                                "https://images.unsplash.com/photo-1708597525178-6c302364f37c?w=500", true, "pfuerte"));
                menu.put(12, new Plato(12, "Okonomiyaki", 51960,
                                "Un panqueque japonés salado hecho de masa y repollo cocido hasta obtener una perfección dorada. Cubierto con salsa sabrosa, mayonesa cremosa y copos de bonito.",
                                "https://plus.unsplash.com/premium_photo-1722593856486-5f87f9fca308?w=500", true,
                                "pfuerte"));
                menu.put(13, new Plato(13, "Takoyaki", 35960,
                                "Esferas esponjosas y doradas de masa rebozada rellenas de pulpo tierno, jengibre encurtido y cebolletas verdes. Servidos calientes y rociados con salsa takoyaki y mayonesa.",
                                "https://plus.unsplash.com/premium_photo-1722593856742-085ef5549070?w=500", true,
                                "entrada"));
                menu.put(14, new Plato(14, "Unagi Don", 55960,
                                "Anguila suculenta a la parrilla esmaltada con salsa kabayaki dulce y sabrosa servida sobre arroz blanco esponjoso. La anguila se cocina hasta quedar tierna con caramelización brillante.",
                                "https://japanesetaste.com.au/cdn/shop/articles/how-to-make-unagi-don-grilled-eel-rice-bowl-with-kabayaki-sauce-japanese-taste.jpg?v=1766642304&width=500",
                                true, "pfuerte"));
                menu.put(15, new Plato(15, "Katsudon", 51960,
                                "Un tazón de arroz cubierto con una chuleta de cerdo dorada y crujiente y una mezcla de huevo sabrosa cocida a fuego lento. La combinación de texturas crea una experiencia culinaria inolvidable.",
                                "https://images.unsplash.com/photo-1624517607896-bb5dfd8f5764?w=500", true, "pfuerte"));
                menu.put(16, new Plato(16, "Chirashi", 59960,
                                "Un impresionante tazón de sushi mixto con un surtido de pescado crudo premium, verduras y huevos dispuestos sobre arroz de sushi sazonado. Cada componente se selecciona cuidadosamente por su frescura y calidad.",
                                "https://images.unsplash.com/photo-1565967531713-45739e0cad63?w=500", true, "sushi"));
                menu.put(17, new Plato(17, "Sukiyaki", 67960,
                                "Una experiencia de olla caliente lujosa con carne de res premium, verduras frescas y tofu en un caldo dulce y sabroso. Los ingredientes se cocinan en la mesa, creando una experiencia comunitaria.",
                                "https://images.unsplash.com/photo-1648977555545-4dd006e30d3f?w=500", true, "pfuerte"));
                menu.put(18, new Plato(18, "Shabu Shabu", 63960,
                                "Un plato de olla caliente interactivo donde rodajas ultrafinas de carne y verduras se agitan brevemente en un caldo hirviente. Cada comensal cocina sus ingredientes al nivel de cocción que prefiera.",
                                "https://images.unsplash.com/photo-1559602580-78f1ba809b92?w=500", true, "pfuerte"));
                menu.put(19, new Plato(19, "Nigiri de Salmón", 43960,
                                "Salmón fresco premium delicadamente colocado sobre un montículo perfectamente formado de arroz de sushi sazonado. La textura sedosa del salmón crudo se derrite en el paladar.",
                                "https://images.unsplash.com/photo-1680675228874-9b9963812b7c?w=500", true, "sushi"));
                menu.put(20, new Plato(20, "Res Wagyu", 99960,
                                "Exquisita carne de res premium japonesa conocida por su veteado y excepcional ternura que se derrite en la boca. Cada bocado revela el sabor rico y mantequilloso.",
                                "https://images.unsplash.com/photo-1708388464912-d4ad82dca990?w=500", true, "pfuerte"));
                menu.put(21, new Plato(21, "Ensalada de Alga Marina", 27960,
                                "Una ensalada refrescante de alga marina marinada sazonada con un aderezo ligero de sésamo. Rico en minerales y yodo, es una adición nutritiva y deliciosa.",
                                "https://plus.unsplash.com/premium_photo-1700840833134-3f6ad783f8bb?w=500", true,
                                "entrada"));
                menu.put(22, new Plato(22, "Tofu Agedashi", 35960,
                                "Tofu sedoso suavemente frito hasta que el exterior se vuelva ligero y crujiente. Servido en un caldo dashi delicado con champiñones y cebolletas verdes.",
                                "https://images.unsplash.com/photo-1765295218809-784d6c2fe39c?w=500", true, "entrada"));
                menu.put(23, new Plato(23, "Yakisoba", 43960,
                                "Tallarines de trigo masticables cocidos en una plancha caliente con verduras coloridas y una salsa dulce y sabrosa. Los tallarines desarrollan bordes marrones crujientes mientras permanecen tiernos.",
                                "https://images.unsplash.com/photo-1624904025321-24e2f17d06ce?w=500", true, "pfuerte"));
                menu.put(24, new Plato(24, "Pollo Teriyaki", 51960,
                                "Pecho de pollo tierno esmaltado con una salsa teriyaki brillante que combina notas dulces y sabrosas. El glaseado espeso se carameliza durante la cocción.",
                                "https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?w=500", true, "pfuerte"));
                menu.put(25, new Plato(25, "Karaage", 39960,
                                "Piezas de pollo marinadas en una mezcla sabrosa de salsa de soya, jengibre y ajo, luego rebozadas en un rebozado ligero crujiente. El exterior cruje mientras la carne permanece jugosa en el interior.",
                                "https://images.unsplash.com/photo-1706513045864-e122d45f2f1d?w=500", true, "entrada"));
                menu.put(26, new Plato(26, "Tabla de Sashimi", 67960,
                                "Una presentación elegante de pescado crudo variado premium incluyendo salmón, atún y otras variedades de temporada. Cada pieza se selecciona por su calidad prístina y frescura excepcional.",
                                "https://images.unsplash.com/photo-1758384075930-6e3835d22b1d?w=500", true, "sushi"));
                menu.put(27, new Plato(27, "Rollo Arcoíris", 63960,
                                "Un rollo al revés espectacular relleno de verduras y cubierto con una variedad de pescado crudo fresco dispuesto en un patrón de arcoíris. El arreglo colorido hace que esto sea tanto una obra maestra artística como una experiencia culinaria deliciosa.",
                                "https://images.unsplash.com/photo-1636425730652-ffdbada1ed4d?w=500", true, "sushi"));
                menu.put(28, new Plato(28, "Tártara de Atún", 47960,
                                "Un aperitivo sofisticado con atún premium finamente picado combinado con una mezcla picante de condimentos y cítricos. Servido con chips de wonton crujientes o verduras frescas.",
                                "https://images.unsplash.com/photo-1656106577512-0259bf5b9fd6?w=500", true, "entrada"));
                menu.put(29, new Plato(29, "Maki de Pepino", 31960,
                                "Un rollo de sushi simple con pepino crujiente refrescante envuelto en arroz de sushi sazonado y alga nori. Esta opción vegetariana es ligera y saludable.",
                                "https://www.sushiya.in/cdn/shop/files/Cucumber_Maki.png?v=1742021465&width=500", true,
                                "sushi"));
                menu.put(30, new Plato(30, "Cheesecake Matcha", 27960,
                                "Un postre delicioso que mezcla las notas terrosas del té matcha verde premium con cheesecake rico y cremoso. La combinación crea un equilibrio sofisticado perfecto como final de tu experiencia.",
                                "https://plus.unsplash.com/premium_photo-1694599325857-24139cf22ace?w=500", true,
                                "postre"));
        }

        public Collection<Plato> getMenu() {
                return menu.values();
        }

        public Plato getById(Integer id) {
                return menu.get(id);
        }
}
