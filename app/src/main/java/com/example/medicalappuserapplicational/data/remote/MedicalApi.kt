package com.example.medicalappuserapplicational.data.remote

import com.example.medicalappuserapplicational.domain.dataModel.DeleteSpecificUserOrderResponse
import com.example.medicalappuserapplicational.domain.dataModel.GetAllProductResponse
import com.example.medicalappuserapplicational.domain.dataModel.GetSpecificUserResponse
import com.example.medicalappuserapplicational.domain.dataModel.GetUserOrderProductResponse
import com.example.medicalappuserapplicational.domain.dataModel.GetUserStockResponse
import com.example.medicalappuserapplicational.domain.dataModel.LoginResponse
import com.example.medicalappuserapplicational.domain.dataModel.SignUpResponse
import com.example.medicalappuserapplicational.domain.dataModel.UserOrderResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MedicalApi {

    @FormUrlEncoded
    @POST("createUser")
    suspend fun signUpUser(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("address") address: String,
        @Field("pinCode") pinCode: String
    ): Response<SignUpResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("addUserOrder")
    suspend fun userOrder(
        @Field("productName") productName: String,
        @Field("productId") productId: String,
        @Field("price") price: String,
        @Field("category") category: String,
        @Field("quantity") quantity: String,
        @Field("expireDate") expireDate: String
    ): Response<UserOrderResponse>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("userID") userID: String
    ): Response<GetSpecificUserResponse>

    @GET("getAllProducts")
    suspend fun getAllProducts(): Response<GetAllProductResponse>

    @GET("getAllUserStock")
    suspend fun getUserStock(): Response<GetUserStockResponse>

    @GET("getUserOrderProduct")
    suspend fun getUserOrderProduct(): Response<GetUserOrderProductResponse>

    @DELETE("deleteSpecificOrderProduct")
    suspend fun deleteSpecificUserOrder(
        @Query("orderID") orderID: String
    ): Response<DeleteSpecificUserOrderResponse>
}