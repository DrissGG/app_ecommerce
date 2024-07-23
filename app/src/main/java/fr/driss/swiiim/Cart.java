package fr.driss.swiiim;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<CartItem> cartList;

    private Cart() {
        cartList = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<CartItem> getCartList() {
        return cartList;
    }

    public void addToCart(Product product) {
        boolean productFound = false;
        for (CartItem cartItem : cartList) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                // Vérifiez si la quantité actuelle + 1 dépasse le stock
                int newQuantity = cartItem.getQuantity() + 1;
                if (newQuantity <= product.getQuantity()) {
                    cartItem.setQuantity(newQuantity); // Incrémenter la quantité
                } else {
                    // Afficher un message d'erreur ou gérer le cas où la quantité dépasse le stock
                    // Par exemple, vous pouvez afficher un toast ou une notification
                    System.out.println("La quantité demandée dépasse le stock disponible.");
                }
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            if (product.getQuantity() > 0) { // Assurez-vous qu'il y a du stock
                cartList.add(new CartItem(product, 1)); // Ajouter le produit avec une quantité de 1
            } else {
                // Gérer le cas où le produit n'est pas en stock
                // Par exemple, afficher un message ou ne pas ajouter le produit
                System.out.println("Produit non disponible en stock.");
            }
        }
    }

}
