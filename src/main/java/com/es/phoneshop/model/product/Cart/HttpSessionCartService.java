package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

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
    public Cart getCart(HttpSession session) {
        Cart result = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new Cart();
            session.setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws OutOfStockException {
        Optional<CartItem> currentCartItemOptional = cart.getCartItems().stream()
                .filter(x -> x.getProductId().equals(product.getId()))
                .findFirst();
        int currentProductQuantity = currentCartItemOptional.map(CartItem::getQuantity).orElse(0);
        if (quantity + currentProductQuantity > product.getStock() || quantity < 0) {
            throw new OutOfStockException(product.getStock());
        }
        if (currentCartItemOptional.isPresent()) {
            currentCartItemOptional.get().setQuantity(currentProductQuantity + quantity);
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
