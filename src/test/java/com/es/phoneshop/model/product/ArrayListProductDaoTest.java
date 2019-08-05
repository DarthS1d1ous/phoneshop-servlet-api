package com.es.phoneshop.model.product;


import com.es.phoneshop.model.product.enums.OutputOrder;
import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;

    @InjectMocks
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() {
        setupProduct(product1, 1L, 1, "Samsung Galaxy S", new BigDecimal(100));
        setupProduct(product2, 2L, 1, "Apple iPhone", new BigDecimal(200));
        setupProduct(product3, 3L, 0, "Siemens C56", new BigDecimal(300));
        setupProduct(product4, null, 1, "Palm Pixi", new BigDecimal(200));
        productDao.setProducts(new ArrayList<>(Arrays.asList(product1, product2, product3)));
    }

    public void setupProduct(Product product, Long id, int stock, String description, BigDecimal price) {
        when(product.getId()).thenReturn(id);
        when(product.getStock()).thenReturn(stock);
        when(product.getPrice()).thenReturn(price);
        when(product.getDescription()).thenReturn(description);
    }

    @Test
    public void testFindProducts() {
        List<Product> products = productDao.findProducts();
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));
    }

    @Test
    public void testFindProductsByQuery() {
        List<Product> products = productDao.findProducts("s o p");
        assertEquals(product2, products.get(0));
        assertEquals(product1, products.get(1));
    }

    @Test
    public void testFindProductByNullSortBy() {
        List<Product> products = productDao.findProducts("", OutputOrder.DESC, null);
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));

    }

    @Test
    public void testFindProductByDescriptionAsk() {
        List<Product> products = productDao.findProducts("", OutputOrder.ASC, SortBy.DESCRIPTION);
        assertEquals(product2, products.get(0));
        assertEquals(product1, products.get(1));
    }

    @Test
    public void testFindProductByDescriptionDesc() {
        List<Product> products = productDao.findProducts("", OutputOrder.DESC, SortBy.DESCRIPTION);
        assertEquals(product2, products.get(1));
        assertEquals(product1, products.get(0));
    }

    @Test
    public void testFindProductByPriceAsk() {
        List<Product> products = productDao.findProducts("", OutputOrder.ASC, SortBy.PRICE);
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));
    }

    @Test
    public void testFindProductByPriceDesc() {
        List<Product> products = productDao.findProducts("", OutputOrder.DESC, SortBy.PRICE);
        assertEquals(product1, products.get(1));
        assertEquals(product2, products.get(0));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductsIllegalArgumentException() throws ProductNotFoundException {
        productDao.getProduct(Long.MAX_VALUE);
    }

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
        productDao.save(product4);
        assertEquals(sizeBefore + 1, productDao.findProducts().size());
    }

    @Test
    public void testDelete() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(product1.getId());
        assertEquals(sizeBefore - 1, productDao.findProducts().size());
    }
}
