package com.edu.springboard.model.gallery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edu.springboard.domain.Gallery;
import com.edu.springboard.model.util.FileManager;

@Service
public class GalleryServiceImpl implements GalleryService{

	@Autowired
	@Qualifier("MybatisGalleryDAO")
	private GalleryDAO galleryDAO; //어떤 DAO를 쓸지 모르니까
	
	@Autowired
	@Qualifier("MybatisGalleryDAO")
	private PhotoDAO photoDAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gallery select(int gallery_idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void regist(Gallery gallery, String path) {
		//gallerydao 시키기
		
		//photodao 시키기
		
		//fileManager 시키기
	}

	@Override
	public void update(Gallery gallery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Gallery gallery) {
		// TODO Auto-generated method stub
		
	}

}
