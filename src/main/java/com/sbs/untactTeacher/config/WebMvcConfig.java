package com.sbs.untactTeacher.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sbs.untactTeacher.interceptor.BeforeActionInterceptor;
import com.sbs.untactTeacher.interceptor.NeedToLoginInterceptor;
import com.sbs.untactTeacher.interceptor.NeedToLogoutInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	@Autowired
	NeedToLoginInterceptor needToLoginInterceptor;

	@Autowired
	NeedToLogoutInterceptor needToLogoutInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor)
				.addPathPatterns("/**").excludePathPatterns("/resource/**")
				.excludePathPatterns("/erroe");

		registry.addInterceptor(needToLoginInterceptor)
				 .addPathPatterns("/mpaUsr/article/write")
		         .addPathPatterns("/mpaUsr/article/doWrite")
		         .addPathPatterns("/mpaUsr/article/doDelete")
		         .addPathPatterns("/mpaUsr/article/modify")
		         .addPathPatterns("/mpaUsr/article/doModify")
		         .addPathPatterns("/mpaUsr/reply/doWrite")
		         .addPathPatterns("/mpaUsr/reply/doDelete")
		         .addPathPatterns("/mpaUsr/reply/doDeleteAjax")
		         .addPathPatterns("/mpaUsr/reply/modify")
		         .addPathPatterns("/mpaUsr/reply/doModify")
		         .addPathPatterns("/mpaUsr/member/modify")
		         .addPathPatterns("/mpaUsr/member/doModify")
		         .addPathPatterns("/mpaUsr/member/checkPassword")
		         .addPathPatterns("/mpaUsr/member/doCheckPassword");

		registry.addInterceptor(needToLogoutInterceptor)
				.addPathPatterns("/mpaUsr/member/findLoginId")
				.addPathPatterns("/mpaUsr/member/doFindLoginId")
				.addPathPatterns("/mpaUsr/member/findLoginPw")
				.addPathPatterns("/mpaUsr/member/doFindLoginPw")
				.addPathPatterns("/mpaUsr/member/login")
				.addPathPatterns("/mpaUsr/member/doLogin")
				.addPathPatterns("/mpaUsr/member/join")
				.addPathPatterns("/mpaUsr/member/doJoin")
				.addPathPatterns("/mpaUsr/member/findLoginId")
				.addPathPatterns("/mpaUsr/member/doFindLoginId")
				.addPathPatterns("/mpaUsr/member/findLoginPw")
				.addPathPatterns("/mpaUsr/member/doFindLoginPw");
	}
}
