const selectAllBtn = document.querySelector("#selectAllBtn"); // 조회
const selectBody = document.querySelector("#selectBody"); // tbody


// 전달 받은 요소에 속성 추가하는 함수
// const addAttr = (el, key, val) => {
//   el.setAttribute(key, val);
//   return el;
// }

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


selectAllBtn.addEventListener("click", () => {

  fetch("/manage/selectAll")
  .then(resp => resp.json())
  .then(bookList => {

    selectBody.innerHTML = ""; // 이전 내용 삭제

    if(bookList.length == 0){ // 조회 결과가 없을 경우
      const tr = createElement("tr");
      const td = createElement("td", {"colspan" : "5"}, "책이 존재하지 않습니다");

      tr.append(td);
      selectBody.append(tr);
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
      
      arr.forEach(item => tr.append(item));
      selectBody.append(tr);
    });

    


  })

})