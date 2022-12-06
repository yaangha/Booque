/**
 * 포스트 댓글 작성
 */
 
 window.addEventListener('DOMContentLoaded', () => {
    // 댓글 목록
    readAllReplies();
    
    // 확인 버튼
    const btnReplyRegister = document.querySelector('#btnReplyRegister');
    btnReplyRegister.addEventListener('click', registerNewReply)
    
    // 댓글 작성 함수
    function registerNewReply() {
        // 포스트 글
        const postId = document.querySelector('#id').value;
        
        // 댓글 작성자
        const replyWriter = document.querySelector('#replyWriter').value;
        
        // 댓글 내용
        const replyContent = document.querySelector('#replyContent').value;
        
        // 서버로 보낼 데이터
        const data = { 
            postId: postId,
            replyContent: replyContent,
            replyWriter: replyWriter        
        }; 
        
        axios.post('/api/reply', data)
                .then(response => {
                    console.log(response);
                    alert('#' + response.data + ' 댓글 등록 성공');
                    clearInputContent(); 
                    readAllReplies(); 
                })
                .catch(error => {
                    console.log(error);
                });
    }       
    
        
    // 댓글 목록 함수
    function readAllReplies(){
        const postId = document.querySelector('#id').value;
        
        axios
        .get('/api/reply/all/1')  // TODO: postId test용으로 1을 넣음.-> postId변수로 교체
        .then(response => { updateReplyList(response.data) } ) 
        .catch(err => { console.log(err) });
    }    
    function updateReplyList(data){
        const divReplies = document.querySelector('#replies');
        let str = '';
        for (let r of data){ 
            str += '<div class="card my-2">'
                + '<div class="card-header">'
                + '<h5> 작성자: ' + r.username + '</h5>'
                + '</div>'
                + '<div class="card-body">'
                + '<p>' + r.replyContent + '</p>'
                + '<p> 작성시간: ' + r.createdTime + '</p>'
                + '<p> 수정시간: ' + r.modifiedTime + '</p>'                
                + '</div>'
                + '</div>';
        }
        divReplies.innerHTML = str;
        
        // [수정] 버튼들이 HTML 요소로 만들어진 이후에, [수정] 버튼에 이벤트 리스너를 등록
        const buttons = document.querySelectorAll('.btnModifies');
        buttons.forEach(btn => {
            btn.addEventListener('click', getReply);
        });
    }
    
    // 작성 후 작성창 비워주는 함수
    function clearInputContent(){
        
    }
    
});