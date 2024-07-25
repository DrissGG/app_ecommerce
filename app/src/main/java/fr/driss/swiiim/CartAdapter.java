package fr.driss.swiiim;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItemList;
    private Context context;

    public CartAdapter(List<CartItem> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        Product product = cartItem.getProduct();

        String imageUrl = product.getImageUrl();
        Log.d("CartAdapter", "Loading image from URL: " + imageUrl);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("%.2f €", product.getPrice()));
        holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load("http://192.168.1.211:3000/" + imageUrl)
                    .placeholder(R.drawable.default_image) // Image par défaut pendant le chargement
                    .error(R.drawable.error_image) // Image en cas d'erreur
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.default_image); // Image par défaut
        }
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, productQuantity;
        public ImageView productImage;

        public CartViewHolder(@NonNull View view) {
            super(view);
            productName = view.findViewById(R.id.cart_product_name);
            productPrice = view.findViewById(R.id.cart_product_price);
            productQuantity = view.findViewById(R.id.cart_product_quantity);
            productImage = view.findViewById(R.id.cart_product_image);
        }
    }
}
