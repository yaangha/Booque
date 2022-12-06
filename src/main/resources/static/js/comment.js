/**
 * 
 */
 
 window.addEventListener('DOMContentLoaded', () => {
    
    //readAllComments();
    // 사용할 버튼, bookId, commentWriter, commentText, btnComment
    
    const btnComment = document.querySelector('#btnComment');
    btnComment.addEventListener('click', newComment);
    
    // comment 작성 함수
    
    function newComment (){
        const bookId = document.querySelector('#id').value;
        const writer = document.querySelector('#commentWriter').value;
        const commentText = document.querySelector('#commentText').value;
        
        if(writer=='' || commentText==''){
            alert('한줄 평을 입력해 주세여.')
            return;
        }
        
        axios.post(/api/comment, data)
            .then(response => {
                
                
            })


        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
})
 
 
 
 
 