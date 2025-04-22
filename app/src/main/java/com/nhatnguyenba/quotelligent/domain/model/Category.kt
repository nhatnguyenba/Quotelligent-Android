package com.nhatnguyenba.quotelligent.domain.model

data class Category(
    val id: String? = "",
    val name: String? = "",
    val count: Long? = 0 // number of quotes in this category
)