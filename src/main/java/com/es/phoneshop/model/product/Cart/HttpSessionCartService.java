package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class HttpSessionCartService implements CartService {
    protected static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class + ".cart";
    private ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();

    private HttpSessionCartService() {
    }

    public static HttpSessionCartService getInstance() {
        return SingletonHolder.instance;
    }

    public static class SingletonHolder {
        public static final HttpSessionCartService instance = new HttpSessionCartService();
    }


    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart result = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new Cart();
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws OutOfStockException, ProductNotFoundException {
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        product.setStock(product.getStock() - quantity);
        cart.getCartItems().add(new CartItem(product.getId(), quantity));
        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart) throws ProductNotFoundException {
        BigDecimal totalCost = new BigDecimal(0);
        int totalQuantity = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            totalCost = totalCost.add(arrayListProductDao.getProduct(cartItem.getProductId()).getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity())));
            totalQuantity += cartItem.getQuantity();
        }
        cart.setTotalCost(totalCost);
        cart.setTotalQuantity(totalQuantity);
    }
}
