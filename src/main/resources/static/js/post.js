/**
 * 
 */
 window.addEventListener('DOMContentLoaded', () => {
    // (하은) 
    
    readPost();
    
    function readPost() {
        
        const userId = document.querySelector('#userId').value;
        const postId = document.querySelector('#postId').value;
        const postWriter = document.querySelector('#postWriter').value;
        
        const data = {
            userId: userId,
            postId: postId,
            postWriter: postWriter  
        };
     
        axios
        .get('/api/post/content' + postId)
        .then(response => { postContentShow(response.data) })
        .catch(err => { console.log(err) })
    };
    
    function postContentShow(data) {
        const divPost = document.querySelector('#postContent');
        
        let str = '';
        
        for (let r of data) {
            str += '<div class="w-100 px-3 py-3 border" style="height:500px;">'
                + '<div>' + r.postContent + '</div>'
                + '</div>';
        }
        divPost.innerHTML = str;
        
    };
    
});