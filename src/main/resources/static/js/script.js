/**
 * 
 */
 
 // 정혁
 const modalConfirm = (tar) => {
    let signupUsername = document.getElementById('signupUsername');
    let signupPassword = document.getElementById('signupPassword');
    let signupEmail = document.getElementById('signupEmail');
    let signupNickname = document.getElementById('signupNickname');
    let signupName = document.getElementById('signupName');
    let signupPhone = document.getElementById('signupPhone')
    let signupaddress = document.getElementById('signupaddress');
    console.log('modalConfirm');
    console.log(`data-type : ${tar.getAttribute('data-bs-toggle')}`);

    
    if(signupUsername.value === '' || signupUsername.value === ' ') return signupUsername.focus();
    if(signupPassword.value === '' || signupPassword.value === ' ') return signupPassword.focus();
    if(signupEmail.value === '' || signupEmail.value === ' ') return signupEmail.focus();
    if(signupNickname.value === '' || signupNickname.value === ' ') return signupNickname.focus();
    if(signupName.value === '' || signupName.value === ' ') return signupName.focus();
    if(signupPhone.value === '' || signupPhone.value === ' ') return signupPhone.focus();
    if(signupaddress.value === '' || signupaddress.value === ' ') return signupaddress.focus();
    


    document.getElementById('username').value = signupUsername.value;
    document.getElementById('password').value = signupPassword.value;
    document.getElementById('email').value = signupEmail.value;
    document.getElementById('nickname').value = signupNickname.value;
    document.getElementById('name').value = signupName.value;
    document.getElementById('phone').value = signupPhone.value;
    document.getElementById('address').value = signupaddress.value;

    
    
    return false;
    }

    //회원가입정보가 전부 들어갔는지 각각 체크하는 함수
    const checkValue = (tar) =>{
        if(signupUsername.value != '' && signupPassword.value != '' && signupEmail.value != '' &&
              signupNickname.value != '' && signupName.value != '' && signupPhone.value != '' && signupaddress.value != ''){ //모든 값이 반 값이 아닐때
            document.getElementById('registerConfirmBtn').setAttribute('data-bs-toggle','modal');
    
        }else{ //빈 값이 하나라도 있을 때
            document.getElementById('registerConfirmBtn').setAttribute('data-bs-toggle','');
  
        }
        return false;
    }

    const sendForm = (type) => {

        if(type === 'register'){
            const registerForm = document.getElementById('registerForm');
            registerForm.submit();
        }else if(type === 'login'){

            if(document.getElementById('signinUsername').value === '' || document.getElementById('signinUsername').value === ' ') return document.getElementById('signinUsername').focus();
            if(document.getElementById('signinPassword').value === '' || document.getElementById('signinPassword').value === ' ') return document.getElementById('signinPassword').focus();

            const loginForm = document.getElementById('loginForm');
            loginForm.submit();
        }

        
        
        return false;
       
    }
    window.addEventListener('DOMContentLoaded', function() {
       
       const usernameInput = document.querySelector('#signupUsername');
       const emailInput = document.querySelector('#signupEmail');
       const nicknameInput = document.querySelector('#signupNickname');
       
       const nameokDiv = document.querySelector('#nameok');
       const emailokDiv = document.querySelector('#emailok');
       const nickokDiv = document.querySelector('#nickok');
       const namenokIdv = document.querySelector('#namenok');
       const emailnokDiv = document.querySelector('#emailnok');
       const nicknokDiv = document.querySelector('#nicknok');
       
       const btnSubmit = document.querySelector('.btnSubmit');
       
        let nameok = false;
        let emailok = false;
        let nickok = false;
    
       usernameInput.addEventListener('change', function() {
            
            const username = usernameInput.value;
            axios
            .get('/user/checkid?username=' + username) // GET Ajax 요청 보냄.
            .then(response => { displayNameCheckResult(response.data) }) // 성공(HTTP 200) 응답 콜백
            .catch(err => { console.log(err); }); // 실패 응답 콜백
            
            
        });
       
       function displayNameCheckResult(data) {
            if (data == 'nameok') { // 사용할 수 있는 아이디
                nameokDiv.className = 'my-2';
                namenokDiv.className = 'my-2 d-none';
                nameok = true;
            } else {
                nameokDiv.className = 'my-2 d-none';
                namenokDiv.className = 'my-2';
            }
        }
   
       
       nicknameInput.addEventListener('change', function() {
          
          const nickname = nicknameInput.value;
            axios
            .get('/user/checknick?nickname=' + nickname) // GET Ajax 요청 보냄.
            .then(response => { displayNickCheckResult(response.data) }) // 성공(HTTP 200) 응답 콜백
            .catch(err => { console.log(err); }); // 실패 응답 콜백
          
           
            
       });
       
        function displayNickCheckResult(data) {
             if (data == 'nickok') { // 사용할 수 있는 아이디
                 nickokDiv.className = 'my-2';
                 nicknokDiv.className = 'my-2 d-none';
                 nickok = true;
             } else {
                 nickokDiv.className = 'my-2 d-none';
                 nicknokDiv.className = 'my-2';
             }
         }
       
       emailInput.addEventListener('change', function () {
          
          const email = emailInput.value;
            axios
            .get('/user/checkemail?email=' + email) // GET Ajax 요청 보냄.
            .then(response => { displayEmailCheckResult(response.data) }) // 성공(HTTP 200) 응답 콜백
            .catch(err => { console.log(err); }); // 실패 응답 콜백
            
            
       });
       
       function displayEmailCheckResult(data) {
            if (data == 'emailok') { // 사용할 수 있는 아이디
                emailokDiv.className = 'my-2';
                emailnokDiv.className = 'my-2 d-none';
                emailok = true;
            } else {
                emailokDiv.className = 'my-2 d-none';
                emailnokDiv.className = 'my-2';
            }
        }

        document.getElementById('signupaddress').addEventListener('change', function () {
          
            if(nameok == emailok == nickok == true){
                document.getElementById('registerConfirmBtn').className.remove('disabled');
                console.log('true 지렁');
            }else{
                console.log('false 지렁');
            }
            
            
       });
       
    });
    
    function displayBtnSubmit(data) {
        if (nameok == nickok == emailok == true) {
            btnSubmit.className.remove('disabled');
        } else {
            return;
        }
    }
    
    function alertNewUser() {
       alert("BOOQUE의 가족이 되신걸 환영합니다!")
    };
       
       
       
       
       
       
       
       
 
 
 
 
 
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




