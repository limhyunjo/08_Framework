const contactName = document.querySelector("#contactName");
const contactAge = document.querySelector("#contactAge");
const contactTel = document.querySelector("#contactTel");
const contactTime = document.querySelector("#contactTime");
const contactAddress = document.querySelector("#contactAddress");
const contactJob = document.querySelector("#contactJob");
const contactFrm = document.querySelector("#contactFrm");


contactFrm.addEventListener("submit",e=>{

    
    /* 성명 */
    if(contactName.value.trim().length==0){
        alert("성명을 작성해주세요.")
        contactName.focus();
        e.preventDefault();
        return;
    }
    /* 연령 */
    if(contactAge.value.trim().length==0){
        alert("나이를 작성해주세요.")
        contactAge.focus();
        e.preventDefault();
        return;
    }

    /* 나이 숫자 여부 */
    if(isNaN(Number(contactAge.value))==true){
        alert("연령은 숫자만 기입 가능합니다.")
        contactAge.focus();
        e.preventDefault();
        return;
    }

    /* 전화번호 */
    if(contactTel.value.trim().length==0){
        alert("전화번호를 작성해주세요.")
        contactTel.focus();
        e.preventDefault();
        return;
    }

    /* 전화번호 숫자 여부 */
    if(isNaN(Number(contactTel.value))==true){
        alert("전화번호는 숫자만 기입 가능합니다.")
        contactTel.focus();
        e.preventDefault();
        return;
    }

    // 전화번호 형식 X
    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

    if(!regExp.test(contactTel.value)) { 
        alert("전화번호의 형식이 올바르지 않습니다.")
        e.preventDefault();
        return;
    }


    /* 연락 가능한 시간 */
    if(contactTime.value.trim().length==0){
        alert("연락 가능한 시간을 작성해주세요.")
        contactTime.focus();
        e.preventDefault();
        return;
    }
    /* 주소 */
    if(contactAddress.value.trim().length==0){
        alert("거주 지역을 작성해주세요.")
        contactAddress.focus();
        e.preventDefault();
        return;
    }
    /* 직업 */
    if(contactJob.value.trim().length==0){
        alert("직업을 작성해주세요.")
        contactJob.focus();
        e.preventDefault();
        return;
    }
})