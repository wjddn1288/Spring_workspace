package com.edu.springshop.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Pimg;
import com.edu.springshop.exception.PimgException;

@Repository
public class MybatisPimgDAO implements PimgDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List selectAll() {
		List list=sqlSessionTemplate.selectList("pimg.selectAll");
		return list;
	}

	@Override
	public List selectByProduct(int product_idx) {
		return sqlSessionTemplate.selectList("Pimg.selectByProduct", product_idx);
	}

	@Override
	public void insert(Pimg pimg) throws PimgException{
		int result=sqlSessionTemplate.insert("Pimg.insert", pimg);
		if(result<1) {
			throw new PimgException("이미지 등록 실패");
		}
	}

	@Override
	public void delete(int product_idx) throws PimgException{
		int result=sqlSessionTemplate.delete("Pimg.delete", product_idx);
		if(result<1) {
			throw new PimgException("이미지 삭제 실패");
		}
	}

}
