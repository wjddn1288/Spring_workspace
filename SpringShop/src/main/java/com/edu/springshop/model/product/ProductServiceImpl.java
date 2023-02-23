package com.edu.springshop.model.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springshop.domain.Pimg;
import com.edu.springshop.domain.Product;
import com.edu.springshop.util.FileManager;

@Service
public class ProductServiceImpl implements ProductService {

	// DAO 모델
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private PimgDAO pimgDAO;

	// FileManager 모델
	@Autowired
	private FileManager fileManager;

	@Override
	public List selectAll() {
		return productDAO.selectAll();
	}

	@Override
	public Product select(int product_idx) {
		return productDAO.select(product_idx);
	}

	@Override
	public void regist(Product product, String dir) {
		//상품 저장
		productDAO.insert(product); //select-key에 의해 pk존재하게 됨
		
		//파일 저장
		fileManager.save(product, dir);
		
		List<Pimg> PimgList=product.getPimgList();
		for(Pimg pimg: PimgList) {
			pimgDAO.insert(pimg);
		}
		
		//이미지 저장
		MultipartFile[] photoList=product.getPhoto();
		for(MultipartFile photo : photoList) {
			Pimg pimg= new Pimg();
			//pimgDAO.insert(pimg);
		}
	}

	@Override
	public void update(Product product) {
		productDAO.update(product);
	}

	@Override
	public void delete(int product_idx) {
		productDAO.delete(product_idx);
	}

}
