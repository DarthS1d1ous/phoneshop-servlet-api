package com.es.phoneshop.model.product.recentlyViewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.es.phoneshop.model.product.recentlyViewed.HttpSessionRecentlyViewedService.HISTORY_SESSION_ATTRIBUTE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionRecentlyViewedServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private List<Product> recentlyViewedProducts1;
    @Mock
    private List<Product> recentlyViewedProducts2;
    @Mock
    private ArrayListProductDao arrayListProductDao;
    @Mock
    private Product product;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @InjectMocks
    private HttpSessionRecentlyViewedService httpSessionHistoryService = HttpSessionRecentlyViewedService.getInstance();

    @Before
    public void setup() throws ProductNotFoundException {
        Long productId = 4L;
        when(session.getAttribute(HISTORY_SESSION_ATTRIBUTE)).thenReturn(recentlyViewedProducts1);
        when(request.getSession()).thenReturn(session);
        when(arrayListProductDao.getProduct(productId)).thenReturn(product);
        when(recentlyViewedProducts1.get(0)).thenReturn(product1);
        when(recentlyViewedProducts1.get(1)).thenReturn(product2);
        when(recentlyViewedProducts1.get(2)).thenReturn(product3);
        when(recentlyViewedProducts1.size()).thenReturn(3);
    }

    @Test
    public void testAddFirstRecentlyViewedProduct() throws ProductNotFoundException {
        Long productId = 1L;
        when(recentlyViewedProducts1.size()).thenReturn(0);
        when(arrayListProductDao.getProduct(productId)).thenReturn(product);

        httpSessionHistoryService.addRecentlyViewedProduct(recentlyViewedProducts1, productId);

        verify(recentlyViewedProducts1).add(0, product);
    }

    @Test
    public void testAddRecentlyViewedProductInFull() throws ProductNotFoundException {
        Long productId = 4L;
        setupProductId(product, 4L);
        setupProductId(product1, 1L);
        setupProductId(product2, 2L);
        setupProductId(product3, 3L);

        httpSessionHistoryService.addRecentlyViewedProduct(recentlyViewedProducts1, productId);

        verify(recentlyViewedProducts1).add(0, product);
        verify(recentlyViewedProducts1).remove(3);
    }

    @Test
    public void testAddSameRecentlyViewedProduct() throws ProductNotFoundException {
        Long productId = 4L;
        setupProductId(product, 2L);
        setupProductId(product1, 1L);
        setupProductId(product2, 2L);
        setupProductId(product3, 3L);

        httpSessionHistoryService.addRecentlyViewedProduct(recentlyViewedProducts1, productId);

        verify(recentlyViewedProducts1).add(0, product2);
        verify(recentlyViewedProducts1).remove(1);
    }

    private void setupProductId(Product product, Long productId) {
        when(product.getId()).thenReturn(productId);
    }

    @Test
    public void testGetNullRecentlyViewed() {
        when(session.getAttribute(HISTORY_SESSION_ATTRIBUTE)).thenReturn(null);

        httpSessionHistoryService.getRecentlyViewed(request);

        verify(request).getSession();
        verify(session).getAttribute(HISTORY_SESSION_ATTRIBUTE);
    }

    @Test
    public void testGetRecentlyViewed() {
        recentlyViewedProducts2 = httpSessionHistoryService.getRecentlyViewed(request);

        assertEquals(recentlyViewedProducts1, recentlyViewedProducts2);
    }
}