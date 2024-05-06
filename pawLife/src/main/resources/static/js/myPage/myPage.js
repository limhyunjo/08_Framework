// 마이 페이지 -> 개인 정보 수정 페이지로 보내는 동작
const profileUpdateBtn = document.querySelector("#profileUpdateBtn");

profileUpdateBtn.addEventListener("click", () => {
  location.href = "myPage-profileupdate";

});

//------------------------------------------------------------------



//-------------------------------------------------------------------

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




//--------------------------------------------------------------------

const newEl = (tagName, attr) => {

  const tag = document.createElement(tagName);

  for(let key in attr){
    tag.setAttribute(key, attr[key]);
  }

  return tag;
}


/* 내가 입양한 리스트 비동기로 조회하기 */

const selectAdoptBoard = (cp) => {

  fetch("/myPage/selectAdoptList?cp=" + cp)

    .then(resp => resp.json())

    .then(map => {
      const adoptList = map.adoptList;
      const pg = map.pagination;

      console.log(adoptList);

      profilebox.innerHTML = "";



      if (adoptList == null || adoptList.length === 0) {
        // bookmarkList null이거나 빈 배열인 경우
        // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가




        // 없다면 새로운 메시지 요소를 생성합니다.
        let message = document.createElement("div");
        message.id = "noCommentMessage";
        message.innerText = "아직 작성된 입양 게시물이 없습니다";

        // 그리고 해당 메시지를 보여줄 컨테이너에 추가합니다. (profilebox라고 가정합니다)
        const profilebox = document.querySelector("#profileContainer");
        profilebox.append(message);



      } else {
        // bookmarkList 값이 있는 경우
        // 댓글을 화면에 표시하는 코드 추가

        const table = document.createElement("table");
        table.id = "adoptkBox"
        const tbody = document.createElement("tbody");
        tbody.id = "myAdoptList"
        const thead = document.createElement("thead");
        const tr = document.createElement("tr");


        /* 테이블 헤더 만들기 */
        const headers = ["", "이름", "나이", "종", "발견장소", "입양완료여부"];
        for (let head of headers) {

          const th = document.createElement("th");
          th.innerText = head;
          tr.append(th);

        }

        thead.append(tr);

        table.append(thead);

        for (let adopt of adoptList) {


          const arr = ['thumnail', 'adoptName', 'adoptAge', 'adoptType', 'adoptAddress'];
          const tr = document.createElement("tr");
          for (let key of arr) {
            const td = document.createElement("td");

            // 제목인 경우
            if (key == 'thumnail') {
              const img = document.createElement("img");
              img.classList.add("adopt-img"); // 이미지에 클래스 추가

              img.src = adopt[key];
              img.id = "thumbnailP";

              const a = document.createElement("a");

              a.href = "/adoption/adoptionList/" + adopt.adoptNo;

              a.append(img); // 이미지를 링크(a) 태그에 추가

              td.append(a);

            } else {

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
              + "/" + adopt.adoptNo
              + "/update?";

          });
          const editCell = document.createElement("td");
          editCell.append(editButton);
          tr.append(editCell);

          // 입양완료 버튼
          const adoptComFlButton = document.createElement("button");
          adoptComFlButton.name = "adoptComFlButton"
          adoptComFlButton.innerHTML = adopt.adoptCompl;

          editCell.append(adoptComFlButton);

          adoptComFlButton.addEventListener("click", () => {
            // 변경할 입양 게시글 번호, 입양 완료 여부 (Y<->N)
            let adoptCompl = adoptComFlButton.innerHTML === 'Y' ? 'N' : 'Y';
            
            

           if (adoptCompl === 'Y') {
            
              if (!confirm("입양 완료 하시겠습니까?")) {
                
                alert("취소되었습니다");
                return; 
                
              }
            } 
            if (adoptCompl === 'N') {
            
            
            
              if (!confirm("입양 완료를 취소 하시겠습니까?")) {
                
                alert("취소되었습니다");
                return; 
                
              }
            
            } 
            
            const obj = { "adoptNo": adopt.adoptNo, "adoptCompl": adoptCompl };

            fetch("adoptDelComplete", {
              method: "PUT",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify(obj) // obj를 JSON으로 변경
            })
              .then(resp => resp.text())
              .then(result => {
                if (result > 0) {
                  alert("입양 상태가 변경 되었습니다");
                  adoptComFlButton.innerText = adoptCompl;
                } else {
                  alert("입양 완료 변경이 실패하였습니다")
                }
              });
          });


          tbody.append(tr);

        }

        table.append(tbody);
        profilebox.append(table);
        


        // -------------------------------------------------------------------
        
      
        /* pagination */
        const div = newEl("div", {"class" : "pagination-area"});
        div.class="pageBox";
        const ul = newEl("ul", {"class" : "pagination"});

        div.append(ul);
        
        /* 앞쪽 화살표 */
        const startList = newEl("li") 
        const startSpan = newEl("span");
        startSpan.id="stP";
        startSpan.innerHTML = '&lt;&lt;'
        
        startList.append(startSpan);
        ul.append(startList);
       
     
        startSpan.addEventListener("click", () => selectAdoptBoard(pg.startPage) )
        
        
        /* 숫자 부분 */
        for(let i=pg.startPage ; i <= pg.endPage ; i ++){
          const li = newEl("li")
          li.class="current";
          const span = newEl("span");
          span.innerText = i;
          li.append(span);
          ul.append(li);

          // 현재 페이지인 경우
          if(i == pg.currentPage) span.classList.add("current");
          else{
            span.addEventListener("click", () => selectAdoptBoard(i) )
          }

        }

       /* 뒤쪽 화살표 */
       
       const endList = newEl("li") 
        const endSpan = newEl("span");
        
        endSpan.innerHTML = '&gt;&gt;';
        
        endList.append(endSpan);
        ul.append(endList);
        endSpan.addEventListener("click", () => selectAdoptBoard(pg.endPage) )
        
        

        profilebox.append(div);
      }

    });

}



