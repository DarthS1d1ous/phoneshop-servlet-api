package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.exceptions.OrderNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoImplTest {
    @Mock
    private Order order;
    @InjectMocks
    private OrderDao orderDao = OrderDaoImpl.getInstance();

    @Test
    public void testFindById() throws OrderNotFoundException {
        UUID uuid = UUID.randomUUID();
        when(order.getId()).thenReturn(uuid);
        orderDao.placeOrder(order);

        Order foundOrder = orderDao.findById(uuid);

        assertEquals(order, foundOrder);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testFindByIdOrderNotFoundException() throws OrderNotFoundException {
        UUID uuid = UUID.randomUUID();
        when(order.getId()).thenReturn(UUID.randomUUID());
        orderDao.placeOrder(order);

        orderDao.findById(uuid);
    }

}