package com.edu.mvc3.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.mvc3.domain.Gallery;

@Service
public class GalleryServiceImpl implements GalleryService{
	
	@Autowired
	private GalleryDAO galleryDAO;
	

	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gallery select(int gallery_idx) {
		// TODO Auto-generated method stub
		return null;
	}

	//파일저장 + db insert
	public void regist(Gallery gallery) {
		//FileManager 
		
		//GalleryDAO
	}

	@Override
	public void update(Gallery gallery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int gallery_idx) {
		// TODO Auto-generated method stub
		
	}
	
}