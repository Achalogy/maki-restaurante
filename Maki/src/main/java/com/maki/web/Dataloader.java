package com.maki.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import com.maki.web.entities.Aditional;
import com.maki.web.entities.AditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.entities.Client;
import com.maki.web.entities.Operator;
import com.maki.web.entities.Administrator;
import com.maki.web.entities.Plate;
import com.maki.web.entities.Order;
import com.maki.web.entities.OrderDetails;
import com.maki.web.entities.Delivery;
import com.maki.web.entities.AditionalOrderDetails;
import com.maki.web.repository.AdicionalCategoriaRepository;
import com.maki.web.repository.AdicionalPedidoDetallesRepository;
import com.maki.web.repository.AdicionalRepository;
import com.maki.web.repository.AdministradorRepository;
import com.maki.web.repository.CategoriaRepository;
import com.maki.web.repository.ClienteRepository;
import com.maki.web.repository.OperadorRepository;
import com.maki.web.repository.PedidoDetallesRepository;
import com.maki.web.repository.PlatoRepository;
import com.maki.web.service.PedidoService;
import com.maki.web.service.PlatoService;
import com.maki.web.repository.PedidoRepository;
import com.maki.web.repository.DomiciliarioRepository;
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
        @Autowired
        private AdministradorRepository adminRep;
        @Autowired
        private OperadorRepository operadorRepo;
        @Autowired
        private PedidoRepository pedidoRepo;
        @Autowired
        private PedidoDetallesRepository pedidoDetallesRepo;

        @Autowired
        private DomiciliarioRepository domiciliarioRepo;
        @Autowired
        private AdicionalPedidoDetallesRepository adicionalPedidoDetallesRepo;

        @Override
        public void run(String... args) throws Exception {

                Random random = new Random();
                Category entradas = categoriaRepo.save(new Category("Entradas"));
                Category platosFuertes = categoriaRepo.save(new Category("Platos fuertes"));
                Category sushi = categoriaRepo.save(new Category("Sushi"));
                Category postres = categoriaRepo.save(new Category("Postres"));
                Category bebidas = categoriaRepo.save(new Category("Bebidas"));

                clienteRepo.save(new Client("Miguel", "Vargas", "acha@acha.dev", "eveyzoe", "+57 314 852 7241",
                                "Cra 123 #24-242B"));
                clienteRepo.save(new Client("Tomas", "Silva", "Neon@Zynth.dev", "StarlightSolos", "+57 316 776 6274",
                                "Cra 53B #131A-72"));
                clienteRepo.save(new Client("Alex", "Aponte", "Alex@Ap.dev", "AlezElNaci", "+57 317 445 8921",
                                "Cra 15 #45-67"));
                clienteRepo.save(new Client("Juan", "Vargas", "Pabon@GUI.dev", "GUIllermo", "+57 318 556 7832",
                                "Cra 22 #89-145"));
                clienteRepo.save(new Client("Laura", "Martínez", "laura.m@email.com", "LauraMart99",
                                "+57 319 667 8743", "Cra 30 #12-34"));
                clienteRepo.save(new Client("Diego", "López", "diego.lopez@email.com", "DiegoL2024",
                                "+57 310 778 9654", "Cra 8 #56-78"));
                clienteRepo.save(new Client("Sofía", "Herrera", "sofia.h@email.com", "SofiaHe88", "+57 311 889 0765",
                                "Cra 18 #90-23"));
                clienteRepo.save(new Client("Pablo", "Sánchez", "pablo.sanchez@email.com", "PabloSan77",
                                "+57 312 990 1876", "Cra 25 #34-56"));
                clienteRepo.save(new Client("Marcela", "Pérez", "marcela.p@email.com", "MarcelaPerez55",
                                "+57 313 101 2987", "Cra 11 #67-89"));
                clienteRepo.save(new Client("Javier", "Castro", "javier.c@email.com", "JavierCast44",
                                "+57 314 212 3098", "Cra 20 #23-45"));

                Plate sushiVariado = new Plate("Sushi Variado", 51960,
                                "Hermosa combinación de nigiri y rollos con los mejores ingredientes.",
                                "https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500", true);
                sushiVariado.setCategory(sushi);
                platoRepo.save(sushiVariado);
                Plate ramen = new Plate("Ramen", 47960,
                                "Tazón abundante con caldo tonkotsu rico, tallarines tiernos, huevos y barriga de cerdo marinada.",
                                "https://images.unsplash.com/photo-1638866281450-3933540af86a?w=500", true);
                ramen.setCategory(platosFuertes);
                platoRepo.save(ramen);
                Plate tempura = new Plate("Tempura", 43960,
                                "Verduras y camarones fritos hasta obtener una perfección dorada con salsa tradicional.",
                                "https://images.unsplash.com/photo-1677743537607-f7fc9273ec4d?w=500", true);
                tempura.setCategory(entradas);
                platoRepo.save(tempura);
                Plate tonkatsu = new Plate("Tonkatsu", 55960,
                                "Chuleta de cerdo premium rebozada en panko y frita hasta quedar dorada y crujiente.",
                                "https://images.unsplash.com/photo-1734775373504-ff24ea8419b2?w=500", true);
                tonkatsu.setCategory(platosFuertes);
                platoRepo.save(tonkatsu);
                Plate gyoza = new Plate("Gyoza", 31960,
                                "Empanadillas fritas rellenas de cerdo sazonado y verduras, hechas a mano.",
                                "https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500", true);
                gyoza.setCategory(entradas);
                platoRepo.save(gyoza);
                Plate edamame = new Plate("Edamame", 23960,
                                "Frijoles de soya jóvenes cocidos al vapor, ligeramente salados y ricos en proteína.",
                                "https://images.unsplash.com/photo-1575262599410-837a72005862?w=500", true);
                edamame.setCategory(entradas);
                platoRepo.save(edamame);
                Plate sopaMiso = new Plate("Sopa Miso", 15960,
                                "Sopa tradicional japonesa con pasta miso fermentada, tofu y alga marina.",
                                "https://images.unsplash.com/photo-1610393069309-2607fcf74146?w=500", true);
                sopaMiso.setCategory(entradas);
                platoRepo.save(sopaMiso);
                Plate rolloCalifornia = new Plate("Rollo California", 39960,
                                "Rollo al revés con jurel imitado, aguacate y pepino fresco envuelto en arroz.",
                                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=500", true);
                rolloCalifornia.setCategory(sushi);
                platoRepo.save(rolloCalifornia);
                Plate rolloDragon = new Plate("Rollo Dragón", 59960,
                                "Rollo especializado con anguila tierna, pepino crujiente y aguacate en la parte superior.",
                                "https://images.unsplash.com/photo-1712192674556-4a89f20240c1?w=500", true);
                rolloDragon.setCategory(sushi);
                platoRepo.save(rolloDragon);
                Plate rolloPhiladelphia = new Plate("Rollo Philadelphia", 47960,
                                "Rollo premium con salmón ahumado, queso crema suave y pepino fresco.",
                                "https://images.unsplash.com/photo-1759646828324-c215a83828ae?w=500", true);
                rolloPhiladelphia.setCategory(sushi);
                platoRepo.save(rolloPhiladelphia);
                Plate yakitori = new Plate("Yakitori", 43960,
                                "Piezas de pollo a la parrilla en pinchos sobre carbón con glaseado sabroso y dulce.",
                                "https://images.unsplash.com/photo-1708597525178-6c302364f37c?w=500", true);
                yakitori.setCategory(platosFuertes);
                platoRepo.save(yakitori);
                Plate okonomiyaki = new Plate("Okonomiyaki", 51960,
                                "Panqueque japonés salado hecho de masa y repollo con salsa, mayonesa y copos de bonito.",
                                "https://plus.unsplash.com/premium_photo-1722593856486-5f87f9fca308?w=500", true);
                okonomiyaki.setCategory(platosFuertes);
                platoRepo.save(okonomiyaki);
                Plate takoyaki = new Plate("Takoyaki", 35960,
                                "Esferas doradas de masa rellenas de pulpo tierno, jengibre y cebolletas con salsa takoyaki.",
                                "https://plus.unsplash.com/premium_photo-1722593856742-085ef5549070?w=500", true);
                takoyaki.setCategory(entradas);
                platoRepo.save(takoyaki);
                Plate unagiDon = new Plate("Unagi Don", 55960,
                                "Anguila a la parrilla esmaltada con salsa kabayaki servida sobre arroz blanco esponjoso.",
                                "https://japanesetaste.com.au/cdn/shop/articles/how-to-make-unagi-don-grilled-eel-rice-bowl-with-kabayaki-sauce-japanese-taste.jpg?v=1766642304&width=500",
                                true);
                unagiDon.setCategory(platosFuertes);
                platoRepo.save(unagiDon);
                Plate katsudon = new Plate("Katsudon", 51960,
                                "Tazón de arroz con chuleta de cerdo dorada, crujiente y cubierto con mezcla de huevo.",
                                "https://images.unsplash.com/photo-1624517607896-bb5dfd8f5764?w=500", true);
                katsudon.setCategory(platosFuertes);
                platoRepo.save(katsudon);
                Plate chirashi = new Plate("Chirashi", 59960,
                                "Tazón de sushi mixto con pescado crudo premium, verduras y huevos sobre arroz de sushi.",
                                "https://images.unsplash.com/photo-1565967531713-45739e0cad63?w=500", true);
                chirashi.setCategory(sushi);
                platoRepo.save(chirashi);
                Plate sukiyaki = new Plate("Sukiyaki", 67960,
                                "Olla caliente lujosa con carne de res premium, verduras y tofu en caldo dulce y sabroso.",
                                "https://images.unsplash.com/photo-1648977555545-4dd006e30d3f?w=500", true);
                sukiyaki.setCategory(platosFuertes);
                platoRepo.save(sukiyaki);
                Plate shabuShabu = new Plate("Shabu Shabu", 63960,
                                "Olla caliente interactiva donde rodajas ultrafinas de carne y verduras se agitan en caldo hirviente.",
                                "https://images.unsplash.com/photo-1559602580-78f1ba809b92?w=500", true);
                shabuShabu.setCategory(platosFuertes);
                platoRepo.save(shabuShabu);
                Plate nigiriSalmón = new Plate("Nigiri de Salmón", 43960,
                                "Salmón fresco premium delicadamente colocado sobre montículo de arroz de sushi sazonado.",
                                "https://images.unsplash.com/photo-1680675228874-9b9963812b7c?w=500", true);
                nigiriSalmón.setCategory(sushi);
                platoRepo.save(nigiriSalmón);
                Plate resWagyu = new Plate("Res Wagyu", 99960,
                                "Carne de res premium japonesa conocida por su veteado excepcional y ternura que se derrite en la boca.",
                                "https://images.unsplash.com/photo-1708388464912-d4ad82dca990?w=500", true);
                resWagyu.setCategory(platosFuertes);
                platoRepo.save(resWagyu);
                Plate ensaladaAlgaMarina = new Plate("Ensalada de Alga Marina", 27960,
                                "Ensalada refrescante de alga marina marinada con aderezo ligero de sésamo.",
                                "https://plus.unsplash.com/premium_photo-1700840833134-3f6ad783f8bb?w=500", true);
                ensaladaAlgaMarina.setCategory(entradas);
                platoRepo.save(ensaladaAlgaMarina);
                Plate tofuAgedashi = new Plate("Tofu Agedashi", 35960,
                                "Tofu sedoso frito con exterior crujiente, servido en caldo dashi con champiñones y cebolletas.",
                                "https://images.unsplash.com/photo-1765295218809-784d6c2fe39c?w=500", true);
                tofuAgedashi.setCategory(entradas);
                platoRepo.save(tofuAgedashi);
                Plate yakisoba = new Plate("Yakisoba", 43960,
                                "Tallarines de trigo masticables cocidos en plancha caliente con verduras y salsa dulce y sabrosa.",
                                "https://images.unsplash.com/photo-1624904025321-24e2f17d06ce?w=500", true);
                yakisoba.setCategory(platosFuertes);
                platoRepo.save(yakisoba);
                Plate polloTeriyaki = new Plate("Pollo Teriyaki", 51960,
                                "Pecho de pollo tierno esmaltado con salsa teriyaki brillante y caramelizada.",
                                "https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?w=500", true);
                polloTeriyaki.setCategory(platosFuertes);
                platoRepo.save(polloTeriyaki);
                Plate karaage = new Plate("Karaage", 39960,
                                "Piezas de pollo marinadas y rebozadas en rebozado crujiente, jugosas en el interior.",
                                "https://images.unsplash.com/photo-1706513045864-e122d45f2f1d?w=500", true);
                karaage.setCategory(entradas);
                platoRepo.save(karaage);
                Plate tablaSashimi = new Plate("Tabla de Sashimi", 67960,
                                "Presentación elegante de pescado crudo variado premium seleccionado por calidad y frescura.",
                                "https://images.unsplash.com/photo-1758384075930-6e3835d22b1d?w=500", true);
                tablaSashimi.setCategory(sushi);
                platoRepo.save(tablaSashimi);
                Plate rolloArcoiris = new Plate("Rollo Arcoíris", 63960,
                                "Rollo al revés espectacular cubierto con variedad de pescado crudo dispuesto en patrón de arcoíris.",
                                "https://images.unsplash.com/photo-1636425730652-ffdbada1ed4d?w=500", true);
                rolloArcoiris.setCategory(sushi);
                platoRepo.save(rolloArcoiris);
                Plate tartaraAtun = new Plate("Tártara de Atún", 47960,
                                "Aperitivo sofisticado con atún premium finamente picado combinado con condimentos y cítricos.",
                                "https://images.unsplash.com/photo-1656106577512-0259bf5b9fd6?w=500", true);
                tartaraAtun.setCategory(entradas);
                platoRepo.save(tartaraAtun);
                Plate makiPepino = new Plate("Maki de Pepino", 31960,
                                "Rollo de sushi simple con pepino crujiente refrescante, opción vegetariana ligera y saludable.",
                                "https://www.sushiya.in/cdn/shop/files/Cucumber_Maki.png?v=1742021465&width=500", true);
                makiPepino.setCategory(sushi);
                platoRepo.save(makiPepino);
                Plate cheesecakeMatcha = new Plate("Cheesecake Matcha", 27960,
                                "Cheesecake rico y cremoso con notas terrosas del té matcha verde premium.",
                                "https://plus.unsplash.com/premium_photo-1694599325857-24139cf22ace?w=500", true);
                cheesecakeMatcha.setCategory(postres);
                platoRepo.save(cheesecakeMatcha);
                Plate motiHelado = new Plate("Moti Helado", 19960,
                                "Pequeñas esferas de arroz glutinoso rellenas de helado cremoso con textura única.",
                                "https://www.elespectador.com/resizer/JbZ9LGO_ygDGxdnmvdEh-63br7g=/arc-anglerfish-arc2-prod-elespectador/public/EXYQ4FEM3RBHTPTQ7JNQ5NKREU.jpg",
                                true);
                motiHelado.setCategory(postres);
                platoRepo.save(motiHelado);
                Plate wontons = new Plate("Wontons de Chocolate", 31960,
                                "Wontons fritos rellenos de chocolate derretido con exterior crujiente e interior suave.",
                                "https://www.umami.recipes/api/image/recipes/EZodnaVbHrA9KhS1qvaN/images/4PduTgOOsidjSZEVbUQZXM?w=3840&q=75",
                                true);
                wontons.setCategory(postres);
                platoRepo.save(wontons);
                Plate donut = new Plate("Donut de Matcha", 15960,
                                "Donut cubierto con glaseado de matcha verde vibrante y relleno de crema suave.",
                                "https://buenprovecho.hn/wp-content/uploads/2020/10/Donas_matcha.jpg", true);
                donut.setCategory(postres);
                platoRepo.save(donut);
                Plate brownie = new Plate("Brownie de Chocolate", 23960,
                                "Brownie denso y pegajoso hecho con chocolate premium, servido caliente con helado de vainilla.",
                                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiFnc04hxbpnE4BATtLUfa7f2_oFZYqVnXaHxb9nkaiDgzf-U0qjBIInHXiHjV1sxrtiKFHXek6G_69WFoBpBPJwxCNCBI1OopcjF4C6UdxTgcBf59cq5JIWgTdtTaZHsR9NpwCTz-BmIk/s1600/20121209_151908.jpg",
                                true);
                brownie.setCategory(postres);
                platoRepo.save(brownie);

                Plate teVerde = new Plate("Té Verde", 7960,
                                "Té verde tradicional japonés con sabor fresco y ligero.",
                                "https://image.tuasaude.com/media/article/yp/dt/beneficios-del-te-verde_17350.jpg",
                                true);
                teVerde.setCategory(bebidas);
                platoRepo.save(teVerde);

                Plate sakeTradicional = new Plate("Sake Tradicional", 35960,
                                "Bebida alcohólica japonesa elaborada con arroz fermentado con notas complejas de sabor.",
                                "https://monstersushi.es/blog/wp-content/uploads/2022/04/sake-robata-barcelona-e1637227199971-1024x784-1.png",
                                true);
                sakeTradicional.setCategory(bebidas);
                platoRepo.save(sakeTradicional);

                Plate limonada = new Plate("Limonada Fresca", 9960,
                                "Bebida refrescante y natural preparada con limón fresco y azúcar.",
                                "https://cdn.shopify.com/s/files/1/0191/9978/files/Como-hacer-limonada.jpg?v=1753088533",
                                true);
                limonada.setCategory(bebidas);
                platoRepo.save(limonada);

                Plate jugoNatural = new Plate("Jugos naturales", 11960,
                                "Jugo natural de durazno con sabor dulce y tropical hecho con fruta fresca de temporada.",
                                "https://www.wikihow.com/images_en/thumb/b/b0/Peach-juice-Intro.jpg/v4-1200px-Peach-juice-Intro.jpg",
                                true);
                jugoNatural.setCategory(bebidas);
                platoRepo.save(jugoNatural);

                Plate aguaArroz = new Plate("Agua de Arroz", 5960,
                                "Bebida tradicional refrescante hecha con arroz y un toque de vainilla.",
                                "https://image.tuasaude.com/media/article/pc/nx/agua-de-arroz-para-la-diarrea_19076.jpg",
                                true);
                aguaArroz.setCategory(bebidas);
                platoRepo.save(aguaArroz);

                Plate smoothie = new Plate("Smoothies", 13960,
                                "Smoothies saludables hechos con frutas frescas, yogur y miel para un impulso natural de energía.",
                                "https://saposyprincesas.elmundo.es/assets/2017/05/Batidos-verano.jpg", true);
                smoothie.setCategory(bebidas);
                platoRepo.save(smoothie);

                Aditional algaNori = new Aditional("Alga Nori", 3960);
                adicionalRepo.save(algaNori);

                Aditional sésamo = new Aditional("Semillas de Sésamo", 2960);
                adicionalRepo.save(sésamo);

                Aditional rayu = new Aditional("Rayu", 2960);
                adicionalRepo.save(rayu);

                Aditional jengibre = new Aditional("Jengibre Encurtido", 1960);
                adicionalRepo.save(jengibre);

                Aditional cebolleta = new Aditional("Cebolleta Fresca", 1960);
                adicionalRepo.save(cebolleta);

                Aditional mayo = new Aditional("Mayonesa Japonesa", 2960);
                adicionalRepo.save(mayo);

                Aditional sriracha = new Aditional("Sriracha", 2960);
                adicionalRepo.save(sriracha);

                Aditional aguacate = new Aditional("Aguacate Extra", 5960);
                adicionalRepo.save(aguacate);

                Aditional panko = new Aditional("Panko Crujiente", 3960);
                adicionalRepo.save(panko);

                Aditional curry = new Aditional("Curry Japonés", 4960);
                adicionalRepo.save(curry);

                Aditional wasabi = new Aditional("Wasabi", 3960);
                adicionalRepo.save(wasabi);

                Aditional salsasoja = new Aditional("Salsa de Soja", 1960);
                adicionalRepo.save(salsasoja);

                Aditional teriyaki = new Aditional("Salsa Teriyaki", 3960);
                adicionalRepo.save(teriyaki);

                Aditional bonito = new Aditional("Copos de Bonito", 4960);
                adicionalRepo.save(bonito);

                Aditional ponzu = new Aditional("Salsa Ponzu", 2960);
                adicionalRepo.save(ponzu);

                int cantidadCategorias = (int) categoriaRepo.findAll().size();

                for (Aditional adicional : adicionalRepo.findAll()) {
                        int randomNum = random.nextInt(1, cantidadCategorias + 1);
                        adcatRepo.save(new AditionalCategory((long) randomNum, adicional.getId()));
                }

                adminRep.save(new Administrator("Miguel", "eveyzoe",
                                "1234"));
                adminRep.save(new Administrator("Tomas", "Neon",
                                "4567"));
                adminRep.save(new Administrator("Juan", "Wonton",
                                "7789"));
                adminRep.save(new Administrator("Alex", "Aliz",
                                "3452"));
                adminRep.save(new Administrator("Akiara", "Starlight",
                                "2231"));

                Operator operador1 = new Operator((long) 101011, "Gomez", "carlos.gomez@example.com", "123456");
                operadorRepo.save(operador1);
                Operator operador2 = new Operator((long) 101012, "Lopez", "maria.lopez@example.com", "123456");
                operadorRepo.save(operador2);
                Operator operador3 = new Operator((long) 101013, "Martinez", "ana.martinez@example.com", "123456");
                operadorRepo.save(operador3);
                Operator operador4 = new Operator((long) 101014, "Garcia", "luis.garcia@example.com", "123456");
                operadorRepo.save(operador4);
                Operator operador5 = new Operator((long) 101015, "Rodriguez", "sofia.rodriguez@example.com", "123456");
                operadorRepo.save(operador5);
                Operator operador6 = new Operator((long) 101016, "Martinez", "javier.martinez@example.com", "123456");
                operadorRepo.save(operador6);
                Operator operador7 = new Operator((long) 101017, "Ramirez", "laura.ramirez@example.com", "123456");
                operadorRepo.save(operador7);
                Operator operador8 = new Operator((long) 101018, "Torres", "andres.torres@example.com", "123456");
                operadorRepo.save(operador8);
                Operator operador9 = new Operator((long) 101019, "Vargas", "sofia.vargas@example.com", "123456");
                operadorRepo.save(operador9);
                Operator operador10 = new Operator((long) 101020, "Castillo", "camila.castillo@example.com", "123456");
                operadorRepo.save(operador10);
                Operator operador11 = new Operator((long) 101021, "Silva", "diego.silva@example.com", "123456");
                operadorRepo.save(operador11);
                Operator operador12 = new Operator((long) 101022, "Cruz", "mariana.cruz@example.com", "123456");
                operadorRepo.save(operador12);
                Operator operador13 = new Operator((long) 101023, "Ortiz", "felipe.ortiz@example.com", "123456");
                operadorRepo.save(operador13);
                Operator operador14 = new Operator((long) 101024, "Rojas", "andrea.rojas@example.com", "123456");
                operadorRepo.save(operador14);
                Operator operador15 = new Operator((long) 101025, "Pérez", "jose.perez@example.com", "123456");
                operadorRepo.save(operador15);
                Operator operador16 = new Operator((long) 101026, "Suarez", "valentina.suarez@example.com", "123456");
                operadorRepo.save(operador16);
                Operator operador17 = new Operator((long) 101027, "Moreno", "santiago.moreno@example.com", "123456");
                operadorRepo.save(operador17);
                Operator operador18 = new Operator((long) 101028, "Núñez", "camilo.nunez@example.com", "123456");
                operadorRepo.save(operador18);
                Operator operador19 = new Operator((long) 101029, "Herrera", "natasha.herrera@example.com", "123456");
                operadorRepo.save(operador19);
                Operator operador20 = new Operator((long) 101030, "Medina", "gabriel.medina@example.com", "123456");
                operadorRepo.save(operador20);

                Delivery domiciliario1 = new Delivery("Perez", "101", "11010101", true);
                domiciliarioRepo.save(domiciliario1);
                Delivery domiciliario2 = new Delivery("Gonzalez", "102", "11010102", true);
                domiciliarioRepo.save(domiciliario2);
                Delivery domiciliario3 = new Delivery("Sanchez", "103", "11010103", true);
                domiciliarioRepo.save(domiciliario3);
                Delivery domiciliario4 = new Delivery("Rodriguez", "104", "11010104", true);
                domiciliarioRepo.save(domiciliario4);
                Delivery domiciliario5 = new Delivery("Fernandez", "105", "11010105", true);
                domiciliarioRepo.save(domiciliario5);

                Order pedido1 = new Order();
                pedido1.setCliente(clienteRepo.findById(1L).orElse(null));
                pedido1.setOperador(operadorRepo.findById(1L).orElse(null));
                pedido1.setDomiciliario(domiciliarioRepo.findById(1L).orElse(null));
                pedido1.setFechaEntrega(java.time.LocalDateTime.now().plusHours(1));
                pedido1.setFechaCreacion(java.time.LocalDateTime.now());
                pedido1.setEstado("En preparación");
                pedidoRepo.save(pedido1);

                Order pedido2 = new Order();
                pedido2.setCliente(clienteRepo.findById(2L).orElse(null));
                pedido2.setOperador(operadorRepo.findById(2L).orElse(null));
                pedido2.setDomiciliario(domiciliarioRepo.findById(2L).orElse(null));
                pedido2.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2));
                pedido2.setFechaCreacion(java.time.LocalDateTime.now());
                pedido2.setEstado("Pendiente");
                pedidoRepo.save(pedido2);

                Order pedido3 = new Order();
                pedido3.setCliente(clienteRepo.findById(3L).orElse(null));
                pedido3.setOperador(operadorRepo.findById(3L).orElse(null));
                pedido3.setDomiciliario(domiciliarioRepo.findById(3L).orElse(null));
                pedido3.setFechaEntrega(java.time.LocalDateTime.now().plusHours(3));
                pedido3.setFechaCreacion(java.time.LocalDateTime.now());
                pedido3.setEstado("En camino");
                pedidoRepo.save(pedido3);

                Order pedido4 = new Order();
                pedido4.setCliente(clienteRepo.findById(4L).orElse(null));
                pedido4.setOperador(operadorRepo.findById(4L).orElse(null));
                pedido4.setDomiciliario(domiciliarioRepo.findById(4L).orElse(null));
                pedido4.setFechaEntrega(java.time.LocalDateTime.now().plusHours(1).plusMinutes(30));
                pedido4.setFechaCreacion(java.time.LocalDateTime.now());
                pedido4.setEstado("Entregado");
                pedidoRepo.save(pedido4);

                Order pedido5 = new Order();
                pedido5.setCliente(clienteRepo.findById(5L).orElse(null));
                pedido5.setOperador(operadorRepo.findById(5L).orElse(null));
                pedido5.setDomiciliario(domiciliarioRepo.findById(5L).orElse(null));
                pedido5.setFechaEntrega(java.time.LocalDateTime.now().plusHours(4));
                pedido5.setFechaCreacion(java.time.LocalDateTime.now());
                pedido5.setEstado("Pendiente");
                pedidoRepo.save(pedido5);

                Order pedido6 = new Order();
                pedido6.setCliente(clienteRepo.findById(6L).orElse(null));
                pedido6.setOperador(operadorRepo.findById(1L).orElse(null));
                pedido6.setDomiciliario(domiciliarioRepo.findById(1L).orElse(null));
                pedido6.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2).plusMinutes(15));
                pedido6.setFechaCreacion(java.time.LocalDateTime.now());
                pedido6.setEstado("En preparación");
                pedidoRepo.save(pedido6);
                
                Order pedido7 = new Order();
                pedido7.setCliente(clienteRepo.findById(7L).orElse(null));
                pedido7.setOperador(operadorRepo.findById(6L).orElse(null));
                pedido7.setDomiciliario(domiciliarioRepo.findById(2L).orElse(null));
                pedido7.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2).plusMinutes(45));
                pedido7.setFechaCreacion(java.time.LocalDateTime.now());
                pedido7.setEstado("Pendiente");
                pedidoRepo.save(pedido7);

                Order pedido8 = new Order();
                pedido8.setCliente(clienteRepo.findById(8L).orElse(null));
                pedido8.setOperador(operadorRepo.findById(7L).orElse(null));
                pedido8.setDomiciliario(domiciliarioRepo.findById(3L).orElse(null));
                pedido8.setFechaEntrega(java.time.LocalDateTime.now().plusHours(3).plusMinutes(30));
                pedido8.setFechaCreacion(java.time.LocalDateTime.now());
                pedido8.setEstado("En preparación");
                pedidoRepo.save(pedido8);

                Order pedido9 = new Order();
                pedido9.setCliente(clienteRepo.findById(9L).orElse(null));
                pedido9.setOperador(operadorRepo.findById(8L).orElse(null));
                pedido9.setDomiciliario(domiciliarioRepo.findById(4L).orElse(null));
                pedido9.setFechaEntrega(java.time.LocalDateTime.now().plusHours(1).plusMinutes(15));
                pedido9.setFechaCreacion(java.time.LocalDateTime.now());
                pedido9.setEstado("En camino");
                pedidoRepo.save(pedido9);

                Order pedido10 = new Order();
                pedido10.setCliente(clienteRepo.findById(10L).orElse(null));
                pedido10.setOperador(operadorRepo.findById(9L).orElse(null));
                pedido10.setDomiciliario(domiciliarioRepo.findById(5L).orElse(null));
                pedido10.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2).plusMinutes(20));
                pedido10.setFechaCreacion(java.time.LocalDateTime.now());
                pedido10.setEstado("Entregado");
                pedidoRepo.save(pedido10);

                Order pedido11 = new Order();
                pedido11.setCliente(clienteRepo.findById(1L).orElse(null));
                pedido11.setOperador(operadorRepo.findById(10L).orElse(null));
                pedido11.setDomiciliario(domiciliarioRepo.findById(1L).orElse(null));
                pedido11.setFechaEntrega(java.time.LocalDateTime.now().plusHours(4).plusMinutes(10));
                pedido11.setFechaCreacion(java.time.LocalDateTime.now());
                pedido11.setEstado("Pendiente");
                pedidoRepo.save(pedido11);

                Order pedido12 = new Order();
                pedido12.setCliente(clienteRepo.findById(2L).orElse(null));
                pedido12.setOperador(operadorRepo.findById(11L).orElse(null));
                pedido12.setDomiciliario(domiciliarioRepo.findById(2L).orElse(null));
                pedido12.setFechaEntrega(java.time.LocalDateTime.now().plusHours(1).plusMinutes(50));
                pedido12.setFechaCreacion(java.time.LocalDateTime.now());
                pedido12.setEstado("En preparación");
                pedidoRepo.save(pedido12);

                Order pedido13 = new Order();
                pedido13.setCliente(clienteRepo.findById(3L).orElse(null));
                pedido13.setOperador(operadorRepo.findById(12L).orElse(null));
                pedido13.setDomiciliario(domiciliarioRepo.findById(3L).orElse(null));
                pedido13.setFechaEntrega(java.time.LocalDateTime.now().plusHours(3).plusMinutes(5));
                pedido13.setFechaCreacion(java.time.LocalDateTime.now());
                pedido13.setEstado("En camino");
                pedidoRepo.save(pedido13);

                Order pedido14 = new Order();
                pedido14.setCliente(clienteRepo.findById(4L).orElse(null));
                pedido14.setOperador(operadorRepo.findById(13L).orElse(null));
                pedido14.setDomiciliario(domiciliarioRepo.findById(4L).orElse(null));
                pedido14.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2).plusMinutes(35));
                pedido14.setFechaCreacion(java.time.LocalDateTime.now());
                pedido14.setEstado("Pendiente");
                pedidoRepo.save(pedido14);

                Order pedido15 = new Order();
                pedido15.setCliente(clienteRepo.findById(5L).orElse(null));
                pedido15.setOperador(operadorRepo.findById(14L).orElse(null));
                pedido15.setDomiciliario(domiciliarioRepo.findById(5L).orElse(null));
                pedido15.setFechaEntrega(java.time.LocalDateTime.now().plusHours(1).plusMinutes(40));
                pedido15.setFechaCreacion(java.time.LocalDateTime.now());
                pedido15.setEstado("En preparación");
                pedidoRepo.save(pedido15);

                Order pedido16 = new Order();
                pedido16.setCliente(clienteRepo.findById(6L).orElse(null));
                pedido16.setOperador(operadorRepo.findById(15L).orElse(null));
                pedido16.setDomiciliario(domiciliarioRepo.findById(1L).orElse(null));
                pedido16.setFechaEntrega(java.time.LocalDateTime.now().plusHours(3).plusMinutes(25));
                pedido16.setFechaCreacion(java.time.LocalDateTime.now());
                pedido16.setEstado("Entregado");
                pedidoRepo.save(pedido16);

                Order pedido17 = new Order();
                pedido17.setCliente(clienteRepo.findById(7L).orElse(null));
                pedido17.setOperador(operadorRepo.findById(16L).orElse(null));
                pedido17.setDomiciliario(domiciliarioRepo.findById(2L).orElse(null));
                pedido17.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2).plusMinutes(55));
                pedido17.setFechaCreacion(java.time.LocalDateTime.now());
                pedido17.setEstado("En camino");
                pedidoRepo.save(pedido17);

                Order pedido18 = new Order();
                pedido18.setCliente(clienteRepo.findById(8L).orElse(null));
                pedido18.setOperador(operadorRepo.findById(17L).orElse(null));
                pedido18.setDomiciliario(domiciliarioRepo.findById(3L).orElse(null));
                pedido18.setFechaEntrega(java.time.LocalDateTime.now().plusHours(1).plusMinutes(35));
                pedido18.setFechaCreacion(java.time.LocalDateTime.now());
                pedido18.setEstado("Pendiente");
                pedidoRepo.save(pedido18);

                Order pedido19 = new Order();
                pedido19.setCliente(clienteRepo.findById(9L).orElse(null));
                pedido19.setOperador(operadorRepo.findById(18L).orElse(null));
                pedido19.setDomiciliario(domiciliarioRepo.findById(4L).orElse(null));
                pedido19.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2).plusMinutes(10));
                pedido19.setFechaCreacion(java.time.LocalDateTime.now());
                pedido19.setEstado("En preparación");
                pedidoRepo.save(pedido19);

                Order pedido20 = new Order();
                pedido20.setCliente(clienteRepo.findById(10L).orElse(null));
                pedido20.setOperador(operadorRepo.findById(19L).orElse(null));
                pedido20.setDomiciliario(domiciliarioRepo.findById(5L).orElse(null));
                pedido20.setFechaEntrega(java.time.LocalDateTime.now().plusHours(3).plusMinutes(45));
                pedido20.setFechaCreacion(java.time.LocalDateTime.now());
                pedido20.setEstado("En camino");
                pedidoRepo.save(pedido20);

                Order pedido21 = new Order();
                pedido21.setCliente(clienteRepo.findById(1L).orElse(null));
                pedido21.setOperador(operadorRepo.findById(20L).orElse(null));
                pedido21.setDomiciliario(domiciliarioRepo.findById(1L).orElse(null));
                pedido21.setFechaEntrega(java.time.LocalDateTime.now().plusHours(2).plusMinutes(20));
                pedido21.setFechaCreacion(java.time.LocalDateTime.now());
                pedido21.setEstado("Entregado");
                pedidoRepo.save(pedido21);
                

                OrderDetails detalle1 = new OrderDetails(pedido1, platoRepo.findById(1L).orElse(null), 2);
                pedidoDetallesRepo.save(detalle1);
                OrderDetails detalle2 = new OrderDetails(pedido1, platoRepo.findById(2L).orElse(null), 1);
                pedidoDetallesRepo.save(detalle2);

                OrderDetails detalle3 = new OrderDetails(pedido2, platoRepo.findById(3L).orElse(null), 3);
                pedidoDetallesRepo.save(detalle3);

                OrderDetails detalle4 = new OrderDetails(pedido2, platoRepo.findById(5L).orElse(null), 2);
                pedidoDetallesRepo.save(detalle4);

                OrderDetails detalle5 = new OrderDetails(pedido3, platoRepo.findById(10L).orElse(null), 1);
                pedidoDetallesRepo.save(detalle5);

                OrderDetails detalle6 = new OrderDetails(pedido4, platoRepo.findById(25L).orElse(null), 2);
                pedidoDetallesRepo.save(detalle6);

                OrderDetails detalle7 = new OrderDetails(pedido2, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle7);

                OrderDetails detalle8 = new OrderDetails(pedido3, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle8);

                OrderDetails detalle9 = new OrderDetails(pedido4, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle9);

                OrderDetails detalle10 = new OrderDetails(pedido5, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle10);

                OrderDetails detalle11 = new OrderDetails(pedido6, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle11);

                OrderDetails detalle12 = new OrderDetails(pedido7, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle12);

                OrderDetails detalle13 = new OrderDetails(pedido8, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle13);

                OrderDetails detalle14 = new OrderDetails(pedido9, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle14);

                OrderDetails detalle15 = new OrderDetails(pedido10, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle15);

                OrderDetails detalle16 = new OrderDetails(pedido11, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle16);

                OrderDetails detalle17 = new OrderDetails(pedido12, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle17);

                OrderDetails detalle18 = new OrderDetails(pedido13, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle18);

                OrderDetails detalle19 = new OrderDetails(pedido14, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle19);

                OrderDetails detalle20 = new OrderDetails(pedido15, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle20);

                OrderDetails detalle21 = new OrderDetails(pedido16, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle21);

                OrderDetails detalle22 = new OrderDetails(pedido17, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle22);

                OrderDetails detalle23 = new OrderDetails(pedido18, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle23);

                OrderDetails detalle24 = new OrderDetails(pedido19, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle24);

                OrderDetails detalle25 = new OrderDetails(pedido20, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle25);

                OrderDetails detalle26 = new OrderDetails(pedido21, platoRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                pedidoDetallesRepo.save(detalle26);

                AditionalOrderDetails adicionalDetalle1 = new AditionalOrderDetails(pedidoDetallesRepo.findById(1L).orElse(null), adicionalRepo.findById(1L).orElse(null));
                adicionalPedidoDetallesRepo.save(adicionalDetalle1);
                AditionalOrderDetails adicionalDetalle2 = new AditionalOrderDetails(pedidoDetallesRepo.findById(2L).orElse(null), adicionalRepo.findById(2L).orElse(null));
                adicionalPedidoDetallesRepo.save(adicionalDetalle2);
                AditionalOrderDetails adicionalDetalle3 = new AditionalOrderDetails(pedidoDetallesRepo.findById(3L).orElse(null), adicionalRepo.findById(3L).orElse(null));
                adicionalPedidoDetallesRepo.save(adicionalDetalle3);
                AditionalOrderDetails adicionalDetalle4 = new AditionalOrderDetails(pedidoDetallesRepo.findById(4L).orElse(null), adicionalRepo.findById(4L).orElse(null));
                adicionalPedidoDetallesRepo.save(adicionalDetalle4);
                AditionalOrderDetails adicionalDetalle5 = new AditionalOrderDetails(pedidoDetallesRepo.findById(5L).orElse(null), adicionalRepo.findById(5L).orElse(null));
                adicionalPedidoDetallesRepo.save(adicionalDetalle5);

        }


}