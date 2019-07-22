package com.es.phoneshop.model.product;


import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;


    @InjectMocks
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() {
        when(product1.getId()).thenReturn(1L);
        when(product1.getStock()).thenReturn(1);
        when(product1.getPrice()).  thenReturn(new BigDecimal(100));
        when(product1.getDescription()).thenReturn("samsung");
        when(product2.getDescription()).thenReturn("sony");
        when(product2.getId()).thenReturn(null);
        when(product2.getStock()).thenReturn(1);
        when(product2.getPrice()).thenReturn(new BigDecimal(100));
    }

    @Test
    public void testFindProducts() {
        assertEquals(1,productDao.findProducts().size());
    }

    @Test
    public void testFindProductsByQuery() {
        int size =productDao.findProducts("sony").size();
        assertEquals(1,size);
    }

    @Test
    public void testFindProductByDescriptionAsk(){

    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductsIllegalArgumentException() throws ProductNotFoundException {
        productDao.getProduct(null);
    }

    //Не работает
    @Test
    public void testGetProduct() throws ProductNotFoundException {
        assertEquals((Long) 1L, productDao.getProduct(1L).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveIllegalArgumentException() {
        productDao.save(product1);
    }

    @Test
    public void testSave() {
        int sizeBefore = productDao.findProducts().size();
        productDao.save(product2);
        assertEquals(sizeBefore + 1, productDao.findProducts().size());
    }

    //Не работает
    @Test
    public void testDelete() {
        List<Product> products = productDao.findProducts();
        int sizeBefore = products.size();
        productDao.delete(products.get(0).getId());
        assertEquals(sizeBefore - 1, productDao.findProducts().size());
    }
}
