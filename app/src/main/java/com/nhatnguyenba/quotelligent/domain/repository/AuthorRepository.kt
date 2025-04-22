package com.nhatnguyenba.quotelligent.domain.repository

import com.nhatnguyenba.quotelligent.domain.model.Author

interface AuthorRepository {
    suspend fun searchAuthors(keyword: String): List<Author>
}