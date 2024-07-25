package fr.driss.swiiim;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<CartItem> items;

    private Cart() {
        items = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addToCart(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                if (item.getQuantity() < product.getQuantity()) {
                    item.setQuantity(item.getQuantity() + 1);
                }
                return;
            }
        }
        items.add(new CartItem(product, 1));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
}
