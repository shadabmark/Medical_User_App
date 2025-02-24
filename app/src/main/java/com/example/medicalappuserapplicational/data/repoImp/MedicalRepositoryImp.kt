package com.example.medicalappuserapplicational.data.repoImp

import com.example.medicalappuserapplicational.utility.ResultState
import com.example.medicalappuserapplicational.data.remote.MedicalApi
import com.example.medicalappuserapplicational.domain.dataModel.DeleteSpecificUserOrderResponse
import com.example.medicalappuserapplicational.domain.repository.MedicalRepository
import com.example.medicalappuserapplicational.domain.dataModel.GetAllProductResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.GetSpecificUserResponse
import com.example.medicalappuserapplicational.domain.dataModel.GetUserOrderProductResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.GetUserStockResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.LoginResponse
import com.example.medicalappuserapplicational.domain.dataModel.SignUpResponse
import com.example.medicalappuserapplicational.domain.dataModel.UserOrderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MedicalRepositoryImp @Inject constructor(
    private val api: MedicalApi
) : MedicalRepository {

    override suspend fun signUpUser(
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ): Flow<ResultState<SignUpResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = api.signUpUser(name, password, email, phoneNumber, address, pinCode)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                emit(ResultState.Success(body))
            } else {
                emit(ResultState.Error(response.errorBody()?.string() ?: "Signup Failed"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }

    override suspend fun userLogin(
        email: String,
        password: String
    ): Flow<ResultState<LoginResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = api.userLogin(email, password)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                emit(ResultState.Success(body))
            } else {
                emit(ResultState.Error(response.errorBody()?.string() ?: "Login Failed"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }

    override suspend fun getSpecificUser(userID: String): Flow<ResultState<GetSpecificUserResponse>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = api.getSpecificUser(userID)
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    emit(ResultState.Success(body))
                } else {
                    emit(
                        ResultState.Error(
                            response.errorBody()?.string() ?: "Get Specific User Failed"
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    override suspend fun userOrder(
        productName: String,
        productId: String,
        price: String,
        category: String,
        quantity: String,
        expireDate: String
    ): Flow<ResultState<UserOrderResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response =
                api.userOrder(productName, productId, price, category, quantity, expireDate)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                emit(ResultState.Success(body))
            } else {
                emit(ResultState.Error(response.errorBody()?.string() ?: "Order Failed"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }

    override suspend fun getAllProducts(): Flow<ResultState<ArrayList<GetAllProductResponseItem>>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = api.getAllProducts()
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    emit(ResultState.Success(body))
                } else {
                    emit(
                        ResultState.Error(
                            response.errorBody()?.string() ?: "Get All Products Failed"
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    override suspend fun getUserStock(): Flow<ResultState<ArrayList<GetUserStockResponseItem>>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = api.getUserStock()
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    emit(ResultState.Success(body))
                } else {
                    emit(
                        ResultState.Error(
                            response.errorBody()?.string() ?: "Get User Stock Failed"
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    override suspend fun getUserOrderProduct(): Flow<ResultState<ArrayList<GetUserOrderProductResponseItem>>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = api.getUserOrderProduct()
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    emit(ResultState.Success(body))
                } else {
                    emit(
                        ResultState.Error(
                            response.errorBody()?.string() ?: "Get User Order Failed"
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    override suspend fun deleteUserOrder(orderID: String): Flow<ResultState<DeleteSpecificUserOrderResponse>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = api.deleteSpecificUserOrder(orderID)
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    emit(ResultState.Success(body))
                } else {
                    emit(ResultState.Error(response.errorBody()?.string() ?: "Delete Failed"))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: "unknown Error"))
            }
        }
}