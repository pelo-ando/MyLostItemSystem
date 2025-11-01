package com.example.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.LostItem;
import com.example.app.domain.UpdateInfo;
import com.example.app.login.LoginStatus;
import com.example.app.service.AreaService;
import com.example.app.service.LostItemService;
import com.example.app.service.StrageService;
import com.example.app.service.UpdateInfoService;
import com.example.app.service.UserService;
import com.example.app.validation.ItemGroup;
import com.example.app.validation.UpdateGroup;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LostItemController {
	
	// １ページ当たりの備品表示件数
	private final int NUM_PER_PAGE = 5;
	
	private final LostItemService service;
//	private final ItemTypeService itemTypeService;
	private final AreaService areaService;
	private final StrageService strageService;
	private final UserService userService;
	private final UpdateInfoService updateInfoService;
	private final HttpSession session;
	
	private static final String UPLOAD_DIRECTORY = "C:/Users/moand/gallery";
	
	@GetMapping("/user/list")
	public String showLostItemList(
					@RequestParam(name = "page", defaultValue = "1") Integer page,
					Model model) throws Exception{
			model.addAttribute("lostItems", service.getLostItemsByPage(page, NUM_PER_PAGE));
//			model.addAttribute("lostItems", service.getLostItemList());
			model.addAttribute("currentPage", page);
			// 他のページから戻る際に利用
			session.setAttribute("page", page);
			model.addAttribute("totalPages", service.getTotalPages(NUM_PER_PAGE));
		return "/user/lostitem";
	}
	
	@GetMapping("/user/show/{id}")
	public String show(
					@PathVariable Integer id,
					Model model) throws Exception{
				model.addAttribute("lostItem",service.getLostItemById(id));
				model.addAttribute("updateInfo",updateInfoService.getUpdateInfoById(id));
				System.out.println("show-findById成功");
				// file check
				String showImg = "photo_" + Integer.toString(id) + ".jpg";
				// アップロードされているファイルのリストの取得
				File uploadsDirectory = new File(UPLOAD_DIRECTORY);
				File[] fileList = uploadsDirectory.listFiles();
				
				List <String> fileNames = Arrays.stream(fileList)
						.map(File::getName).toList();
				//
//				for (String name : fileNames) {
//					if (name.equals(showImg)) {
//						fileName = name;
//						break;
//					}
//				}
				model.addAttribute("imgName", showImg);
				model.addAttribute("filenames", fileNames);
				return "/user/show-lostitem";
	}
	
	@GetMapping("/user/add")
	public String getAdd( HttpServletRequest request, Model model) throws Exception{
//			model.addAttribute("itemTypeList", itemTypeService.getItemTypeList());
			model.addAttribute("areaList", areaService.getAreaList());
			model.addAttribute("strageList", strageService.getStrageList());
			model.addAttribute("userList", userService.getUserList());
			model.addAttribute("heading", "忘れ物の登録");
			model.addAttribute("updateInfo", new UpdateInfo());
			//前日の日付を渡す
			LostItem lostItem = new LostItem();
			lostItem.setFindDate(LocalDate.now().minusDays(1));
			model.addAttribute("lostItem", lostItem);
			//
			String currentUri = request.getRequestURI().toString();
			model.addAttribute("currentUri", currentUri);
			//
			String fileName = null;
			model.addAttribute("name", fileName);
			return "user/save-lostitem";
		
	}

	// 追加画面で保存ボタンが押された時
	@PostMapping("/user/add")
	public String postAdd(HttpServletRequest request,
					@Validated(ItemGroup.class) LostItem lostItem,
					Errors errors,
					Model model,
					RedirectAttributes redirectAttributes) throws Exception {
				
				if (errors.hasErrors()) {
					model.addAttribute("areaList", areaService.getAreaList());
					model.addAttribute("strageList", strageService.getStrageList());
					model.addAttribute("userList", userService.getUserList());
					model.addAttribute("heading", "忘れ物の登録");
					//URI取得して渡す
					String currentUri = request.getRequestURI().toString();
					model.addAttribute("currentUri", currentUri);
					System.out.println(errors);
					return "user/save-lostitem";
				}
				
				LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");
				lostItem.setUserId(loginStatus.getId());
				
				service.addLostItem(lostItem);
				//
				// 画像が取り込まれている時は、画像を保存する
				//
				byte [] imageBytes = (byte []) session.getAttribute("imageBytes");
				// パスとファイル名をここで指定すればいいのね！ここではダメ！！
	            String filePath = UPLOAD_DIRECTORY + "/photo_" + Integer.toString(lostItem.getId()) + ".jpg";

	            //画像をファイルに保存
	            try (FileOutputStream fos = new FileOutputStream(filePath)) {
	                fos.write(imageBytes);
	           	}catch (IOException e) {
	        		e.printStackTrace();
	            }

				
				
				
				
//				File oldFile = new File(UPLOAD_DIRECTORY + "/temp.jpg");
//				File newFile = new File(UPLOAD_DIRECTORY + "/photo_"+ Integer.toString(lostItem.getId()) + ".jpg");
//
//				if(oldFile.renameTo(newFile)) {
//					System.out.println("変更OK");
//				}else {
//					System.out.println("変更失敗");
//				}
				
				
				
				redirectAttributes.addFlashAttribute("message", "忘れ物を新規登録しました。");
				// 追加後に戻るページ
				int totalPages = service.getTotalPages(NUM_PER_PAGE);
				return "redirect:/user/list?page=" + totalPages;
	}
	
	@GetMapping("/user/edit/{id}")
	public String edit(
					@PathVariable Integer id,
					HttpServletRequest request,
					Model model) throws Exception{
				LostItem oriLostItem = service.getLostItemById(id);
				model.addAttribute("lostItem",oriLostItem);
				session.setAttribute("registerAt",oriLostItem.getRegisterAt());
				session.setAttribute("oriLostItem",oriLostItem);
//				model.addAttribute("itemTypeList", itemTypeService.getItemTypeList());
				model.addAttribute("areaList", areaService.getAreaList());
				model.addAttribute("strageList", strageService.getStrageList());
				model.addAttribute("user", userService.getUserById(id));
				model.addAttribute("updateInfo" , updateInfoService.getUpdateInfoById(id));
				model.addAttribute("heading", "忘れ物の編集");
				
				//URI取得して渡す
				String currentUri = request.getRequestURI().toString();
				model.addAttribute("currentUri", currentUri);
				// statusをStremで渡す
				Stream<String> stream =
						Stream.of("保管中", "対応中", "処置済", "警察届中", "その他");
				model.addAttribute("statuses", stream);
				// 編集内容を受け取る準備
				model.addAttribute("infoOut", new UpdateInfo());
				// file check
				String showImg = "photo_" + Integer.toString(id) + ".jpg";
				// アップロードされているファイルのリストの取得
				String fileName = null;
				File uploadsDirectory = new File(UPLOAD_DIRECTORY);
				File[] fileList = uploadsDirectory.listFiles();
				
				List <String> fileNames = Arrays.stream(fileList)
						.map(File::getName).toList();
				//
				for (String name : fileNames) {
					if (name.equals(showImg)) {
						fileName = name;
						break;
					}
				}
//				model.addAttribute("idName", showImg);
				model.addAttribute("name", fileName);
				return "/user/save-lostitem";
	}
	

	@PostMapping("/user/edit/{id}")
	public String edit_addUpdate(
						@PathVariable Integer id,
						HttpServletRequest request,
						@Validated(UpdateGroup.class) UpdateInfo infoOut,
						@Validated(ItemGroup.class) LostItem lostItem,
						
						Errors errors,
						Model model,
						RedirectAttributes redirectAttributes) throws Exception {

					if (errors.hasErrors()) {
//						model.addAttribute("lostItem", service.getLostItemById(id));
						model.addAttribute("areaList", areaService.getAreaList());
						model.addAttribute("strageList", strageService.getStrageList());
						model.addAttribute("user", userService.getUserById(id));
						model.addAttribute("updateInfo", updateInfoService.getUpdateInfoById(id));						model.addAttribute("heading", "忘れ物の編集");
						//URI取得して渡す
						String currentUri = request.getRequestURI().toString();
						model.addAttribute("currentUri", currentUri);
						// statusをStremで渡す
						Stream<String> stream =
								Stream.of("保管中", "対応中", "処置済", "警察届中", "その他");
						model.addAttribute("statuses", stream);
						System.out.println("from PostEdit" + errors);
						UpdateInfo updateInfo = new UpdateInfo();
						model.addAttribute("infoOut", updateInfo);
						return "user/save-lostitem";
					}
				// 変更箇所がない場合メッセージを表示する
				// 変更箇所がある場合、または変更内容テキストに入力がある場合のみ、保存処理をする
//				if(oldLostItem.getFindDate().equals(lostItem.getFindDate()) &&
//					oldLostItem.getAreaId() == lostItem.getAreaId() &&
//					oldLostItem.getContent().equals(lostItem.getContent()) &&
//					oldLostItem.getFindPersonName().equals(lostItem.getFindPersonName()) &&
//					oldLostItem.getStrageId() == lostItem.getStrageId() &&
//					oldLostItem.getStatus().equals(lostItem.getStatus())) {

				LostItem oldLostItem = (LostItem) session.getAttribute("oriLostItem");
				if(!(service.isChangeObj(oldLostItem, lostItem)) || !(updateInfoService.isOkLength(infoOut))){
					if(!(service.isChangeObj(oldLostItem,lostItem))){
						errors.rejectValue("content", "error.nonChange_items");
					} // end if
					if(!(updateInfoService.isOkLength(infoOut))) {
						errors.rejectValue("findPersonName", "error.over_length_info_memo");
					} // end if
									
					model.addAttribute("areaList", areaService.getAreaList());
					model.addAttribute("strageList", strageService.getStrageList());
					model.addAttribute("user", userService.getUserById(id));
					model.addAttribute("updateInfo", updateInfoService.getUpdateInfoById(id));						model.addAttribute("heading", "忘れ物の編集");
					//URI取得して渡す
					String currentUri = request.getRequestURI().toString();
					model.addAttribute("currentUri", currentUri);
					// statusをStremで渡す
					Stream<String> stream =
							Stream.of("保管中", "対応中", "処置済", "警察届中", "その他");
					model.addAttribute("statuses", stream);
					UpdateInfo updateInfo = new UpdateInfo();
					model.addAttribute("infoOut", updateInfo);
					//
					return "user/save-lostitem";
				}
				
				// 編集データの保存準備
				infoOut.setContentId(lostItem.getId());
				LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");
				infoOut.setModifyPersonId(loginStatus.getId());
				
				updateInfoService.addUpdateInfo(infoOut);
				
				Object obj = session.getAttribute("registerAt");

				// 	登録日は変更しないので、sessionから呼び出してキャストして変換
				if (obj instanceof LocalDateTime localDateTime) {
				    System.out.println("変換成功: " + localDateTime);
				    lostItem.setRegisterAt(localDateTime);
				}
				//
				lostItem.setUserId(loginStatus.getId());
				service.editLostItem(lostItem);
//				updateInfoService.add
				System.out.println("editLostItem成功");
				redirectAttributes.addFlashAttribute("message", "忘れ物を編集しました。");
				//
				// 画像が取り込まれている時は、画像を保存する
				//
				
				    
				// 編集後に戻るページ
				int previousPage = (int) session.getAttribute("page");
				return "redirect:/user/list?page=" + previousPage;
	}
	
	

		// 画像があるかないか まだ機能していない！
		public String setImgFileName (String imgName) {
			// アップロードされているファイルのリストの取得
			String fileName = "";
			File uploadsDirectory = new File(UPLOAD_DIRECTORY);
			File[] fileList = uploadsDirectory.listFiles();
			
			List <String> fileNames = Arrays.stream(fileList)
					.map(File::getName).toList();
			//
			for (String name : fileNames) {
				if (name.equals(imgName)) {
					fileName = name;
					break;
				}
			}
			return fileName;
		}
}