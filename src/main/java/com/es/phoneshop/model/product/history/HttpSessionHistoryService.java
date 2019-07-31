package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class HttpSessionHistoryService implements HistoryService {
    protected static final String HISTORY_SESSION_ATTRIBUTE = HttpSessionHistoryService.class + ".history";
    private static final int MAX_HISTORY_SIZE = 3;

    public static HttpSessionHistoryService getInstance() {
        return SingletonHolder.instance;
    }

    public static class SingletonHolder {
        public static final HttpSessionHistoryService instance = new HttpSessionHistoryService();
    }

    private void add(List<Product> historyProducts, Long productId) throws ProductNotFoundException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        int i;
        for (i = 0; i < historyProducts.size(); i++) {
            if (historyProducts.get(i).getId().equals(product.getId())) {
                Product temp = historyProducts.get(i);
                historyProducts.set(i, historyProducts.get(0));
                historyProducts.set(0, temp);
                break;
            }
        }
        if (i == historyProducts.size()) {
            historyProducts.add(0, product);
        }
        if (i == MAX_HISTORY_SIZE) {
            historyProducts.remove(MAX_HISTORY_SIZE - 1);
        }
    }

    @Override
    public void update(HttpServletRequest request, Long productId) throws ProductNotFoundException {
        HttpSession session = request.getSession();
        if (session.getAttribute(HISTORY_SESSION_ATTRIBUTE) == null) {
            List<Product> historyProducts = new ArrayList<>();
            session.setAttribute(HISTORY_SESSION_ATTRIBUTE, historyProducts);
        }
        List<Product> historyProducts = (List<Product>) session.getAttribute(HISTORY_SESSION_ATTRIBUTE);
        if (historyProducts != null) {
            if (productId != null) {
                add(historyProducts, productId);
            }
            request.setAttribute("history", historyProducts);
        }
    }
}
