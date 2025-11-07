package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.UpdateInfo;
import com.example.app.mapper.UpdateInfoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
	public class UpdateInfoServiceImpl implements UpdateInfoService{
	
		private final UpdateInfoMapper updateInfoMapper;
	
		@Override
		public List<UpdateInfo> getUpdateInfoList() throws Exception {
			return updateInfoMapper.selectAll();
		}
		 
		@Override
		public List<UpdateInfo> getUpdateInfoById(Integer id) throws Exception {
			return updateInfoMapper.selectById(id);
		}
		
		@Override
		public void addUpdateInfo(UpdateInfo updateInfo) throws Exception{
			updateInfoMapper.insert(updateInfo);
		}

		@Override
		public List<Integer> getUpdateInfoId(Integer id) throws Exception{
			return updateInfoMapper.selectByContentId(id);
		}
		
		@Override
		public Boolean isOkLength(UpdateInfo updateInfo) throws Exception{
			if(updateInfo.getMemo().length() > 100) {
				System.out.println("NGLength:" + updateInfo.getMemo().length() );
				return false;
			}else {
				System.out.println("OKLength:" + updateInfo.getMemo().length() );
				return true;
			}
			
		}
		
		@Override
		public void deleteUpdateInfo(Integer id) throws Exception{
			updateInfoMapper.delete(id);
		}
		
				
		
}