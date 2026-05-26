package com.maki.Repository;

import static org.junit.jupiter.api.Assertions.*;

import com.maki.web.MakiApplication;
import com.maki.web.entities.Category;
import com.maki.web.entities.Plate;
import com.maki.web.repository.CategoryRepository;
import com.maki.web.repository.PlateRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = MakiApplication.class)
@RunWith(SpringRunner.class)
public class PlateCRUDTest {

    @Autowired private PlateRepository plateRepository;

    @Autowired private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    public void setUp() {
        // Crea una categoría de prueba para asociar a los platos
        Category cat = new Category("Categoría de prueba");
        testCategory = categoryRepository.save(cat);
    }

    // Genera plato con datos básicos para pruebas
    private Plate buildPlate(String name) {
        Plate p = new Plate();
        p.setName(name);
        p.setPrice(9.99);
        p.setDescription("Descripción de " + name);
        p.setUrlImage("https://example.com/imagen.jpg");
        p.setAvailable(true);
        p.setCategory(testCategory);
        return p;
    }

    // ===================== PRUEBAS CRUD =====================
    // SAVE
    @Test
    public void save_plato_retorna_plato_con_id() {
        // Arrange
        Plate plateToSave = buildPlate("Plato test");

        // Act
        Plate saved = plateRepository.save(plateToSave);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Plato test", saved.getName());
    }

    // FIND BY ID
    @Test
    public void findById_retorna_plato_existente() {
        // Arrange
        Plate saved = plateRepository.save(buildPlate("Plato buscable"));

        // Act
        Optional<Plate> found = plateRepository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    // FIND BY ID - INEXISTENTE - RETORNARIA VACIO
    @Test
    public void findById_id_inexistente_retorna_vacio() {
        // Arrange (no specific arrange for non-existent ID)

        // Act
        Optional<Plate> found = plateRepository.findById(99999L);

        // Assert
        assertTrue(found.isEmpty());
    }

    // FIND ALL
    @Test
    public void findAll_retorna_todos_platos() {
        // Arrange
        plateRepository.save(buildPlate("Plato A"));
        plateRepository.save(buildPlate("Plato B"));

        // Act
        var plates = plateRepository.findAll();

        // Assert
        assertEquals(2, plates.size(), "Debería haer exactamente dos platos");

        assertTrue(plates.stream().anyMatch(p -> p.getName().equals("Plato A")));
        assertTrue(plates.stream().anyMatch(p -> p.getName().equals("Plato B")));
    }

    // UPDATE
    @Test
    public void save_actualiza_plato() {
        // Arrange
        Plate saved = plateRepository.save(buildPlate("Original"));
        saved.setName("Actualizado");
        saved.setPrice(19.99);

        // Act
        Plate updated = plateRepository.save(saved);

        // Assert
        assertEquals("Actualizado", updated.getName());
        assertEquals(19.99, updated.getPrice(), 0.01);
    }

    // DELETE
    @Test
    public void deleteById_elimina_plato() {
        // Arrange
        Plate saved = plateRepository.save(buildPlate("Para eliminar"));
        Long id = saved.getId();

        // Act
        plateRepository.deleteById(id);

        // Assert
        assertTrue(plateRepository.findById(id).isEmpty());
    }
}
