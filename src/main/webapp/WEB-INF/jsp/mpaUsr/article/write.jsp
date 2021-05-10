<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/head.jspf"%>

<script>
	let ArticleWrite__submitFormDone = false;
	function ArticleWrite__submitForm(form) {

		if (ArticleWrite__submitFormDone) {
			return;
		}

		form.title.value = form.title.value.trim();

		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();

			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('내용을 입력해주세요.');
			form.body.focus();

			return;
		}

		form.submit();
		ArticleWrite__submitFormDone = true;
	}
</script>
<div class="section section-article-write">
	<div class="container mx-auto">
		<div class="card bordered shadow-lg item-bt-1-not-last-child">
			<div class="card-title">
				<a href="javascript:history.back();" class="cursor-pointer"> <i
					class="fas fa-chevron-left"></i>
				</a> <span>게시물 작성</span>

			</div>
			<div class="px-4 py-8">
				<form action="doWrite" class="grid form-type-1"
					onsubmit="ArticleWrite__submitForm(this); return false;"
					method="POST">
					<input type="hidden" name="boardId" value="${board.id}" />

					<div class="form-control">
					
						<label class="cursor-pointer label"> 작성자 </label>
						<span class="text-light text-sm text-gray-400 ml-2">${rq.loginedMember.name}</span>
						<label class="cursor-pointer label"> 제목 </label> <input
							type="text" class="input input-bordered w-full" name="title"
							placeholder="제목" maxlength="50" /> <label
							class="cursor-pointer label"> 내용 </label>
						<textarea class="textarea textarea-bordered w-full h-40"
							name="body" placeholder="내용" maxlength="2000"></textarea>

						<div class="mt-4 btn-wrap gap-1">
							<button type="submit" class="btn btn-sm btn-primary mb-1">
								<span><i class="fas fa-save"></i></span> &nbsp; <span>작성</span>
							</button>

							<a href="list?boardId=${board.id}" class="btn btn-sm mb-1"
								title="자세히 보기"> <span><i class="fas fa-list"></i></span>
								&nbsp; <span>리스트</span>
							</a>
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>

<%@ include file="../common/foot.jspf"%>