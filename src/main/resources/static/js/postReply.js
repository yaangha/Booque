/**
 * 포스트 댓글 작성
 */

 window.addEventListener('DOMContentLoaded', () => {
    // 댓글 목록
    readAllReplies();
    // 댓글 갯수
    updateReplyCount();
    
    // 확인 버튼
    const btnReplyRegister = document.querySelector('#btnReplyRegister');
    btnReplyRegister.addEventListener('click', registerNewReply);
    
    // 댓글 작성 함수
    function registerNewReply() {
        // 포스트 글
        const postId = document.querySelector('#postId').value;

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
                    alert('#  댓글 등록 성공');
                    clearInputContent();
                    readAllReplies();
                    updateReplyCount();
                })
                .catch(error => {
                    console.log(error);
                });
    }      


    // 댓글 목록 함수
    function readAllReplies(){
        const postId = document.querySelector('#postId').value;
        axios
        .get('/api/reply/all/' + postId)  
        .then(response => { updateReplyList(response.data) } )
        .catch(err => { console.log(err) });
    }    
    function updateReplyList(data){
        const divReplies = document.querySelector('#replies');
        let str = '';
        for (let r of data){
            str += '<div style="margin-left: 50px;" class="card my-2 mt-2>'
                + '<div class="card-header">'
                + '<div class="d-flex mb-4">'
                + '<div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>'
                + '<div class="ms-3">'
                + '<div class="fw-bold">' + r.replyWriter + '</div>'
                + '</div>'
                + '<div class="card-body">'
                + '<p>' + r.replyContent + '</p>'
                + '<p> 작성시간: ' + r.createdTime + '</p>'
                + '<p> 수정시간: ' + r.modifiedTime + '</p>'
                + '</div>'

                + '<div class="card-footer">'
                + `<button type="button" class="btnModifies btn btn-outline-primary" data-rid="${r.replyId}">수정</button>`
                + '</div>'
                + '</div>'
                + '</div>'
                + '</div>';
        }
        divReplies.innerHTML = str;
        
        // [수정] 버튼에 이벤트 리스너를 등록
        const buttons = document.querySelectorAll('.btnModifies');
        buttons.forEach(btn => {
            btn.addEventListener('click', getReply);
        });
    }

    // 작성 후 작성창 비워주는 함수
    function clearInputContent(){
            document.querySelector('#replyContent').value = '';
    }
   
    // 몇 번 댓글을 수정할 것인지 정보 전달
    function getReply(event) {
        const rid = event.target.getAttribute('data-rid');
        axios
        .get('/api/reply/' + rid) 
        .then(response => { showReplyModal(response.data) })
        .catch(err => { console.log(err) });
    }
   
    const divModal = document.querySelector('#postRepModal');
    const postReplyModal = new bootstrap.Modal(divModal); // Modal 생성
    const modalReplyId = document.querySelector('#modalReplyId'); // 댓글 번호
    const modalReplyContent = document.querySelector('#modalReplyContent'); // 댓글 내용
    const modalBtnDelete = document.querySelector('#modalBtnDelete'); // 댓글 삭제 버튼
    const modalBtnUpdate = document.querySelector('#modalBtnUpdate'); // 댓글 수정완료 버튼
   
    // 댓글 수정/삭제 모달 보여주는 함수
    function showReplyModal(reply) {
        modalReplyId.value = reply.replyId;
        modalReplyContent.value = reply.replyContent;
        postReplyModal.show();
    }
   
    modalBtnDelete.addEventListener('click', deleteReply);
    modalBtnUpdate.addEventListener('click', updateReply);
   
    // 댓글 삭제 함수
    function deleteReply(event) {
        const replyId = modalReplyId.value;
        const result = confirm('정말 삭제하시겠습니까?');
        if (result) {
            axios
            .delete('/api/reply/' + replyId) 
            .then(response => {
                alert(`# 댓글 삭제 성공`);
                readAllReplies();
                updateReplyCount();
             })
            .catch(err => { console.log(err) }) 
            .then(function () {
                postReplyModal.hide(); 
            });
        }
    }
   
    // 댓글 수정 함수
    function updateReply(event) {
        const replyId = modalReplyId.value;
        const replyContent = modalReplyContent.value;

        if (replyContent == '') {
            alert('댓글 내용은 반드시 입력');
            return;
        }

        const result = confirm('정말 수정하시겠습니까?');
        if (result) {
            const data = { replyContent: replyContent };
            axios

            .put('/api/reply/' + replyId, data) 
            .then(response => {
                alert('# 댓글 수정 성공');
                readAllReplies();
                updateReplyCount();
             })
            .catch(err => { console.log(err) })
            .then(function () {
                postReplyModal.hide();
            });
        }
    }
    
    // 댓글 갯수 함수
    function updateReplyCount(){
        const postId = document.querySelector('#postId').value;
        const count = document.querySelector('#countSpan');
        axios.get('/api/reply/count/'+ postId)
            .then(response => {
                count.innerHTML = response.data;
            })
            .catch(err => {console.log(err)});
    }
});