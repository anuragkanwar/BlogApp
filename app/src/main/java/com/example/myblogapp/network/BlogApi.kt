package com.example.myblogapp.network

import com.example.myblogapp.model.respond.*
import com.example.myblogapp.model.response.*
import com.example.myblogapp.utils.Constants
import retrofit2.http.*

interface BlogApi {

    // ==================CHECK======================
    @Headers("Content-Type: application/json")
    @GET("")
    suspend fun check(): String

    // ==================USERS======================
    @Headers("Content-Type: application/json")
    @POST("${Constants.API_Version}users/register")
    suspend fun createAccount(
        @Body user: RegisterUser
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("${Constants.API_Version}users/login")
    suspend fun login(
        @Body user: LoginUser
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @PUT("${Constants.API_Version}users/edit")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body user: UpdateUser
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @DELETE("${Constants.API_Version}users/delete")
    suspend fun deleteUser(
        @Header("Authorization") token: String
    ): NoDataResponse

    // ==================BLOGS======================

    @Headers("Content-Type: application/json")
    @POST("${Constants.API_Version}blog/publish")
    suspend fun publishBlog(
        @Header("Authorization") token: String,
        @Body blog: CreateBlog
    ): NoDataResponse

    @Headers("Content-Type: application/json")
    @GET("${Constants.API_Version}blog")
    suspend fun getAllBlogs(
        @Header("Authorization") token: String
    ): AllBlogsResponse

    @Headers("Content-Type: application/json")
    @GET("${Constants.API_Version}blog")
    suspend fun getAllBlogsByFilter(
        @Header("Authorization") token: String,
        @Query("category") category: String
    ): AllBlogsResponse

    @Headers("Content-Type: application/json")
    @GET("${Constants.API_Version}blog/fav")
    suspend fun getAllFavBlogs(
        @Header("Authorization") token: String,
    ): AllBlogsResponse

    @Headers("Content-Type: application/json")
    @GET("${Constants.API_Version}blog/{id}")
    suspend fun getSingleBlogs(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): FullBlogResponse

    // ==================FAV======================


    @Headers("Content-Type: application/json")
    @POST("${Constants.API_Version}fav/add/{id}")
    suspend fun addFavBlog(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): NoDataResponse

    @Headers("Content-Type: application/json")
    @POST("${Constants.API_Version}fav/del/{id}")
    suspend fun deleteFavBlog(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): NoDataResponse

    // ==================COMMENT======================

    @Headers("Content-Type: application/json")
    @GET("${Constants.API_Version}comment/{id}")
    suspend fun getComments(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): AllCommentResponse

    @Headers("Content-Type: application/json")
    @POST("${Constants.API_Version}comment/add")
    suspend fun addComment(
        @Header("Authorization") token: String,
        @Body comment: AddComment
    ): CommentResponse

    @Headers("Content-Type: application/json")
    @PUT("${Constants.API_Version}comment/update")
    suspend fun updateComment(
        @Header("Authorization") token: String,
        @Body comment: UpdateComment
    ): IntUpdateResponse

    @Headers("Content-Type: application/json")
    @PUT("${Constants.API_Version}comment/delete")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Body comment: DeleteComment
    ): IntDeleteResponse

}