// 마이 페이지 -> 개인 정보 수정 페이지로 보내는 동작
const profileUpdateBtn= document.querySelector("#profileUpdateBtn");

profileUpdateBtn.addEventListener("click", () => {
  location.href = "/myPage/myPage-profileupdate"; 
  
});

//------------------------------------------------------------------

/* 화면 비동기로 바꾸는 버튼 얻어오기 */

/* 프로필 버튼 */
// -- 클릭하면 이전 내용을 지우고 수정/저장된 자료를 보여줌

// 수정/저장된 회원 정보
const profileListBtn = document.querySelector("#profileListBtn");

/* session에 loginMember로 있는 memberNo의 입양 리스트 최신 순서로 조회함 */
const adoptionListBtn = document.querySelector("#adoptionListBtn");

/* session에 loginMember로 있는 memberNo의 게시글 전부 조회 */
const boardListBtn = document.querySelector("#boardListBtn");

/* -- memberNo의 댓글 모음  */
const commentListBtn = document.querySelector("#commentListBtn");

/* memberno가 좋아요 누른 게시글 조회 */
const likeListBtn = document.querySelector("#likeListBtn");





// 내가 값을 출력할id를 가져와서 

//  이전 내용 삭제하고 조회 및 출력하는  코드

//----------------------------
/* 내가 쓴 게시글 비동기로 조회하기 */

// 함수 만들기
const selectMyWriteBoard = () =>{

 
  fetch("/myPage/selectReview")

  .then(response => response.json())

  .then(reviewList=>{
     // list를 담아오자


     
     console.log(reviewList);


     const profilebox = document.querySelector("#profileContainer");


     profilebox.innerHTML =""; // 기존 내용 지우기

     const table = document.createElement("table");
     const tbody = document.createElement("tbody");
      tbody.id='reviewList'

      const thead=document.createElement("thead");


      const tr = document.createElement("tr");

      
      /* 테이블 헤더 만들기 */
      
      
      const headers=["게시글 제목", "조회수","댓글","게시글"];
      for(let head of headers){
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
      }
      
      thead.append(tr);

      table.append(thead);

      
    for(let review of reviewList){

     
      
    

    

      const arr = ['reviewTitle', 'readCount', 'reviewContent', 'reviewWriteDate'];
      

      for(let key of arr){
        const td = document.createElement("td");

        // 제목인 경우
         if(key== 'reviewTitle'){
          const a= document.createElement("a");
          a.innerText = review[key]; // 제목을 a 태그 내용으로 대입
          a.href="/review/reviewDetail?reviewNo=" + review.reviewNo;
          td.append(a);
          
 
         }

        td.innerHtml = review[key];
        tr.append(td);
      }
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        table.append(tbody);
     }

     

     profilebox.append(table);
  });
}


/* function getInnerHTML() {
  const element = document.getElementById('reviewTable');
 
}  */

// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
boardListBtn.addEventListener('click',()=>{

  

  // 화면 비워주고
  // myPage의 section 내부 -> 여기 내부를 비우고 다른 화면을 부르는 코드를 쓴다



  // 다른 화면이 보이는 코드
    // profilecontainer가 reviewList로 바꿔어야 함
   /*  getInnerHTML(); */
  selectMyWriteBoard();



});