package fr.driss.swiiim;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<Product> cartList;

    private Cart() {
        cartList = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<Product> getCartList() {
        return cartList;
    }

    public void addToCart(Product product) {
        cartList.add(product);
    }

}

