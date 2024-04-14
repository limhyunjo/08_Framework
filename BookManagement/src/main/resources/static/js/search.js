const inputTitle = document.querySelector("#inputTitle"); // 제목 입력
const searchBtn = document.querySelector("#searchBtn"); // 검색 버튼
const tbody = document.querySelector("#tbody"); // tbody



// 요소 만들어서 반환하는 함수
const createElement = (tagName, attr, text) => {
  const el = document.createElement(tagName);

  // 속성 추가
  if(attr != undefined && attr != null){
    for(let key in attr){
      el.setAttribute(key, attr[key]);
    }
  }

  // 내용 추가
  if(text != undefined && text != null)   el.innerText = text;

  return el;
}


/* ************ 수정 버튼 이벤트 핸들러 ************ */
const updateBtnHandler = (e, bookNo) => {
  
  const updatePrice = prompt("수정할 가격 입력");

  // 취소 클릭 시
  if(updatePrice == null) return;

  // 확인 클릭 시
  fetch("/manage/updatePrice", {
    method : "PUT",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify( {"bookNo" : bookNo, "bookPrice" :updatePrice } )
  })

  .then(resp => resp.text())
  .then(result => {

    if(result > 0){
      const priceTd = e.target.closest("tr").children[3];
      priceTd.innerText = updatePrice;
      priceTd.style.color = "green";
    } else{
      alert("수정 실패");
    }
  });

}

/* ************ 삭제 버튼 이벤트 핸들러 ************ */
const deleteBtnHandler = (e, bookNo) => {
  

  fetch("/manage/delete", {
    method : "DELETE",
    headers : {"Content-Type" : "application/json"},
    body : bookNo 
  })

  .then(resp => resp.text())
  .then(result => {

    if(result > 0){
      e.target.closest("tr").remove(); // 행 삭제
    } else{
      alert("삭제 실패");
    }
  });

}






searchBtn.addEventListener("click", () => {

  if(inputTitle.value.trim().length == 0) {
    alert("검색할 책의 제목을 입력해 주세요");
    return;
  }

  fetch("/manage/searchList?inputTitle=" + inputTitle.value)
  .then(resp => resp.json())
  .then(bookList => {

    tbody.innerHTML = ""; // 이전 내용 삭제

    if(bookList.length == 0){ // 조회 결과가 없을 경우
      const tr = createElement("tr");
      const td = createElement("td", {"colspan" : "7"}, "검색 결과가 없습니다");

      tr.append(td);
      tbody.append(tr);
      return;
    }

    bookList.forEach(book => {
      const tr = createElement("tr");
      
      const arr = [];
      arr.push(createElement("td", null, book.bookNo));
      arr.push(createElement("td", null, book.bookTitle));
      arr.push(createElement("td", null, book.bookWriter));
      arr.push(createElement("td", null, book.bookPrice));
      arr.push(createElement("td", null, book.regDate));

      // *** 수정 버튼 ***
      const updateTd = createElement("td");
      const updateBtn = createElement("button", null, "수정");
      updateTd.append(updateBtn)

      updateBtn.addEventListener("click", e => updateBtnHandler(e, book.bookNo));

      arr.push(updateTd);


      // *** 삭제 버튼 ***
      const deleteTd = createElement("td");
      const deleteBtn = createElement("button", null, "삭제");
      deleteTd.append(deleteBtn);

      deleteBtn.addEventListener("click", e => deleteBtnHandler(e, book.bookNo));

      arr.push(deleteTd);

     
      
      arr.forEach(item => tr.append(item));
      tbody.append(tr);
    });

    


  })

})