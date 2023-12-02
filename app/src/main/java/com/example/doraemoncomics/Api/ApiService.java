package com.example.doraemoncomics.Api;

import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Comment;
import com.example.doraemoncomics.Models.Favorite;
import com.example.doraemoncomics.Models.Genre;
import com.example.doraemoncomics.Models.Statistical;
import com.example.doraemoncomics.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(MainActivity.ip_pixel4)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //User
    @GET("users/")
    Call<List<User>> getListUser();

    @GET("users/{id_u}")
    Call<User> getOneUser(@Path("id_u") String id_u);

    @POST("users/add/")
    Call<User> postUser(@Body User user);

    @PATCH("users/edit/{id_u}")
    Call<User> patchUser(@Path("id_u") String id_u, @Body User user);

    @Multipart
    @PATCH("users/avatar/{id_u}")
    Call<User> postAvatar(@Path("id_u") String id_u, @Part MultipartBody.Part avatar);

    @DELETE("users/delete/{id_u}")
    Call<User> deleteAccount(@Path("id_u") String id_u);

    //Genre
    @GET("genres/")
    Call<List<Genre>> getListGenre();

    @GET("genres/{id_g}")
    Call<Genre> getGenre(@Path("id_g") String id_g);

    @Multipart
    @POST("genres/add/")
    Call<Genre> postGenre(@Part("name") RequestBody name,
                          @Part("description") RequestBody description,
                          @Part MultipartBody.Part image);

    @Multipart
    @PATCH("genres/edit/{id_g}")
    Call<Genre> patchGenre(@Path("id_g") String id_g,
                           @Part("name") RequestBody name,
                           @Part("description") RequestBody description,
                           @Part MultipartBody.Part image);

    @DELETE("genres/delete/{id_g}")
    Call<Genre> deleteGenre(@Path("id_g") String id_g);

    //Comic
    @GET("comics/{id_c}")
    Call<List<Comic>> getListComic(@Path("id_c") String id_c);

    @GET("comics/findOne/{id_c}")
    Call<Comic> getOneComic(@Path("id_c") String id_c);

    @Multipart
    @POST("comics/add/")
    Call<Comic> postComic(@Part("name") RequestBody name,
                          @Part("idGenre") RequestBody idGenre,
                          @Part("description") RequestBody description,
                          @Part("publicationDate") RequestBody publicationDate,
                          @Part("author") RequestBody author,
                          @Part("linkCM") RequestBody linkCM,
                          @Part MultipartBody.Part coverImage,
                          @Part List<MultipartBody.Part> contentImage);

    @Multipart
    @PATCH("comics/edit/{id_c}")
    Call<Comic> patchComic(@Path("id_c") String id_c,
                           @Part("name") RequestBody name,
                           @Part("idGenre") RequestBody idGenre,
                           @Part("description") RequestBody description,
                           @Part("publicationDate") RequestBody publicationDate,
                           @Part("author") RequestBody author,
                           @Part("linkCM") RequestBody linkCM,
                           @Part MultipartBody.Part coverImage,
                           @Part List<MultipartBody.Part> contentImage
    );

    @DELETE("comics/delete/{id_c}")
    Call<Comic> deleteComic(@Path("id_c") String id_c);

    //    Comments
    @GET("comments/{id_cm}")
    Call<List<Comment>> getListComment(@Path("id_cm") String id_cm);
    @POST("comments/add")
    Call<Comment> postComment(@Body Comment comment);

    @PATCH("comments/edit/{id_cm}")
    Call<Comment> patchComment(@Path("id_cm") String id_cm,@Body Comment comment);
    @DELETE("comments/delete/{id_cm}")
    Call<Comment> deleteComment(@Path("id_cm") String id_cm);

    //Favorite
    @GET("favorites/{id_user}/{id_comic}")
    Call<Favorite> checkFavorite(@Path("id_user") String id_user,@Path("id_comic") String id_comic);
    @POST("favorites/add")
    Call<Void> postFavorite(@Body Favorite favorite);

    @DELETE("favorites/delete/{id_f}")
    Call<Void> deleteFavorite(@Path("id_f") String id_f);
    @GET("favorites/{id_c}")
    Call<List<Favorite>> getListFavoriteComic(@Path("id_c") String id_c);
    @GET("favorites/user/{id_u}")
    Call<List<Favorite>> getListFavoriteUser(@Path("id_u") String id_u);

    //  Statistical
    @GET("favorites/top3")
    Call<List<Statistical>> getTop3Favorites();
    @GET("comments/top3")
    Call<List<Statistical>> getTop3Comments();

    @GET("comics/top3new")
    Call<List<Comic>> getTop3ComicNew();
}
