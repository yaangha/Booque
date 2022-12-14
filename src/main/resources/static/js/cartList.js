/**
 * 
 */
 
  window.addEventListener('DOMContentLoaded', () => {
   readCartDesc();

  
    
    

    
   function readCartDesc(){
        const userid = document.querySelector('#userId').value;
        
        axios.get('api/cart/all/' + userid) 
        .then(response => { updateCartList(response.data)
        
        })
        .catch(err => {console.log(err) });
        
        console.log('전체 페이지 읽기 ajax 함수')
        
    };
    
    
    
    function updateCartList(data){
        const divCart = document.querySelector('#cList')
        let str = '';
        
        str += ' <form id="formCheck" mehtod="post">' 
        +'<table class="w-100 table" style="text-align: center;"> '
        + '<thead class="table-light"> '
         +  '  <tr> '
          +      ' <th colspan="2">도서 정보</th> '
           +   '   <th>수량</th> '
            +  '   <th>주문금액</th> '
            +   '  <th>배송비</th> '
            +  '  <th></th> '
          + '  </tr> '
      + '  </thead> '
     + '   <tbody style="height: 200px;"> ';
        
        
        for(let c of data){
        
      str  += '<tr>'
            +  '<td class="align-middle">' 
            +   ' <input type="checkbox"  id="ckBox" style="width: 30px;"  name="cartId" checked value="'+ c.cartId +'"/>' 
            +   ' <img src="' + c.image +'" style="width: 150px;"/></td>' 
            +   ' <td class="align-middle" style="text-align: left;">' 
            +              '  <small class="d-inline-flex px-2 my-1 border rounded text-secondary">' 
            +                 '   <span>'+c.group+'</span><span> / </span><span>'+c.category+'</span>' 
            +              '  </small>' 
            +              '  <div class="h5">'+c.title+'</div>' 
            +             '   <div >'+c.author+'</div>' 
            +              '  <div>'+c.prices 
            +                '    <span>원</span>' 
            +                 '   <small class="px-1 border border-primary rounded text-primary">p</small>' 
            +                  '  <small class="text-primary">'+c.prices*0.05+'</small> ' 
            +               ' </div>' 
            +  '  </td>' 

            +  '  <td class="align-middle">' 
            +  '  <div class="selectPrice " >' 
            +        '<span class="prices"  >'+c.prices*c.count +'</span>' + '<span>원</span>'
            +  '<input type="hidden" id="fixedPrice" value="'+c.prices+'"/>' 
            +   ' </div>' 
            +      '  <input type="button" class="btnPlusMinus"  value="+"/>' 
            +     '   <span style="width: 50px" class="count" >'+c.count +' </span>' 
            +       ' <input type="button" class="btnPlusMinus"  value="-"/>' 
    
            +  '  </td>' 
            +  '  <td class="align-middle">10,000원 이상<br/>' 
            +   '     배송비 무료</td>' 
            +  '  <td class="align-middle">' 
            +      '  <button type="button" class="btn btn-dark btn-sm my-2" style="width: 100px;">Buy Now</button><br/>' 
            +     '   <button type="button" class="btn btn-danger btn-sm my-2" style="width: 100px;">Delete</button>' 
            +  '  </td>'
            + '</tr>'; 
        }
        
       str 
            += '</tbody>'
            + '</table>'
            +   ' </form>' ;
            
        divCart.innerHTML = str;
        
        const buttons = document.querySelectorAll('.btnPlusMinus');
        
        buttons.forEach(btn => {
            
            btn.addEventListener('click', e => {
                const td = btn.closest('tr');
                console.log(td)
                const span = td.querySelector('span.count');
                const pri = td.querySelector('span.prices'); // 변경될 값
                const fix = td.querySelector('input#fixedPrice').value;
                const chk = td.querySelector('#ckBox');
                console.log(chk)
                
                let number = span.innerText;
                
                const type = btn.value;
                if (type == '+') {
                    number = parseInt(number) + 1;
                    
                } else {
                    number = parseInt(number) - 1;
                    if(number == 0){
                       alert('수량은 0이하가 되지 못합니다.')
                       pri.innerText = fix;
                       return;
                   }
                }
                span.innerText = number;
                pri.innerText = number * parseInt(fix);
                    
                })
                
                
                
                
                
                
            });

    
        const form = document.querySelector('#formCheck')
            // 결제창으로 넘어감.
        const btnOrder = document.querySelector('#btnOrder')
        btnOrder.addEventListener('click', function() {
            form.action = '/order';
            form.method = 'post';
            form.submit();

            console.log('누름')
         
        });
    
    // 전체 결제금액은 선택된 곳에서 정가 곱하기 수량
    const total = document.querySelector('#total');
    let totalPrice = total.innerText;
    
    const list = document.querySelectorAll('#ckBox');
    for(let i=0; i<list.length; i++ ){
        
        list[i].addEventListener('change', function(){
              
            const tr = list[i].closest('tr')
            const bookPrice = tr.querySelector('span.prices')
            
            if(list[i].checked){  // 체크가 된 상황에서 값이 바뀌면?
                totalPrice = parseInt(totalPrice) + parseInt(bookPrice.innerText)
            } else{
                totalPrice = parseInt(totalPrice) - parseInt(bookPrice.innerText)
            }
            total.innerText = totalPrice
                
            })
            
        }
    
    
    
    
    } // function updatCartList end --
    

    
    
    
    
    
    const btnDelete = document.querySelector('#btnDelete')
    btnDelete.addEventListener('click', function(){
    const userId = document.querySelector('#userId').value;
    console.log(userId)
    
    const list = document.querySelectorAll('#ckBox');
    let ckList = [];
    
        
        for(let i=0; i<list.length; i++ ){
            if(list[i].checked){
                let a = list[i].value;
                ckList.push(a);
            }
            
        }
        ckList.push(userId)
        
        
        const result = confirm('장바구니를 삭제?')
        
        if(result){
            axios
            .post('api/cartid', ckList)
            .then(response => {
                updateCartList(response.data)
                console.log(response.data);
            })
            .catch(err => {console.log(err)})
        }
        
        }) 
        
        
        
        
        

        

    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
 })







