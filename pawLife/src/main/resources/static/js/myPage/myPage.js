// 마이 페이지 -> 개인 정보 수정 페이지로 보내는 동작
const profileUpdateBtn= document.querySelector("#profileUpdateBtn");

profileUpdateBtn.addEventListener("click", () => {
  location.href = "/myPage/myPage-profileupdate"; 
  
});



// --------------------------------------------------------------------------------------

// 페이지 비동기 요청 버튼
const profileListBtn = document.querySelector("#profileListBtn");
const adoptionListBtn = document.querySelector("#adoptionListBtn");
const boardListBtn = document.querySelector("#boardListBtn");
const commentListBtn = document.querySelector("#commentListBtn");
const likeListBtn = document.querySelector("#likeListBtn");



 



//-------------------------------------------------------------------------------------

/* 내가 쓴 게시글 비동기 조회 + 상세 조회 시 해당 게시글 페이지로 이동 */

// 다른 HTML 파일을 가져와서 삽입하는 함수


// 다른 HTML 파일을 가져와서 삽입하는 함수 정의
function loadHTMLFile(url, container) {
  fetch(url)
  .then(response => {
      if (!response.ok) {
         
      }
      return response.text();
  })
  .then(html => {
      // 가져온 HTML을 삽입할 컨테이너 요소 선택
      container.innerHTML = html;
  })
 
}

// 페이지 비동기 요청 버튼에 이벤트 리스너 추가
commentListBtn.addEventListener('click', () => {
  // mycommentContainer를 가져와서 함수에 전달
  const mycommentContainer = document.querySelector("#mycomment-container");
  // 예시: 다른 HTML 파일을 가져와서 mycommentContainer에 삽입
  loadHTMLFile('/myPage/myPage-myComment', mycommentContainer);
});