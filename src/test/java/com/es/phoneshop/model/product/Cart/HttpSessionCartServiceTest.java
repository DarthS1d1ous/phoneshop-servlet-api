package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.es.phoneshop.model.product.Cart.HttpSessionCartService.CART_SESSION_ATTRIBUTE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private Cart cart1;
    @Mock
    private Cart cart2;
    @Mock
    private CartItem cartItem1;
    @Mock
    private CartItem cartItem2;
    @Mock
    private HttpSession session;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    private List<CartItem> cartItems;
    @Mock
    private ArrayListProductDao arrayListProductDao;
    @InjectMocks
    private HttpSessionCartService httpSessionCartService = HttpSessionCartService.getInstance();

    @Before
    public void setup() throws ProductNotFoundException {
        when(session.getAttribute(CART_SESSION_ATTRIBUTE)).thenReturn(cart1);
        when(arrayListProductDao.getProduct(1L)).thenReturn(product1);

        setupProduct(product1, 1L, 5, new BigDecimal(5));
        setupProduct(product2, 2L, 10, new BigDecimal(10));
        setupProduct(product3, 3L, 15, new BigDecimal(15));

        setupCartItem(cartItem1, product1, 5);
        setupCartItem(cartItem2, product2, 10);
        cartItems = Stream.of(cartItem1, cartItem2).collect(Collectors.toList());
        when(cart1.getCartItems()).thenReturn(cartItems);

    }

    private void setupCartItem(CartItem cartItem, Product product, int quantity) {
        when(cartItem.getProduct()).thenReturn(product);
        when(cartItem.getQuantity()).thenReturn(quantity);
    }

    public void setupProduct(Product product, Long id, int stock, BigDecimal price) {
        when(product.getId()).thenReturn(id);
        when(product.getStock()).thenReturn(stock);
        when(product.getPrice()).thenReturn(price);
    }

    @Test
    public void testAdd() throws OutOfStockException {
        httpSessionCartService.add(cart1, product3, 15);

        verify(cart1, times(4)).getCartItems();
        verify(cart1).setTotalCost(new BigDecimal(350));
        verify(cart1).setTotalQuantity(30);

    }

    @Test
    public void testUpdate() throws ProductNotFoundException, OutOfStockException {
        httpSessionCartService.update(cart1, product1.getId(), 5);

        verify(cartItem1).setQuantity(5);
    }

    @Test(expected = OutOfStockException.class)
    public void testUpdateOutOfStockException() throws ProductNotFoundException, OutOfStockException {
        httpSessionCartService.update(cart1, product1.getId(), 10);
    }

    @Test
    public void testDelete() {
        int sizeBefore = cartItems.size();

        httpSessionCartService.delete(cart1, 1L);

        assertEquals(sizeBefore, cartItems.size() + 1);
    }

    @Test
    public void testGetCart() {
        cart2 = httpSessionCartService.getCart(session);

        assertEquals(cart1, cart2);
    }

    @Test
    public void testGetNullCart() {
        when(session.getAttribute(CART_SESSION_ATTRIBUTE)).thenReturn(null);

        httpSessionCartService.getCart(session);

        verify(session).getAttribute(CART_SESSION_ATTRIBUTE);
    }


    @Test(expected = OutOfStockException.class)
    public void testAddOutOfStockException() throws OutOfStockException {
        when(product1.getStock()).thenReturn(0);

        httpSessionCartService.add(cart1, product1, 1);
    }
}