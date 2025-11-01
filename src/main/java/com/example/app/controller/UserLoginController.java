package com.example.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Login;
import com.example.app.domain.User;
import com.example.app.login.LoginStatus;
import com.example.app.service.UserService;
import com.example.app.validation.LoginGroup;

import lombok.RequiredArgsConstructor;

	@Controller
	@RequestMapping("/user")
	@RequiredArgsConstructor
	public class UserLoginController {

		private final UserService service;
		private final HttpSession session;

		
		@GetMapping("/login")
		public String login(Model model) {
			model.addAttribute(new Login());
			return "user/login-user";
		}

		@PostMapping("/login")
		public String login(
				@Validated(LoginGroup.class) Login login,
				Errors errors) throws Exception {
			if(errors.hasErrors()) {
				return "user/login-user";
			}

			User user = service.getUserByLoginId(login.getLoginId());
			if(user == null || !login.isCorrectPassword(user.getLoginPass())) {
				errors.rejectValue("loginId", "error.incorrect_id_or_password");
				return "user/login-user";
			}

			// セッションに認証を格納
			LoginStatus loginStatus = LoginStatus.builder()
					.id(user.getId())
					.name(user.getName())
					.loginId(user.getLoginId())
					.authority(user.getUserType())	//見直しloginauthority.ADMINではなくuserTypeをセット
					.build();
			session.setAttribute("loginStatus", loginStatus);
			//System.out.println(loginStatus);
			return "redirect:/user/list";
		}

		@GetMapping("/logout")
		public String logout(
				RedirectAttributes redirectAttributes) {
			session.removeAttribute("loginStatus");
			redirectAttributes.addFlashAttribute("message", "ログアウトしました。");
			return "redirect:/user/login";
		}
	}

