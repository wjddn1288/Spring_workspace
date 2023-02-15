package com.edu.springboard.model.gallery;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springboard.domain.Photo;
import com.edu.springboard.exception.PhotoException;

@Repository
public class MybatisPhotoDAO implements PhotoDAO{
	@Autowired
	private SqlSessionTemplate SqlSessionTemplate;
	
	@Override
	public List selectAll() {
		return SqlSessionTemplate.selectList("Photo.selecAll");
	}

	@Override
	public Photo select(int photo_idx) {
		return SqlSessionTemplate.selectOne("Photo.select", photo_idx);
	}

	@Override
	public void insert(Photo photo) throws PhotoException{
		int result=SqlSessionTemplate.insert("Photo.insert", photo);
		if(result<1) {
			throw new PhotoException("이미지 등록 실패");
		}
	}

	@Override
	public void deleteByGallery(int gallery_idx) throws PhotoException{
		int result=SqlSessionTemplate.delete("Photo.insert", gallery_idx);
		if(result<1) {
			throw new PhotoException("이미지 삭제 실패");
		}

	}

}
