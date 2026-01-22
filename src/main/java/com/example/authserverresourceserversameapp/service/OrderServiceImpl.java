package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.*;
import com.example.authserverresourceserversameapp.repository.CartRepository;
import com.example.authserverresourceserversameapp.repository.ItemRepository;
import com.example.authserverresourceserversameapp.repository.OrderRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;


    public OrderServiceImpl(ProductRepository productRepository,
                            ItemRepository itemRepository,
                            CartRepository cartRepository,
                            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
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
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
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
    public Cart getCartByUser(User user) {
        return cartRepository.getByUser(user);
    }

    @Override
    public List<Order> getOrders(User user) {
        return orderRepository.getAllByUser(user);
    }

    @Override
    public List<Order> addOrder(OrderDto dto, User user) {
        Cart cart = cartRepository.getByUser(user);
        Order order = new Order();
        user.addOrder(order);
        List<Item> items = new ArrayList<>(cart.getItems());
        for (Item item : items) {
            cart.removeItem(item);
            order.addItem(item);
        }
        order.setName(dto.getName());
        orderRepository.save(order);
        return orderRepository.getAllByUser(user);
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


    @Override
    public long deleteOrder(long itemId) {
        Item item = itemRepository.findById(itemId).get();
        Order order = item.getOrder();
        order.removeItem(item);
        itemRepository.delete(item);
        if (order.getItems().isEmpty()) {
            orderRepository.delete(order);
        }
        return itemId;
    }
}
