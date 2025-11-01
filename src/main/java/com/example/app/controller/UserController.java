package com.example.app.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.User;
import com.example.app.service.UserService;
import com.example.app.validation.LoginGroup;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
	
	private static final int NUM_PER_PAGE = 5;

	private final UserService service;
	private final HttpSession session;

	@GetMapping("/list")
	public String list(
			@RequestParam(name="page", defaultValue="1") Integer page,
			Model model) throws Exception {
		// 詳細・追加・編集ページから戻る際に利用
		session.setAttribute("page", page);
				
		int totalPages = service.getTotalPages(NUM_PER_PAGE);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
		model.addAttribute("userList", service.getUserListPerPage(page, NUM_PER_PAGE));
		return "admin/list-user";
	}

	@GetMapping("/add")
	public String add(Model model) {
		User user = new User();
//		user.setBirthday(LocalDate.of(2000, 1, 1)); // 生年月日初期値
		model.addAttribute("user", user);
		model.addAttribute("heading", "利用者の追加");
		return "admin/save-user";
	}

	@PostMapping("/add")
	public String add(
			@Validated(LoginGroup.class) User user,
			Errors errors,
			Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		if(!user.getLoginId().isBlank()) {
			if(service.isExsitingUser(user.getLoginId())) {
				errors.rejectValue("loginId", "error.existing_user_loginId");
			}
		}

		if(errors.hasErrors()) {
			model.addAttribute("heading", "利用者の追加");
			return "admin/save-user";
		}

		service.addUser(user);
		redirectAttributes.addFlashAttribute("message", "利用者を追加しました。");
		
		// 追加後に戻るページ(⇒最終ページ)
		int totalPages = service.getTotalPages(NUM_PER_PAGE);
		return "redirect:/admin/user/list?page=" + totalPages;
	}

	@GetMapping("/edit/{id}")
	public String edit(
			@PathVariable Integer id,
			Model model) throws Exception {
		model.addAttribute("user", service.getUserById(id));
		model.addAttribute("heading", "利用者の追加");
		return "admin/save-user";
	}

	@PostMapping("/edit/{id}")
	public String edit(
			@PathVariable Integer id,
			@Validated(LoginGroup.class) User user,
			Errors errors,
			Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		String originalLoginId = service.getUserById(id).getLoginId();
		// id のユーザー情報を取得
		User preUser = service.getUserById(id);

		if(!user.getLoginId().isBlank()) {
			if(!originalLoginId.equals(user.getLoginId()) && service.isExsitingUser(user.getLoginId())) {
				errors.rejectValue("loginId", "error.existing_user_loginId");
			}
		}

		if(errors.hasErrors()) {
			model.addAttribute("heading", "利用者の編集");
			return "admin/save-user";
		}
		// 登録日と更新日をuserにセット
		user.setRegisterAt(preUser.getRegisterAt());
		user.setUpdateAt(LocalDateTime.now());
		service.editUser(user);
		redirectAttributes.addFlashAttribute("message", "利用者を編集しました。");
		
		// 編集後に戻るページ(元のページ)
		int previousPage = (int) session.getAttribute("page");
		return "redirect:/admin/user/list?page=" + previousPage;
	}

	@GetMapping("/delete/{id}")
	public String delete(
			@PathVariable Integer id,
			RedirectAttributes redirectAttributes) throws Exception {
		service.deleteUserById(id);
		redirectAttributes.addFlashAttribute("message", "利用者を削除しました。");
		
		// 削除後に戻るページ(⇒ページ数が減って、元のページが無くなった場合は最終ページ)
		int previousPage = (int) session.getAttribute("page");
		int totalPages = service.getTotalPages(NUM_PER_PAGE);
		int page = previousPage <= service.getTotalPages(NUM_PER_PAGE) ? previousPage : totalPages;
		return "redirect:/admin/user/list?page=" + page;
	}


}
