package fr.driss.swiiim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("MainActivity", "RecyclerView: " + recyclerView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.211:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Appel API pour récupérer les produits
        fetchProducts(apiService);

        Button viewCartButton = findViewById(R.id.view_cart_button);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                if (productList == null) {
                    productList = new ArrayList<>();
                }
                intent.putParcelableArrayListExtra("products", new ArrayList<>(productList));
                startActivity(intent);
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
}