/* carousel */
let slideIndex = 0;
showSlides();

function showSlides() {

   let i;
   let slides = document.getElementsByClassName("mySlides");
   let dots = document.getElementsByClassName("dot");
   
   for(i=0; i < slides.length; i++)   slides[i].style.display = "none"; // 이미지 전체 안보이게 설정

   slideIndex++;

   if(slideIndex > slides.length) slideIndex = 1; // 마지막 이미지일때 첫번째 이미지로 이동

   for(i=0; i < dots.length; i++) dots[i].className = dots[i].className.replace(" active", " ");

   let index = slideIndex - 1;
   slides[index].style.display = "block";
   dots[slideIndex-1].className += " active";
   setTimeout(showSlides, 5000); // 슬라이드 이미지 변경 시간 지정(2초)
};


/* 빠른 로그인 */
// 버튼 얻어오기
const quickLoginBtn = document.querySelectorAll(".quickLogin");

quickLoginBtn.forEach((item, index) =>{


   // list로 얻어온 quickLoginBtn 요소 하나씩 꺼내서 이벤트 추가하기
   item.addEventListener("click", e=>{

      const email = item.innerText; // 버튼에 작성된 이메일 얻어오기

      location.href = "/member/quickLogin?memberEmail=" + email;
   })
})
