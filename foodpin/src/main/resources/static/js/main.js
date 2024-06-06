// 메인 페이지 -> 가게 검색 페이지로 보내는 동작


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



/* 검색 관련된 요소 */
const searchKey = document.querySelector("#searchKey");

const searchQuery = document.querySelector("#searchQuery"); /* 검색어 입력 값 */

const options = document.querySelectorAll("#searchKey > option"); /* 배열로 전부 얻어옴 */

// 검색창에 이전 검색 기록을 남겨놓기

// 즉시 실행 함수
//   (() => {})();
// -> 원래 함수는 정의 따로 호출 따로 이지만

// -> 즉시 실행 함수는 함수가 정의 되자마자 바로 실행됨

// 장점 1. 변수명 중복 해결
// 장점 2. 속도가 조금 더 빠름(속도적 우위)

(()=>{

    // 쿼리스트링 값을 key, value 구분해서 저장하는 객체 반환
    const params = new URL(location.href).searchParams;
  
    const key = params.get("key"); // t, c, tc, w 중 하나
    const query = params.get("query"); // 검색어
  
    if(key != null){ // 검색을 했을 때
        searchQuery.value = query; // 검색어를 화면에 출력
  
        // option태그를 하나씩 순차 접근해서 value가 key랑 같으면
        // selected 속성 추가 / 선택되어 있는 것 처럼 보임
        for(let op of options){
            if(op.value == key){
                op.selected = true; 
            }
        }
    }

})();

