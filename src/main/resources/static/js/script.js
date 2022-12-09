/**
 * 
 */
 
 // Accordion(사이드바) 메뉴
function AccFunc1() {
  var x = document.getElementById("Acc1");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else {
    x.className = x.className.replace(" w3-show", "");
  }
}

// 사이드바 하위메뉴 펼치기(구현 중)
document.getElementById("myBtn1").click();




// 사이드바 열고 닫기
function w3_open() {
  document.getElementById("mySidebar").style.display = "block";
  document.getElementById("myOverlay").style.display = "block";
}
 
function w3_close() {
  document.getElementById("mySidebar").style.display = "none";
  document.getElementById("myOverlay").style.display = "none";
}


// 검색 모달창 open/close function? 담에 넣어 보기




