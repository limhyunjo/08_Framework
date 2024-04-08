// 할 일 추가 관련 요소
const bookTitle = document.querySelector("#bookTitle");
const bookWriter = document.querySelector("#bookWriter");
const bookPrice = document.querySelector("#bookPrice");

const addBtn = document.querySelector("#addBtn");


// 할 일 추가 버튼 클릭 시 동작
addBtn.addEventListener('click',()=>{

    // 비동기로 할 일 추가하는 fetch() API 코드 작성
    // - 요청 주소 : "/ajax/add"
    // - 데이터 전달 방식(method) : "POST" 방식
   
   
    // 파라미터를 저장한 JS 객체
    //               key       :    value
    const param = {"bookTitle" : bookTitle.value,
                   "bookWriter" : bookWriter.value,
                   "bookPrice" : bookPrice.value
    }
   
    fetch("/book/add", {
   
     // key : value
       method : "POST",  // POST 방식 요청
       headers : {"Content-Type" : "application/json"}, // 요청 데이터의 형식을 json으로 지정
       body    : JSON.stringify(param) // param 객체를 JSON(String)으로 변환
        
   
     })
      // 변수 이름 마음대로 해도 됨 resp
     .then(resp=> resp.text())  // 반환된 값을 text로 변환
   
     // 첫 번째 then에서 반환된 값 중 변환된 text를 temp에 저장
     . then(temp =>{
   
       if(temp>0) { // 성공
           alert("등록 성공");
   
           // 추가 성공한 제목, 내용 지우기
           bookTitle.value="";
           bookWriter.value="";
           bookPrice.value="";
   
       
           // 할 일 목록 다시 조회
           selectbookList();
   
       }else{ // 실패
           alert("추가 실패");
       }
     })
   });
   