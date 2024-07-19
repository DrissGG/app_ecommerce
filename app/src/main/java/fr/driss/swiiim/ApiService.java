package fr.driss.swiiim;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Body;


public interface ApiService {
    @GET("/products")
    Call<List<Product>> getProducts();

    @POST("/products")
    Call<Void> addProduct(@Body Product product);

    @PUT("/products/{id}")
    Call<Void> updateProductQuantity(@Path("id") int id, @Body Product product);
}

