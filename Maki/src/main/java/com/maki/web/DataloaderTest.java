package com.maki.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
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
@Profile("test")
public class DataloaderTest implements CommandLineRunner {

    @Autowired private ClientRepository clientRepo;
    @Autowired private CategoryRepository categoriaRepo;
    @Autowired private PlateRepository plateRepo;
    @Autowired private AdditionalRepository additionalRepo;
    @Autowired private AdditionalCategoryRepository adcatRepo;

    @Override
    public void run(String... args) throws Exception {

        // Cliente que usa el test: acha@acha.dev / eveyzoe (id=1)
        clientRepo.save(new Client("Miguel", "Vargas", "acha@acha.dev", "eveyzoe",
                "+57 314 852 7241", "Cra 123 #24-242B"));

        // Categorías
        Category entradas = categoriaRepo.save(new Category("Entradas"));
        Category sushi    = categoriaRepo.save(new Category("Sushi"));

        // Al menos 2 platos para el menú
        Plate p1 = new Plate("Gyoza", 31960,
                "Empanadillas fritas rellenas de cerdo.",
                "https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500", true);
        p1.setCategory(entradas);
        plateRepo.save(p1);

        Plate p2 = new Plate("Sushi Variado", 51960,
                "Combinación de nigiri y rollos.",
                "https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500", true);
        p2.setCategory(sushi);
        plateRepo.save(p2);

        // Al menos 2 adicionales por categoría
        Additional a1 = additionalRepo.save(new Additional("Alga Nori", 3960));
        Additional a2 = additionalRepo.save(new Additional("Wasabi", 2960));
        Additional a3 = additionalRepo.save(new Additional("Jengibre", 1960));

        adcatRepo.save(new AdditionalCategory(entradas.getId(), a1.getId()));
        adcatRepo.save(new AdditionalCategory(entradas.getId(), a2.getId()));
        adcatRepo.save(new AdditionalCategory(entradas.getId(), a3.getId()));
        adcatRepo.save(new AdditionalCategory(sushi.getId(),    a1.getId()));
        adcatRepo.save(new AdditionalCategory(sushi.getId(),    a2.getId()));
        adcatRepo.save(new AdditionalCategory(sushi.getId(),    a3.getId()));
    }
}
