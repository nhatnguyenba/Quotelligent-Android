package com.nhatnguyenba.quotelligent.data.remote.mapper

import com.nhatnguyenba.quotelligent.data.remote.dto.AuthorDto
import com.nhatnguyenba.quotelligent.data.remote.dto.CategoryDto
import com.nhatnguyenba.quotelligent.data.remote.dto.FavQsQuoteDto
import com.nhatnguyenba.quotelligent.data.remote.dto.QuoteDto
import com.nhatnguyenba.quotelligent.data.remote.dto.SearchQuoteDto
import com.nhatnguyenba.quotelligent.domain.model.Author
import com.nhatnguyenba.quotelligent.domain.model.Category
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

fun QuoteDto?.toDomain(): Quote {
    if (this == null) return Quote()
    return Quote(
        id = this.id.toString(),
        content = this.content,
        author = this.author,
        tags = this.tags
    )
}

fun AuthorDto?.toDomain(): Author {
    if (this == null) return Author()
    return Author(
        name = this.name,
        count = this.count
    )
}

fun CategoryDto?.toDomain(): Category {
    if (this == null) return Category()
    return Category(
        name = this.name,
        count = this.count
    )
}