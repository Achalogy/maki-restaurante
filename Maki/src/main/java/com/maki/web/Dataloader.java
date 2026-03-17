package com.maki.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import com.maki.web.entities.Adicional;
import com.maki.web.entities.AdicionalCategoria;
import com.maki.web.entities.Categoria;
import com.maki.web.entities.Cliente;
import com.maki.web.entities.Plato;
import com.maki.web.repository.AdicionalCategoriaRepository;
import com.maki.web.repository.AdicionalRepository;
import com.maki.web.repository.CategoriaRepository;
import com.maki.web.repository.ClienteRepository;
import com.maki.web.repository.PlatoRepository;
import java.util.Random;

@Component
@Transactional
public class Dataloader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepo;
    @Autowired
    private PlatoRepository platoRepo;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private AdicionalRepository adicionalRepo;
    @Autowired
    private AdicionalCategoriaRepository adcatRepo;
    @Override
    public void run(String... args) throws Exception {

        Random random = new Random();
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
                "Hermosa combinación de nigiri y rollos con los mejores ingredientes.",
                "https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500", true);
        sushiVariado.setCategoria(sushi);
        platoRepo.save(sushiVariado);
        Plato ramen = new Plato("Ramen", 47960,
                "Tazón abundante con caldo tonkotsu rico, tallarines tiernos, huevos y barriga de cerdo marinada.",
                "https://images.unsplash.com/photo-1638866281450-3933540af86a?w=500", true);
        ramen.setCategoria(platosFuertes);
        platoRepo.save(ramen);
        Plato tempura = new Plato("Tempura", 43960,
                "Verduras y camarones fritos hasta obtener una perfección dorada con salsa tradicional.",
                "https://images.unsplash.com/photo-1677743537607-f7fc9273ec4d?w=500", true);
        tempura.setCategoria(entradas);
        platoRepo.save(tempura);
        Plato tonkatsu = new Plato("Tonkatsu", 55960,
                "Chuleta de cerdo premium rebozada en panko y frita hasta quedar dorada y crujiente.",
                "https://images.unsplash.com/photo-1734775373504-ff24ea8419b2?w=500", true);
        tonkatsu.setCategoria(platosFuertes);
        platoRepo.save(tonkatsu);
        Plato gyoza = new Plato("Gyoza", 31960,
                "Empanadillas fritas rellenas de cerdo sazonado y verduras, hechas a mano.",
                "https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500", true);
        gyoza.setCategoria(entradas);
        platoRepo.save(gyoza);
        Plato edamame = new Plato("Edamame", 23960,
                "Frijoles de soya jóvenes cocidos al vapor, ligeramente salados y ricos en proteína.",
                "https://images.unsplash.com/photo-1575262599410-837a72005862?w=500", true);
        edamame.setCategoria(entradas);
        platoRepo.save(edamame);
        Plato sopaMiso = new Plato("Sopa Miso", 15960,
                "Sopa tradicional japonesa con pasta miso fermentada, tofu y alga marina.",
                "https://images.unsplash.com/photo-1610393069309-2607fcf74146?w=500", true);
        sopaMiso.setCategoria(entradas);
        platoRepo.save(sopaMiso);
        Plato rolloCalifornia = new Plato("Rollo California", 39960,
                "Rollo al revés con jurel imitado, aguacate y pepino fresco envuelto en arroz.",
                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=500", true);
        rolloCalifornia.setCategoria(sushi);
        platoRepo.save(rolloCalifornia);
        Plato rolloDragon = new Plato("Rollo Dragón", 59960,
                "Rollo especializado con anguila tierna, pepino crujiente y aguacate en la parte superior.",
                "https://images.unsplash.com/photo-1712192674556-4a89f20240c1?w=500", true);
        rolloDragon.setCategoria(sushi);
        platoRepo.save(rolloDragon);
        Plato rolloPhiladelphia = new Plato("Rollo Philadelphia", 47960,
                "Rollo premium con salmón ahumado, queso crema suave y pepino fresco.",
                "https://images.unsplash.com/photo-1759646828324-c215a83828ae?w=500", true);
        rolloPhiladelphia.setCategoria(sushi);
        platoRepo.save(rolloPhiladelphia);
        Plato yakitori = new Plato("Yakitori", 43960,
                "Piezas de pollo a la parrilla en pinchos sobre carbón con glaseado sabroso y dulce.",
                "https://images.unsplash.com/photo-1708597525178-6c302364f37c?w=500", true);
        yakitori.setCategoria(platosFuertes);
        platoRepo.save(yakitori);
        Plato okonomiyaki = new Plato("Okonomiyaki", 51960,
                "Panqueque japonés salado hecho de masa y repollo con salsa, mayonesa y copos de bonito.",
                "https://plus.unsplash.com/premium_photo-1722593856486-5f87f9fca308?w=500", true);
        okonomiyaki.setCategoria(platosFuertes);
        platoRepo.save(okonomiyaki);
        Plato takoyaki = new Plato("Takoyaki", 35960,
                "Esferas doradas de masa rellenas de pulpo tierno, jengibre y cebolletas con salsa takoyaki.",
                "https://plus.unsplash.com/premium_photo-1722593856742-085ef5549070?w=500", true);
        takoyaki.setCategoria(entradas);
        platoRepo.save(takoyaki);
        Plato unagiDon = new Plato("Unagi Don", 55960,
                "Anguila a la parrilla esmaltada con salsa kabayaki servida sobre arroz blanco esponjoso.",
                "https://japanesetaste.com.au/cdn/shop/articles/how-to-make-unagi-don-grilled-eel-rice-bowl-with-kabayaki-sauce-japanese-taste.jpg?v=1766642304&width=500",
                true);
        unagiDon.setCategoria(platosFuertes);
        platoRepo.save(unagiDon);
        Plato katsudon = new Plato("Katsudon", 51960,
                "Tazón de arroz con chuleta de cerdo dorada, crujiente y cubierto con mezcla de huevo.",
                "https://images.unsplash.com/photo-1624517607896-bb5dfd8f5764?w=500", true);
        katsudon.setCategoria(platosFuertes);
        platoRepo.save(katsudon);
        Plato chirashi = new Plato("Chirashi", 59960,
                "Tazón de sushi mixto con pescado crudo premium, verduras y huevos sobre arroz de sushi.",
                "https://images.unsplash.com/photo-1565967531713-45739e0cad63?w=500", true);
        chirashi.setCategoria(sushi);
        platoRepo.save(chirashi);
        Plato sukiyaki = new Plato("Sukiyaki", 67960,
                "Olla caliente lujosa con carne de res premium, verduras y tofu en caldo dulce y sabroso.",
                "https://images.unsplash.com/photo-1648977555545-4dd006e30d3f?w=500", true);
        sukiyaki.setCategoria(platosFuertes);
        platoRepo.save(sukiyaki);
        Plato shabuShabu = new Plato("Shabu Shabu", 63960,
                "Olla caliente interactiva donde rodajas ultrafinas de carne y verduras se agitan en caldo hirviente.",
                "https://images.unsplash.com/photo-1559602580-78f1ba809b92?w=500", true);
        shabuShabu.setCategoria(platosFuertes);
        platoRepo.save(shabuShabu);
        Plato nigiriSalmón = new Plato("Nigiri de Salmón", 43960,
                "Salmón fresco premium delicadamente colocado sobre montículo de arroz de sushi sazonado.",
                "https://images.unsplash.com/photo-1680675228874-9b9963812b7c?w=500", true);
        nigiriSalmón.setCategoria(sushi);
        platoRepo.save(nigiriSalmón);
        Plato resWagyu = new Plato("Res Wagyu", 99960,
                "Carne de res premium japonesa conocida por su veteado excepcional y ternura que se derrite en la boca.",
                "https://images.unsplash.com/photo-1708388464912-d4ad82dca990?w=500", true);
        resWagyu.setCategoria(platosFuertes);
        platoRepo.save(resWagyu);
        Plato ensaladaAlgaMarina = new Plato("Ensalada de Alga Marina", 27960,
                "Ensalada refrescante de alga marina marinada con aderezo ligero de sésamo.",
                "https://plus.unsplash.com/premium_photo-1700840833134-3f6ad783f8bb?w=500", true);
        ensaladaAlgaMarina.setCategoria(entradas);
        platoRepo.save(ensaladaAlgaMarina);
        Plato tofuAgedashi = new Plato("Tofu Agedashi", 35960,
                "Tofu sedoso frito con exterior crujiente, servido en caldo dashi con champiñones y cebolletas.",
                "https://images.unsplash.com/photo-1765295218809-784d6c2fe39c?w=500", true);
        tofuAgedashi.setCategoria(entradas);
        platoRepo.save(tofuAgedashi);
        Plato yakisoba = new Plato("Yakisoba", 43960,
                "Tallarines de trigo masticables cocidos en plancha caliente con verduras y salsa dulce y sabrosa.",
                "https://images.unsplash.com/photo-1624904025321-24e2f17d06ce?w=500", true);
        yakisoba.setCategoria(platosFuertes);
        platoRepo.save(yakisoba);
        Plato polloTeriyaki = new Plato("Pollo Teriyaki", 51960,
                "Pecho de pollo tierno esmaltado con salsa teriyaki brillante y caramelizada.",
                "https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?w=500", true);
        polloTeriyaki.setCategoria(platosFuertes);
        platoRepo.save(polloTeriyaki);
        Plato karaage = new Plato("Karaage", 39960,
                "Piezas de pollo marinadas y rebozadas en rebozado crujiente, jugosas en el interior.",
                "https://images.unsplash.com/photo-1706513045864-e122d45f2f1d?w=500", true);
        karaage.setCategoria(entradas);
        platoRepo.save(karaage);
        Plato tablaSashimi = new Plato("Tabla de Sashimi", 67960,
                "Presentación elegante de pescado crudo variado premium seleccionado por calidad y frescura.",
                "https://images.unsplash.com/photo-1758384075930-6e3835d22b1d?w=500", true);
        tablaSashimi.setCategoria(sushi);
        platoRepo.save(tablaSashimi);
        Plato rolloArcoiris = new Plato("Rollo Arcoíris", 63960,
                "Rollo al revés espectacular cubierto con variedad de pescado crudo dispuesto en patrón de arcoíris.",
                "https://images.unsplash.com/photo-1636425730652-ffdbada1ed4d?w=500", true);
        rolloArcoiris.setCategoria(sushi);
        platoRepo.save(rolloArcoiris);
        Plato tartaraAtun = new Plato("Tártara de Atún", 47960,
                "Aperitivo sofisticado con atún premium finamente picado combinado con condimentos y cítricos.",
                "https://images.unsplash.com/photo-1656106577512-0259bf5b9fd6?w=500", true);
        tartaraAtun.setCategoria(entradas);
        platoRepo.save(tartaraAtun);
        Plato makiPepino = new Plato("Maki de Pepino", 31960,
                "Rollo de sushi simple con pepino crujiente refrescante, opción vegetariana ligera y saludable.",
                "https://www.sushiya.in/cdn/shop/files/Cucumber_Maki.png?v=1742021465&width=500", true);
        makiPepino.setCategoria(sushi);
        platoRepo.save(makiPepino);
        Plato cheesecakeMatcha = new Plato("Cheesecake Matcha", 27960,
                "Cheesecake rico y cremoso con notas terrosas del té matcha verde premium.",
                "https://plus.unsplash.com/premium_photo-1694599325857-24139cf22ace?w=500", true);
        cheesecakeMatcha.setCategoria(postres);
        platoRepo.save(cheesecakeMatcha);
        Plato motiHelado = new Plato("Moti Helado", 19960,
                "Pequeñas esferas de arroz glutinoso rellenas de helado cremoso con textura única.",
                "https://www.elespectador.com/resizer/JbZ9LGO_ygDGxdnmvdEh-63br7g=/arc-anglerfish-arc2-prod-elespectador/public/EXYQ4FEM3RBHTPTQ7JNQ5NKREU.jpg", true);
        motiHelado.setCategoria(postres);
        platoRepo.save(motiHelado);
        Plato wontons = new Plato("Wontons de Chocolate", 31960,
                "Wontons fritos rellenos de chocolate derretido con exterior crujiente e interior suave.",
                "https://www.umami.recipes/api/image/recipes/EZodnaVbHrA9KhS1qvaN/images/4PduTgOOsidjSZEVbUQZXM?w=3840&q=75", true);
        wontons.setCategoria(postres);
        platoRepo.save(wontons);
        Plato donut = new Plato("Donut de Matcha", 15960,
                "Donut cubierto con glaseado de matcha verde vibrante y relleno de crema suave.",
                "https://buenprovecho.hn/wp-content/uploads/2020/10/Donas_matcha.jpg", true);
        donut.setCategoria(postres);
        platoRepo.save(donut);
        Plato brownie = new Plato("Brownie de Chocolate", 23960,
                "Brownie denso y pegajoso hecho con chocolate premium, servido caliente con helado de vainilla.",
                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiFnc04hxbpnE4BATtLUfa7f2_oFZYqVnXaHxb9nkaiDgzf-U0qjBIInHXiHjV1sxrtiKFHXek6G_69WFoBpBPJwxCNCBI1OopcjF4C6UdxTgcBf59cq5JIWgTdtTaZHsR9NpwCTz-BmIk/s1600/20121209_151908.jpg", true);
        brownie.setCategoria(postres);
        platoRepo.save(brownie);

        Plato teVerde = new Plato("Té Verde", 7960,
        "Té verde tradicional japonés con sabor fresco y ligero.",
        "https://image.tuasaude.com/media/article/yp/dt/beneficios-del-te-verde_17350.jpg", true);
        teVerde.setCategoria(bebidas);
        platoRepo.save(teVerde);

        Plato sakeTradicional = new Plato("Sake Tradicional", 35960,
                "Bebida alcohólica japonesa elaborada con arroz fermentado con notas complejas de sabor.",
                "https://monstersushi.es/blog/wp-content/uploads/2022/04/sake-robata-barcelona-e1637227199971-1024x784-1.png", true);
        sakeTradicional.setCategoria(bebidas);
        platoRepo.save(sakeTradicional);

        Plato limonada = new Plato("Limonada Fresca", 9960,
                "Bebida refrescante y natural preparada con limón fresco y azúcar.",
                "https://cdn.shopify.com/s/files/1/0191/9978/files/Como-hacer-limonada.jpg?v=1753088533", true);
        limonada.setCategoria(bebidas);
        platoRepo.save(limonada);

        Plato jugoNatural = new Plato("Jugos naturales", 11960,
                "Jugo natural de durazno con sabor dulce y tropical hecho con fruta fresca de temporada.",
                "https://www.wikihow.com/images_en/thumb/b/b0/Peach-juice-Intro.jpg/v4-1200px-Peach-juice-Intro.jpg", true);
        jugoNatural.setCategoria(bebidas);
        platoRepo.save(jugoNatural);

        Plato aguaArroz = new Plato("Agua de Arroz", 5960,
                "Bebida tradicional refrescante hecha con arroz y un toque de vainilla.",
                "https://image.tuasaude.com/media/article/pc/nx/agua-de-arroz-para-la-diarrea_19076.jpg", true);
        aguaArroz.setCategoria(bebidas);
        platoRepo.save(aguaArroz);

        Plato smoothie = new Plato("Smoothies", 13960,
                "Smoothies saludables hechos con frutas frescas, yogur y miel para un impulso natural de energía.",
                "https://saposyprincesas.elmundo.es/assets/2017/05/Batidos-verano.jpg", true);
        smoothie.setCategoria(bebidas);
        platoRepo.save(smoothie);

        Adicional algaNori = new Adicional("Alga Nori", 3960);
        adicionalRepo.save(algaNori);

        Adicional sésamo = new Adicional("Semillas de Sésamo", 2960);
        adicionalRepo.save(sésamo);

        Adicional rayu = new Adicional("Rayu", 2960);
        adicionalRepo.save(rayu);

        Adicional jengibre = new Adicional("Jengibre Encurtido", 1960);
        adicionalRepo.save(jengibre);

        Adicional cebolleta = new Adicional("Cebolleta Fresca", 1960);
        adicionalRepo.save(cebolleta);

        Adicional mayo = new Adicional("Mayonesa Japonesa", 2960);
        adicionalRepo.save(mayo);

        Adicional sriracha = new Adicional("Sriracha", 2960);
        adicionalRepo.save(sriracha);

        Adicional aguacate = new Adicional("Aguacate Extra", 5960);
        adicionalRepo.save(aguacate);        

        Adicional panko = new Adicional("Panko Crujiente", 3960);
        adicionalRepo.save(panko);

        Adicional curry = new Adicional("Curry Japonés", 4960);
        adicionalRepo.save(curry);

        Adicional wasabi = new Adicional("Wasabi", 3960);
        adicionalRepo.save(wasabi);

        Adicional salsasoja = new Adicional("Salsa de Soja", 1960);
        adicionalRepo.save(salsasoja);

        Adicional teriyaki = new Adicional("Salsa Teriyaki", 3960);
        adicionalRepo.save(teriyaki);

        Adicional bonito = new Adicional("Copos de Bonito", 4960);
        adicionalRepo.save(bonito);

        Adicional ponzu = new Adicional("Salsa Ponzu", 2960);
        adicionalRepo.save(ponzu);

        int cantidadCategorias = (int) categoriaRepo.findAll().size();

        for(Adicional adicional : adicionalRepo.findAll()) {
                int randomNum = random.nextInt(1, cantidadCategorias + 1);
                adcatRepo.save(new AdicionalCategoria((long) randomNum, adicional.getId()));
        }
        
        
    }

}
