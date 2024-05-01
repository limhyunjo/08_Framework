// 마이 페이지 -> 개인 정보 수정 페이지로 보내는 동작
const profileUpdateBtn= document.querySelector("#profileUpdateBtn");

profileUpdateBtn.addEventListener("click", () => {
  location.href = "myPage-profileupdate"; 
  
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

/* memberno가 북마크한 누른 게시글 조회 */
const bookListBtn = document.querySelector("#bookListBtn");


/* ----------------------------------------------------------- */

// 비동기로 내용 불러올 공간
const profilebox = document.querySelector("#profileContainer");

// 내가 값을 출력할id를 가져와서 

//  이전 내용 삭제하고 조회 및 출력하는  코드

//--------------------------------------------------------------------

/* 내가 입양한 리스트 비동기로 조회하기 */

const selectAdoptBoard=()=>{

  fetch("/myPage/selectAdoptList")

  .then(resp => resp.json())

  .then(adoptList =>{

    console.log(adoptList);

    profilebox.innerHTML="";


    
    if (adoptList == null || adoptList.length === 0) {
      // bookmarkList null이거나 빈 배열인 경우
      // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

     // 기존 요소 지우기
     const message = document.getElementById("noCommentMessage");
     if (message) {
         message.innerHTML = ""; // 내용 지우기
     } else {
         // 내용 설정
     
     message.innerHTML = "아직 등록한 입양 게시물이 없습니다";
     }
 
   


  } else {
      // bookmarkList 값이 있는 경우
      // 댓글을 화면에 표시하는 코드 추가

      const table = document.createElement("table");
      table.id="adoptkBox"
      const tbody = document.createElement("tbody");
      tbody.id="myAdoptList"
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

            
      /* 테이블 헤더 만들기 */
      const headers=["","이름","나이","종","발견장소",""];
      for(let head of headers){
        
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
       
      }
      
      thead.append(tr);

      table.append(thead);
   
    for(let adopt of adoptList){
   

      const arr = ['thumnail','adoptName','adoptAge','adoptType','adoptAddress'];
      const tr= document.createElement("tr");
      for(let key of arr){
        const td = document.createElement("td");
        
        // 제목인 경우
         if(key== 'thumnail'){
          const img= document.createElement("img");
          
          img.src = adopt[key];
          img.id="thumbnailP";

          const a= document.createElement("a");
         
          a.href="/adoption/adoptionList/" + adopt.adoptNo;
          
          a.append(img); // 이미지를 링크(a) 태그에 추가

          td.append(a);
        
         }else{
  
          td.innerHTML = adopt[key];
         }

        tr.append(td);
      }

    // 수정 버튼
        const editButton = document.createElement("button");
        editButton.innerText = "수정";
        editButton.addEventListener("click", () => {
            // 로그인한 회원이 작성한 입양게시글의 수정 페이지로 보내기
            location.href = location.pathname.replace('myPage/first', 'adoption/editAdoption') 
            + "/"+ adopt.adoptNo
            + "/update?";
            
        });
        const editCell = document.createElement("td");
        editCell.append(editButton);
        tr.append(editCell);

        // 입양완료 버튼
        const adoptDelButton = document.createElement("button");
        adoptDelButton.name="adoptDelButton"
        adoptDelButton.innerText = "입양 완료 수정";
        adoptDelButton.addEventListener("click", () => {
            // 입양 완료 버튼 클릭 시 해당 게시글의 adoptDelFl 'Y'로 바꾸기
            // 입양 완료 버튼 누르면 이미 입양 완료된 애는 이미 입양 완료 되었다고 뜨고
            // 
               
                
                if (adopt.adoptDelFl == 'N') {
                  // 확인 다이얼로그 표시
                  if (!confirm("입양 완료 하시겠습니까?")) {
                      // 취소를 선택한 경우 알림 표시 및 기본 이벤트 중지
                      alert("취소되었습니다");
                      return; // 함수 종료
                  }
          
                }else{


                  fetch("adoptDelButton")

                  .then(resp => resp.json())
                
                  .then(result =>{

                    if(result > 0){
                      alert("입양 완료 되었습니다");
                    }
                    
                    
                  });
                }


                  
                




                
        
                
           });
       
        editCell.append(adoptDelButton);
        tr.append(editCell);
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        
     }

     table.append(tbody);
     profilebox.append(table);
     



    }


  });


}

adoptionListBtn.addEventListener('click',()=>{
 
  selectAdoptBoard();


});



//-------------------------------------------------------------------
/* 내가 쓴 후기 게시글 비동기로 조회하기 */

// 함수 만들기
const selectMyReviewBoard = () =>{

 
  fetch("/myPage/selectReview")

  .then(response => response.json())

  .then(reviewList=>{
     // list를 담아오자
    
     // list 확인
     console.log(reviewList);

   
     profilebox.innerHTML =""; // 기존 내용 지우기

     const table = document.createElement("table");
     table.id='reviewbox'
     const tbody = document.createElement("tbody");
     tbody.id='reviewList'
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

      
      /* 테이블 헤더 만들기 */
      const headers=["게시글 제목", "조회수","게시글 작성일"];
      for(let head of headers){
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
      }
      
      thead.append(tr);

      table.append(thead);
   
    for(let review of reviewList){
   

      const arr = ['reviewTitle', 'readCount', 'reviewWriteDate'];
      const tr= document.createElement("tr");

      for(let key of arr){
        const td = document.createElement("td");

        // 제목인 경우
         if(key== 'reviewTitle'){
          const a= document.createElement("a");
          a.innerText = review[key]; // 제목을 a 태그 내용으로 대입
          a.href="/review/reviewList/" + review.reviewNo;
          td.append(a);
        
         }else{
  
          td.innerText = review[key];
         }

        tr.append(td);
      }
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        
     }

     table.append(tbody);
     profilebox.append(table);
     

     
   
  });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
boardListBtn.addEventListener('click',()=>{

  // 화면 비워주고
  // myPage의 section 내부 -> 여기 내부를 비우고 다른 화면을 부르는 코드를 쓴다

  // 다른 화면이 보이는 코드
    // profilecontainer가 reviewList로 바꿔어야 함
   /*  getInnerHTML(); */
   selectMyReviewBoard();



});






//------------------------------------------------------------------------------

/* ---- 내가 쓴 댓글 비동기로 조회하기 ---- */

// 함수 만들기
const selectMyCommentBoard = () =>{

 
  fetch("/myPage/selectComment")

  .then(resp => resp.json())

  .then(commentList=>{
     // list를 담아오자
    console.log(commentList);

     profilebox.innerHTML =""; // 기존 내용 지우기


     if (commentList == null || commentList.length === 0) {
      // commentList가 null이거나 빈 배열인 경우
      // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

     // 기존 요소 지우기
     const message = document.getElementById("noCommentMessage");
     if (message) {
         message.innerHTML = ""; // 내용 지우기
     } else {
         // 내용 설정
     
     message.innerHTML = "아직 작성된 댓글이 없습니다";
     }
 
   


  } else {
      // commentList에 값이 있는 경우
      // 댓글을 화면에 표시하는 코드 추가

      const table = document.createElement("table");
      table.id="commentBox"
      const tbody = document.createElement("tbody");
      tbody.id="commentList"
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

            
      /* 테이블 헤더 만들기 */
      const headers=["게시글 제목","댓글","댓글 작성일"];
      for(let head of headers){
        
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
       
      }
      
      thead.append(tr);

      table.append(thead);
   
    for(let com of commentList){
   

      const arr = ['reviewTitle', 'commentContent', 'commentWriteDate'];
      const tr = document.createElement("tr"); 

      for(let key of arr){
        const td = document.createElement("td");
        
        // 제목인 경우
         if(key== 'reviewTitle'){
          const a= document.createElement("a");
          a.innerText = com[key]; // 제목을 a 태그 내용으로 대입
          a.href="/review/reviewList/" + com.reviewNo;
          
          td.append(a);
        
         }else{
  
          td.innerText = com[key];
         }

        tr.append(td);
      }
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        
     }

     table.append(tbody);
     profilebox.append(table);
     



  }


  });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
commentListBtn.addEventListener('click',()=>{

  

  // 다른 화면이 보이는 코드
    // profilecontainer가 reviewList로 바꿔어야 함
   /*  getInnerHTML(); */
   selectMyCommentBoard();



});

//----------------------------------------------------------------------------

/* 내가 북마크한 게시물 비동기로 조회하기 */

const selectMyBookMark=()=>{

  fetch("/myPage/selectBookMark")

  .then(resp => resp.json())

  .then(bookmarkList =>{

    console.log(bookmarkList);

    profilebox.innerHTML="";


    
    if (bookmarkList == null || bookmarkList.length === 0) {
      // bookmarkList null이거나 빈 배열인 경우
      // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

     // 기존 요소 지우기
     const message = document.getElementById("noCommentMessage");
     if (message) {
         message.innerHTML = ""; // 내용 지우기
     } else {
         // 내용 설정
     
     message.innerHTML = "아직 북마크한 게시글이 없습니다";
     }
 
   


  } else {
      // bookmarkList 값이 있는 경우
      // 댓글을 화면에 표시하는 코드 추가

      const table = document.createElement("table");
      table.id="bookMarkBox"
      const tbody = document.createElement("tbody");
      tbody.id="bookMarkList"
      const thead=document.createElement("thead");
      const tr = document.createElement("tr");

            
      /* 테이블 헤더 만들기 */
      const headers=["게시글 제목","입양 상태"];
      for(let head of headers){
        
        const th = document.createElement("th");
        th.innerText = head;
        tr.append(th);
       
      }
      
      thead.append(tr);

      table.append(thead);
   
    for(let mark of bookmarkList){
   

      const arr = ['adoptTitle', 'adoptCompl'];
      const tr= document.createElement("tr");
      for(let key of arr){
        const td = document.createElement("td");
        
        // 제목인 경우
         if(key== 'adoptTitle'){
          const a= document.createElement("a");
          a.innerText = mark[key]; // 제목을 a 태그 내용으로 대입
          a.href="/adoption/adoptionList/" + mark.adoptNo;
          td.append(a);
        
         }else{
  
          td.innerHTML = mark[key];
         }

        tr.append(td);
      }
        // tbody의 자식으로 tr( 한 줄 ) 추가
        tbody.append(tr);
        
     }

     table.append(tbody);
     profilebox.append(table);
     



    }


  });


}

bookListBtn.addEventListener('click',()=>{
 
  selectMyBookMark();


});




// ---------------------------------------------------------- ----------

/* 첫 화면에서 프로필 페이지 바로 ajax로 연결 + 프로필 수정 */

/* 프로필 첫 화면 만들기 */

// 함수 만들기
const selectMyProfile = () =>{

 
  fetch("/myPage/selectProfile")

  .then(response => response.json())

  .then(profileList=>{
     // list를 담아오자
    
     // list 확인
     console.log(profileList);

   
     profilebox.innerHTML =""; // 기존 내용 지우기

    let formData = new FormData();


    formData.append('statusCheck', '-1');
    formData.append('imageInput', 'blob', 'fileName');
       


  });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
profileListBtn.addEventListener('click',()=>{

  // 화면 비워주고
  // myPage의 section 내부 -> 여기 내부를 비우고 다른 화면을 부르는 코드를 쓴다

  // 다른 화면이 보이는 코드
    
   /*  getInnerHTML(); */
   selectMyProfile();



});


//---------------------------------------------------------------------------------
/* 마이페이지 프로필  */ 


/* 회원 탈퇴 */

const memberDel = document.querySelector("#memberDel");

if(memberDel !=null){

  memberDel.addEventListener("submit", e=>{

    if( !confirm("정말 탈퇴하시겠습니까?")){ 

      alert("취소되었습니다");
      e.preventDefault();
      return;
     }


  })
}





