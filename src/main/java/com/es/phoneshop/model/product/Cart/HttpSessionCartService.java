package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    public Cart getCart(HttpSession session) {
        Cart result = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new Cart();
            session.setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void delete(Cart cart, Long productId) {
        Optional<CartItem> currentCartItemOptional = getCurrentCartItemOptional(cart, productId);
        List<CartItem> cartItems = cart.getCartItems();
        if (currentCartItemOptional.isPresent()) {
            CartItem cartItem = currentCartItemOptional.get();
            cartItems.remove(cartItem);
            recalculateCart(cart);
        }
    }

    @Override
    public void update(Cart cart, Long productId, int quantity) throws ProductNotFoundException, OutOfStockException {
        Product product = arrayListProductDao.getProduct(productId);
        Optional<CartItem> currentCartItemOptional = getCurrentCartItemOptional(cart, productId);
        if (quantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException(product.getStock());
        }
        currentCartItemOptional.ifPresent(cartItem -> cartItem.setQuantity(quantity));
        recalculateCart(cart);
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws OutOfStockException {
        Optional<CartItem> currentCartItemOptional = getCurrentCartItemOptional(cart, product.getId());
        int currentProductQuantity = currentCartItemOptional.map(CartItem::getQuantity).orElse(0);
        if (quantity + currentProductQuantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException(product.getStock());
        }
        if (currentCartItemOptional.isPresent()) {
            currentCartItemOptional.get().setQuantity(currentProductQuantity + quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart) {
        BigDecimal totalCost = new BigDecimal(cart.getCartItems().stream()
                .mapToInt(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice().intValue()).sum());
        int totalQuantity = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        cart.setTotalCost(totalCost);
        cart.setTotalQuantity(totalQuantity);
    }

    protected Optional<CartItem> getCurrentCartItemOptional(Cart cart, Long productId) {
        return cart.getCartItems().stream()
                .filter(x -> x.getProduct().getId().equals(productId))
                .findFirst();
    }
}
