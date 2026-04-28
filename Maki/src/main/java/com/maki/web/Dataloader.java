package com.maki.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import com.maki.web.entities.Additional;
import com.maki.web.entities.AdditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.entities.Client;
import com.maki.web.entities.Operator;
import com.maki.web.entities.Administrator;
import com.maki.web.entities.Plate;
import com.maki.web.entities.PurchaseOrder;
import com.maki.web.entities.OrderDetails;
import com.maki.web.entities.Delivery;
import com.maki.web.entities.AdditionalOrderDetails;
import com.maki.web.repository.AdditionalCategoryRepository;
import com.maki.web.repository.AdditionalOrderDetailsRepository;
import com.maki.web.repository.AdditionalRepository;
import com.maki.web.repository.AdministratorRepository;
import com.maki.web.repository.CategoryRepository;
import com.maki.web.repository.ClientRepository;
import com.maki.web.repository.OperatorRepository;
import com.maki.web.repository.OrderDetailsRepository;
import com.maki.web.repository.PlateRepository;
import com.maki.web.repository.PurchaseOrderRepository;
import com.maki.web.repository.DeliveryRepository;
import java.util.Random;

@Component
@Transactional
public class Dataloader implements CommandLineRunner {

        @Autowired
        private CategoryRepository categoriaRepo;
        @Autowired
        private PlateRepository plateRepo;
        @Autowired
        private ClientRepository clientRepo;
        @Autowired
        private AdditionalRepository additionalRepo;
        @Autowired
        private AdditionalCategoryRepository adcatRepo;
        @Autowired
        private AdministratorRepository adminRep;
        @Autowired
        private OperatorRepository operatorRepo;
        @Autowired
        private PurchaseOrderRepository orderRepo;
        @Autowired
        private OrderDetailsRepository orderDetallesRepo;

        @Autowired
        private DeliveryRepository deliveryRepo;
        @Autowired
        private AdditionalOrderDetailsRepository additionalPedidoDetallesRepo;

