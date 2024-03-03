package com.example.ramzon.Service;

import com.example.ramzon.Exception.OrderException;
import com.example.ramzon.Model.*;
import com.example.ramzon.RamEnums.OrderStatus;
import com.example.ramzon.RamEnums.PaymentStatus;
import com.example.ramzon.Repository.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService{

    private CartService cartService;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;
    private AddressRepository addressRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(CartService cartService, OrderItemService orderItemService, OrderItemRepository orderItemRepository, AddressRepository addressRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address =addressRepository.save(shippingAddress);
        user.getAddresList().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = orderItemService.createOrderItem(cart,new ArrayList<>());

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscount(cart.getTotalDiscountPrice());
        createdOrder.setDiscounte(cart.getDiscounte());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetail().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(createdOrder);

        for (OrderItem orderItem : orderItems){
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw  new OrderException("Order not found");
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {

        return orderRepository.findUserOrderHistory(userId);
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetail().setStatus(PaymentStatus.COMPLETED);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return order;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order =findOrderById(orderId);

        orderRepository.deleteById(orderId);
    }
}
