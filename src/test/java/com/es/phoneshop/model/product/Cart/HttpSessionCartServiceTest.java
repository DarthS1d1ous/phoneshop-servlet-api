package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.es.phoneshop.model.product.Cart.HttpSessionCartService.CART_SESSION_ATTRIBUTE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private Cart cart;
    @Mock
    private HttpSession session;
    @Mock
    private Product product;
    @InjectMocks
    private HttpSessionCartService httpSessionCartService = HttpSessionCartService.getInstance();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CART_SESSION_ATTRIBUTE)).thenReturn(null);
    }

    @Test
    public void testGetCart() {
        httpSessionCartService.getCart(request);

        verify(request, times(2)).getSession();
        verify(session).getAttribute(CART_SESSION_ATTRIBUTE);
    }

    @Test
    public void testAdd() throws OutOfStockException, ProductNotFoundException {
        when(product.getStock()).thenReturn(10);

        httpSessionCartService.add(cart, product, 1);

        verify(product, times(2)).setStock(product.getStock() - 1);
        verify(cart, times(2)).getCartItems();

    }

    @Test(expected = OutOfStockException.class)
    public void testAddOutOfStockException() throws OutOfStockException, ProductNotFoundException {
        httpSessionCartService.add(cart, product, 1);

        verify(product).setStock(product.getStock() - 1);
    }
}