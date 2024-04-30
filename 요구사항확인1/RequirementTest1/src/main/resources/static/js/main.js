
const selectAllBtn = document.querySelector("#selectAllBtn");
const tbody = document.querySelector("#tbody");


const selectStudentList = ()=>{

fetch("selectStudentList")


.then( resp=> resp.json()) 

.then(studentList =>{ 

 console.log(studentList);

 tbody.innerHTML=""; 



     for(let student of studentList){ 

   


      
      const tr = document.createElement("tr");

      const arr = ['studentNo', 'studentName', 'studentMajor', 'studentGender'];
      
      for(let key of arr){
        const td = document.createElement("td");
        td.innerText = student[key];
        tr.append(td);
      }
       
        tbody.append(tr);
     }

  });

}; 

selectAllBtn.addEventListener('click',e =>{

    selectStudentList();
})

