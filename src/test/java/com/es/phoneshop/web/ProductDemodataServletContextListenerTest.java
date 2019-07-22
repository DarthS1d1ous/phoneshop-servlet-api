package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    ServletContextEvent servletContextEvent;

    private final ProductDemodataServletContextListener productDemodataServletContextListener = new ProductDemodataServletContextListener();

    @Test
    public void contextInitialized() {
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
        assertEquals(ArrayListProductDao.getInstance().findProducts().size(),12);
    }
}