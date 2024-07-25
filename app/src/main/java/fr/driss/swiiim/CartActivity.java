package fr.driss.swiiim;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = Cart.getInstance().getItems();
        Log.d("CartActivity", "Cart items count: " + cartItemList.size());

        if (cartItemList.isEmpty()) {
            Log.e("CartActivity", "Cart is empty");
        } else {
            for (CartItem item : cartItemList) {
                Log.d("CartActivity", "Cart item: " + item.getProduct().getName() + ", Quantity: " + item.getQuantity());
            }
        }

        cartAdapter = new CartAdapter(cartItemList, this);
        recyclerView.setAdapter(cartAdapter);

        totalTextView = findViewById(R.id.total_text_view);
        updateTotal();
    }

    private void updateTotal() {
        double total = Cart.getInstance().getTotal();
        totalTextView.setText(String.format("Total: %.2f €", total));
    }
}
