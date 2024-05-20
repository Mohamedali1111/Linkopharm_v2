
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




