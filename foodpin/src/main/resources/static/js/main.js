// 메인 페이지 -> 가게 검색 페이지로 보내는 동작
 const mainCategoryBtns = document.querySelectorAll(".main-category-button button");

    mainCategoryBtns.forEach(function(button) {
        button.addEventListener("click", function() {
            window.location.href = "store/storeSearch/" + catgoryCode;

        });
    });

/* 빠른 로그인 */
// 버튼 얻어오기
const quickLoginBtn = document.querySelectorAll(".main-quick-login");

quickLoginBtn.forEach((item) =>{


   // list로 얻어온 quickLoginBtn 요소 하나씩 꺼내서 이벤트 추가하기
   item.addEventListener("click", e=>{

      const id = item.innerText; // 버튼에 작성된 이메일 얻어오기

      location.href = "/member/quickLogin?memberId=" + id;
   })

   
})

const chatBtn = document.querySelector("#chat");

if(chatBtn !=null){


chatBtn.addEventListener("click", ()=>{
    
    if(loginMemberCode==3){
        alert("관리자는 사용할 수 없는 기능입니다.")
        return;
    }
    location.href = "/chatting/askChat";


})
}


const searchStoreList = document.querySelector("#searchStoreList");


if(searchStoreList != null){

    searchStoreList.addEventListener("submit", e=>{
        const mainSearch =document.querySelector("#mainSearch");
        
       

    })
}

/* SWIPER */
if(document.querySelector(".swiper-container") != null) {

    /* banner swipe */
    var swiper = new Swiper(".swiper-container", {
        slidesPerView: 1,
        direction: 'horizontal',
        autoplay: {
            delay: 3000,
        },
        loop: true,
        spaceBetween: 30,
        centeredSlides: true,
    });
}
