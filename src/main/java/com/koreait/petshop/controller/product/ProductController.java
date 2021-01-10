package com.koreait.petshop.controller.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.koreait.petshop.model.common.MessageData;
import com.koreait.petshop.model.domain.Product;
import com.koreait.petshop.model.domain.Psize;
import com.koreait.petshop.model.domain.SubCategory;
import com.koreait.petshop.model.product.service.SubCategoryService;
import com.koreait.petshop.model.product.service.TopCategoryService;

@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private TopCategoryService topCategoryService;
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	//목록 폼
	@RequestMapping(value="/admin/product/list", method=RequestMethod.GET )
	public String getProductList() {
		return "admin/product/product_list";
	}
	
	//등록 폼
	@RequestMapping(value="/admin/product/registform")
	public ModelAndView registForm() {
		List topList=topCategoryService.selectAll();
		ModelAndView mav = new ModelAndView("admin/product/regist_form");
		mav.addObject("topList", topList);
		
		return mav;
	}
	
	//하위 카테고리 가져오기
	@GetMapping("/admin/product/sublist")
	@ResponseBody
	public List getSubList(int topcategory_id) {
		List<SubCategory> subList=subCategoryService.selectAllById(topcategory_id);
		return subList;
	}
	
}
