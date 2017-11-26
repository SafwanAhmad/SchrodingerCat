package com.example.android.mobileprogrammingassignment.api;

import com.example.android.mobileprogrammingassignment.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by safwanx on 11/25/17.
 */

public interface UserListService {
    @GET("api/users")
    Call<Result> getCallGetAllUsers(
            @Query("offset") int offset,
            @Query("limit") int limit
    );
}
