package com.nhatnguyenba.quotelligent.domain.model

data class Author(
    val id: String? = "",
    val name: String? = "",
    val imageUrl: String? = "",
    val count: Long? = 0 // number of quotes by this author
)