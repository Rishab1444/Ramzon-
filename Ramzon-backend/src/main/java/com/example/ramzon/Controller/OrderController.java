package com.example.ramzon.Controller;

import com.example.ramzon.Exception.OrderException;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.Address;
import com.example.ramzon.Model.Order;
import com.example.ramzon.Model.User;
import com.example.ramzon.Service.OrderService;
import com.example.ramzon.Service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Order> createOrderHandler(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findByJwt(jwt);
        Order newOrder = orderService.createOrder(user,shippingAddress);

        return new ResponseEntity<Order>(newOrder, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                        String jwt) throws OrderException, UserException{

        User user=userService.findByJwt(jwt);
        List<Order> orders=orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException,UserException {
        User user = userService.findByJwt(jwt);
        Order order =orderService.findOrderById(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }


}
