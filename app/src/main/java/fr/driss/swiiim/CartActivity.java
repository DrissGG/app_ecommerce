package fr.driss.swiiim;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtenir les produits du panier depuis la classe Cart
        //List<Product> products = getIntent().getParcelableArrayListExtra("products");


        cartItemList = new ArrayList<>();
        for (Product product : Cart.getInstance().getCartList()) {
            // Vérifie si le produit existe déjà dans la liste
//            boolean exists =false;
//            for (CartItem cartItem : cartItemList){
//                if(cartItem.getProduct().getId().equals(product.getId())){
//                    cartItem.setQuantity(cartItem.getQuantity()+1); //augmente la quantité
//                    exists = true;
//                    break;

            cartItemList.add(new CartItem(product,1));
        }
        cartAdapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(cartAdapter);

    }
}