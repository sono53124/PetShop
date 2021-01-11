package com.koreait.petshop.model.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.petshop.model.domain.SubCategory;
import com.koreait.petshop.model.product.repository.SubCategoryDAO;

@Service
public class SubCategoryServiceImpl implements SubCategoryService{
	@Autowired
	private SubCategoryDAO subCategoryDAO;
	
	public List selectAllById(int topcategory_id) {
		return subCategoryDAO.selectAllById(topcategory_id);
	}

	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SubCategory select(int subcategory_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(SubCategory subCategory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SubCategory subCategory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int SubCategory_id) {
		// TODO Auto-generated method stub
		
	}

}
