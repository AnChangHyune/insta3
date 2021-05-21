<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='fas fa-home'></i></span> <span>HOME</span>" />
<script>
(function () {
    const fullscroll = document.querySelector(".fullscroll-container");
    const sections = document.querySelectorAll(
        ".fullscroll-container .onepage"
    );
    const footer = document.querySelector(".section-footer");
    const fullNav = document.querySelector("#fullscroll-nav ul");
    const marker = document.querySelector(".marker");

    const footerHeight = footer.clientHeight;
    let index = 0;
    let currentPageTop;
    let movable = false;
    let navBtn;
    let markerDistans;

    function setPage() {
        if (index == sections.length) {
            currentPageTop = (index - 1) * window.innerHeight + footerHeight;
        } else {
            currentPageTop = index * window.innerHeight;
            marker.style.top = markerDistans * index + "px";
        }
        fullscroll.style.top = -currentPageTop + "px";
        fullscroll.id = "fullscroll-" + index;
    }
  
  function checkDirection() {
        if (window.pageYOffset > 1 && index < sections.length) {
            movable = false;
            index++;
        } else if (window.pageYOffset < 1 && index > 0) {
            movable = false;
            index--;
        }
    }

    function setMarker() {
        for (let i = 0; i < sections.length; i++) {
            const navBtn = document.createElement("li");
            fullNav.append(navBtn);
        }
        navBtn = document.querySelectorAll("#fullscroll-nav li");
        markerDistans =
            (fullNav.clientHeight + navBtn[0].offsetHeight) / sections.length;
        for (let i = 0; i < navBtn.length; i++) {
            navBtn[i].addEventListener("click", function () {
                movable = false;
                index = i;
                fullscroll.id = "fullscroll-" + index;
                setPage();
            });
        }
    }

    window.addEventListener("scroll", function () {
        if (movable == true) {
            checkDirection();
            setPage();
            changeMarker();
        } else return;
    });

    fullscroll.addEventListener("transitionend", function () {
      window.scrollTo(0, 1);
        movable = true;
    });

    window.addEventListener("resize", function () {
        setPage();
    });

    window.addEventListener("load", function () {
        window.scrollTo(0, 1);
        setPage();
        setMarker();
        movable = true;
    });
})();

</script>
<%@ include file="../common/head.jspf"%>
<div id="fullscroll-0" class="fullscroll-container">
	<section class="onepage section-0">
		<div class="newArticle">
		<div>
			<div class="mb-3 flex items-center ">
				<div>
					<h1>최신글</h1>
				</div>
				<div>
					<a href="/mpaUsr/article/list?boardId=1" style="float: right;"><i class="fas fa-plus"></i></a>
				</div>
			</div>
			<div class="newArticle-list">
				<hr />
				<c:forEach items="${articles}" var="article">
					<div class="mt-8 mb-4">
							<a href="../article/detail?id=${article.id}" class="title-list">${article.title}</a>
							<a href="../article/detail?id=${article.id}" class="regDate-list">${article.regDate}</a>
					</div>
						
				</c:forEach>
			</div>
		</div>
			<div>
				<div class="mb-3 flex items-center ">
				<div>
					<h1>공지사항</h1>
				</div>
				<div>
					<a href="/mpaUsr/article/list?boardId=1" style="float: right;"><i class="fas fa-plus"></i></a>
				</div>
			</div>
				<div class="newArticle-list">
					<hr />
					<c:forEach items="${articles}" var="article">
						<div class="mt-8 mb-4">
								<a href="../article/detail?id=${article.id}" class="title-list">${article.title}</a>
								<a href="../article/detail?id=${article.id}" class="regDate-list">${article.regDate}</a>
						</div>
							
					</c:forEach>
				</div>
			</div>
		</div>
		
	</section>
	<section class="onepage section-1">full scroll-2</section>
	<section class="onepage section-2">full scroll-3</section>
	<section class="onepage section-3">full scroll-4</section>
	<footer class="section-footer">this is footer</footer>
</div>
<nav id="fullscroll-nav">
	<ul>
		<div class="marker"></div>
	</ul>
</nav>
<%@ include file="../common/foot.jspf"%>