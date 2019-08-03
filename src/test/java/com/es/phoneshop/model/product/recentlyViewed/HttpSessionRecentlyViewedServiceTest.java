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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.es.phoneshop.model.product.recentlyViewed.HttpSessionRecentlyViewedService.HISTORY_SESSION_ATTRIBUTE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionRecentlyViewedServiceTest {
    @Mock
    private HttpSession session;
    private List<Product> recentlyViewedProducts1 = new ArrayList<>();
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
        when(session.getAttribute(HISTORY_SESSION_ATTRIBUTE)).thenReturn(recentlyViewedProducts1);
        when(arrayListProductDao.getProduct(4L)).thenReturn(product);
        when(arrayListProductDao.getProduct(2L)).thenReturn(product2);

        recentlyViewedProducts1.add(product1);
        recentlyViewedProducts1.add(product2);
        recentlyViewedProducts1.add(product3);
    }

    @Test
    public void testAddFirstRecentlyViewedProduct() throws ProductNotFoundException {
        Long productId = 4L;
        when(arrayListProductDao.getProduct(productId)).thenReturn(product);
        recentlyViewedProducts1.clear();

        httpSessionHistoryService.addRecentlyViewedProduct(recentlyViewedProducts1, productId);

        assertEquals(recentlyViewedProducts1.get(0), product);
    }

    @Test
    public void testAddRecentlyViewedProductInFull() throws ProductNotFoundException {
        Long productId = 4L;

        httpSessionHistoryService.addRecentlyViewedProduct(recentlyViewedProducts1, productId);

        assertEquals(recentlyViewedProducts1.get(0), product);
        assertEquals(recentlyViewedProducts1.size(), 3);
    }

    @Test
    public void testAddSameRecentlyViewedProduct() throws ProductNotFoundException {
        Long productId = 2L;

        httpSessionHistoryService.addRecentlyViewedProduct(recentlyViewedProducts1, productId);

        assertEquals(recentlyViewedProducts1.get(0), product2);
        assertEquals(recentlyViewedProducts1.size(), 3);
    }

    @Test
    public void testGetNullRecentlyViewed() {
        when(session.getAttribute(HISTORY_SESSION_ATTRIBUTE)).thenReturn(null);

        httpSessionHistoryService.getRecentlyViewed(session);

        verify(session).getAttribute(HISTORY_SESSION_ATTRIBUTE);
    }

    @Test
    public void testGetRecentlyViewed() {
        List<Product> recentlyViewedProducts2 = httpSessionHistoryService.getRecentlyViewed(session);

        assertEquals(recentlyViewedProducts1, recentlyViewedProducts2);
    }
}