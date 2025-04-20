package com.nhatnguyenba.quotelligent.domain.usecase

import com.nhatnguyenba.quotelligent.domain.repository.QuoteRepository
import javax.inject.Inject

class GetBackgroundImageUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(tags: List<String>): String = repository.getBackgroundImage(tags)
}