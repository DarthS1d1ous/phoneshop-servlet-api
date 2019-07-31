package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.enums.OutputOrder;
import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.history.HistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private List<Product> products;
    @Mock
    private ArrayListProductDao arrayListProductDao;
    @Mock
    private HistoryService historyService;
    @InjectMocks
    private ProductListPageServlet servlet;

    @Before
    public void setup() {
        when(request.getParameter("sort")).thenReturn("DESCRIPTION");
        when(request.getParameter("order")).thenReturn("ASC");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        String query = "samsung";
        when(arrayListProductDao.findProducts(query,OutputOrder.ASC,SortBy.DESCRIPTION)).thenReturn(products);
        when(request.getParameter("query")).thenReturn(query);

        servlet.doGet(request, response);

        verify(arrayListProductDao).findProducts(eq(query),eq(OutputOrder.ASC),eq(SortBy.DESCRIPTION));
        verify(historyService).update(request,null);
        verify(request, times(1)).setAttribute(eq("products"), eq(products));
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/productList.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetQueryNull() throws ServletException, IOException {
        when(arrayListProductDao.findProducts("",OutputOrder.ASC,SortBy.DESCRIPTION)).thenReturn(products);
        when(request.getParameter("query")).thenReturn(null);

        servlet.doGet(request, response);

        verify(arrayListProductDao).findProducts(eq(""),eq(OutputOrder.ASC),eq(SortBy.DESCRIPTION));
        verify(request, times(1)).setAttribute(eq("products"), eq(products));
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/productList.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}