adoptionListBtn.addEventListener('click', () => {
  selectAdoptBoard(1);

});




//-------------------------------------------------------------------
/* 내가 쓴 후기 게시글 비동기로 조회하기 */

// 함수 만들기
const selectMyReviewBoard = (cp) => {


  fetch("/myPage/selectReview?cp=" + cp)

    .then(response => response.json())

    .then( map => {
      
      const reviewList = map.reviewList;
      const pg = map.pagination;

      console.log(reviewList);


      profilebox.innerHTML = ""; // 기존 내용 지우기


      if (reviewList == null || reviewList.length === 0) {
        // commentList가 null이거나 빈 배열인 경우
        // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

        // 기존 요소 지우기
        // 없다면 새로운 메시지 요소를 생성합니다.
        let message = document.createElement("div");
        message.id = "noCommentMessage";
        message.innerText = "아직 작성된 후기 게시글이 없습니다";

        // 그리고 해당 메시지를 보여줄 컨테이너에 추가합니다. (profilebox라고 가정합니다)
        const profilebox = document.querySelector("#profileContainer");
        profilebox.append(message);





      } else {

        const table = document.createElement("table");
        table.id = 'reviewbox'
        const tbody = document.createElement("tbody");
        tbody.id = 'reviewList'
        const thead = document.createElement("thead");
        const tr = document.createElement("tr");


        /* 테이블 헤더 만들기 */
        const headers = ["게시글 제목", "조회수", "게시글 작성일"];
        for (let head of headers) {
          const th = document.createElement("th");
          th.innerText = head;
          tr.append(th);
        }

        thead.append(tr);

        table.append(thead);


        for (let review of reviewList) {


          const arr = ['reviewTitle', 'readCount', 'reviewWriteDate'];
          const tr = document.createElement("tr");

          for (let key of arr) {
            const td = document.createElement("td");

            // 제목인 경우
            if (key == 'reviewTitle') {
              const a = document.createElement("a");
              a.innerText = review[key]; // 제목을 a 태그 내용으로 대입
              a.href = "/review/reviewList/" + review.reviewNo;
              td.append(a);

            } else {

              td.innerText = review[key];
            }

            tr.append(td);
          }
          // tbody의 자식으로 tr( 한 줄 ) 추가
          tbody.append(tr);

        }

        table.append(tbody);

        profilebox.append(table);
        
         //---------------------- 후기 게시글 페이지네이션-----------------------------
         /* pagination */
         const div = newEl("div", {"class" : "pagination-area"});
         div.class="pageBox";
         const ul = newEl("ul", {"class" : "pagination"});
 
         div.append(ul);
         
         /* 앞쪽 화살표 */
         const startList = newEl("li") 
         const startSpan = newEl("span");
         startSpan.id="stP";
         startSpan.innerHTML = '&lt;&lt;'
         
         startList.append(startSpan);
         ul.append(startList);
        
      
         startSpan.addEventListener("click", () => selectMyReviewBoard(pg.startPage) )
         
         
         /* 숫자 부분 */
         for(let i=pg.startPage ; i <= pg.endPage ; i ++){
           const li = newEl("li")
           li.class="current";
           const span = newEl("span");
           span.innerText = i;
           li.append(span);
           ul.append(li);
 
           // 현재 페이지인 경우
           if(i == pg.currentPage) span.classList.add("current");
           else{
             span.addEventListener("click", () => selectMyReviewBoard(i) )
           }
 
         }
 
        /* 뒤쪽 화살표 */
        
        const endList = newEl("li") 
         const endSpan = newEl("span");
         
         endSpan.innerHTML = '&gt;&gt;';
         
         endList.append(endSpan);
         ul.append(endList);
         endSpan.addEventListener("click", () => selectMyReviewBoard(pg.endPage) )
         
         
         profilebox.append(div);

      }

    });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
boardListBtn.addEventListener('click', () => {

  // 화면 비워주고
  // myPage의 section 내부 -> 여기 내부를 비우고 다른 화면을 부르는 코드를 쓴다

  // 다른 화면이 보이는 코드
  // profilecontainer가 reviewList로 바꿔어야 함
  /*  getInnerHTML(); */
  selectMyReviewBoard(1);
 


});






//------------------------------------------------------------------------------

/* ---- 내가 쓴 댓글 비동기로 조회하기 ---- */

// 함수 만들기
const selectMyCommentBoard = (cp) => {


  fetch("/myPage/selectComment?cp=" + cp)

    .then(resp => resp.json())

    .then(map => {

      const commentList = map.commentList;
      const pg = map.pagination;
      // list를 담아오자
      console.log(commentList);

      profilebox.innerHTML = ""; // 기존 내용 지우기


      if (commentList == null || commentList.length === 0) {
        // commentList가 null이거나 빈 배열인 경우
        // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

        // 기존 요소 지우기
        // 없다면 새로운 메시지 요소를 생성합니다.
        let message = document.createElement("div");
        message.id = "noCommentMessage";
        message.innerText = "아직 작성된 댓글이 없습니다";

        // 그리고 해당 메시지를 보여줄 컨테이너에 추가합니다. (profilebox라고 가정합니다)
        const profilebox = document.querySelector("#profileContainer");
        profilebox.append(message);





      } else {
        // commentList에 값이 있는 경우
        // 댓글을 화면에 표시하는 코드 추가

        const table = document.createElement("table");
        table.id = "commentBox"
        const tbody = document.createElement("tbody");
        tbody.id = "commentList"
        const thead = document.createElement("thead");
        const tr = document.createElement("tr");


        /* 테이블 헤더 만들기 */
        const headers = ["게시글 제목", "댓글", "댓글 작성일"];
        for (let head of headers) {

          const th = document.createElement("th");
          th.innerText = head;
          tr.append(th);

        }

        thead.append(tr);

        table.append(thead);

        for (let com of commentList) {


          const arr = ['reviewTitle', 'commentContent', 'commentWriteDate'];
          const tr = document.createElement("tr");

          for (let key of arr) {
            const td = document.createElement("td");

            // 제목인 경우
            if (key == 'reviewTitle') {
              const a = document.createElement("a");
              a.innerText = com[key]; // 제목을 a 태그 내용으로 대입
              a.href = "/review/reviewList/" + com.reviewNo;

              td.append(a);

            } else {

              td.innerText = com[key];
            }

            tr.append(td);
          }
          // tbody의 자식으로 tr( 한 줄 ) 추가
          tbody.append(tr);

        }

        table.append(tbody);
        profilebox.append(table);


         /* pagination */
         const div = newEl("div", {"class" : "pagination-area"});
         div.class="pageBox";
         const ul = newEl("ul", {"class" : "pagination"});
 
         div.append(ul);
         
         /* 앞쪽 화살표 */
         const startList = newEl("li") 
         const startSpan = newEl("span");
         startSpan.id="stP";
         startSpan.innerHTML = '&lt;&lt;'
         
         startList.append(startSpan);
         ul.append(startList);
        
      
         startSpan.addEventListener("click", () => selectMyCommentBoard(pg.startPage) )
         
         
         /* 숫자 부분 */
         for(let i=pg.startPage ; i <= pg.endPage ; i ++){
           const li = newEl("li")
           li.class="current";
           const span = newEl("span");
           span.innerText = i;
           li.append(span);
           ul.append(li);
 
           // 현재 페이지인 경우
           if(i == pg.currentPage) span.classList.add("current");
           else{
             span.addEventListener("click", () => selectMyCommentBoard(i) )
           }
 
         }
 
        /* 뒤쪽 화살표 */
        
        const endList = newEl("li") 
         const endSpan = newEl("span");
         
         endSpan.innerHTML = '&gt;&gt;';
         
         endList.append(endSpan);
         ul.append(endList);
         endSpan.addEventListener("click", () => selectMyCommentBoard(pg.endPage) )
      

         profilebox.append(div);

      }


    });
}


// button 클릭시 작동하게 하기
// 버튼 클릭시 profileContainer 내부 비우고 위에서 만든 전체 조회 함수 호출
commentListBtn.addEventListener('click', () => {



  // 다른 화면이 보이는 코드
  // profilecontainer가 reviewList로 바꿔어야 함
  /*  getInnerHTML(); */
  selectMyCommentBoard(1);



});

//----------------------------------------------------------------------------

/* 내가 북마크한 게시물 비동기로 조회하기 */

const selectMyBookMark = (cp) => {

  fetch("/myPage/selectBookMark?cp=" + cp)

    .then(resp => resp.json())


    .then(map => {

      
      const bookmarkList= map.bookmarkList;
      const pg = map.pagination;

      console.log(bookmarkList);

      profilebox.innerHTML = "";



      if (bookmarkList == null || bookmarkList.length === 0) {
        // bookmarkList null이거나 빈 배열인 경우
        // HTML 화면에 내용이 없다는 메시지를 표시하는 코드 추가

        // 기존 요소 지우기
        // 없다면 새로운 메시지 요소를 생성합니다.
        let message = document.createElement("div");
        message.id = "noCommentMessage";
        message.innerText = "아직 북마크한 게시물이 없습니다";

        // 그리고 해당 메시지를 보여줄 컨테이너에 추가합니다. (profilebox라고 가정합니다)
        const profilebox = document.querySelector("#profileContainer");
        profilebox.append(message);





      } else {
        // bookmarkList 값이 있는 경우
        // 댓글을 화면에 표시하는 코드 추가

        const table = document.createElement("table");
        table.id = "bookMarkBox"
        const tbody = document.createElement("tbody");
        tbody.id = "bookMarkList"
        const thead = document.createElement("thead");
        const tr = document.createElement("tr");


        /* 테이블 헤더 만들기 */
        const headers = ["게시글 제목", "입양 상태"];
        for (let head of headers) {

          const th = document.createElement("th");
          th.innerText = head;
          tr.append(th);

        }

        thead.append(tr);

        table.append(thead);

        for (let mark of bookmarkList) {


          const arr = ['adoptTitle', 'adoptCompl'];
          const tr = document.createElement("tr");
          for (let key of arr) {
            const td = document.createElement("td");

            // 제목인 경우
            if (key == 'adoptTitle') {
              const a = document.createElement("a");
              a.innerText = mark[key]; // 제목을 a 태그 내용으로 대입
              a.href = "/adoption/adoptionList/" + mark.adoptNo;
              td.append(a);

            } else {

              td.innerHTML = mark[key];

              if (mark[key] == 'N') {
                td.innerText = '미입양';
              } else {
                td.innerText = '입양완료';
              }

            }

            tr.append(td);
          }
          // tbody의 자식으로 tr( 한 줄 ) 추가
          tbody.append(tr);

        }

        table.append(tbody);
        profilebox.append(table);


        
         /* pagination */
         const div = newEl("div", {"class" : "pagination-area"});
         div.class="pageBox";
         const ul = newEl("ul", {"class" : "pagination"});
 
         div.append(ul);
         
         /* 앞쪽 화살표 */
         const startList = newEl("li") 
         const startSpan = newEl("span");
         startSpan.id="stP";
         startSpan.innerHTML = '&lt;&lt;'
         
         startList.append(startSpan);
         ul.append(startList);
        
      
         startSpan.addEventListener("click", () => selectMyBookMark(pg.startPage) )
         
         
         /* 숫자 부분 */
         for(let i=pg.startPage ; i <= pg.endPage ; i ++){
           const li = newEl("li")
           li.class="current";
           const span = newEl("span");
           span.innerText = i;
           li.append(span);
           ul.append(li);
 
           // 현재 페이지인 경우
           if(i == pg.currentPage) span.classList.add("current");
           else{
             span.addEventListener("click", () => selectMyBookMark(i) )
           }
 
         }
 
        /* 뒤쪽 화살표 */
        
        const endList = newEl("li") 
         const endSpan = newEl("span");
         
         endSpan.innerHTML = '&gt;&gt;';
         
         endList.append(endSpan);
         ul.append(endList);
         endSpan.addEventListener("click", () => selectMyBookMark(pg.endPage) )
      

         profilebox.append(div);

      }


    });
}



bookListBtn.addEventListener('click', () => {

  selectMyBookMark(1);


});


// ---------------------------------------------------------- ----------


/* 프로필 버튼 클릭시 첫화면으로 보내주기 */



// button 클릭시 작동하게 하기

profileListBtn.addEventListener('click', () => {

  location.href = "myPage-first";


});


//---------------------------------------------------------------------------------
/* 마이페이지 프로필  */


/* 회원 탈퇴 */

const memberDel = document.querySelector("#memberDel");

if (memberDel != null) {

  memberDel.addEventListener("submit", e => {

    if (!confirm("정말 탈퇴하시겠습니까?")) {

      alert("취소되었습니다");
      e.preventDefault();
      return;
    }


  })
}





