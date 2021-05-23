function isTouchDevice() {
	return (('ontouchstart' in window) ||
		(navigator.maxTouchPoints > 0) ||
		(navigator.msMaxTouchPoints > 0));
}

const $html = document.querySelector('html');
$html.classList.add(isTouchDevice() ? 'touch-posible' : 'touch-imposible');

(function() {
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
			navBtn[i].addEventListener("click", function() {
				movable = false;
				index = i;
				fullscroll.id = "fullscroll-" + index;
				setPage();
			});
		}
	}

	window.addEventListener("scroll", function() {
		if (movable == true) {
			checkDirection();
			setPage();
			changeMarker();
		} else return;
	});

	fullscroll.addEventListener("transitionend", function() {
		window.scrollTo(0, 1);
		movable = true;
	});

	window.addEventListener("resize", function() {
		setPage();
	});

	window.addEventListener("load", function() {
		window.scrollTo(0, 1);
		setPage();
		setMarker();
		movable = true;
	});
})();

