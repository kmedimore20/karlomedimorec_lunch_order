package com.example.lunchorder_v2;

import com.example.lunchorder_v2.controller.MenuController;
import com.example.lunchorder_v2.controller.OrderController;
import com.example.lunchorder_v2.model.Menu;
import com.example.lunchorder_v2.model.Order;
import com.example.lunchorder_v2.repository.MenuRepository;
import com.example.lunchorder_v2.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LunchOrderV2ApplicationTests {

    @InjectMocks
    private MenuController menuController;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUpMenu(){
        //Setting up menu
        Menu menu = new Menu();

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);

        Mockito.when(menuRepository.findAll()).thenReturn(menuList);
    }

    @Test
    public void testGetMenu(){
        ResponseEntity<List<Menu>> responseEntity = menuController.getMenu();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateOrder(){
        Order inputOrder = new Order("2x1; 3x4; 5x6");

        ResponseEntity<Order> responseEntity = orderController.createOrder(inputOrder);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateOrder(){
        Order updatedOrder = new Order("2x1; 3x4;");

        long orderId = 1;
        Order existingOrder = new Order("2x1; 3x4; 5x6");
        Optional<Order> optionalOrder = Optional.of(existingOrder);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(optionalOrder);
        Mockito.when(orderRepository.save(existingOrder)).thenReturn(updatedOrder);

        ResponseEntity<Order> responseEntity = orderController.updateOrder(orderId, updatedOrder);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateOrderNotFound(){

        long orderId = 1;
        Optional<Order> optionalOrder = Optional.empty();
        Mockito.when(orderRepository.findById(orderId)).thenReturn(optionalOrder);

        Order updatedOrder = new Order("2x1; 3x4; 5x6");

        ResponseEntity<Order> responseEntity = orderController.updateOrder(orderId, updatedOrder);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
