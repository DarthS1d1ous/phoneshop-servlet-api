package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.*;

public class ProductDemodataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        getSampleProducts().forEach(productDao::save);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        List<PriceHistory> priceHistory1 = new ArrayList<>(Arrays.asList(
                new PriceHistory(new Date(), new BigDecimal(100)),
                new PriceHistory(new Date(2018, 5, 25), new BigDecimal(125)),
                new PriceHistory(new Date(2017, 6, 20), new BigDecimal(150))));
        List<PriceHistory> priceHistory2 = new ArrayList<>(Arrays.asList(
                new PriceHistory(new Date(), new BigDecimal(200)),
                new PriceHistory(new Date(2018, 3, 13), new BigDecimal(180)),
                new PriceHistory(new Date(2017, 5, 28), new BigDecimal(210))));
        result.add(new Product(null, "sgs", "Samsung Galaxy S", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", priceHistory1));
        result.add(new Product(null, "sgs2", "Samsung Galaxy S II", usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory2));
        result.add(new Product(null, "sgs3", "Samsung Galaxy S III", usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory2));
        result.add(new Product(null, "iphone", "Apple iPhone", usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", priceHistory2));
        result.add(new Product(null, "iphone6", "Apple iPhone 6", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", priceHistory2));
        result.add(new Product(null, "htces4g", "HTC EVO Shift 4G", usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", priceHistory2));
        result.add(new Product(null, "sec901", "Sony Ericsson C901", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg", priceHistory2));
        result.add(new Product(null, "xperiaxz", "Sony Xperia XZ", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg", priceHistory2));
        result.add(new Product(null, "nokia3310", "Nokia 3310", usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg", priceHistory2));
        result.add(new Product(null, "palmp", "Palm Pixi", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg", priceHistory2));
        result.add(new Product(null, "simc56", "Siemens C56", usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg", priceHistory2));
        result.add(new Product(null, "simc61", "Siemens C61", usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg", priceHistory2));
        result.add(new Product(null, "simsxg75", "Siemens SXG75", usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg", priceHistory2));

        return result;
    }
}
