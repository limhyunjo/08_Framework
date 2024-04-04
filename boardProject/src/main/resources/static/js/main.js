/* 쿠키에서 key가 일치하는 value 얻어오기 함수 */

// 쿠키는 "K=V"; "K=V"; 형식

// 배열.map(함수) : 배열의 각요소를 이용해 함수 수행 후 
//                 결과 값으로 새로운 배열을 만들어서 반환 -> 새로운 배열을 만들어냄
const getCookie= key =>{
    const cookies = document.cookie; //"K=V"; "K:V";



    // cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split(";") // ["K=V", "K=V"] 배열 모양으로 쪼개짐
                             // .map(el => {return el.split}) // ["K", "V"]
                             .map( el => el.split("=") ); // ["K", "V"]
    
        console.log(cookieList); // -> 2차원 배열이 됨

    // 배열 -> 객체로 변환 (그래야 다루기 쉽다)
    
    const obj = {}; // 비어있는 객체 선언

    for(let i=0; i<cookieList.length; i++){
       const k= cookieList[i][0]; // key 값
       const v= cookieList[i][1];
       obj[k]=v; // 객체에 추가
    }

    //console.log("obj", obj);

    return obj[key]; // 매개 변수로 전달 받은 key와
                     // obj 객체에 저장된 key가 일치하는 요소의 값 반환
}

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']")

// 아이디가 loginForm의 후손 중에서 input name이 memberEmail인 요소

// 로그인 안된 상태인 경우에만 수행
if(loginEmail != null){// 로그인창의 이메일 입력 부분이 있을 때


    // 쿠키 중 key값이 "saveId"인 요소의 value 얻어오기
    const saveId= getCookie("saveId"); // undefined 또는 이메일

    // saveId 값이 있을 경우
    if(saveId != undefined){
        loginEmail.value = saveId; // 쿠키에서 얻어온 값을 input에 value로 세팅

        document.querySelector("input[name='saveId']").checked = true;
    }

}

// 로그인 시 비밀번호가 작성되지 않은 경우
// 로그인 시도조차 못하게 하겠다 == form 제출을 못하게 하겠다
// form 태그 제출을 막는 방법

// form요소.addEventListener('submit", e=>{
   // e.preventDefualt(); // 이메일이나 비밀번호가 없을 때 기본 이벤트 막기
//})

/* 이메일, 비밀번호 미작성 시 로그인 막기 */
const loginForm = document.querySelector("#loginForm");
const loginPw
  = document.querySelector("#loginForm input[name='memberPw']");


// #loginForm이 화면에 존재할 때 (== 로그인 상태 아닐 때)
if(loginForm !=null){

        // 제출 이벤트 발생 시
        loginForm.addEventListener("submit", e=>{
        //logingEmail : 이메일 input 요소
        //loginPw : 비밀번호 input 요소

        // 이메일 미작성
       if(loginEmail.value.trim().length===0){
          alert("이메일을 작성해 주세요!")
       
        e.preventDefault(); // 기본 이벤트 (제출) 막기
         
        loginEmail.focus(); // 초점 이동
        return; 

       }
        // 비밀번호 미작성
       if(loginPw.value.trim().length===0){
          alert("비밀번호를 작성해 주세요!")
       
        e.preventDefault(); // 기본 이벤트 (제출) 막기
         
        loginPW.focus(); // 초점 이동
        return; 
       }
    });
}

/*빠른 로그인 */
const quickLoginBtns = document.querySelectorAll(".quick-login"); // NodeList로 얻어와짐
//foreach문 사용하여 반복
quickLoginBtns.forEach((item, index)=>{

 // item : 현재 반복 시 꺼내온 객체
 // index : 현재 반복 중인 인덱스

 // quickLoginBtns 요소를 하나씩 꺼내서 이벤트 리스너 추가 
 item.addEventListener("click", e=>{
 
  const email = item.innerText; // 버튼에 작성된 이메일 얻어오기

  location.href = "/member/quickLogin?memberEmail=" + email;

 });

});


//---------------------------------------------------
/* 회원 목록 조회(비동기) */
const selectMemberList = document.querySelector("#selectMemberList");
const memberList = document.querySelector("#memberList");

