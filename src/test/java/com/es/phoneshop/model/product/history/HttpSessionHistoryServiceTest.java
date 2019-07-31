package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.es.phoneshop.model.product.history.HttpSessionHistoryService.HISTORY_SESSION_ATTRIBUTE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionHistoryServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private List<Product> historyProducts;
    @InjectMocks
    private HttpSessionHistoryService httpSessionHistoryService = HttpSessionHistoryService.getInstance();

    @Test
    public void testUpdate() throws ProductNotFoundException {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStock(2);
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>(Arrays.asList(product)));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HISTORY_SESSION_ATTRIBUTE)).thenReturn(historyProducts);

        httpSessionHistoryService.update(request, productId);

        verify(request).getSession();
        verify(session, times(2)).getAttribute(HISTORY_SESSION_ATTRIBUTE);
        verify(request).setAttribute(eq("history"), eq(historyProducts));

    }
}