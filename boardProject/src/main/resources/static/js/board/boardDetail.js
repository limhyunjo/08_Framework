/* 좋아요 버튼(하트) 클릭 시 비동기로 좋아요 INSERT/ DELETE */

// 회원 번호와 게시글 번호 얻어오기


//   Thymeleaf (Template Engine) 
// Servlet : Java + Web 요청 받으면 응답 화면 만들어서 출력
// Java에서 HTML 쓰기 힘들어 JSP (html + java) 사용 -> Java로 변함
// Thymeleaf는 확장자 .Html로 단독 실행 -> 컴파일 이후 Java가됨
// java에서 화면 만들기 힘들어서 사용하는 Jsp와 같은 template engine

// Tymeleaf는 아래와 같이 이루어져 있음
// - html 코드( + css, js)
// - th: 코드(java) + Spring EL 

// Thymeleaf 코드 해석 순서
// 1. th: 코드(java) + Spring EL 
// 2. html 코드( + css, js)

// java의 Session과 model에서 얻어온 값 사용


// 1) 로그인한 회원 번호 준비
//     -->  Session에서 얻어오기
//          (Session은 서버에서 관리하기 때문에 JS로 바로 얻어올 수 있는 방법이 없음)

// 2) 현재 게시글 번호 준비

// 3) 좋아요 여부 준비



// 1. #boardLike가 클릭 되었을 때
const boardLike = document.querySelector("#boardLike");
boardLike.addEventListener("click", e=>{

    //2. 로그인 상태가 아닌 경우 동작 X

    if(loginMemberNo == null){
        alert("로그인 후 이용해 주세요");
        return;
    }


    // 3. 준비된 3개의 변수를 객체로 저장 -> (Json 변환 예정)
    const obj = {
        "memberNo" : loginMemberNo,
        "boardNo"  : boardNo,
        "likeCheck": likeCheck
    };

    //4. 좋아요 INSERT / DELETE 비동기 요청
    fetch("/board/like", {

    method  : "POST",
    headers : {"Content-Type" : "application/json"},
    body    : JSON.stringify(obj) // 객체를 Json으로 문자화 

    })

    .then(resp =>resp.text()) // 반환 결과 text(글자) 형태로 변환
    .then(count =>{

        // count == 첫 번째 then의 파싱되어 반환된 값('-1' 또는 게시글 좋아요 수)
        //console.log("result :", result);


        if(count == -1){
            console.log("좋아요 처리 실패");
            return;
        }

        // 5. likeCheck 값 0<->1 변환
        // (왜? 클릭 될 때 마다 INSERT/DELETE 동작을 번갈아 가면서 할 수 있음)
        likeCheck = likeCheck == 0? 1: 0;

        // 6. 하트를 채웠다/비웠다 바꾸기
        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        // 7. 게시글 좋아요 수 수정
        e.target.nextElementSibling.innerText = count;

    });

});

/* 게시글 삭제 버튼 */
const deleteBtn = document.querySelector("#deleteBtn");

if(deleteBtn != null){
  deleteBtn.addEventListener("click", e =>{

      
    if(confirm("삭제 하시겠습니까?")){
      location.href = `/editBoard/${boardCode}/${boardNo}/delete`;
    }
    else{
      alert("취소됨");
    }

  })
}





/* 게시글 수정 버튼 */

const updateBtn = document.querySelector("#updateBtn");

if(updateBtn != null){ // 수정 버튼이 존재 시

  updateBtn.addEventListener("click", () =>{

    // 현재 : /board/1/2010?cp=1
   // 목표 :  /editBoard/1/2010/update?cp=1 (Get 방식)

   //location.pathname : /board/1/2010
   //.replace : 이름을 바꿔줌
   //location.search : 현재 주소의 쿼리 스트링이 붙음 ?cp=1
    location.href = location.pathname.replace('board','editBoard')
                    +"/update"
                    +location.search;

  })

}



// -----------------------------------------------------------

/* 목록으로 돌아가는 버튼 */
const goToList = document.querySelector("#goToListBtn");

goToListBtn.addEventListener("click", ()=>{

  // location.pathname = 현재 주소  /board/1/2011
  // location.search = 쿼리 스트링이 나옴  ?cp=1

  // 상세조회 : /board/1/2011?cp=1

  // 목록 : /board/1?cp=1

  let url = location.pathname;

  // url을 자른 것을 다시 url에 저장
  // /로 자른 마지막 index
  url = url.substring(0, url.lastIndexOf("/"));

  location.href = url + location.search;
                          // 쿼리 스트링

});