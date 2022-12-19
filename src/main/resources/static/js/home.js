/**
 * 
 */
 
 // 홈(메인) 화면 컨텐츠에 쓰일 자바스크립트
 
    // Category's BEST
function openTap(e, category) {
    let i;
    let cateTap = document.querySelectorAll('cateTap');  // 모든 탭내용 찾기
      
    for (i = 0; i < cateTap.length; i++) {
        cateTap[i].style.display = "none";    // 탭내용들 숨기기
    }
    
    let button = document.querySelectorAll('btnTap');  // 탭버튼들 찾기
    
    for (i = 0; i<cateTap.length; i++) {
        button[i].className = button[i].className.replace(" w3-dark-grey", "");
    }  // 모든 탭버튼들 강조효과 지우기
      
      document.getElementById(category).style.display = "block";
      e.currentTarget.className += " w3-dark-grey";
    }

    let btnNow = document.querySelectorAll('btnTap')[0];
    btnNow.click();
    