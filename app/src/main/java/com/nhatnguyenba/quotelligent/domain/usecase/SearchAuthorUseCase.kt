package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.domain.model.Author
import com.nhatnguyenba.quotelligent.domain.repository.AuthorRepository
import javax.inject.Inject

class SearchAuthorUseCase @Inject constructor(
    private val repository: AuthorRepository
) {
    suspend operator fun invoke(keyword: String): List<Author> = repository.searchAuthors(keyword)
}