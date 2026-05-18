package com.maki.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operator {

  @OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
  private UserEntity user;
  
  @Id
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "username", length = 100, nullable = false, unique = true)
  private String username;

  @Column(name = "password", length = 100, nullable = false)
  private String password;

  public Operator(String name, String username, String password) {
    this.name = name;
    this.username = username;
    this.password = password;
  }
}