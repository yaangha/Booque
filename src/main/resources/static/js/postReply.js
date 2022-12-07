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
        // const postId = document.querySelector('#id').value;

        // 댓글 작성자
        const replyWriter = document.querySelector('#replyWriter').value;

        // 댓글 내용
        const replyContent = document.querySelector('#replyContent').value;

        // 서버로 보낼 데이터
        const data = {
            postId: 13,
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
        // const postId = document.querySelector('#id').value;

        axios
        .get('/api/reply/all/13')  // TODO: postId test용으로 13을 넣음.-> postId변수로 교체
        .then(response => { updateReplyList(response.data) } )
        .catch(err => { console.log(err) });
    }    
    function updateReplyList(data){
        const divReplies = document.querySelector('#replies');
        let str = '';
        for (let r of data){
            str += '<div class="d-flex mb-4">'
                + '<div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>'
                + '<div class="ms-3">'
                + '<div class="fw-bold">' + r.replyWriter + '</div>'
                + '<p>' + r.replyContent + '</p>'
                + '<p> 작성시간: ' + r.createdTime + '</p>'
                + '<p> 수정시간: ' + r.modifiedTime + '</p>'
                + '<div class="card-footer">'
                + `<button type="button" class="btnModifies btn btn-outline-primary" data-rid="${r.replyId}">수정</button>`
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
    const modalReplyWriter = document.querySelector('#modalReplyWriter'); // 댓글 아이디
    const modalReplyContent = document.querySelector('#modalReplyContent'); // 댓글 내용
    const modalBtnDelete = document.querySelector('#modalBtnDelete'); // 댓글 삭제 버튼
    const modalBtnUpdate = document.querySelector('#modalBtnUpdate'); // 댓글 수정완료 버튼
   
    // 댓글 수정/삭제 모달 보여주는 함수
    function showReplyModal(reply) {
        // 수정,
        modalReplyWriter.value = reply.replyWriter;
        modalReplyContent.value = reply.ReplyContent;
       
        postReplyModal.show(); // 모달을 화면에 보여주기
    }
   
    modalBtnDelete.addEventListener('click', deleteReply);
    modalBtnUpdate.addEventListener('click', updateReply);
   
    // 댓글 삭제 함수
    function deleteReply(event) {
        const replyWriter = modalReplyWriter.value;
        const result = confirm('정말 삭제하시겠습니까?');
        if (result) {
            axios
            .delete('/api/reply/' + replyWriter)
            .then(response => {
                alert(`#${response.data} 댓글 삭제 성공`);
                readAllReplies();
             })
            .catch(err => { console.log(err) })
            .then(function () {
                postReplyModal.hide();
            });
        }
    }
   
    // 댓글 수정 함수
    function updateReply(event) {
        const replyWriter = modalReplyWriter.value;
        const replyContent = modalReplyContent.value;
        if (replyContent == '') {
            alert('댓글 내용은 반드시 입력');
            return;
        }
       
        const result = confirm('정말 수정하시겠습니까?');
        if (result) {
            const data = { replyContent: replyContent };
            axios
            .put('/api/reply/' + replyWriter, data)
            .then(response => {
                alert('#' + response.data + ' 댓글 수정 성공');
                readAllReplies();
             })
            .catch(err => { console.log(err) })
            .then(function () {
                postReplyModal.hide();
            });
        }
    }
});