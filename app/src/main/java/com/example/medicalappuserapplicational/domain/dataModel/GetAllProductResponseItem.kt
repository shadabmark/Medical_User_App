package com.example.medicalappuserapplicational.domain.dataModel

data class GetAllProductResponseItem(
    val category: String,
    val expire_date: String,
    val id: Int,
    val price: Double,
    val product_id: String,
    val product_image: String,
    val product_name: String,
    val stock: Int
)