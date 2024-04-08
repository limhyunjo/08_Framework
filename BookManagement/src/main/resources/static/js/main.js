/* 모든 책 조회 */
const selectBookList = document.querySelector("#selectBookList");
const tbody = document.querySelector("#bookList");

selectBookList.addEventListener("click", ()=>{


    fetch("/book/bookList")
    .then(resp => resp.json())
  
    .then(bookList=>{


             
      tbody.innerHTML = "";

      for(let book of bookList){
   
   
       const tr = document.createElement("tr");
       const arr = ['bookNo', 'bookTitle','bookWriter', 'bookPrice','regDate'];
   
       for(let key of arr){
         const td = document.createElement("td");
   
         td.innerText = book[key];
         tr.append(td);
       }
         // tbody의 자식으로 tr( 한 줄 ) 추가
         tbody.append(tr);
      }
   
     });
   
});
   