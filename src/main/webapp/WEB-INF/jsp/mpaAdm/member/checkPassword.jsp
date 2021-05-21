<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
	let MemberCheckPassword__submitFormDone = false;
	function MemberCheckPassword__submitForm(form) {

		if (MemberCheckPassword__submitFormDone) {
			return;
		}


		form.loginPwInput.value = form.loginPwInput.value.trim();

		if (form.loginPwInput.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPwInput.focus();

			return;
		}
		
		form.loginPw.value = sha256(form.loginPwInput.value);
		form.loginPwInput.value = ''; 

		form.submit();
		MemberCheckPassword__submitFormDone = true;
	}
</script>
<div class="section section-check-password">
	<div class="container mx-auto">
		<div class="card bordered shadow-lg item-bt-1-not-last-child">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer"> <i
					class="fas fa-chevron-left"></i>
				</a> <span>비밀번호 확인</span>

			</div>
			<div class="px-4 py-8">
				<form action="doCheckPassword" class="grid form-type-1"
					onsubmit="MemberCheckPassword__submitForm(this); return false;"
					method="POST">
					<input type="hidden" name="redirectUri" value="${param.afterUri}" />
					<input type="hidden" name="loginPw" />
					<div class="form-control">
						<label class="cursor-pointer label"> 비밀번호 </label> 
						<input type="password" class="input input-bordered w-full"
							name="loginPwInput" placeholder="비밀번호" maxlength="30" />



						<div class="mt-4 btn-wrap gap-1">
							<button type="submit" class="btn btn-sm btn-primary mb-1">
								<span><i class="fas fa-sign-in-alt"></i></span> &nbsp; <span>확인</span>
							</button>

							<a href="/mpaAdm" class="btn btn-sm mb-1" title="자세히 보기"> <span><i
									class="fas fa-user-plus"></i></span> &nbsp; <span>홈</span>
							</a> 
							
							<a href="../member/mypage" class="btn btn-sm btn-sm mb-1">
	                  			   <span><i class="fas fa-home"></i></span>
	                  			   &nbsp;
	                   			   <span>마이페이지</span>
                			</a>
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>

<%@ include file="../common/foot.jspf"%>