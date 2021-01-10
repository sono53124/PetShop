package com.koreait.petshop.model.product.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.petshop.model.common.FileManager;
import com.koreait.petshop.model.domain.Color;
import com.koreait.petshop.model.domain.Image;
import com.koreait.petshop.model.domain.Product;
import com.koreait.petshop.model.domain.Psize;
import com.koreait.petshop.model.product.repository.ColorDAO;
import com.koreait.petshop.model.product.repository.ImageDAO;
import com.koreait.petshop.model.product.repository.ProductDAO;
import com.koreait.petshop.model.product.repository.PsizeDAO;

@Service
public class ProductServiceImpl implements ProductService{
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private PsizeDAO psizeDAO;
	@Autowired
	private ColorDAO colorDAO;
	
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List selectById(int subcategory_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product select(int product_id) {
		// TODO Auto-generated method stub
		return null;
	}

	//등록
	public void regist(FileManager fileManager, Product product) {
		String ext=fileManager.getExtend(product.getRepImg().getOriginalFilename());
		product.setFilename(ext);
		productDAO.regist(product);
		
		//파일 업로드!!는 FileManager에게 시키자
		//대표이미지 업로드
		//상품의 product_id를 이용하여, 대표이미지명을 결정짓자!!
		String basicImg = product.getProduct_id()+"."+fileManager.getExtend(product.getRepImg().getOriginalFilename());
		fileManager.saveFile(fileManager.getSaveBasicDir()+File.separator+basicImg, product.getRepImg());
		
		//추가이미지 업로드 (FileManager에게 추가이미지 갯수만큼 업로드 시키면 된다.)
		for(int i=0; i<product.getAddImg().length; i++) {
			
			MultipartFile file=product.getAddImg()[i];
			ext=fileManager.getExtend(file.getOriginalFilename());
			
			//image table에 넣기
			Image image = new Image();
			image.setProduct_id(product.getProduct_id()); //fk
			image.setFilename(ext); //확장자 넣기
			imageDAO.insert(image);
			
			//메니저의 저장 메서드 호출
			String addImg = image.getImage_id()+"."+fileManager.getExtend(file.getOriginalFilename());
			fileManager.saveFile(fileManager.getSaveAddonDir()+File.separator+addImg, file);
		}
		
		//기다 옵션 중, 색상 사이즈 넣기
		
		//사이즈
		for(Psize psize : product.getPsize()) {
			//logger.debug("선택한 사이즈는 ="+psize.getFit());
			psize.setProduct_id(product.getProduct_id());//fk대입
			psizeDAO.insert(psize);
		}
		
		//색상
		for(Color color : product.getColor()) {
			//logger.debug("선택한 색상은 ="+color.getPicker());
			color.setProduct_id(product.getProduct_id());
			colorDAO.insert(color);
		}
		
	}

	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int product_id) {
		// TODO Auto-generated method stub
		
	}

}
