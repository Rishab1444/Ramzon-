package com.example.ramzon.Controller;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.User;
import com.example.ramzon.Request.AddItemReq;
import com.example.ramzon.Response.ApiResponse;
import com.example.ramzon.Service.CartService;
import com.example.ramzon.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
public class CartController {
    private CartService cartService;
    private UserService userService;

    public CartController(CartService cartService,UserService userService) {
        this.cartService=cartService;
        this.userService=userService;
    }

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        User user=userService.findByJwt(jwt);

        Cart cart=cartService.findUserCart(user.getId());

        System.out.println("cart - "+cart.getUser().getEmail());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemReq req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        User user=userService.findByJwt(jwt);

        cartService.addCartItem(user.getId(), req);

        ApiResponse res= new ApiResponse("Item Added To Cart Successfully",true);

        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);

    }
}
