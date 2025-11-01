package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.LostItem;
import com.example.app.mapper.LostItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LostItemServiceImpl implements LostItemService {

//	private final LostItemMapper lostItemMapper;
	@Autowired
	LostItemMapper lostItemMapper;
	
	@Override
	public List<LostItem> getLostItemList() {
		return lostItemMapper.findAll();
	}
	
	@Override
	public LostItem getLostItemById(Integer id) {
		return lostItemMapper.findById(id);
	}
	
	// ページ分割機能用
	@Override
	public List<LostItem> getLostItemsByPage(int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
		return lostItemMapper.findLimited(offset, numPerPage);
	}
	
	@Override
	public int getTotalPages(int numPerPage) {
		double totalNum = lostItemMapper.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}
	
	@Override
	public void addLostItem(LostItem lostItem) {
		lostItemMapper.insert(lostItem);
	}
	
	@Override
	public void editLostItem(LostItem lostItem) {
		lostItemMapper.update(lostItem);
	}
	
	// 編集画面での変更チェック
	@Override
	public Boolean isChangeObj(LostItem info1, LostItem info2) {
		if(info1.getFindDate().equals(info2.getFindDate()) &&
			info1.getAreaId() == info2.getAreaId() &&
			info1.getContent().equals(info2.getContent()) &&
			info1.getFindPersonName().equals(info2.getFindPersonName()) &&
			info1.getStrageId() == info2.getStrageId() &&
			info1.getStatus().equals(info2.getStatus())) {
				return false;
			}
				return true;
	}
	
}
