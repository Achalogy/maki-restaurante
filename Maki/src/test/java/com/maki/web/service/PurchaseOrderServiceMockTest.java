package com.maki.web.service;

import com.maki.web.entities.*;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdditionalOrderDetailsRepository;
import com.maki.web.repository.OrderDetailsRepository;
import com.maki.web.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias con Mocks para PurchaseOrderServiceImpl.
 * Se utiliza Mockito para simular el comportamiento de los repositorios y servicios dependientes.
 */
@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceMockTest {

    @Mock
    private PurchaseOrderRepository repo;

    @Mock
    private OrderDetailsRepository orderDetailsRepo;

    @Mock
    private ClientService clientService;

    @Mock
    private AdditionalOrderDetailsRepository additionalOrderDetailsRepo;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    private PurchaseOrder testOrder;
    private Client testClient;

    @BeforeEach
    void setUp() {
        testClient = new Client("Juan", "Perez", "juan@example.com", "123", "555", "Calle Falsa 123");
        testClient.setId(1L);
        testOrder = new PurchaseOrder(testClient);
        testOrder.setId(1L);
    }

    @Test
    void selectAll_ReturnsList() {
        when(repo.findAll()).thenReturn(Arrays.asList(testOrder));

        List<PurchaseOrder> result = purchaseOrderService.selectAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void selectById_Found_ReturnsOrder() throws EntityNotFoundException {
        when(repo.findById(1L)).thenReturn(Optional.of(testOrder));

        PurchaseOrder result = purchaseOrderService.selectById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void selectById_NotFound_ThrowsException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            purchaseOrderService.selectById(99L);
        });
    }

    @Test
    void insert_ValidOrder_ReturnsSavedOrder() throws EntityConstraintException {
        PurchaseOrder newOrder = new PurchaseOrder(testClient);
        newOrder.setId(null); // Aseguramos que no tiene ID para el insert

        when(repo.save(any(PurchaseOrder.class))).thenAnswer(invocation -> {
            PurchaseOrder saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        PurchaseOrder result = purchaseOrderService.insert(newOrder);

        assertNotNull(result.getId());
        assertEquals(10L, result.getId());
        assertNotNull(result.getCreation_date());
        verify(repo, times(1)).save(any(PurchaseOrder.class));
    }

    @Test
    void insert_OrderWithId_ThrowsException() {
        testOrder.setId(1L);
        assertThrows(EntityConstraintException.class, () -> {
            purchaseOrderService.insert(testOrder);
        });
    }

    @Test
    void deleteByID_Success() throws EntityNotFoundException {
        // Arrange
        when(repo.findById(1L)).thenReturn(Optional.of(testOrder));
        when(additionalOrderDetailsRepo.findAll()).thenReturn(new ArrayList<>());
        when(orderDetailsRepo.findAll()).thenReturn(new ArrayList<>());

        // Act
        purchaseOrderService.deleteByID(1L);

        // Assert
        verify(repo, times(1)).delete(testOrder);
    }

    @Test
    void createPurchaseOrderFromcart_Success() {
        // Arrange
        when(clientService.selectById(1L)).thenReturn(testClient);
        // El insert interno llamará a repo.save
        when(repo.save(any(PurchaseOrder.class))).thenAnswer(i -> i.getArgument(0));

        Plate plate = new Plate();
        plate.setName("Sushi Roll");

        OrderDetails detailInput = new OrderDetails();
        detailInput.setPlate(plate);
        detailInput.setQuantity(2);

        PlateWithAdditionals pwa = new PlateWithAdditionals();
        pwa.detail = detailInput;
        pwa.additionals = new ArrayList<>();

        List<PlateWithAdditionals> cart = Arrays.asList(pwa);

        when(orderDetailsRepo.save(any(OrderDetails.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        PurchaseOrder result = purchaseOrderService.createPurchaseOrderFromcart(1L, cart);

        // Assert
        assertNotNull(result);
        assertEquals(testClient, result.getClient());
        assertEquals("pending", result.getStatus());
        verify(clientService).selectById(1L);
        verify(orderDetailsRepo).save(any(OrderDetails.class));
    }

    @Test
    void selectNotCompleted_FiltersCorrectly() {
        PurchaseOrder completedOrder = new PurchaseOrder(testClient);
        completedOrder.setStatus("completed");

        PurchaseOrder pendingOrder = new PurchaseOrder(testClient);
        pendingOrder.setStatus("pending");

        when(repo.findAll()).thenReturn(Arrays.asList(completedOrder, pendingOrder));

        List<PurchaseOrder> result = purchaseOrderService.selectNotCompleted();

        assertEquals(1, result.size());
        assertEquals("pending", result.get(0).getStatus());
    }
}
