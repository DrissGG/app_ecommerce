package fr.driss.swiiim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("%.2f €", product.getPrice()));
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

        // Glide pour charger l'image
        Glide.with(context)
                .load(product.getImageUrl())//url de l'image
                .placeholder(R.drawable.default_image) // Image par défaut pendant le chargement
                .error(R.drawable.error_image) // Image en cas d'erreur
                .into(holder.productImage);

        // Ajouter un clic sur le bouton "Ajouter au panier"
        holder.addToCartButton.setOnClickListener(v -> {
            Cart.getInstance().addToCart(product);
            Toast.makeText(context, "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, productQuantity;
        public ImageView productImage;
        public Button addToCartButton;

        public ProductViewHolder(@NonNull View view) {
            super(view);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
            productQuantity = view.findViewById(R.id.product_quantity);
            productImage = view.findViewById(R.id.product_image);
            addToCartButton = view.findViewById(R.id.add_to_cart_button);
        }
    }
}