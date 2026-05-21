package com.maki.web;

import com.maki.web.entities.Additional;
import com.maki.web.entities.AdditionalCategory;
import com.maki.web.entities.Administrator;
import com.maki.web.entities.Category;
import com.maki.web.entities.Client;
import com.maki.web.entities.Plate;
import com.maki.web.security.CustomUserDetailService;
import com.maki.web.entities.UserEntity;
import com.maki.web.repository.UserEntityRepository; 
import com.maki.web.repository.AdditionalCategoryRepository;
import com.maki.web.repository.AdditionalRepository;
import com.maki.web.repository.AdministratorRepository;
import com.maki.web.repository.CategoryRepository;
import com.maki.web.repository.ClientRepository;
import com.maki.web.repository.PlateRepository;
import com.maki.web.repository.RoleRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Transactional
@Profile("test")
public class DataloaderTest implements CommandLineRunner {

  @Autowired
  private ClientRepository clientRepo;

  @Autowired
  private AdministratorRepository adminRepo;

  @Autowired
  private CategoryRepository categoriaRepo;

  @Autowired
  private PlateRepository plateRepo;

  @Autowired
  private AdditionalRepository additionalRepo;

  @Autowired
  private AdditionalCategoryRepository adcatRepo;

  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Autowired
  private UserEntityRepository userRepository; 
  @Autowired
  private RoleRepository roleRepo;

  @Override
  public void run(String... args) throws Exception {

    roleRepo.save(new com.maki.web.entities.Role("CLIENT"));
    roleRepo.save(new com.maki.web.entities.Role("ADMIN"));
    roleRepo.save(new com.maki.web.entities.Role("OPERATOR"));

    Client client = new Client(
        "Miguel",
        "Vargas",
        "acha@acha.dev",
        "eveyzoe",
        "+57 314 852 7241",
        "Cra 123 #24-242B"
    );
    
    UserEntity clientUser = customUserDetailService.clientToUserEntity(client);
    clientUser = userRepository.save(clientUser);
    client.setUser(clientUser);
    clientRepo.save(client);

    // Categorías
    Category entradas = categoriaRepo.save(new Category("Entradas"));
    Category sushi = categoriaRepo.save(new Category("Sushi"));

    // Al menos 2 platos para el menú
    Plate p1 = new Plate(
      "Gyoza",
      31960,
      "Empanadillas fritas rellenas de cerdo.",
      "https://images.unsplash.com/photo-1738681336104-608b4e7dc3b0?w=500",
      true
    );
    p1.setCategory(entradas);
    plateRepo.save(p1);

    Plate p2 = new Plate(
      "Sushi Variado",
      51960,
      "Combinación de nigiri y rollos.",
      "https://images.unsplash.com/photo-1581781870027-04212e231e96?w=500",
      true
    );
    p2.setCategory(sushi);
    plateRepo.save(p2);

    // Al menos 2 adicionales por categoría
    Additional a1 = additionalRepo.save(new Additional("Alga Nori", 3960));
    Additional a2 = additionalRepo.save(new Additional("Wasabi", 2960));
    Additional a3 = additionalRepo.save(new Additional("Jengibre", 1960));

    adcatRepo.save(new AdditionalCategory(entradas.getId(), a1.getId()));
    adcatRepo.save(new AdditionalCategory(entradas.getId(), a2.getId()));
    adcatRepo.save(new AdditionalCategory(entradas.getId(), a3.getId()));
    adcatRepo.save(new AdditionalCategory(sushi.getId(), a1.getId()));
    adcatRepo.save(new AdditionalCategory(sushi.getId(), a2.getId()));
    adcatRepo.save(new AdditionalCategory(sushi.getId(), a3.getId()));

    // Admin
    Administrator admin = new Administrator("Tomas", "Neon", "4567");
    
    UserEntity adminUser = customUserDetailService.administratorToUserEntity(admin);
    adminUser = userRepository.save(adminUser);
    admin.setUser(adminUser);
    adminRepo.save(admin);
  }
}