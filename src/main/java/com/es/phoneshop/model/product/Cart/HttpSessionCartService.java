package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class HttpSessionCartService implements CartService {
    protected static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class + ".cart";

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
    public void add(Cart cart, Product product, int quantity) throws OutOfStockException {
        CartItem currentCartItem = cart.getCartItems().stream()
                .filter(x -> x.getProductId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        int totalProductQuantity = 0;
        if (currentCartItem != null) {
            totalProductQuantity = currentCartItem.getQuantity();
        }
        if (quantity + totalProductQuantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        if (currentCartItem != null) {
            currentCartItem.setQuantity(currentCartItem.getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(product.getId(), quantity));
        }
        recalculateCart(cart, product, quantity);
    }

    private void recalculateCart(Cart cart, Product product, int quantity) {
        BigDecimal totalCost = cart.getTotalCost().add(product.getPrice().multiply(new BigDecimal(quantity)));
        int totalQuantity = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        cart.setTotalCost(totalCost);
        cart.setTotalQuantity(totalQuantity);
    }
}
