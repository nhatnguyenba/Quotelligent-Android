package com.nhatnguyenba.quotelligent.data.remote.mapper

import com.nhatnguyenba.quotelligent.data.remote.dto.FavQsQuoteDto
import com.nhatnguyenba.quotelligent.domain.model.Quote

fun FavQsQuoteDto?.toDomain(): Quote {
    if (this == null) return Quote()
    return Quote(
        id = this.quote.id.toString(),
        content = this.quote.content,
        author = this.quote.author,
        tags = this.quote.tags
    )
}