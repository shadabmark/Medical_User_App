package com.example.medicalappuserapplicational.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalappuserapplicational.UserPreferenceManager
import com.example.medicalappuserapplicational.domain.dataModel.DeleteSpecificUserOrderResponse
import com.example.medicalappuserapplicational.utility.ResultState
import com.example.medicalappuserapplicational.domain.repository.MedicalRepository
import com.example.medicalappuserapplicational.domain.dataModel.GetAllProductResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.GetSpecificUserResponse
import com.example.medicalappuserapplicational.domain.dataModel.GetUserOrderProductResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.GetUserStockResponseItem
import com.example.medicalappuserapplicational.domain.dataModel.LoginResponse
import com.example.medicalappuserapplicational.domain.dataModel.SignUpResponse
import com.example.medicalappuserapplicational.domain.dataModel.UserOrderResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: MedicalRepository,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _loginUserState = MutableStateFlow(UserLoginState())
    val loginUserState = _loginUserState.asStateFlow()

    private val _getSpecificUserState = MutableStateFlow(GetSpecificUserState())
    val getSpecificUserState = _getSpecificUserState.asStateFlow()

    private val _getAllProductState = MutableStateFlow(GetAllProductsState())
    val getAllProductState = _getAllProductState.asStateFlow()

    private val _getUserStockState = MutableStateFlow(GetUserStockState())
    val getUserStockState = _getUserStockState.asStateFlow()

    private val _getUserOrderState = MutableStateFlow(GetUserOrderState())
    val getUserOrderState = _getUserOrderState.asStateFlow()

    private val _userOrderState = MutableStateFlow(UserOrderState())
    val userOrderState = _userOrderState.asStateFlow()

    private val _deleteUserOrderState = MutableStateFlow(DeleteUserOrderState())
    val deleteUserOrderState = _deleteUserOrderState.asStateFlow()


    fun signUp(
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ) {
        viewModelScope.launch {
            repository.signUpUser(name, password, email, phoneNumber, address, pinCode)
                .collect { state ->
                    when (state) {
                        is ResultState.Loading -> {
                            _signUpState.value = SignUpState(isLoading = true)
                        }

                        is ResultState.Success -> {
                            _signUpState.value = SignUpState(data = state.data, isLoading = false)
                        }

                        is ResultState.Error -> {
                            _signUpState.value =
                                SignUpState(error = state.message, isLoading = false)
                        }
                    }
                }
        }
    }

    fun userLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.userLogin(email, password).collect { state ->
                when (state) {
                    is ResultState.Loading -> {
                        _loginUserState.value = UserLoginState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        val userId = state.data.message
                        if (state.data.message == userId) {
                            userPreferenceManager.saveUserId(userId)
                            _loginUserState.value =
                                UserLoginState(data = state.data, isLoading = false)
                        } else {
                            _loginUserState.value = UserLoginState(
                                error = "Invalid email or password",
                                isLoading = false
                            )
                        }
                    }

                    is ResultState.Error -> {
                        _loginUserState.value =
                            UserLoginState(error = state.message, isLoading = false)
                    }
                }
            }
        }
    }

    private fun getSpecificUser(userID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSpecificUser(userID).collectLatest { state ->
                when (state) {
                    is ResultState.Loading -> {
                        _getSpecificUserState.value = GetSpecificUserState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        val userId = state.data.user_id
                        userPreferenceManager.saveUserId(userId)
                        _getSpecificUserState.value =
                            GetSpecificUserState(data = state.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getSpecificUserState.value =
                            GetSpecificUserState(error = state.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun fetchCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userPreferenceManager.userId.firstOrNull()
            Log.d("fetchCurrentUser", "userID:  $userId")

            if (userId.isNullOrBlank()) {
                _getSpecificUserState.value = GetSpecificUserState(error = "User Id not found")
            } else {
                getSpecificUser(userID = userId)
            }
        }
    }

    init {
        getAllProducts()
        getUserStock()
        getUserOrderProduct()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            repository.getAllProducts().collect { state ->
                when (state) {
                    is ResultState.Loading -> {
                        _getAllProductState.value = GetAllProductsState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllProductState.value =
                            GetAllProductsState(data = state.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getAllProductState.value =
                            GetAllProductsState(error = state.message, isLoading = false)
                    }
                }
            }
        }
    }

    private fun getUserStock() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserStock().collectLatest { userStockState ->
                when (userStockState) {
                    is ResultState.Loading -> {
                        _getUserStockState.value = GetUserStockState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getUserStockState.value =
                            GetUserStockState(data = userStockState.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getUserStockState.value =
                            GetUserStockState(error = userStockState.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun getUserOrderProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserOrderProduct().collectLatest { state ->
                when (state) {
                    is ResultState.Loading -> {
                        _getUserOrderState.value = GetUserOrderState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getUserOrderState.value =
                            GetUserOrderState(data = state.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getUserOrderState.value =
                            GetUserOrderState(error = state.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun userOrder(
        productName: String,
        productId: String,
        price: String,
        category: String,
        quantity: String,
        expireDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.userOrder(productName, productId, price, category, quantity, expireDate)
                .collect { state ->
                    when (state) {
                        is ResultState.Error -> {
                            _userOrderState.value =
                                UserOrderState(error = state.message, isLoading = false)
                        }

                        is ResultState.Loading -> {
                            _userOrderState.value = UserOrderState(isLoading = true)
                        }

                        is ResultState.Success -> {
                            _userOrderState.value =
                                UserOrderState(data = state.data, isLoading = false)
                        }
                    }
                }
        }
    }

    fun deleteUserOrder(orderID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserOrder(orderID).collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> _deleteUserOrderState.value =
                        DeleteUserOrderState(isLoading = true)

                    is ResultState.Success -> {
                        _deleteUserOrderState.value = DeleteUserOrderState(data = result.data)
                        delay(500)
                        _deleteUserOrderState.value = DeleteUserOrderState()
                    }

                    is ResultState.Error -> _deleteUserOrderState.value =
                        DeleteUserOrderState(error = result.message)
                }
            }
        }
    }
}

data class SignUpState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: SignUpResponse? = null
)

data class UserLoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: LoginResponse? = null
)

data class GetSpecificUserState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: GetSpecificUserResponse? = null
)

data class GetAllProductsState(
    val data: List<GetAllProductResponseItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class GetUserStockState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<GetUserStockResponseItem> = emptyList()
)

data class UserOrderState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: UserOrderResponse? = null
)

data class DeleteUserOrderState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: DeleteSpecificUserOrderResponse? = null
)

data class GetUserOrderState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<GetUserOrderProductResponseItem> = emptyList()
)

