submitPost = function() {
    oEditors.getById["reviewContent"].exec("UPDATE_CONTENTS_FIELD", []);
   
    const title = document.querySelector("#reviewTitle").value;
    const content = document.querySelector("#reviewContent").value;

    console.log(title);
    console.log(content);

    const obj = {
        "reviewTitle" : title,
        "reviewContent" : content
    };

    fetch("/review/reviewWrite", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then( resp => resp.text() )
    .then( result => {
        console.log(result);
    })

}