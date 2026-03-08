package com.maki.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Cliente;
import com.maki.web.entities.Plato;
import com.maki.web.repository.CategoriaRepository;
import com.maki.web.repository.ClienteRepository;
import com.maki.web.repository.PlatoRepository;

@Component
@Transactional
public class Dataloader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepo;
    @Autowired
    private PlatoRepository platoRepo;
    @Autowired
    private ClienteRepository clienteRepo;

    @Override
    public void run(String... args) throws Exception {

        Categoria entradas = categoriaRepo.save(new Categoria("Entradas"));
        Categoria platosFuertes = categoriaRepo.save(new Categoria("Platos fuertes"));
        Categoria sushi = categoriaRepo.save(new Categoria("Sushi"));
        Categoria postres = categoriaRepo.save(new Categoria("Postres"));
        Categoria bebidas = categoriaRepo.save(new Categoria("Bebidas"));

        clienteRepo.save(new Cliente("Miguel", "Vargas", "acha@acha.dev", "eveyzoe", "+57 314 852 7241", "Cra 123 #24-242B"));
        clienteRepo.save(new Cliente("Tomas", "Silva", "Neon@Zynth.dev", "StarlightSolos", "+57 316 776 6274", "Cra 53B #131A-72"));
        clienteRepo.save(new Cliente("Alex", "Aponte", "Alex@Ap.dev", "AlezElNaci", "+57 317 445 8921", "Cra 15 #45-67"));
        clienteRepo.save(new Cliente("Juan", "Vargas", "Pabon@GUI.dev", "GUIllermo", "+57 318 556 7832", "Cra 22 #89-145"));
        clienteRepo.save(new Cliente("Laura", "Martínez", "laura.m@email.com", "LauraMart99", "+57 319 667 8743", "Cra 30 #12-34"));
        clienteRepo.save(new Cliente("Diego", "López", "diego.lopez@email.com", "DiegoL2024", "+57 310 778 9654", "Cra 8 #56-78"));
        clienteRepo.save(new Cliente("Sofía", "Herrera", "sofia.h@email.com", "SofiaHe88", "+57 311 889 0765", "Cra 18 #90-23"));
        clienteRepo.save(new Cliente("Pablo", "Sánchez", "pablo.sanchez@email.com", "PabloSan77", "+57 312 990 1876", "Cra 25 #34-56"));
        clienteRepo.save(new Cliente("Marcela", "Pérez", "marcela.p@email.com", "MarcelaPerez55", "+57 313 101 2987", "Cra 11 #67-89"));
        clienteRepo.save(new Cliente("Javier", "Castro", "javier.c@email.com", "JavierCast44", "+57 314 212 3098", "Cra 20 #23-45"));


        Plato sushiVariado = new Plato("Sushi Variado", 51960,
                "Una hermosa combinación de nigiri y rollos con los mejores ingredientes. Cada pieza es cuidadosamente elaborada por nuestros chefs expertos de sushi con precisión y artesanía.",
                "https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500", true);
        sushiVariado.setCategoria(sushi);
        platoRepo.save(sushiVariado);
        Plato ramen = new Plato("Ramen", 47960,
                "Un tazón abundante con un caldo tonkotsu rico y tallarines tiernos. Cubierto con huevos cocidos, brotes de bambú y barriga de cerdo marinada.",
                "https://images.unsplash.com/photo-1638866281450-3933540af86a?w=500", true);
        ramen.setCategoria(platosFuertes);
        platoRepo.save(ramen);
        Plato tempura = new Plato("Tempura", 43960,
                "Verduras rebozadas crujientes y camarones suculentos fritos hasta obtener una perfección dorada. Servido con una salsa tradicional para mojar.",
                "https://images.unsplash.com/photo-1677743537607-f7fc9273ec4d?w=500", true);
        tempura.setCategoria(entradas);
        platoRepo.save(tempura);
        Plato tonkatsu = new Plato("Tonkatsu", 55960,
                "Una chuleta de cerdo premium rebozada en panko y frita profundamente hasta quedar dorada y crujiente. La carne en el interior permanece tierna y jugosa, cubierta con salsa sabrosa.",
                "https://images.unsplash.com/photo-1734775373504-ff24ea8419b2?w=500", true);
        tonkatsu.setCategoria(platosFuertes);
        platoRepo.save(tonkatsu);
        Plato gyoza = new Plato("Gyoza", 31960,
                "Delicadas empanadillas fritas en sartén rellenas de cerdo sazonado y verduras. Cada empanadilla está hecha a mano para asegurar el equilibrio ideal entre relleno y masa.",
                "https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500", true);
        gyoza.setCategoria(entradas);
        platoRepo.save(gyoza);
        Plato edamame = new Plato("Edamame", 23960,
                "Frijoles de soya jóvenes y frescos cocidos al vapor hasta quedar tiernos y ligeramente salados. Ricos en proteína, estos son un aperitivo saludable y delicioso.",
                "https://images.unsplash.com/photo-1575262599410-837a72005862?w=500", true);
        edamame.setCategoria(entradas);
        platoRepo.save(edamame);
        Plato sopaMiso = new Plato("Sopa Miso", 15960,
                "Una sopa japonesa tradicional hecha con pasta miso fermentada y caldo dashi sabroso. Tipicamente servida con tofu suave y alga marina.",
                "https://images.unsplash.com/photo-1610393069309-2607fcf74146?w=500", true);
        sopaMiso.setCategoria(entradas);
        platoRepo.save(sopaMiso);
        Plato rolloCalifornia = new Plato("Rollo California", 39960,
                "Rollo de sushi al revés con jurel imitado, aguacate cremoso y pepino fresco envuelto en arroz sazonado. Este rollo icónico ofrece un equilibrio perfecto de sabores y texturas.",
                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=500", true);
        rolloCalifornia.setCategoria(sushi);
        platoRepo.save(rolloCalifornia);
        Plato rolloDragon = new Plato("Rollo Dragón", 59960,
                "Un rollo especializado con anguila tierna y pepino crujiente envueltos en arroz con aguacate en la parte superior. La dulzura de la anguila combina hermosamente con el aguacate cremoso.",
                "https://images.unsplash.com/photo-1712192674556-4a89f20240c1?w=500", true);
        rolloDragon.setCategoria(sushi);
        platoRepo.save(rolloDragon);
        Plato rolloPhiladelphia = new Plato("Rollo Philadelphia", 47960,
                "Un rollo premium que combina salmón ahumado delicado con queso crema suave y pepino fresco. La combinación crea una mezcla lujosa que une las cocinas japonesa y occidental.",
                "https://images.unsplash.com/photo-1759646828324-c215a83828ae?w=500", true);
        rolloPhiladelphia.setCategoria(sushi);
        platoRepo.save(rolloPhiladelphia);
        Plato yakitori = new Plato("Yakitori", 43960,
                "Piezas de pollo tiernas a la parrilla en pinchos sobre carbón hasta quedar perfectamente cocidas y caramelizadas. Cubierto con un glaseado sabroso y dulce.",
                "https://images.unsplash.com/photo-1708597525178-6c302364f37c?w=500", true);
        yakitori.setCategoria(platosFuertes);
        platoRepo.save(yakitori);
        Plato okonomiyaki = new Plato("Okonomiyaki", 51960,
                "Un panqueque japonés salado hecho de masa y repollo cocido hasta obtener una perfección dorada. Cubierto con salsa sabrosa, mayonesa cremosa y copos de bonito.",
                "https://plus.unsplash.com/premium_photo-1722593856486-5f87f9fca308?w=500", true);
        okonomiyaki.setCategoria(platosFuertes);
        platoRepo.save(okonomiyaki);
        Plato takoyaki = new Plato("Takoyaki", 35960,
                "Esferas esponjosas y doradas de masa rebozada rellenas de pulpo tierno, jengibre encurtido y cebolletas verdes. Servidos calientes y rociados con salsa takoyaki y mayonesa.",
                "https://plus.unsplash.com/premium_photo-1722593856742-085ef5549070?w=500", true);
        takoyaki.setCategoria(entradas);
        platoRepo.save(takoyaki);
        Plato unagiDon = new Plato("Unagi Don", 55960,
                "Anguila suculenta a la parrilla esmaltada con salsa kabayaki dulce y sabrosa servida sobre arroz blanco esponjoso. La anguila se cocina hasta quedar tierna con caramelización brillante.",
                "https://japanesetaste.com.au/cdn/shop/articles/how-to-make-unagi-don-grilled-eel-rice-bowl-with-kabayaki-sauce-japanese-taste.jpg?v=1766642304&width=500",
                true);
        unagiDon.setCategoria(platosFuertes);
        platoRepo.save(unagiDon);
        Plato katsudon = new Plato("Katsudon", 51960,
                "Un tazón de arroz cubierto con una chuleta de cerdo dorada y crujiente y una mezcla de huevo sabrosa cocida a fuego lento. La combinación de texturas crea una experiencia culinaria inolvidable.",
                "https://images.unsplash.com/photo-1624517607896-bb5dfd8f5764?w=500", true);
        katsudon.setCategoria(platosFuertes);
        platoRepo.save(katsudon);
        Plato chirashi = new Plato("Chirashi", 59960,
                "Un impresionante tazón de sushi mixto con un surtido de pescado crudo premium, verduras y huevos dispuestos sobre arroz de sushi sazonado. Cada componente se selecciona cuidadosamente por su frescura y calidad.",
                "https://images.unsplash.com/photo-1565967531713-45739e0cad63?w=500", true);
        chirashi.setCategoria(sushi);
        platoRepo.save(chirashi);
        Plato sukiyaki = new Plato("Sukiyaki", 67960,
                "Una experiencia de olla caliente lujosa con carne de res premium, verduras frescas y tofu en un caldo dulce y sabroso. Los ingredientes se cocinan en la mesa, creando una experiencia comunitaria.",
                "https://images.unsplash.com/photo-1648977555545-4dd006e30d3f?w=500", true);
        sukiyaki.setCategoria(platosFuertes);
        platoRepo.save(sukiyaki);
        Plato shabuShabu = new Plato("Shabu Shabu", 63960,
                "Un plato de olla caliente interactivo donde rodajas ultrafinas de carne y verduras se agitan brevemente en un caldo hirviente. Cada comensal cocina sus ingredientes al nivel de cocción que prefiera.",
                "https://images.unsplash.com/photo-1559602580-78f1ba809b92?w=500", true);
        shabuShabu.setCategoria(platosFuertes);
        platoRepo.save(shabuShabu);
        Plato nigiriSalmón = new Plato("Nigiri de Salmón", 43960,
                "Salmón fresco premium delicadamente colocado sobre un montículo perfectamente formado de arroz de sushi sazonado. La textura sedosa del salmón crudo se derrite en el paladar.",
                "https://images.unsplash.com/photo-1680675228874-9b9963812b7c?w=500", true);
        nigiriSalmón.setCategoria(sushi);
        platoRepo.save(nigiriSalmón);
        Plato resWagyu = new Plato("Res Wagyu", 99960,
                "Exquisita carne de res premium japonesa conocida por su veteado y excepcional ternura que se derrite en la boca. Cada bocado revela el sabor rico y mantequilloso.",
                "https://images.unsplash.com/photo-1708388464912-d4ad82dca990?w=500", true);
        resWagyu.setCategoria(platosFuertes);
        platoRepo.save(resWagyu);
        Plato ensaladaAlgaMarina = new Plato("Ensalada de Alga Marina", 27960,
                "Una ensalada refrescante de alga marina marinada sazonada con un aderezo ligero de sésamo. Rico en minerales y yodo, es una adición nutritiva y deliciosa.",
                "https://plus.unsplash.com/premium_photo-1700840833134-3f6ad783f8bb?w=500", true);
        ensaladaAlgaMarina.setCategoria(entradas);
        platoRepo.save(ensaladaAlgaMarina);
        Plato tofuAgedashi = new Plato("Tofu Agedashi", 35960,
                "Tofu sedoso suavemente frito hasta que el exterior se vuelva ligero y crujiente. Servido en un caldo dashi delicado con champiñones y cebolletas verdes.",
                "https://images.unsplash.com/photo-1765295218809-784d6c2fe39c?w=500", true);
        tofuAgedashi.setCategoria(entradas);
        platoRepo.save(tofuAgedashi);
        Plato yakisoba = new Plato("Yakisoba", 43960,
                "Tallarines de trigo masticables cocidos en una plancha caliente con verduras coloridas y una salsa dulce y sabrosa. Los tallarines desarrollan bordes marrones crujientes mientras permanecen tiernos.",
                "https://images.unsplash.com/photo-1624904025321-24e2f17d06ce?w=500", true);
        yakisoba.setCategoria(platosFuertes);
        platoRepo.save(yakisoba);
        Plato polloTeriyaki = new Plato("Pollo Teriyaki", 51960,
                "Pecho de pollo tierno esmaltado con una salsa teriyaki brillante que combina notas dulces y sabrosas. El glaseado espeso se carameliza durante la cocción.",
                "https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?w=500", true);
        polloTeriyaki.setCategoria(platosFuertes);
        platoRepo.save(polloTeriyaki);
        Plato karaage = new Plato("Karaage", 39960,
                "Piezas de pollo marinadas en una mezcla sabrosa de salsa de soya, jengibre y ajo, luego rebozadas en un rebozado ligero crujiente. El exterior cruje mientras la carne permanece jugosa en el interior.",
                "https://images.unsplash.com/photo-1706513045864-e122d45f2f1d?w=500", true);
        karaage.setCategoria(entradas);
        platoRepo.save(karaage);
        Plato tablaSashimi = new Plato("Tabla de Sashimi", 67960,
                "Una presentación elegante de pescado crudo variado premium incluyendo salmón, atún y otras variedades de temporada. Cada pieza se selecciona por su calidad prístina y frescura excepcional.",
                "https://images.unsplash.com/photo-1758384075930-6e3835d22b1d?w=500", true);
        tablaSashimi.setCategoria(sushi);
        platoRepo.save(tablaSashimi);
        Plato rolloArcoiris = new Plato("Rollo Arcoíris", 63960,
                "Un rollo al revés espectacular relleno de verduras y cubierto con una variedad de pescado crudo fresco dispuesto en un patrón de arcoíris. El arreglo colorido hace que esto sea tanto una obra maestra artística como una experiencia culinaria deliciosa.",
                "https://images.unsplash.com/photo-1636425730652-ffdbada1ed4d?w=500", true);
        rolloArcoiris.setCategoria(sushi);
        platoRepo.save(rolloArcoiris);
        Plato tartaraAtun = new Plato("Tártara de Atún", 47960,
                "Un aperitivo sofisticado con atún premium finamente picado combinado con una mezcla picante de condimentos y cítricos. Servido con chips de wonton crujientes o verduras frescas.",
                "https://images.unsplash.com/photo-1656106577512-0259bf5b9fd6?w=500", true);
        tartaraAtun.setCategoria(entradas);
        platoRepo.save(tartaraAtun);
        Plato makiPepino = new Plato("Maki de Pepino", 31960,
                "Un rollo de sushi simple con pepino crujiente refrescante envuelto en arroz de sushi sazonado y alga nori. Esta opción vegetariana es ligera y saludable.",
                "https://www.sushiya.in/cdn/shop/files/Cucumber_Maki.png?v=1742021465&width=500", true);
        makiPepino.setCategoria(sushi);
        platoRepo.save(makiPepino);
        Plato cheesecakeMatcha = new Plato("Cheesecake Matcha", 27960,
                "Un postre delicioso que mezcla las notas terrosas del té matcha verde premium con cheesecake rico y cremoso. La combinación crea un equilibrio sofisticado perfecto como final de tu experiencia.",
                "https://plus.unsplash.com/premium_photo-1694599325857-24139cf22ace?w=500", true);
        cheesecakeMatcha.setCategoria(postres);
        platoRepo.save(cheesecakeMatcha);
        Plato motiHelado = new Plato("Moti Helado", 19960,
                        "Pequeñas esferas de arroz glutinoso rellenas de helado cremoso de sabores variados. Una textura única que combina lo suave del helado con la masticabilidad del moti.",
                        "https://www.elespectador.com/resizer/JbZ9LGO_ygDGxdnmvdEh-63br7g=/arc-anglerfish-arc2-prod-elespectador/public/EXYQ4FEM3RBHTPTQ7JNQ5NKREU.jpg", true);
        motiHelado.setCategoria(postres);
        platoRepo.save(motiHelado);
        Plato wontons = new Plato("Wontons de Chocolate", 31960,
                        "Deliciosos wontons fritos rellenos de una mezcla decadente de chocolate derretido. El exterior crujiente contrasta perfectamente con el interior suave y chocolatoso, creando un postre indulgente que es difícil de resistir.",
                        "https://www.umami.recipes/api/image/recipes/EZodnaVbHrA9KhS1qvaN/images/4PduTgOOsidjSZEVbUQZXM?w=3840&q=75", true);
        wontons.setCategoria(postres);
        platoRepo.save(wontons);
        Plato donut = new Plato("Donut de Matcha", 15960,
                        "Un donut cubierto con glaseado de matcha verde vibrante y relleno de crema suave. Una combinación deliciosa de frío y lo cálido, con sabor a matcha auténtico.",
                        "https://buenprovecho.hn/wp-content/uploads/2020/10/Donas_matcha.jpg", true);
        donut.setCategoria(postres);
        platoRepo.save(donut);
        Plato brownie = new Plato("Brownie de Chocolate", 23960,
                        "Un brownie denso y pegajoso hecho con chocolate premium de alta calidad. Servido caliente con helado de vainilla, es el postre de chocolate perfecto para los amantes del cacao.",
                        "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiFnc04hxbpnE4BATtLUfa7f2_oFZYqVnXaHxb9nkaiDgzf-U0qjBIInHXiHjV1sxrtiKFHXek6G_69WFoBpBPJwxCNCBI1OopcjF4C6UdxTgcBf59cq5JIWgTdtTaZHsR9NpwCTz-BmIk/s1600/20121209_151908.jpg", true);
        brownie.setCategoria(postres);
        platoRepo.save(brownie);

        Plato teVerde = new Plato("Té Verde", 7960,
        "Té verde tradicional japonés con un sabor fresco y ligero. Perfecto para acompañar tus platos favoritos.",
        "https://image.tuasaude.com/media/article/yp/dt/beneficios-del-te-verde_17350.jpg", true);
        teVerde.setCategoria(bebidas);
        platoRepo.save(teVerde);

        Plato sakeTradicional = new Plato("Sake Tradicional", 35960,
                "Bebida alcohólica japonesa elaborada con arroz fermentado. Suave con notas complejas de sabor.",
                "https://monstersushi.es/blog/wp-content/uploads/2022/04/sake-robata-barcelona-e1637227199971-1024x784-1.png", true);
        sakeTradicional.setCategoria(bebidas);
        platoRepo.save(sakeTradicional);

        Plato limonada = new Plato("Limonada Fresca", 9960,
                "Bebida refrescante y natural preparada con limón fresco y azúcar. Ideal para días calurosos.",
                "https://cdn.shopify.com/s/files/1/0191/9978/files/Como-hacer-limonada.jpg?v=1753088533", true);
        limonada.setCategoria(bebidas);
        platoRepo.save(limonada);

        Plato jugoNatural = new Plato("Jugos naturales", 11960,
                "Jugo natural de durazno con un sabor dulce y tropical. Hecho con fruta fresca de temporada.",
                "https://www.wikihow.com/images_en/thumb/b/b0/Peach-juice-Intro.jpg/v4-1200px-Peach-juice-Intro.jpg", true);
        jugoNatural.setCategoria(bebidas);
        platoRepo.save(jugoNatural);

        Plato aguaArroz = new Plato("Agua de Arroz", 5960,
                "Bebida tradicional refrescante hecha con arroz y un toque de vainilla. Suave y reconfortante.",
                "https://image.tuasaude.com/media/article/pc/nx/agua-de-arroz-para-la-diarrea_19076.jpg", true);
        aguaArroz.setCategoria(bebidas);
        platoRepo.save(aguaArroz);

        Plato smoothie = new Plato("Smoothies", 13960,
                "Selección de smoothies saludables y deliciosos hechos con frutas frescas, yogur y un toque de miel. Perfectos para un impulso de energía natural.",
                "https://saposyprincesas.elmundo.es/assets/2017/05/Batidos-verano.jpg", true);
        smoothie.setCategoria(bebidas);
        platoRepo.save(smoothie);
    }

}
