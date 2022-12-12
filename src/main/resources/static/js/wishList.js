/**
 * (지혜) 위시리스트 버튼 클릭시 담기/취소
 */
 
 window.addEventListener('DOMContentLoaded', () => {
 
 // 하트(위시리스트) 버튼 찾기, 이벤트 리스너 등록
 const btnWish = document.querySelector('#btnWish');
    btnWish.addEventListener('click', clickBtnWish);
    
    function clickBtnWish () {
        
        // 현재 로그인되어 있는 유저 아이디 찾기
        // let userId = document.querySelector('#userId').value;
        // 찜(위시리스트) 누른 책 id 찾기
        // let bookId = document.querySelector('$id').value;
        // 상태 반영할 찜(위시리스트) 아이콘(=버튼) 찾기
        let btnWish = document.querySelector('#btnWish');
        
        // if (userId == undefined) {
            // alret("로그인 후 가능한 서비스입니다.");
            // 로그인 모달 열기?
            // return false;
        // }
        
        $.axios ( {
            url: "/book/wishList",
            type: "Get",
            data: {
                // userId: userId, 
                // bookId:bookId
                },
            dataType: "text",
            
            success: function(wishSelectedOrNot) {
                let html = '';
                html += '<span id="btnWish">';
                
                if (wishSelectedOrNot == "selected") {
                    html += 
                '<img id="wishFull" style="width:25px;" alt="" src=" /images/likeFull.png " />';
                } else {
                    html +=
                '<img id="wishEmpty" style="width:25px;" alt="" src=" /images/likeEmpty.png " />';
                }
                
                html += '</span>';
                
                btnWish.innerHTML = html;
                
            },
            
            error: function(err) {
                alert("실패: " + err.responseText);
            }
            
        })
        
        
     }
    
    });