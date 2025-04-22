package com.nhatnguyenba.quotelligent.data.remote.dto

data class FiltersResponse(
    val authors: List<AuthorDto>,
    val tags: List<CategoryDto>,
    val users: List<UserDto>,
)

data class AuthorDto(
    val name: String,
    val permalink: String,
    val count: Long,
)

data class CategoryDto(
    val name: String,
    val permalink: String,
    val count: Long,
)

data class UserDto(
    val name: String,
    val permalink: String,
    val count: Long,
)
