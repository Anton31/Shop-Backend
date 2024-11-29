package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.*;
import com.example.authserverresourceserversameapp.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(ProductRepository productRepository,
                            ItemRepository itemRepository,
                            CartRepository cartRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    /**
     * adds new item to cart
     *
     * @param dto  dto for adding new item or editing existing item
     * @param user current user
     * @return cart with added new item
     */
    @Override
    public Cart addItem(ItemDto dto, User user) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Cart cart = cartRepository.getByUser(user);
        Item item = new Item();
        cart.addItem(item);
        product.addItem(item);
        item.setQuantity(1);
        return cartRepository.save(cart);
    }

    /**
     * edits existing item in cart
     *
     * @param dto dto for adding new item or editing existing item
     * @return cart with edited new item
     */
    @Override
    public Cart editItem(ItemDto dto) {
        Item item = itemRepository.findById(dto.getItemId()).get();
        Cart cart = item.getCart();
        if (item.getQuantity() == 1 && dto.getQuantity() == -1) {
            item.setQuantity(1);
        } else {
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        }
        return cartRepository.save(cart);
    }

    /**
     * returns cart created by current user
     *
     * @param user current user
     * @return cart created by current user
     */
    @Override
    public Cart getUserCart(User user) {
        return cartRepository.getByUser(user);
    }

    @Override
    public List<Order> getOrders(User user) {
        return orderRepository.getByUser(user);
    }

    @Override
    public Order addOrder(OrderDto dto, User user) {
        Cart cart = cartRepository.getByUser(user);
        Order order = new Order();
        List<Item> items = new ArrayList<>(cart.getItems());
        for (Item item : items) {
            cart.removeItem(item);
            order.addItem(item);
        }
        user.addOrder(order);
        order.setDescription(dto.getDescription());
        order.setUsername(dto.getUsername());
        order.setEmail(dto.getEmail());
        return orderRepository.save(order);
    }

    /**
     * deletes item from cart
     *
     * @param itemId id of item to delete
     * @return id of deleted item
     */
    @Override
    public long deleteItem(long itemId) {
        Item item = itemRepository.findById(itemId).get();
        Cart cart = item.getCart();
        cart.removeItem(item);
        itemRepository.delete(item);
        return itemId;
    }
}
