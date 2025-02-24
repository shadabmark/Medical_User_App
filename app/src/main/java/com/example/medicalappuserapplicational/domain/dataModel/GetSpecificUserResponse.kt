package com.example.medicalappuserapplicational.domain.dataModel

data class GetSpecificUserResponse(
    var address: String,
    val block: Int,
    val date_of_account_creation: String,
    val email: String,
    val id: Int,
    val isApproved: Int,
    val level: Int,
    var name: String,
    val password: String,
    var phone_number: String,
    var pinCode: String,
    val user_id: String
)