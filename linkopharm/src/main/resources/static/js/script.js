
const nav=document.querySelector('.navbar')

window.addEventListener('scroll',function(){
    if(window.scrollY >= 56){
        nav.classList.add('navbar-scrolled')
    }else{
        nav.classList.remove('navbar-scrolled')
    }
})

const li=document.querySelectorAll(".nav-link");
const sec=document.querySelectorAll("section");

function activeMenu(){
    let len = sec.length;
    while (--len && window.scrollY + 97< sec[len].offsetTop){}
    li.forEach(item => 
        item.classList.remove("active"));
        li[len].classList.add("active");
}
activeMenu()
window.addEventListener("scroll",activeMenu);


AOS.init({
    
  offset: 120,
  delay: 0,
  duration: 900,
  easing: 'ease',
  once: false,
  mirror: false,
  anchorPlacement: 'top-bottom',
});




function searchTable() {
    console.log("AAAAAAAAAAA");
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
  
    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        let matchFound = false;
        let tdElements = tr[i].getElementsByTagName("td");
        for (let j = 1; j < tdElements.length; j++) {
          td = tdElements[j];
          txtValue = td.textContent || td.innerText;
          if (txtValue.toUpperCase().indexOf(filter) > -1) {
            matchFound = true;
            break;
          }
        }
        if (matchFound) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
  }