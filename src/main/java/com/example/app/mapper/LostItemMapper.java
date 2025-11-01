package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.LostItem;

@Mapper
public interface LostItemMapper {

	List<LostItem> findAll() ;
	LostItem findById(Integer id) ;
	
	// ページ分割機能用
	List<LostItem> findLimited(@Param("offset") int offset, @Param("limit") int limit);
	Long count();
	
	void insert(LostItem lostItem) ;
	void update(LostItem lostItemd) ;
//	List<LostItem> findByDate(Date from, Date to) throws Exception;
//	void delete(Integer id) throws Exception;
//	long countAll() throws Exception;
//	long countFindByDate(Date from, Date to) throws Exception;
	
}