        @Override
        public void run(String... args) throws Exception {

                Random random = new Random();
                Category entradas = categoriaRepo.save(new Category("Entradas"));
                Category platesFuertes = categoriaRepo.save(new Category("Platos fuertes"));
                Category sushi = categoriaRepo.save(new Category("Sushi"));
                Category postres = categoriaRepo.save(new Category("Postres"));
                Category bebidas = categoriaRepo.save(new Category("Bebidas"));

                clientRepo.save(new Client("Miguel", "Vargas", "acha@acha.dev", "eveyzoe", "+57 314 852 7241",
                                "Cra 123 #24-242B"));
                clientRepo.save(new Client("Tomas", "Silva", "Neon@Zynth.dev", "StarlightSolos", "+57 316 776 6274",
                                "Cra 53B #131A-72"));
                clientRepo.save(new Client("Alex", "Aponte", "Alex@Ap.dev", "AlezElNaci", "+57 317 445 8921",
                                "Cra 15 #45-67"));
                clientRepo.save(new Client("Juan", "Vargas", "Pabon@GUI.dev", "GUIllermo", "+57 318 556 7832",
                                "Cra 22 #89-145"));
                clientRepo.save(new Client("Laura", "Martínez", "laura.m@email.com", "LauraMart99",
                                "+57 319 667 8743", "Cra 30 #12-34"));
                clientRepo.save(new Client("Diego", "López", "diego.lopez@email.com", "DiegoL2024",
                                "+57 310 778 9654", "Cra 8 #56-78"));
                clientRepo.save(new Client("Sofía", "Herrera", "sofia.h@email.com", "SofiaHe88", "+57 311 889 0765",
                                "Cra 18 #90-23"));
                clientRepo.save(new Client("Pablo", "Sánchez", "pablo.sanchez@email.com", "PabloSan77",
                                "+57 312 990 1876", "Cra 25 #34-56"));
                clientRepo.save(new Client("Marcela", "Pérez", "marcela.p@email.com", "MarcelaPerez55",
                                "+57 313 101 2987", "Cra 11 #67-89"));
                clientRepo.save(new Client("Javier", "Castro", "javier.c@email.com", "JavierCast44",
                                "+57 314 212 3098", "Cra 20 #23-45"));

                Plate sushiVariado = new Plate("Sushi Variado", 51960,
                                "Hermosa combinación de nigiri y rollos con los mejores ingredientes.",
                                "https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500", true);
                sushiVariado.setCategory(sushi);
                plateRepo.save(sushiVariado);
                Plate ramen = new Plate("Ramen", 47960,
                                "Tazón abundante con caldo tonkotsu rico, tallarines tiernos, huevos y barriga de cerdo marinada.",
                                "https://images.unsplash.com/photo-1638866281450-3933540af86a?w=500", true);
                ramen.setCategory(platesFuertes);
                plateRepo.save(ramen);
                Plate tempura = new Plate("Tempura", 43960,
                                "Verduras y camarones fritos hasta obtener una perfección dorada con salsa tradditional.",
                                "https://images.unsplash.com/photo-1677743537607-f7fc9273ec4d?w=500", true);
                tempura.setCategory(entradas);
                plateRepo.save(tempura);
                Plate tonkatsu = new Plate("Tonkatsu", 55960,
                                "Chuleta de cerdo premium rebozada en panko y frita hasta quedar dorada y crujiente.",
                                "https://images.unsplash.com/photo-1734775373504-ff24ea8419b2?w=500", true);
                tonkatsu.setCategory(platesFuertes);
                plateRepo.save(tonkatsu);
                Plate gyoza = new Plate("Gyoza", 31960,
                                "Empanadillas fritas rellenas de cerdo sazonado y verduras, hechas a mano.",
                                "https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500", true);
                gyoza.setCategory(entradas);
                plateRepo.save(gyoza);
                Plate edamame = new Plate("Edamame", 23960,
                                "Frijoles de soya jóvenes cocidos al vapor, ligeramente salados y ricos en proteína.",
                                "https://images.unsplash.com/photo-1575262599410-837a72005862?w=500", true);
                edamame.setCategory(entradas);
                plateRepo.save(edamame);
                Plate sopaMiso = new Plate("Sopa Miso", 15960,
                                "Sopa tradditional japonesa con pasta miso fermentada, tofu y alga marina.",
                                "https://images.unsplash.com/photo-1610393069309-2607fcf74146?w=500", true);
                sopaMiso.setCategory(entradas);
                plateRepo.save(sopaMiso);
                Plate rolloCalifornia = new Plate("Rollo California", 39960,
                                "Rollo al revés con jurel imitado, aguacate y pepino fresco envuelto en arroz.",
                                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=500", true);
                rolloCalifornia.setCategory(sushi);
                plateRepo.save(rolloCalifornia);
                Plate rolloDragon = new Plate("Rollo Dragón", 59960,
                                "Rollo especializado con anguila tierna, pepino crujiente y aguacate en la parte superior.",
                                "https://images.unsplash.com/photo-1712192674556-4a89f20240c1?w=500", true);
                rolloDragon.setCategory(sushi);
                plateRepo.save(rolloDragon);
                Plate rolloPhiladelphia = new Plate("Rollo Philadelphia", 47960,
                                "Rollo premium con salmón ahumado, queso crema suave y pepino fresco.",
                                "https://images.unsplash.com/photo-1759646828324-c215a83828ae?w=500", true);
                rolloPhiladelphia.setCategory(sushi);
                plateRepo.save(rolloPhiladelphia);
                Plate yakitori = new Plate("Yakitori", 43960,
                                "Piezas de pollo a la parrilla en pinchos sobre carbón con glaseado sabroso y dulce.",
                                "https://images.unsplash.com/photo-1708597525178-6c302364f37c?w=500", true);
                yakitori.setCategory(platesFuertes);
                plateRepo.save(yakitori);
                Plate okonomiyaki = new Plate("Okonomiyaki", 51960,
                                "Panqueque japonés salado hecho de masa y repollo con salsa, mayonesa y copos de bonito.",
                                "https://plus.unsplash.com/premium_photo-1722593856486-5f87f9fca308?w=500", true);
                okonomiyaki.setCategory(platesFuertes);
                plateRepo.save(okonomiyaki);
                Plate takoyaki = new Plate("Takoyaki", 35960,
                                "Esferas doradas de masa rellenas de pulpo tierno, jengibre y cebolletas con salsa takoyaki.",
                                "https://plus.unsplash.com/premium_photo-1722593856742-085ef5549070?w=500", true);
                takoyaki.setCategory(entradas);
                plateRepo.save(takoyaki);
                Plate unagiDon = new Plate("Unagi Don", 55960,
                                "Anguila a la parrilla esmaltada con salsa kabayaki servida sobre arroz blanco esponjoso.",
                                "https://japanesetaste.com.au/cdn/shop/articles/how-to-make-unagi-don-grilled-eel-rice-bowl-with-kabayaki-sauce-japanese-taste.jpg?v=1766642304&width=500",
                                true);
                unagiDon.setCategory(platesFuertes);
                plateRepo.save(unagiDon);
                Plate katsudon = new Plate("Katsudon", 51960,
                                "Tazón de arroz con chuleta de cerdo dorada, crujiente y cubierto con mezcla de huevo.",
                                "https://images.unsplash.com/photo-1624517607896-bb5dfd8f5764?w=500", true);
                katsudon.setCategory(platesFuertes);
                plateRepo.save(katsudon);
                Plate chirashi = new Plate("Chirashi", 59960,
                                "Tazón de sushi mixto con pescado crudo premium, verduras y huevos sobre arroz de sushi.",
                                "https://images.unsplash.com/photo-1565967531713-45739e0cad63?w=500", true);
                chirashi.setCategory(sushi);
                plateRepo.save(chirashi);
                Plate sukiyaki = new Plate("Sukiyaki", 67960,
                                "Olla caliente lujosa con carne de res premium, verduras y tofu en caldo dulce y sabroso.",
                                "https://images.unsplash.com/photo-1648977555545-4dd006e30d3f?w=500", true);
                sukiyaki.setCategory(platesFuertes);
                plateRepo.save(sukiyaki);
                Plate shabuShabu = new Plate("Shabu Shabu", 63960,
                                "Olla caliente interactiva donde rodajas ultrafinas de carne y verduras se agitan en caldo hirviente.",
                                "https://images.unsplash.com/photo-1559602580-78f1ba809b92?w=500", true);
                shabuShabu.setCategory(platesFuertes);
                plateRepo.save(shabuShabu);
                Plate nigiriSalmón = new Plate("Nigiri de Salmón", 43960,
                                "Salmón fresco premium delicadamente colocado sobre montículo de arroz de sushi sazonado.",
                                "https://images.unsplash.com/photo-1680675228874-9b9963812b7c?w=500", true);
                nigiriSalmón.setCategory(sushi);
                plateRepo.save(nigiriSalmón);
                Plate resWagyu = new Plate("Res Wagyu", 99960,
                                "Carne de res premium japonesa conocida por su veteado excepcional y ternura que se derrite en la boca.",
                                "https://images.unsplash.com/photo-1708388464912-d4ad82dca990?w=500", true);
                resWagyu.setCategory(platesFuertes);
                plateRepo.save(resWagyu);
                Plate ensaladaAlgaMarina = new Plate("Ensalada de Alga Marina", 27960,
                                "Ensalada refrescante de alga marina marinada con aderezo ligero de sésamo.",
                                "https://plus.unsplash.com/premium_photo-1700840833134-3f6ad783f8bb?w=500", true);
                ensaladaAlgaMarina.setCategory(entradas);
                plateRepo.save(ensaladaAlgaMarina);
                Plate tofuAgedashi = new Plate("Tofu Agedashi", 35960,
                                "Tofu sedoso frito con exterior crujiente, servido en caldo dashi con champiñones y cebolletas.",
                                "https://images.unsplash.com/photo-1765295218809-784d6c2fe39c?w=500", true);
                tofuAgedashi.setCategory(entradas);
                plateRepo.save(tofuAgedashi);
                Plate yakisoba = new Plate("Yakisoba", 43960,
                                "Tallarines de trigo masticables cocidos en plancha caliente con verduras y salsa dulce y sabrosa.",
                                "https://images.unsplash.com/photo-1624904025321-24e2f17d06ce?w=500", true);
                yakisoba.setCategory(platesFuertes);
                plateRepo.save(yakisoba);
                Plate polloTeriyaki = new Plate("Pollo Teriyaki", 51960,
                                "Pecho de pollo tierno esmaltado con salsa teriyaki brillante y caramelizada.",
                                "https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?w=500", true);
                polloTeriyaki.setCategory(platesFuertes);
                plateRepo.save(polloTeriyaki);
                Plate karaage = new Plate("Karaage", 39960,
                                "Piezas de pollo marinadas y rebozadas en rebozado crujiente, jugosas en el interior.",
                                "https://images.unsplash.com/photo-1706513045864-e122d45f2f1d?w=500", true);
                karaage.setCategory(entradas);
                plateRepo.save(karaage);
                Plate tablaSashimi = new Plate("Tabla de Sashimi", 67960,
                                "Presentación elegante de pescado crudo variado premium seleccionado por calidad y frescura.",
                                "https://images.unsplash.com/photo-1758384075930-6e3835d22b1d?w=500", true);
                tablaSashimi.setCategory(sushi);
                plateRepo.save(tablaSashimi);
                Plate rolloArcoiris = new Plate("Rollo Arcoíris", 63960,
                                "Rollo al revés espectacular cubierto con variedad de pescado crudo dispuesto en patrón de arcoíris.",
                                "https://images.unsplash.com/photo-1636425730652-ffdbada1ed4d?w=500", true);
                rolloArcoiris.setCategory(sushi);
                plateRepo.save(rolloArcoiris);
                Plate tartaraAtun = new Plate("Tártara de Atún", 47960,
                                "Aperitivo sofisticado con atún premium finamente picado combinado con condimentos y cítricos.",
                                "https://images.unsplash.com/photo-1656106577512-0259bf5b9fd6?w=500", true);
                tartaraAtun.setCategory(entradas);
                plateRepo.save(tartaraAtun);
                Plate makiPepino = new Plate("Maki de Pepino", 31960,
                                "Rollo de sushi simple con pepino crujiente refrescante, opción vegetariana ligera y saludable.",
                                "https://www.sushiya.in/cdn/shop/files/Cucumber_Maki.png?v=1742021465&width=500", true);
                makiPepino.setCategory(sushi);
                plateRepo.save(makiPepino);
                Plate cheesecakeMatcha = new Plate("Cheesecake Matcha", 27960,
                                "Cheesecake rico y cremoso con notas terrosas del té matcha verde premium.",
                                "https://plus.unsplash.com/premium_photo-1694599325857-24139cf22ace?w=500", true);
                cheesecakeMatcha.setCategory(postres);
                plateRepo.save(cheesecakeMatcha);
                Plate motiHelado = new Plate("Moti Helado", 19960,
                                "Pequeñas esferas de arroz glutinoso rellenas de helado cremoso con textura única.",
                                "https://www.elespectador.com/resizer/JbZ9LGO_ygDGxdnmvdEh-63br7g=/arc-anglerfish-arc2-prod-elespectador/public/EXYQ4FEM3RBHTPTQ7JNQ5NKREU.jpg",
                                true);
                motiHelado.setCategory(postres);
                plateRepo.save(motiHelado);
                Plate wontons = new Plate("Wontons de Chocolate", 31960,
                                "Wontons fritos rellenos de chocolate derretido con exterior crujiente e interior suave.",
                                "https://www.umami.recipes/api/image/recipes/EZodnaVbHrA9KhS1qvaN/images/4PduTgOOsidjSZEVbUQZXM?w=3840&q=75",
                                true);
                wontons.setCategory(postres);
                plateRepo.save(wontons);
                Plate donut = new Plate("Donut de Matcha", 15960,
                                "Donut cubierto con glaseado de matcha verde vibrante y relleno de crema suave.",
                                "https://buenprovecho.hn/wp-content/uploads/2020/10/Donas_matcha.jpg", true);
                donut.setCategory(postres);
                plateRepo.save(donut);
                Plate brownie = new Plate("Brownie de Chocolate", 23960,
                                "Brownie denso y pegajoso hecho con chocolate premium, servido caliente con helado de vainilla.",
                                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiFnc04hxbpnE4BATtLUfa7f2_oFZYqVnXaHxb9nkaiDgzf-U0qjBIInHXiHjV1sxrtiKFHXek6G_69WFoBpBPJwxCNCBI1OopcjF4C6UdxTgcBf59cq5JIWgTdtTaZHsR9NpwCTz-BmIk/s1600/20121209_151908.jpg",
                                true);
                brownie.setCategory(postres);
                plateRepo.save(brownie);

                Plate teVerde = new Plate("Té Verde", 7960,
                                "Té verde tradditional japonés con sabor fresco y ligero.",
                                "https://image.tuasaude.com/media/article/yp/dt/beneficios-del-te-verde_17350.jpg",
                                true);
                teVerde.setCategory(bebidas);
                plateRepo.save(teVerde);

                Plate sakeTradditional = new Plate("Sake Tradditional", 35960,
                                "Bebida alcohólica japonesa elaborada con arroz fermentado con notas complejas de sabor.",
                                "https://monstersushi.es/blog/wp-content/uploads/2022/04/sake-robata-barcelona-e1637227199971-1024x784-1.png",
                                true);
                sakeTradditional.setCategory(bebidas);
                plateRepo.save(sakeTradditional);

                Plate limonada = new Plate("Limonada Fresca", 9960,
                                "Bebida refrescante y natural preparada con limón fresco y azúcar.",
                                "https://cdn.shopify.com/s/files/1/0191/9978/files/Como-hacer-limonada.jpg?v=1753088533",
                                true);
                limonada.setCategory(bebidas);
                plateRepo.save(limonada);

                Plate jugoNatural = new Plate("Jugos naturales", 11960,
                                "Jugo natural de durazno con sabor dulce y tropical hecho con fruta fresca de temporada.",
                                "https://www.wikihow.com/images_en/thumb/b/b0/Peach-juice-Intro.jpg/v4-1200px-Peach-juice-Intro.jpg",
                                true);
                jugoNatural.setCategory(bebidas);
                plateRepo.save(jugoNatural);

                Plate aguaArroz = new Plate("Agua de Arroz", 5960,
                                "Bebida tradditional refrescante hecha con arroz y un toque de vainilla.",
                                "https://image.tuasaude.com/media/article/pc/nx/agua-de-arroz-para-la-diarrea_19076.jpg",
                                true);
                aguaArroz.setCategory(bebidas);
                plateRepo.save(aguaArroz);

                Plate smoothie = new Plate("Smoothies", 13960,
                                "Smoothies saludables hechos con frutas frescas, yogur y miel para un impulso natural de energía.",
                                "https://saposyprincesas.elmundo.es/assets/2017/05/Batidos-verano.jpg", true);
                smoothie.setCategory(bebidas);
                plateRepo.save(smoothie);

                Additional algaNori = new Additional("Alga Nori", 3960);
                additionalRepo.save(algaNori);

                Additional sésamo = new Additional("Semillas de Sésamo", 2960);
                additionalRepo.save(sésamo);

                Additional rayu = new Additional("Rayu", 2960);
                additionalRepo.save(rayu);

                Additional jengibre = new Additional("Jengibre Encurtido", 1960);
                additionalRepo.save(jengibre);

                Additional cebolleta = new Additional("Cebolleta Fresca", 1960);
                additionalRepo.save(cebolleta);

                Additional mayo = new Additional("Mayonesa Japonesa", 2960);
                additionalRepo.save(mayo);

                Additional sriracha = new Additional("Sriracha", 2960);
                additionalRepo.save(sriracha);

                Additional aguacate = new Additional("Aguacate Extra", 5960);
                additionalRepo.save(aguacate);

                Additional panko = new Additional("Panko Crujiente", 3960);
                additionalRepo.save(panko);

                Additional curry = new Additional("Curry Japonés", 4960);
                additionalRepo.save(curry);

                Additional wasabi = new Additional("Wasabi", 3960);
                additionalRepo.save(wasabi);

                Additional salsasoja = new Additional("Salsa de Soja", 1960);
                additionalRepo.save(salsasoja);

                Additional teriyaki = new Additional("Salsa Teriyaki", 3960);
                additionalRepo.save(teriyaki);

                Additional bonito = new Additional("Copos de Bonito", 4960);
                additionalRepo.save(bonito);

                Additional ponzu = new Additional("Salsa Ponzu", 2960);
                additionalRepo.save(ponzu);

                int cantidadCategorias = (int) categoriaRepo.findAll().size();

                for (Additional additional : additionalRepo.findAll()) {
                        int randomNum = random.nextInt(1, cantidadCategorias + 1);
                        adcatRepo.save(new AdditionalCategory((long) randomNum, additional.getId()));
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

                Operator operator1 = new Operator((long) 101011, "Gomez", "carlos.gomez@example.com", "123456");
                operatorRepo.save(operator1);
                Operator operator2 = new Operator((long) 101012, "Lopez", "maria.lopez@example.com", "123456");
                operatorRepo.save(operator2);
                Operator operator3 = new Operator((long) 101013, "Martinez", "ana.martinez@example.com", "123456");
                operatorRepo.save(operator3);
                Operator operator4 = new Operator((long) 101014, "Garcia", "luis.garcia@example.com", "123456");
                operatorRepo.save(operator4);
                Operator operator5 = new Operator((long) 101015, "Rodriguez", "sofia.rodriguez@example.com", "123456");
                operatorRepo.save(operator5);
                Operator operator6 = new Operator((long) 101016, "Martinez", "javier.martinez@example.com", "123456");
                operatorRepo.save(operator6);
                Operator operator7 = new Operator((long) 101017, "Ramirez", "laura.ramirez@example.com", "123456");
                operatorRepo.save(operator7);
                Operator operator8 = new Operator((long) 101018, "Torres", "andres.torres@example.com", "123456");
                operatorRepo.save(operator8);
                Operator operator9 = new Operator((long) 101019, "Vargas", "sofia.vargas@example.com", "123456");
                operatorRepo.save(operator9);
                Operator operator10 = new Operator((long) 101020, "Castillo", "camila.castillo@example.com", "123456");
                operatorRepo.save(operator10);
                Operator operator11 = new Operator((long) 101021, "Silva", "diego.silva@example.com", "123456");
                operatorRepo.save(operator11);
                Operator operator12 = new Operator((long) 101022, "Cruz", "mariana.cruz@example.com", "123456");
                operatorRepo.save(operator12);
                Operator operator13 = new Operator((long) 101023, "Ortiz", "felipe.ortiz@example.com", "123456");
                operatorRepo.save(operator13);
                Operator operator14 = new Operator((long) 101024, "Rojas", "andrea.rojas@example.com", "123456");
                operatorRepo.save(operator14);
                Operator operator15 = new Operator((long) 101025, "Pérez", "jose.perez@example.com", "123456");
                operatorRepo.save(operator15);
                Operator operator16 = new Operator((long) 101026, "Suarez", "valentina.suarez@example.com", "123456");
                operatorRepo.save(operator16);
                Operator operator17 = new Operator((long) 101027, "Moreno", "santiago.moreno@example.com", "123456");
                operatorRepo.save(operator17);
                Operator operator18 = new Operator((long) 101028, "Núñez", "camilo.nunez@example.com", "123456");
                operatorRepo.save(operator18);
                Operator operator19 = new Operator((long) 101029, "Herrera", "natasha.herrera@example.com", "123456");
                operatorRepo.save(operator19);
                Operator operator20 = new Operator((long) 101030, "Medina", "gabriel.medina@example.com", "123456");
                operatorRepo.save(operator20);

                Delivery delivery1 = new Delivery("Perez", "101", "11010101", true);
                deliveryRepo.save(delivery1);
                Delivery delivery2 = new Delivery("Gonzalez", "102", "11010102", false);
                deliveryRepo.save(delivery2);
                Delivery delivery3 = new Delivery("Sanchez", "103", "11010103", false);
                deliveryRepo.save(delivery3);
                Delivery delivery4 = new Delivery("Rodriguez", "104", "11010104", true);
                deliveryRepo.save(delivery4);
                Delivery delivery5 = new Delivery("Fernandez", "105", "11010105", true);
                deliveryRepo.save(delivery5);

                PurchaseOrder order1 = new PurchaseOrder();
                order1.setClient(clientRepo.findById(1L).orElse(null));
                order1.setOperator(operatorRepo.findById(1L).orElse(null));
                order1.setDelivery(deliveryRepo.findById(1L).orElse(null));
                order1.setDelivery_date(java.time.LocalDateTime.now().plusHours(1));
                order1.setCreation_date(java.time.LocalDateTime.now());
                order1.setStatus("preparation");
                orderRepo.save(order1);

                PurchaseOrder order2 = new PurchaseOrder();
                order2.setClient(clientRepo.findById(2L).orElse(null));
                order2.setOperator(operatorRepo.findById(2L).orElse(null));
                order2.setDelivery(deliveryRepo.findById(2L).orElse(null));
                order2.setDelivery_date(java.time.LocalDateTime.now().plusHours(2));
                order2.setCreation_date(java.time.LocalDateTime.now());
                order2.setStatus("pending");
                orderRepo.save(order2);

                PurchaseOrder order3 = new PurchaseOrder();
                order3.setClient(clientRepo.findById(3L).orElse(null));
                order3.setOperator(operatorRepo.findById(3L).orElse(null));
                order3.setDelivery(deliveryRepo.findById(3L).orElse(null));
                order3.setDelivery_date(java.time.LocalDateTime.now().plusHours(3));
                order3.setCreation_date(java.time.LocalDateTime.now());
                order3.setStatus("sent");
                orderRepo.save(order3);

                PurchaseOrder order4 = new PurchaseOrder();
                order4.setClient(clientRepo.findById(4L).orElse(null));
                order4.setOperator(operatorRepo.findById(4L).orElse(null));
                order4.setDelivery(deliveryRepo.findById(4L).orElse(null));
                order4.setDelivery_date(java.time.LocalDateTime.now().plusHours(1).plusMinutes(30));
                order4.setCreation_date(java.time.LocalDateTime.now());
                order4.setStatus("delivered");
                orderRepo.save(order4);

                PurchaseOrder order5 = new PurchaseOrder();
                order5.setClient(clientRepo.findById(5L).orElse(null));
                order5.setOperator(operatorRepo.findById(5L).orElse(null));
                order5.setDelivery(deliveryRepo.findById(5L).orElse(null));
                order5.setDelivery_date(java.time.LocalDateTime.now().plusHours(4));
                order5.setCreation_date(java.time.LocalDateTime.now());
                order5.setStatus("pending");
                orderRepo.save(order5);

                PurchaseOrder order6 = new PurchaseOrder();
                order6.setClient(clientRepo.findById(6L).orElse(null));
                order6.setOperator(operatorRepo.findById(1L).orElse(null));
                order6.setDelivery(deliveryRepo.findById(1L).orElse(null));
                order6.setDelivery_date(java.time.LocalDateTime.now().plusHours(2).plusMinutes(15));
                order6.setCreation_date(java.time.LocalDateTime.now());
                order6.setStatus("preparation");
                orderRepo.save(order6);
                
                PurchaseOrder order7 = new PurchaseOrder();
                order7.setClient(clientRepo.findById(7L).orElse(null));
                order7.setOperator(operatorRepo.findById(6L).orElse(null));
                order7.setDelivery(deliveryRepo.findById(2L).orElse(null));
                order7.setDelivery_date(java.time.LocalDateTime.now().plusHours(2).plusMinutes(45));
                order7.setCreation_date(java.time.LocalDateTime.now());
                order7.setStatus("pending");
                orderRepo.save(order7);

                PurchaseOrder order8 = new PurchaseOrder();
                order8.setClient(clientRepo.findById(8L).orElse(null));
                order8.setOperator(operatorRepo.findById(7L).orElse(null));
                order8.setDelivery(deliveryRepo.findById(3L).orElse(null));
                order8.setDelivery_date(java.time.LocalDateTime.now().plusHours(3).plusMinutes(30));
                order8.setCreation_date(java.time.LocalDateTime.now());
                order8.setStatus("preparation");
                orderRepo.save(order8);

                PurchaseOrder order9 = new PurchaseOrder();
                order9.setClient(clientRepo.findById(9L).orElse(null));
                order9.setOperator(operatorRepo.findById(8L).orElse(null));
                order9.setDelivery(deliveryRepo.findById(4L).orElse(null));
                order9.setDelivery_date(java.time.LocalDateTime.now().plusHours(1).plusMinutes(15));
                order9.setCreation_date(java.time.LocalDateTime.now());
                order9.setStatus("sent");
                orderRepo.save(order9);

                PurchaseOrder order10 = new PurchaseOrder();
                order10.setClient(clientRepo.findById(10L).orElse(null));
                order10.setOperator(operatorRepo.findById(9L).orElse(null));
                order10.setDelivery(deliveryRepo.findById(5L).orElse(null));
                order10.setDelivery_date(java.time.LocalDateTime.now().plusHours(2).plusMinutes(20));
                order10.setCreation_date(java.time.LocalDateTime.now());
                order10.setStatus("delivered");
                orderRepo.save(order10);

                PurchaseOrder order11 = new PurchaseOrder();
                order11.setClient(clientRepo.findById(1L).orElse(null));
                order11.setOperator(operatorRepo.findById(10L).orElse(null));
                order11.setDelivery(deliveryRepo.findById(1L).orElse(null));
                order11.setDelivery_date(java.time.LocalDateTime.now().plusHours(4).plusMinutes(10));
                order11.setCreation_date(java.time.LocalDateTime.now());
                order11.setStatus("pending");
                orderRepo.save(order11);

                PurchaseOrder order12 = new PurchaseOrder();
                order12.setClient(clientRepo.findById(2L).orElse(null));
                order12.setOperator(operatorRepo.findById(11L).orElse(null));
                order12.setDelivery(deliveryRepo.findById(2L).orElse(null));
                order12.setDelivery_date(java.time.LocalDateTime.now().plusHours(1).plusMinutes(50));
                order12.setCreation_date(java.time.LocalDateTime.now());
                order12.setStatus("preparation");
                orderRepo.save(order12);

                PurchaseOrder order13 = new PurchaseOrder();
                order13.setClient(clientRepo.findById(3L).orElse(null));
                order13.setOperator(operatorRepo.findById(12L).orElse(null));
                order13.setDelivery(deliveryRepo.findById(3L).orElse(null));
                order13.setDelivery_date(java.time.LocalDateTime.now().plusHours(3).plusMinutes(5));
                order13.setCreation_date(java.time.LocalDateTime.now());
                order13.setStatus("sent");
                orderRepo.save(order13);

                PurchaseOrder order14 = new PurchaseOrder();
                order14.setClient(clientRepo.findById(4L).orElse(null));
                order14.setOperator(operatorRepo.findById(13L).orElse(null));
                order14.setDelivery(deliveryRepo.findById(4L).orElse(null));
                order14.setDelivery_date(java.time.LocalDateTime.now().plusHours(2).plusMinutes(35));
                order14.setCreation_date(java.time.LocalDateTime.now());
                order14.setStatus("pending");
                orderRepo.save(order14);

                PurchaseOrder order15 = new PurchaseOrder();
                order15.setClient(clientRepo.findById(5L).orElse(null));
                order15.setOperator(operatorRepo.findById(14L).orElse(null));
                order15.setDelivery(deliveryRepo.findById(5L).orElse(null));
                order15.setDelivery_date(java.time.LocalDateTime.now().plusHours(1).plusMinutes(40));
                order15.setCreation_date(java.time.LocalDateTime.now());
                order15.setStatus("preparation");
                orderRepo.save(order15);

                PurchaseOrder order16 = new PurchaseOrder();
                order16.setClient(clientRepo.findById(6L).orElse(null));
                order16.setOperator(operatorRepo.findById(15L).orElse(null));
                order16.setDelivery(deliveryRepo.findById(1L).orElse(null));
                order16.setDelivery_date(java.time.LocalDateTime.now().plusHours(3).plusMinutes(25));
                order16.setCreation_date(java.time.LocalDateTime.now());
                order16.setStatus("delivered");
                orderRepo.save(order16);

                PurchaseOrder order17 = new PurchaseOrder();
                order17.setClient(clientRepo.findById(7L).orElse(null));
                order17.setOperator(operatorRepo.findById(16L).orElse(null));
                order17.setDelivery(deliveryRepo.findById(2L).orElse(null));
                order17.setDelivery_date(java.time.LocalDateTime.now().plusHours(2).plusMinutes(55));
                order17.setCreation_date(java.time.LocalDateTime.now());
                order17.setStatus("sent");
                orderRepo.save(order17);

                PurchaseOrder order18 = new PurchaseOrder();
                order18.setClient(clientRepo.findById(8L).orElse(null));
                order18.setOperator(operatorRepo.findById(17L).orElse(null));
                order18.setDelivery(deliveryRepo.findById(3L).orElse(null));
                order18.setDelivery_date(java.time.LocalDateTime.now().plusHours(1).plusMinutes(35));
                order18.setCreation_date(java.time.LocalDateTime.now());
                order18.setStatus("pending");
                orderRepo.save(order18);

                PurchaseOrder order19 = new PurchaseOrder();
                order19.setClient(clientRepo.findById(9L).orElse(null));
                order19.setOperator(operatorRepo.findById(18L).orElse(null));
                order19.setDelivery(deliveryRepo.findById(4L).orElse(null));
                order19.setDelivery_date(java.time.LocalDateTime.now().plusHours(2).plusMinutes(10));
                order19.setCreation_date(java.time.LocalDateTime.now());
                order19.setStatus("preparation");
                orderRepo.save(order19);

                PurchaseOrder order20 = new PurchaseOrder();
                order20.setClient(clientRepo.findById(10L).orElse(null));
                order20.setOperator(operatorRepo.findById(19L).orElse(null));
                order20.setDelivery(deliveryRepo.findById(5L).orElse(null));
                order20.setDelivery_date(java.time.LocalDateTime.now().plusHours(3).plusMinutes(45));
                order20.setCreation_date(java.time.LocalDateTime.now());
                order20.setStatus("sent");
                orderRepo.save(order20);

                PurchaseOrder order21 = new PurchaseOrder();
                order21.setClient(clientRepo.findById(1L).orElse(null));
                order21.setOperator(operatorRepo.findById(20L).orElse(null));
                order21.setDelivery(deliveryRepo.findById(1L).orElse(null));
                order21.setDelivery_date(java.time.LocalDateTime.now().plusHours(2).plusMinutes(20));
                order21.setCreation_date(java.time.LocalDateTime.now());
                order21.setStatus("delivered");
                orderRepo.save(order21);
                

                OrderDetails detalle1 = new OrderDetails(order1, plateRepo.findById(1L).orElse(null), 2);
                orderDetallesRepo.save(detalle1);
                OrderDetails detalle2 = new OrderDetails(order1, plateRepo.findById(2L).orElse(null), 1);
                orderDetallesRepo.save(detalle2);

                OrderDetails detalle3 = new OrderDetails(order2, plateRepo.findById(3L).orElse(null), 3);
                orderDetallesRepo.save(detalle3);

                OrderDetails detalle4 = new OrderDetails(order2, plateRepo.findById(5L).orElse(null), 2);
                orderDetallesRepo.save(detalle4);

                OrderDetails detalle5 = new OrderDetails(order3, plateRepo.findById(10L).orElse(null), 1);
                orderDetallesRepo.save(detalle5);

                OrderDetails detalle6 = new OrderDetails(order4, plateRepo.findById(25L).orElse(null), 2);
                orderDetallesRepo.save(detalle6);

                OrderDetails detalle7 = new OrderDetails(order2, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle7);

                OrderDetails detalle8 = new OrderDetails(order3, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle8);

                OrderDetails detalle9 = new OrderDetails(order4, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle9);

                OrderDetails detalle10 = new OrderDetails(order5, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle10);

                OrderDetails detalle11 = new OrderDetails(order6, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle11);

                OrderDetails detalle12 = new OrderDetails(order7, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle12);

                OrderDetails detalle13 = new OrderDetails(order8, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle13);

                OrderDetails detalle14 = new OrderDetails(order9, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle14);

                OrderDetails detalle15 = new OrderDetails(order10, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle15);

                OrderDetails detalle16 = new OrderDetails(order11, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle16);

                OrderDetails detalle17 = new OrderDetails(order12, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle17);

                OrderDetails detalle18 = new OrderDetails(order13, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle18);

                OrderDetails detalle19 = new OrderDetails(order14, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle19);

                OrderDetails detalle20 = new OrderDetails(order15, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle20);

                OrderDetails detalle21 = new OrderDetails(order16, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle21);

                OrderDetails detalle22 = new OrderDetails(order17, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle22);

                OrderDetails detalle23 = new OrderDetails(order18, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle23);

                OrderDetails detalle24 = new OrderDetails(order19, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle24);

                OrderDetails detalle25 = new OrderDetails(order20, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle25);

                OrderDetails detalle26 = new OrderDetails(order21, plateRepo.findById((long) random.nextInt(1, 38)).orElse(null), random.nextInt(1, 6));
                orderDetallesRepo.save(detalle26);

                AdditionalOrderDetails additionalDetalle1 = new AdditionalOrderDetails(orderDetallesRepo.findById(1L).orElse(null), additionalRepo.findById(1L).orElse(null));
                additionalPedidoDetallesRepo.save(additionalDetalle1);
                AdditionalOrderDetails additionalDetalle2 = new AdditionalOrderDetails(orderDetallesRepo.findById(2L).orElse(null), additionalRepo.findById(2L).orElse(null));
                additionalPedidoDetallesRepo.save(additionalDetalle2);
                AdditionalOrderDetails additionalDetalle3 = new AdditionalOrderDetails(orderDetallesRepo.findById(3L).orElse(null), additionalRepo.findById(3L).orElse(null));
                additionalPedidoDetallesRepo.save(additionalDetalle3);
                AdditionalOrderDetails additionalDetalle4 = new AdditionalOrderDetails(orderDetallesRepo.findById(4L).orElse(null), additionalRepo.findById(4L).orElse(null));
                additionalPedidoDetallesRepo.save(additionalDetalle4);
                AdditionalOrderDetails additionalDetalle5 = new AdditionalOrderDetails(orderDetallesRepo.findById(5L).orElse(null), additionalRepo.findById(5L).orElse(null));
                additionalPedidoDetallesRepo.save(additionalDetalle5);

        }


}