// 조회 버튼 클릭 시

selectMemberList.addEventListener("click", ()=>{

  // 1) 비동기로 회원 목록 조회
  // (포함될 회원 정보 : 회원 번호, 이메일, 닉네임, 탈퇴 여부)
  
  // 첫 번째 then(response => response.json()) ->
  // JSON Array -> JS 객체 배열로 변환 [{}, {}, {},{}]

  // 2) 두 번째 then
  //    tbody에 이미 작성되어 있던 내용(이전에 조회한 목록) 삭제

  // 3) 두 번째 then
  // 조회된 JS 객체 배열을 이용해
  // tbody에 들어갈 요소를 만들고 값 세팅 후 추가

  fetch("/member/memberList")
  .then(resp => resp.json())

  .then(member=>{

    // forEach 는 NodeList Array에서만 사용 가능
    // member.forEach((member.index)=>{
          // member : 반복 접근한 요소 (순서대로 하나씩 꺼낸 요소)
          // index : 현재 접근 중인 index

          // tr 만들어서 그 안에 td 만들어서 append 후
          // tr 을 tbody에 append

          // const keyList = ['memberNo', 'memberEmail', 'memberNickname', 'memberDelFl'];

          // const tr = document.createElement("tr");

            // keyList에서 key를 하나씩 얻어온 후
            // 해당 key에 맞는 member 객체 값을 얻어와
            // 생성되는 td 요소에 innerText로 추가 후
            // tr 요소의 자식으로 추가
         
            // keyList.forEach(key =>{ 
            // tr.append(createTd(member[key])

            // tbody 자식으로 tr 추가
            // memberList.append(tr);
          //})
    //})

    //td 요소를 만들고 text 추가 후 반환
    // const createTd = (text) => {
      // const td
    //}
     
      memberList.innerHTML = "";

   for(let mem of member){


    const tr = document.createElement("tr");
    const arr = ['memberNo', 'memberEmail','memberNickname', 'memberDelFl'];

    for(let key of arr){
      const td = document.createElement("td");

      td.innerText = mem[key];
      tr.append(td);
    }
      // tbody의 자식으로 tr( 한 줄 ) 추가
      memberList.append(tr);
   }

  });

 
});

/* 특정 회원 비밀번호 초기화(Ajax) */

const resetMemberNo = document.querySelector("#resetMemberNo");
const resetPw = document.querySelector("#resetPw");

resetPw.addEventListener("click",()=>{

  // 입력받은 회원 번호 얻어오기
  const inputNo = resetMemberNo.value;

  if(inputNo.trim().length ==0){
    alert("회원 번호를 입력해 주세요");

    return;
  }

 fetch("/resetPw",{
  method : "PUT", // PUT : 수정 용도 요청 방식
  headers : {"Content-Type" : "application/json"}, 
  body : inputNo //inputNo를 PUT 방식으로 넘김

 })
 .then(resp=>resp.text()) 
 //resp.text() -> result 첫번째 then에서 반환한 값이 result로 들어감
 .then(result =>{

  // result == 컨트롤러로부터 반환받아 TEXT 로 파싱한 값
  if(result>0) alert("초기화 성공");
  else         alert("해당 회원이 존재하지 않습니다");

 });

});


/* 특정 회원 탈퇴 복구(Ajax) */

const secessionReset = document.querySelector("#secessionReset");
const resetSecession = document.querySelector("#resetSecession");

resetSecession.addEventListener("click", () =>{

  const inputNo = secessionReset.value;

  if(inputNo.trim().length==0){
    alert("회원 번호를 입력해 주세요");
    return;
  }

  // inputNo를 담아서 보냄 fetch를 버튼 안에 넣음
  fetch("/resetSecession",{
    method: "PUT", // 수정
    headers : {"Content-Type" : "application/json"},
    body : inputNo 
  })
  .then(resp=>resp.text()) 
  //resp.text() -> result 첫번째 then에서 반환한 값이 두번째 then의 result로 들어감
  .then(result =>{
 
   // result == 컨트롤러로부터 반환받아 TEXT 로 파싱한 값
   if(result>0) alert("회원 복구 성공");
   else         alert("해당 회원이 존재하지 않습니다");
 
  });


});