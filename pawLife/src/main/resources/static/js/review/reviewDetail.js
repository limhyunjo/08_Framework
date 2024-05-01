/* 수정 버튼 클릭 시 */
const updateBtn = document.querySelector(".update-btn")

if(updateBtn!=null){
    updateBtn.addEventListener("click",()=>{
    
        location.href =  location.pathname.replace('reviewList', 'editReview')
                     + "/update"
                     + location.search;
    })
}



/* 삭제 버튼 클릭 시 */
const deleteBtn = document.querySelector(".delete-btn");

if(deleteBtn!=null){

    deleteBtn.addEventListener("click",()=>{
        if(!confirm("삭제하시겠습니까?")){
            alert("취소됨")
            return;
        }

        const url = location.pathname.replace("reviewList","editReview") +"/delete";

        const form = document.createElement("form")
        form.action = url;
        form.method = "POST";

        // cp값을 저장할 input 생성
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "cp";

        // 쿼리스트링에서 원하는 파라미터 얻어오기
        const params = new URLSearchParams(location.search)
        const cp = params.get("cp");
        input.value = cp;

        form.append(input);

        // 화면에 form태그를 추가한 후 제출하기
        document.querySelector("body").append(form);
        form.submit();
    })

}
    

/* 댓글 목록 조회 */
const selectCommentList = () => {


    fetch("/comment?reviewNo=" + reviewNo)
    .then(resp => resp.json())
    .then(commentList => {

        const ul = document.querySelector("#commentList");
        ul.innerHTML = "";

        for(let comment of commentList) {

            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");

            // 작성자 (p태그)
            const commentWriter = document.createElement("p");
            commentWriter.classList.add("comment-writer");

            // p태그 하위 요소
            const profileImg = document.createElement("img");
            // 이미지 추가할것
            // if(comment.profileImg == null)  profileImg.src = userDefaultImage;
            // else    profileImg.src = comment.profileImg;

            const nickname = document.createElement("span");
            nickname.innerText = comment.memberNickname;

            const commentDate = document.createElement("span");
            commentDate.classList.add("comment-date");
            commentDate.innerText = comment.commentWriteDate;

            commentWriter.append(profileImg, nickname, commentDate);
            //li.append(commentWriter);

            // 덧글 내용
            const content = document.createElement("p");
            content.classList.add("comment-content");
            content.innerText = comment.commentContent;
            commentRow.append(commentWriter, content);

            // 버튼
            const commentBtnArea = document.createElement("div");
            commentBtnArea.classList.add("comment-btn-area");

            if(loginMemberNo != null && loginMemberNo == comment.memberNo){

                const updateBtn = document.createElement("button");
                updateBtn.innerText = "수정";
                updateBtn.setAttribute("onclick", `showUpdateComment(${comment.commentNo}, this)`);

                const deleteBtn = document.createElement("button");
                deleteBtn.innerText = "삭제";
                deleteBtn.setAttribute("onclick", `deleteComment(${comment.commentNo})`);

                // 버튼 영역에 수정/삭제 버튼 추가
                commentBtnArea.append(updateBtn, deleteBtn);
                commentRow.append(commentBtnArea);
            }
            ul.append(commentRow);
        }
    })
}
/* 댓글 등록 */
const commentContent = document.querySelector("#commentContent");
const addComment = document.querySelector("#addComment");

addComment.addEventListener("click", e => {

    if(loginMemberNo == null) {
        alert("로그인후 이용해주세요.");
        return;
    }

    if(commentContent.value.trim().length == 0){
        alert(" 내용 작성후 등록 버튼을 클릭해주세요.");
        commentContent.focus();
        return;
    }

    const data = {
        "commentContent" : commentContent.value,
        "reviewNo" : reviewNo,
        "memberNo" : loginMemberNo
    };

    fetch("/comment", {
        method : "POST",
        headers : {"content-Type" : "application/json"},
        body : JSON.stringify(data)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("댓글이 등록되었습니다.");
            commentContent.value = "";
            selectCommentList();
        }
        else alert("댓글 등록 오류");
    })
    .catch( err => console.log(err));
});

/* 댓글 수정 화면 전환 */
let beforeCommentRow; // 수정 취소시 돌아가기 위한 백업 변수

const showUpdateComment = (commentNo, btn) => {

    const temp = document.querySelector(".update-textarea");

    if(temp != null) {
        
        if(confirm("수정중인 댓글이 있습니다. 현재 댓글을 수정 하시겠습니까?")) {

            const CommentRow = temp.parentElement;
            CommentRow.after(beforeCommentRow);
            CommentRow.remove();
        }
        else return; 
    }
    //
    const commentRow = btn.closest("li");
    beforeCommentRow = commentRow.cloneNode(true);
    let beforeComment = commentRow.children[1].innerText;

    commentRow.innerHTML = "";

    const textarea = document.createElement("textarea");
    textarea.classList.add("update-textarea");
    textarea.value = beforeComment;

    commentRow.append(textarea);

    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");

    const updateBtn = document.createElement("button");
    updateBtn.innerText = "수정";
    updateBtn.setAttribute("onclick", `updateComment(${commentNo}, this)`);

    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "updateCancle(this)");

    commentBtnArea.append(updateBtn, cancelBtn);
    commentRow.append(commentBtnArea);
}

// 수정 취소
const updateCancle = (btn) => {

    if(confirm("취소 하시겠습니까?")){

        const commentRow = btn.closest("li");
        commentRow.after(beforeCommentRow);
        commentRow.remove();
    }
}

// 댓글 수정
const updateComment = (commentNo, btn) => {

    const textarea = btn.parentElement.previousElementSibling;

    if(textarea.value.trim().length == 0){
        alert("댓글 작성 후 수정버튼을 클릭해주세요.");
        textarea.focus();
        return;
    }

    const data = {
        "commentNo" : commentNo,
        "commentContent" : textarea.value
    }

    fetch("/comment", {
        method : "PUT",
        headers : {"content-Type" : "application/json"},
        body : JSON.stringify(data)
    })

    .then(resp => resp.text())
    .then(result => {

        if(result > 0){
            alert("댓글이 수정되었습니다.");
            selectCommentList();
            
        } else alert("댓글 수정 실패");
    })
    .catch( err => console.log(err));
}
/* 댓글 삭제 */
const deleteComment = commentNo => {

    console.log(commentNo);

    if( !confirm("삭제 하시겠습니까?")) {
        return;
    }

    fetch("/comment", {
        method : "DELETE",
        headers : {"content-Type" : "application/json"},
        body : commentNo
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("댓글이 삭제되었습니다.");
            selectCommentList();
        }
        else    alert("댓글 삭제 실패");
    })
    .catch( err => console.log(err));
};

selectCommentList();