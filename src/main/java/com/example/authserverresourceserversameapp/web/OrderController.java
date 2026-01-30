package com.example.authserverresourceserversameapp.web;

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
        User user;
        if (principal == null) {
            user = userService.findByUsername("Anton");
        } else {
            user = userService.findByUsername(principal.getName());
        }
        return orderService.getCartByUser(user);
    }

    @GetMapping("/add/{id}")
    public Cart addItem(@PathVariable long id, Principal principal) {
        User user;
        if (principal == null) {
            user = userService.findByUsername("Anton");
        } else {
            user = userService.findByUsername(principal.getName());
        }
        return orderService.addItem(id, user);
    }

    @GetMapping("/edit/{itemId}/{plus}")
    public Cart editItem(@PathVariable long itemId, @PathVariable long plus) {
        return orderService.editItem(itemId, plus);
    }

    @GetMapping("/delete/{itemId}")
    public long deleteItem(@PathVariable long itemId) {
        return orderService.deleteItem(itemId);
    }

    @GetMapping("/order")
    public List<Order> getOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getOrders(user);
    }

    @PostMapping("/order")
    public List<Order> addOrder(@RequestBody OrderDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addOrder(dto, user);
    }

    @DeleteMapping("/order/{itemId}")
    public long deleteOrder(@PathVariable long itemId) {
        return orderService.deleteOrder(itemId);
    }
}
