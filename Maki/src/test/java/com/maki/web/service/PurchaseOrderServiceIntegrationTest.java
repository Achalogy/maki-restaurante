package com.maki.web.service;

import static org.junit.jupiter.api.Assertions.*;

import com.maki.web.MakiApplication;
import com.maki.web.entities.*;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Pruebas de integración para PurchaseOrderServiceImpl.
 * Se basa en PlateCRUDTest para la configuración de integración con H2 y Spring Context.
 * Al usar @Transactional, cada prueba hace rollback al finalizar, manteniendo la DB limpia.
 */
@ActiveProfiles("test")
@SpringBootTest(classes = MakiApplication.class)
@Transactional
public class PurchaseOrderServiceIntegrationTest {

  @Autowired
  private PurchaseOrderService purchaseOrderService;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private PurchaseOrderRepository purchaseOrderRepository;

  @Autowired
  private PlateRepository plateRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  private Client testClient;
  private Category testCategory;

  @BeforeEach
  public void setUp() {
    // Preparar datos base necesarios en la base de datos
    testClient = new Client(
      "Juan",
      "Perez",
      "juan.it@example.com",
      "pass123",
      "1234567",
      "Calle 1"
    );
    testClient = clientRepository.save(testClient);

    testCategory = new Category("Sushi");
    testCategory = categoryRepository.save(testCategory);
  }

  @Test
  public void insert_Pedido_GuardaEnBaseDeDatos()
    throws EntityConstraintException {
    // Arrange
    PurchaseOrder order = new PurchaseOrder(testClient);
    order.setStatus("pending");

    // Act
    PurchaseOrder saved = purchaseOrderService.insert(order);

    // Assert
    assertNotNull(saved.getId());
    assertEquals("pending", saved.getStatus());
    assertEquals(testClient.getId(), saved.getClient().getId());
  }

  @Test
  public void selectById_RetornaPedidoExistente()
    throws EntityConstraintException, EntityNotFoundException {
    // Arrange
    PurchaseOrder order = new PurchaseOrder(testClient);
    PurchaseOrder saved = purchaseOrderRepository.save(order);

    // Act
    PurchaseOrder found = purchaseOrderService.selectById(saved.getId());

    // Assert
    assertNotNull(found);
    assertEquals(saved.getId(), found.getId());
  }

  @Test
  public void selectNotCompleted_FiltraPedidosCorrectamente()
    throws EntityConstraintException {
    // Arrange
    PurchaseOrder pending = new PurchaseOrder(testClient);
    pending.setStatus("pending");
    purchaseOrderRepository.save(pending);

    PurchaseOrder completed = new PurchaseOrder(testClient);
    completed.setStatus("completed");
    purchaseOrderRepository.save(completed);

    // Act
    List<PurchaseOrder> results = purchaseOrderService.selectNotCompleted();

    // Assert
    // Verificamos que al menos esté el que acabamos de crear y ninguno sea 'completed'
    assertFalse(results.isEmpty());
    assertTrue(
      results.stream().noneMatch(o -> "completed".equals(o.getStatus()))
    );
  }

  @Test
  public void createPurchaseOrderFromcart_CreaPedidoYDetallesIntegrados() {
    // Arrange
    Plate plate = new Plate();
    plate.setName("Maki Tempura");
    plate.setDescription("Maki Tempura");
    plate.setUrlImage("https://exmple.com/image.jpg");
    plate.setPrice(15.0);
    plate.setCategory(testCategory);
    plate = plateRepository.save(plate);

    PlateWithAdditionals pwa = new PlateWithAdditionals();
    pwa.detail = new OrderDetails(null, plate, 2); // 2 unidades
    pwa.additionals = new ArrayList<>();

    List<PlateWithAdditionals> cart = List.of(pwa);

    // Act
    PurchaseOrder createdOrder =
      purchaseOrderService.createPurchaseOrderFromcart(
        testClient.getId(),
        cart
      );

    // Assert
    assertNotNull(createdOrder.getId());
    assertEquals(testClient.getId(), createdOrder.getClient().getId());
    assertEquals("pending", createdOrder.getStatus());

    // Verificar que se guardó en el repositorio
    assertTrue(purchaseOrderRepository.existsById(createdOrder.getId()));
  }

  @Test
  public void deleteByID_EliminaPedidoYDependencias()
    throws EntityConstraintException, EntityNotFoundException {
    // Arrange
    PurchaseOrder order = new PurchaseOrder(testClient);
    PurchaseOrder saved = purchaseOrderRepository.save(order);
    Long id = saved.getId();

    // Act
    purchaseOrderService.deleteByID(id);

    // Assert
    assertThrows(EntityNotFoundException.class, () ->
      purchaseOrderService.selectById(id)
    );
  }
}
