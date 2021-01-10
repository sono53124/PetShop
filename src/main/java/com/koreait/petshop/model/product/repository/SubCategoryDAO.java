package com.koreait.petshop.model.product.repository;

import java.util.List;

public interface SubCategoryDAO {
	public List selectAllById(int topcategory_id);
}
