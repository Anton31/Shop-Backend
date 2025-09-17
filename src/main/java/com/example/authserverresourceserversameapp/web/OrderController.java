package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Order;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.OrderService;
import com.example.authserverresourceserversameapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public Cart getCart(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getCartByUser(user);
    }

    @PostMapping
    public Cart addItem(@RequestBody ItemDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addItem(dto, user);
    }

    @PutMapping
    public Cart editItem(@RequestBody ItemDto dto) {
        return orderService.editItem(dto);
    }

    @DeleteMapping("/{itemId}")
    public long deleteItem(@PathVariable long itemId) {
        return orderService.deleteItem(itemId);
    }

    @GetMapping("/order")
    public List<Order> getOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getOrders(user);
    }

    @PostMapping("/order")
    public Order addOrder(OrderDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addOrder(dto, user);
    }

    @DeleteMapping("/order/{orderId}")
    public long deleteOrder(@PathVariable long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
