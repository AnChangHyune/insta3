<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/head.jspf"%>

<script>
	let MemberFindLoginPw__submitFormDone = false;
	function MemberFindLoginPw__submitForm(form) {

		if (MemberFindLoginPw__submitFormDone) {
			return;
		}
		
		form.loginId.value = form.loginId.loginId.trim();

		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요.');
			form.loginId.focus();

			return;
		}

		form.name.value = form.loginId.name.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();

			return;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();

			return;
		}
	

		form.submit();
		MemberFindLoginPw__submitFormDone = true;
	}
</script>
<div class="section section-find-login-pw px-2">
	<div class="container mx-auto">
		<div class="card bordered shadow-lg item-bt-1-not-last-child">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer"> <i
					class="fas fa-chevron-left"></i>
				</a> <span>비밀번호 찾기</span>

			</div>
			<div class="px-4 py-8">
				<form action="doFindLoginPw" class="grid form-type-1"
					onsubmit="MemberFindLoginPw__submitForm(this); return false;"
					method="POST">
					<input type="hidden" name="redirectUri" value="${param.afterLoginUri}" />
					<div class="form-control">
						<label class="label cursor-pointer"> 아이디 </label>
						<input type="text" name="loginId" placeholder="아이디" class="input input-bordered w-full" maxlength="20"/>
						<label class="label cursor-pointer"> 이름 </label>
						<input type="text" name="name" placeholder="이름" class="input input-bordered w-full" maxlength="20"/>
						
						<label class="cursor-pointer label"> 이메일 </label> 
						<input type="email" class="input input-bordered w-full" name="email"
							placeholder="이메일" maxlength="20" />
							
                 

						<div class="mt-4 btn-wrap gap-1">
							<button type="submit" class="btn btn-sm btn-primary mb-1">
								<span><i class="fas fa-user-plus"></i></span> &nbsp; <span>비밀번호 찾기</span>
							</button>

							<a href="findLoginId" class="btn btn-sm mb-1"
								title="자세히 보기"> <span><i class="fas fa-sign-in-alt"></i></span>
								&nbsp; <span>아이디 찾기</span>
							</a>
							
							<a href="login" class="btn btn-sm mb-1"
								title="자세히 보기"> <span><i class="fas fa-sign-in-alt"></i></span>
								&nbsp; <span>로그인</span>
							</a>
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>

<%@ include file="../common/foot.jspf"%>