package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.CartItem;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.OrderService;
import com.example.authserverresourceserversameapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public List<CartItem> getCart(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getCartItems(user);
    }

    @PostMapping
    public CartItemDto addToCart(@RequestBody CartItemDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addToCart(dto, user);
    }

}
