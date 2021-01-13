package com.koreait.petshop.controller.product;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.koreait.petshop.model.common.FileManager;
import com.koreait.petshop.model.common.MessageData;
import com.koreait.petshop.model.domain.Color;
import com.koreait.petshop.model.domain.Product;
import com.koreait.petshop.model.domain.Psize;
import com.koreait.petshop.model.product.service.ProductService;
import com.koreait.petshop.model.product.service.SubCategoryService;
import com.koreait.petshop.model.product.service.TopCategoryService;

@Controller
@RequestMapping("/async")
public class RestProductController implements ServletContextAware{
	private static final Logger logger = LoggerFactory.getLogger(RestProductController.class);
	
	@Autowired
	private TopCategoryService topCategoryService;
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileManager fileManager;
	
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		//이 타이밍을 놓치지말고 실제 물리적 경로를 FileManager에 대입해놓자!!
		fileManager.setSaveBasicDir(servletContext.getRealPath(fileManager.getSaveBasicDir()));
		fileManager.setSaveAddonDir(servletContext.getRealPath(fileManager.getSaveAddonDir()));
		
		logger.debug("저장 경로 "+this.servletContext.getRealPath(fileManager.getSaveBasicDir()));
		logger.debug(fileManager.getSaveBasicDir());
	}
	
	//등록
	@RequestMapping(value="/admin/product/regist", method=RequestMethod.POST)
	@ResponseBody
	public MessageData getProductRegist(Product product) {
		logger.debug("하위카테고리 "+product.getSubcategory_id());
		logger.debug("상품명 "+product.getProduct_name());
		logger.debug("가격 "+product.getPrice());
		logger.debug("상세내용 "+product.getDetail());
		
		
		
		for(Color color : product.getColors() ) {
			logger.debug("색상  "+color.getPicker());
		}
		for(Psize psize : product.getPsize() ) {
			logger.debug("사이즈  "+psize.getPetfit());
		}
		
		productService.regist(fileManager, product);
		
		MessageData messageData = new MessageData();
		messageData.setResultCode(1);
		messageData.setMsg("등록성공.");
		messageData.setUrl("/admin/product/list");

		return messageData;
	}
	
	//수정
	@PostMapping(value="/admin/product/update")
	@ResponseBody
	public MessageData getProductEdit() {
		return null;
	}
	
	//삭제
	//삭제요청
	@PostMapping("/admin/product/del")
	@ResponseBody
	public MessageData getDelete(Product product) {
		//삭제
		productService.delete(product.getProduct_id());
		
		//파일 삭제
		String basic=product.getDelRep();
		fileManager.delFile(new File(fileManager.getSaveBasicDir()+File.separator+basic));
		for(int i=0; i<product.getDelAdd().size(); i++) {
			List addon=product.getDelAdd();
			fileManager.delFile(new File(fileManager.getSaveAddonDir()+File.separator+(String)addon.get(i)));
		}
		
		MessageData messageData = new MessageData();
		messageData.setResultCode(1);
		messageData.setMsg("삭제완료");
		messageData.setUrl("/admin/product/list");
		
		return messageData;
	}
	
	
}
