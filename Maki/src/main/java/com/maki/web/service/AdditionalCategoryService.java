package com.maki.web.service;

import com.maki.web.entities.AdditionalCategory;
import com.maki.web.entities.Category;

import java.util.List;

public interface AdditionalCategoryService extends ServiceInterface<AdditionalCategory> {
  List<AdditionalCategory> findByCategory_Id(Long category_id);
  List<AdditionalCategory> findByAdditional_Id(Long additional_id);

  List<AdditionalCategory> setCategories(Long additional_id, List<Category> categories);
}
