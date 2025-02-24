package com.example.medicalappuserapplicational.domain.dataModel

data class GetUserOrderProductResponseItem(
    val category: String,
    val expire_date: String,
    val id: Int,
    val order_id: String,
    val order_status: Int,
    val price: Double,
    val product_id: String,
    val product_name: String,
    val quantity: Int
)