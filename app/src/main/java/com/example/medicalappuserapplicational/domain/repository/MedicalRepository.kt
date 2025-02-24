package com.example.medicalappuserapplicational.domain.repository

import com.example.medicalappuserapplicational.domain.dataModel.DeleteSpecificUserOrderResponse
import com.example.medicalappuserapplicational.utility.ResultState
import com.example.medicalappuserapplicational.domain.dataModel.GetAllProductResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.GetSpecificUserResponse
import com.example.medicalappuserapplicational.domain.dataModel.GetUserOrderProductResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.GetUserStockResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.LoginResponse
import com.example.medicalappuserapplicational.domain.dataModel.SignUpResponse
import com.example.medicalappuserapplicational.domain.dataModel.UserOrderResponse
import kotlinx.coroutines.flow.Flow

interface MedicalRepository {

    suspend fun signUpUser(
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ): Flow<ResultState<SignUpResponse>>

    suspend fun userLogin(
        email: String,
        password: String
    ): Flow<ResultState<LoginResponse>>

    suspend fun getSpecificUser(
        userID: String
    ): Flow<ResultState<GetSpecificUserResponse>>

    suspend fun userOrder(
        productName: String,
        productId: String,
        price: String,
        category: String,
        quantity: String,
        expireDate: String
    ): Flow<ResultState<UserOrderResponse>>

    suspend fun getAllProducts(): Flow<ResultState<ArrayList<GetAllProductResponseItem>>>

    suspend fun getUserStock(): Flow<ResultState<ArrayList<GetUserStockResponseItem>>>

    suspend fun getUserOrderProduct(): Flow<ResultState<ArrayList<GetUserOrderProductResponseItem>>>

    suspend fun deleteUserOrder(orderID: String): Flow<ResultState<DeleteSpecificUserOrderResponse>>
}