package com.example.e_voting.Util;

import com.example.e_voting.modl.CandidateResponse;
import com.example.e_voting.modl.LoginResponse;
import com.example.e_voting.modl.RegisterResponse;
import com.example.e_voting.modl.UsersDetail;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.example.e_voting.Util.Constants.GET_BASE_URL;
import static com.example.e_voting.Util.Constants.GET_CREATE_VOTE;
import static com.example.e_voting.Util.Constants.GET_LOGIN_API;
import static com.example.e_voting.Util.Constants.GET_PARTIES_LIST;
import static com.example.e_voting.Util.Constants.GET_REGISTER_API;

public interface MyWebApi {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GET_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST(GET_REGISTER_API)
    Call<RegisterResponse> RegisterUser(@Body UsersDetail detail);

    @GET(GET_LOGIN_API)
    Call<LoginResponse>   Login(@Query("aadharNumber") String aadhar);

    @GET(GET_PARTIES_LIST)
    Call<CandidateResponse> GetPartiesList();

    @POST(GET_CREATE_VOTE)
    Call<RegisterResponse> Vote(@Body UsersDetail detail);

}
