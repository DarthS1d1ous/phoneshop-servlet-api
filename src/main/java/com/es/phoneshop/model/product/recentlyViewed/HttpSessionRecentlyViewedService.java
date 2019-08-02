package com.es.phoneshop.model.product.recentlyViewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class HttpSessionRecentlyViewedService implements RecentlyViewedService {
    protected static final String HISTORY_SESSION_ATTRIBUTE = HttpSessionRecentlyViewedService.class + ".recentlyViewed";
    private static final int MAX_RECENTLY_VIEWED_SIZE = 3;
    private ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();

    public static HttpSessionRecentlyViewedService getInstance() {
        return SingletonHolder.instance;
    }

    public static class SingletonHolder {
        public static final HttpSessionRecentlyViewedService instance = new HttpSessionRecentlyViewedService();
    }

    public void addRecentlyViewedProduct(List<Product> recentlyViewedProducts, Long productId) throws ProductNotFoundException {
        Product product = arrayListProductDao.getProduct(productId);
        int i;
        for (i = 0; i < recentlyViewedProducts.size(); i++) {
            if (recentlyViewedProducts.get(i).getId().equals(product.getId())) {
                Product temp = recentlyViewedProducts.get(i);
                recentlyViewedProducts.remove(i);
                recentlyViewedProducts.add(0,temp);
                break;
            }
        }
        if (i == recentlyViewedProducts.size()) {
            recentlyViewedProducts.add(0, product);
        }
        if (i == MAX_RECENTLY_VIEWED_SIZE) {
            recentlyViewedProducts.remove(MAX_RECENTLY_VIEWED_SIZE);
        }
    }

    @Override
    public List<Product> getRecentlyViewed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Product> recentlyViewedProducts =(List<Product>) session.getAttribute(HISTORY_SESSION_ATTRIBUTE);
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new ArrayList<>();
           session.setAttribute(HISTORY_SESSION_ATTRIBUTE, recentlyViewedProducts);
        }
        return recentlyViewedProducts;
    }
}
