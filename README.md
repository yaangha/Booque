# 📚Booque ver1

## 개요
**일정** 2022년 11월 21일 ~ 2022년 12월 23일<br>
**인원** 6인 팀 프로젝트

## 사용 기술 및 개발환경
+ Java
+ Spring Boot
+ HTML
+ CSS
+ JavaScript

## 구현 기능
1. 작가의 다른 책

detail.html 일부

```html
<span class="h4" th:text="${ book.author }"></span>
<span> 작가의 다른 책</span>

<div class="w-100 mb-5 border-bottom rounded">
    <div class="row container">
        <div class="col" th:each="book : ${ authorOtherBook }">
            <div class="card my-2" style="width: 15rem; height:32rem">
                <a th:href="@{ /detail(id=${ book.bookId }) }" th:bookId="${book.bookId}" th:username="${#authentication.name}" onclick="viewHitUp(this.getAttribute('bookId'),this.getAttribute('username'));">
                    <img th:src="${ book.bookImage }" class="card-img-top" style="height:22rem">
                </a>
                <div class="card-body">
                    <div class="card-text">
                        <div><small class="d-inline-flex px-2 my-1 border rounded text-secondary">
                             <span th:text="${ book.bookgroup }"></span><span> / </span><span th:text="${ book.category }"></span>
                             </small>
                        </div>
                        <small>
                        <span style="font-weight: bold;" th:text="${ book.bookName }"></span><br/>
                        <span th:text="${ book.author }"></span><br/>
                        <span th:text="|${#numbers.formatInteger(book.prices, 0, 'COMMA')}원|"></span>
                        </small>
                    </div>
                </div> <!-- card body end -->
            </div> <!-- card end -->
        </div> <!-- col end -->
    </div> <!-- row end -->
</div>
```

BookDetailController.java 일부

```java
List<Book> authorOtherBook = bookService.readAuthor(book.getAuthor());
model.addAttribute("authorOtherBook", authorOtherBook);
```

BookService.java 일부

```java
public List<Book> readAuthor(String author) {
  List<Book> authorOtherBook = bookRepository.findAllByAuthor(author);
  return authorOtherBook;
}
```

3. 장바구니 이동 유무

CartController.java 

```java
// detail 페이지에서 cart로 넘어갈 때 사용
@PostMapping("/cart/add")
public String addCart(CartAddDto dto, @AuthenticationPrincipal UserSecurityDto userSecurityDto) {
    Integer userId = userSecurityDto.getId();

    if (cartService.checkUser(userId, dto.getId()) == 1) { // 사용자 없으면 create
        cartService.addCart(userId, dto.getId(), dto.getCount());
    } else { // 사용자 있으면 update
        Integer afterCount = cartService.updateCount(userId, dto.getId(), dto.getCount());
        log.info("변경 수량={}", afterCount);
    }

    return "redirect:/cart?id=" + userId;
}

// 장바구니에 넣고 쇼핑 계속하기 버튼 눌렀을 때 사용
@PostMapping("/cart/onlyAdd")
public String onlyAddCart(CartAddDto dto, @AuthenticationPrincipal UserSecurityDto userSecurityDto) {
    Integer userId = userSecurityDto.getId();

    if (cartService.checkUser(userId, dto.getId()) == 1) { // 사용자 없으면 create
        cartService.addCart(userId, dto.getId(), dto.getCount());
    } else { // 사용자 있으면 update
        Integer afterCount = cartService.updateCount(userId, dto.getId(), dto.getCount());
        log.info("변경 수량={}", afterCount);
    } 

    return "redirect:/detail?id=" + dto.getId();
}  
```

CartService.java 일부

```java
// 장바구니 유저 유무 확인
public Integer checkUser(Integer userId, Integer bookId) {
    Cart cart = new Cart();
    cart = cartRepository.findByUserIdAndBookBookId(userId, bookId);

    if (cart != null) { // 사용자가 있으면 0을 리턴
        return 0;
    } else { 
        return 1; // 사용자가 없으면 1을 리턴
    }
}

// 한 유저가 해당 책을 이미 장바구니에 넣었을 때 수량 변경하기
public Integer updateCount(Integer userId, Integer bookId, Integer count) {
    Cart cart = cartRepository.findByUserIdAndBookBookId(userId, bookId);
    Integer afterCount = count + cart.getCartBookCount();
    cart.update(afterCount);  
    cartRepository.save(cart);
    
    return afterCount;
}
```

5. 결제 

OrderController.java 일부

```java
// 카트에서 결제하기 버튼 눌렀을 때 사용
@PostMapping("/order")
public String order(Integer[] cartId, Model model) { 
    Long orderNo = orderService.create(cartId);

    List<OrderFromCartDto> order = new ArrayList<>();

    Cart cartFindUser = cartService.read(cartId[0]);
    User user = userService.read(cartFindUser.getUser().getId());
    Integer total = 0;

    for (Integer i : cartId) {
        Cart cart =  cartService.read(i);
        User userForId = userService.read(cart.getUser().getId());
        Book book = bookService.read(cart.getBook().getBookId());

        OrderFromCartDto dto = OrderFromCartDto.builder().userId(userForId.getId()).id(book.getBookId()).cartId(cart.getCartId())
                .prices(book.getPrices()).count(cart.getCartBookCount()).bookName(book.getBookName()).publisher(book.getPublisher())
                .bookImage(book.getBookImage()).author(book.getAuthor()).category(book.getCategory()).bookgroup(book.getBookgroup())
                .build();

        order.add(dto);

        // total(총 상품금액)
        total += book.getPrices() * cart.getCartBookCount();
    }

    model.addAttribute("order", order);
    model.addAttribute("user", user);
    model.addAttribute("orderNo", orderNo);
    model.addAttribute("total", total);

     return "book/order";
}

// 디테일창에서 바로 구매하기 버튼 눌러서 한 권만 구매할 때 사용
@PostMapping("/orderFromDetail")
public String orderNow(@AuthenticationPrincipal UserSecurityDto userSecurityDto, OrderFromDetailDto dto, Model model) {
   Integer userId =  userSecurityDto.getId();
   Long orderNo = orderService.createFromDetail(userId, dto);
   List<OrderFromCartDto> order = orderService.readByOrderNo(orderNo);
   User user = userService.read(order.get(0).getUserId());
   Integer total = order.get(0).getCount() * order.get(0).getPrices();

   model.addAttribute("order", order);
   model.addAttribute("user", user);
   model.addAttribute("orderNo", orderNo);
   model.addAttribute("total", total);

   return "book/order";
}
```

OrderService.java 일부

```java
// 디테일 페이지에서 바로 구매하는 페이지로 넘어할 때 사용
public Long createFromDetail(Integer userId, OrderFromDetailDto dto) {
    Integer total = dto.getCount() * dto.getPrice(); // 수량 X 가격
    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmSS")); // ex) 20221209
    Long orderNo = Long.parseLong(date + userId);

    User user = userRepository.findById(userId).get();
    Book book = bookRepository.findById(dto.getId()).get();

    Order order = Order.builder().orderNo(orderNo).user(user).book(book)
            .orderDate(LocalDateTime.now()).orderBookCount(dto.getCount()).total(total).build();

    Order orderResult = orderRepository.save(order);

    return orderResult.getOrderNo();
}
```


## 구성 화면
### 메인 페이지
### 판매글 상세 페이지
### 마이페이지
