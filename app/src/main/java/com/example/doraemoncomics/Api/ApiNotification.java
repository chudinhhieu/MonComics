package com.example.doraemoncomics.Api;

import com.example.doraemoncomics.Activity.User.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiNotification {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    ApiNotification apiNotification = new Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/fcm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNotification.class);
    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAATBak04Q:APA91bH3KFe_koIbpGVz1xuZAJO9rNxEuiFapu3FNzb0p9v2XciTp56gu6hW65OJcYyrmcZhF2Yttwyb5oDY_72OzIR_Qf-2KIz-OEgyLIB4I9hMHDppBkupOrrizWF2J8lUT2Pe863q" // Thay YOUR_SERVER_KEY bằng Server Key của Firebase
    })
    @POST("send")
    Call<Void> sendNotification(@Body NotificationData notificationData);
}
