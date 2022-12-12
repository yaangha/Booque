/**
 * 
 */
 
 window.addEventListener('DOMContentLoaded', () => {
 
    const userId = document.querySelector('#userId').value;
    console.log(userId)
    
    const btnDelete = document.querySelector('#btnDelete')
  //  const formCheck = document.querySelector('#formCheck')
    const list = document.querySelectorAll('#ckBox');
    let ckList = [];
    
    btnDelete.addEventListener('click', function() {
        for(let i=0; i<list.length; i++ ){
            if(list[i].checked){
                let a = list[i].value;
                ckList.push(a);
            }
            
        }
        ckList.push(userId)
        console.log(ckList.toString())
        console.log(ckList.length)
        
   //     const cartIdList = document.querySelector('#cartIdList');
   //     cartIdList.innerText = ckList.toString();
   //     console.log(cartIdList.innerText)
        
        const result = confirm('장바구니를 삭제?')
        
        if(result){
            axios
            .post('api/cartid', ckList)
            .then(response => {
                console.log(updateCartList(response.data));
            })
            .catch(err => {console.log(err)})
        }
        
    })   
    
    
    
    function updateCartList(data){
        const divCart = document.querySelector('#cList')
        let str = '';
        
        for(let c of data){
        str +=  '<tr>'
            +  '<td class="align-middle">' 
            +   ' <form id="formCheck">' 
            +   ' <input type="checkbox"  id="ckBox" style="width: 30px;"  name="cartId"' +  c.cartId +' />' 
            +   ' </form>' 
            
            +   ' <img src=" ' + th:src="${ cart.image }" +' style="width: 150px;"/></td>' 
            +   ' <td class="align-middle" style="text-align: left;"><!-- 도서 간략 정보 -->' 
            +              '  <small class="d-inline-flex px-2 my-1 border rounded text-secondary">' 
            +                 '   <span  th:text="${ cart.group }"></span><span> / </span><span th:text="${ cart.category }"></span>' 
            +              '  </small>' 
            +              '  <div class="h5" th:text="${ cart.title }"></div>' 
            +             '   <div th:text="${ cart.author }"></div>' 
            +              '  <div> [[${ cart.prices }]]' 
            +                '    <span>원</span>' 
                                
            +                 '   <small class="px-1 border border-primary rounded text-primary">p</small>' 
            +                  '  <small class="text-primary">[[${ cart.prices*0.05 }]]</small> ' 
            +               ' </div>' 
            +  '  </td>' 
            +  '  <div>' 
            +  '  <td class="align-middle">' 
            +      '  <input type="button" class="btnPlusMinus"  value='+'/>' 
            +     '   <span style="width: 50px" id="countS" > [[${ cart.count }]] </span>' 
            +       ' <input type="button" class="btnPlusMinus"  value='-'/>' 
    
            +  '  </td>' 
            +   ' <td class="align-middle">' 
            +  '  <div class="selectPrice">' 
            +      '  <div id="price">' 
            +         '   [[${ cart.prices }]]<span>원</span>' 
            +     '   </div>' 
            +   ' </div>' 
            +  '  </td>' 
            +  '  <td class="align-middle">10,000원 이상<br/>' 
            +   '     배송비 무료</td>' 
            +  '  <td class="align-middle">' 
            +      '  <button type="button" class="btn btn-dark btn-sm my-2" style="width: 100px;">Buy Now</button><br/>' 
            +     '   <button type="button" class="btn btn-danger btn-sm my-2" style="width: 100px;">Delete</button>' 
            +  '  </td>' 
        + '</tr>' ;
            
            
        }
        
        divCart.innerHTML = str;
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
})