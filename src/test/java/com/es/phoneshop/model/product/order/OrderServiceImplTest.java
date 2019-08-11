package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.Cart.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private Order order;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @InjectMocks
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Test
    public void testGetOrder() {
        when(cart.getTotalCost()).thenReturn(BigDecimal.ZERO);
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);

        orderService.getOrder(request);

        verify(request, times(6)).getParameter(anyString());
    }

    @Test
    public void testIsOrderValid() {
        when(order.getName()).thenReturn("A");
        when(order.getSurname()).thenReturn("A");
        when(order.getPhone()).thenReturn("+375291234567");
        when(order.getDeliveryAddress()).thenReturn("A");
        when(order.getDeliveryDate()).thenReturn(new Date());

        boolean result = orderService.isOrderValid(order);

        assertTrue(result);
    }
}