package fr.driss.swiiim;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener{
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private TextView totalAmount;

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

        cartAdapter = new CartAdapter(cartItemList, this,this);
        recyclerView.setAdapter(cartAdapter);

        totalAmount = findViewById(R.id.total_text_view);
        updateTotalAmount();
    }

    @Override
    public void onQuantityChanged() {
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        double total = 0.0;
        for (CartItem item : cartItemList) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalAmount.setText(String.format("Total: %.2f €", total));
    }

}
