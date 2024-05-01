const submitPost = document.querySelector("#submitPost");

submitPost.addEventListener("submit", e => {

   const title = document.querySelector("#reviewTitle");
   const content = document.querySelector("#reviewContent");

   oEditors.getById["reviewContent"].exec("UPDATE_CONTENTS_FIELD", []);

 })



