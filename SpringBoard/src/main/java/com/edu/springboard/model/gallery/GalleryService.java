package com.edu.springboard.model.gallery;

import java.util.List;

import com.edu.springboard.domain.Gallery;

public interface GalleryService {
	public List selectAll(); //DAO 동일..
	public Gallery select(int gallery_idx); //DAO 동일..
	public void regist(Gallery gallery, String path); //insert + upload 
	public void update(Gallery gallery); //DAO와는 틀리다.. 이미지, DB idx 까지
	public void delete(Gallery gallery); //db+file
}
