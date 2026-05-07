package com.maki.Repository;

import static org.junit.jupiter.api.Assertions.*;

import com.maki.web.MakiApplication;
import com.maki.web.entities.Operator;
import com.maki.web.repository.OperatorRepository;

import java.util.List;
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
public class OperatorRepositoryQueryTest {

  @Autowired
  private OperatorRepository operatorRepository;

  private Operator operatorA;
  private Operator operatorB;
  private Operator operatorC;

  @BeforeEach
  public void setUp() {
    operatorA = new Operator("Ana Martinez", "ana.martinez", "123456");
    operatorB = new Operator("Beto Suarez", "beto.suarez", "123456");
    operatorC = new Operator("Carla Gonzalez", "carla.gonzalez", "123456");
    operatorRepository.saveAll(List.of(operatorA, operatorB, operatorC));
  }

  @Test
  public void findByUsernameQuery_returnsOperator() {
    var found = operatorRepository.findByUsername("ana.martinez");
    assertTrue(found.isPresent());
    assertEquals("Ana Martinez", found.get().getName());
  }

  @Test
  public void findByUsernameAndPasswordQuery_returnsOperator() {
    var found = operatorRepository.findByUsernameAndPassword("beto.suarez", "123456");
    assertTrue(found.isPresent());
    assertEquals("Beto Suarez", found.get().getName());
  }

  @Test
  public void searchByNameOrUsernameQuery_returnsMatches() {
    List<Operator> results = operatorRepository.searchByNameOrUsername("car");
    assertEquals(1, results.size());
    assertEquals("Carla Gonzalez", results.get(0).getName());
  }

  @Test
  public void countByUsernameQuery_returnsCorrectCount() {
    Long count = operatorRepository.countByUsername("carla.gonzalez");
    assertEquals(1L, count);
  }

  @Test
  public void findAllOrderByNameAsc_returnsOperatorsSortedByName() {
    List<Operator> results = operatorRepository.findAllOrderByNameAsc();
    assertEquals(3, results.size());
    assertEquals("Ana Martinez", results.get(0).getName());
    assertEquals("Beto Suarez", results.get(1).getName());
    assertEquals("Carla Gonzalez", results.get(2).getName());
  }
}
