package com.example.apprest_cl2.service;

import com.example.apprest_cl2.entity.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {

    @GET("posts")
    public abstract Call<List<Posts>> listaPublicaciones();

}
