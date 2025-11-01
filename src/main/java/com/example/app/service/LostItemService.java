package com.example.app.service;

import java.util.List;

import com.example.app.domain.LostItem;

public interface LostItemService {

	List<LostItem> getLostItemList() ;
	LostItem getLostItemById(Integer id);
	
	// ページ分割機能用
	List<LostItem> getLostItemsByPage(int page, int numPerPage);
	int getTotalPages(int numPerPage);
	
	void addLostItem(LostItem lostItem) ;
	
	void editLostItem(LostItem lostItem);
	
	//編集画面で内容が編集されているかチェック
	Boolean isChangeObj(LostItem info1, LostItem info2);
	
	// pagination導入で書き直し
//	List<LostItem> getLostItemByDate(Date from, Date to) throws Exception;
//	void deleteLostItem(Integer id) throws Exception;
//	void editLostItem(LostItem lostItem) throws Exception;
//	List<LostItem> getLostItemListPerPage(int page, int numPerPage) throws Exception;
//	int TotalPages(int numPage) throws Exception;
	
	
}
