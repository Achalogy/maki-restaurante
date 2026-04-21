package com.maki.web.entities;

import java.util.List;

public class PlateWithAdditionals {
  public OrderDetails detail;
  public List<Additional> additionals;

  // IMPORTANTE: Necesitas un constructor vacío para Jackson
  public PlateWithAdditionals() {
  }
}