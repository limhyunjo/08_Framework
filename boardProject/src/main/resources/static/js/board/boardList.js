/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");

/* 로그인 안하면 없는 버튼임 */

// 글쓰기 버튼이 존재할 때(로그인 상태인 경우)
if(insertBtn != null){

    insertBtn.addEventListener('click', ()=>{


      
        // boardCode 얻어오기


        // * boardCode 얻어오는 방법 *

        //1) @PathVariable("boardCode")얻어와 전역 변수 저장
        //2) location.pathname.split("/")[2]

        //alert(boardCode);

        // get 방식 요청 // boardCode 얻어와 백틱(` `) 안에 써서 문자열로 만듦
        location.href=`/editBoard/${boardCode}/insert`;
    })
}