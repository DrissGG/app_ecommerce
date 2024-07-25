package fr.driss.swiiim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiService apiService;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.211:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        fetchProducts(apiService);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // Handle Home action
                    return true;
                } else if (id == R.id.nav_search) {
                    // Handle Search action
                    return true;
                } else if (id == R.id.nav_cart) {
                    // Handle Cart action
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_login) {
                    // Handle Login action
                    return true;
                }
                return false;
            }
        });
    }

    private void fetchProducts(ApiService apiService) {
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body();
                    productAdapter = new ProductAdapter(productList, MainActivity.this);
                    recyclerView.setAdapter(productAdapter);
                } else {
                    Log.e("MainActivity", "Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("MainActivity", "Failed to fetch products", t);
            }
        });
    }

    public void addToCart(Product product) {
        Log.d("MainActivity", "Adding to cart: " + product.getName());
        Cart.getInstance().addToCart(product);
        Toast.makeText(this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
