package com.nhatnguyenba.quotelligent.di

import com.nhatnguyenba.quotelligent.data.remote.api.FavQsQuoteApiService
import com.nhatnguyenba.quotelligent.data.remote.api.PexelsApiService
import com.nhatnguyenba.quotelligent.data.repository.AuthorRepositoryImpl
import com.nhatnguyenba.quotelligent.data.repository.CategoryRepositoryImpl
import com.nhatnguyenba.quotelligent.data.repository.QuoteRepositoryImpl
import com.nhatnguyenba.quotelligent.domain.repository.AuthorRepository
import com.nhatnguyenba.quotelligent.domain.repository.CategoryRepository
import com.nhatnguyenba.quotelligent.domain.repository.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideQuoteRepository(
        quoteApi: FavQsQuoteApiService,
        pexelsApi: PexelsApiService
    ): QuoteRepository = QuoteRepositoryImpl(quoteApi, pexelsApi)

    @Provides
    @Singleton
    fun provideAuthorRepository(
        quoteApi: FavQsQuoteApiService,
    ): AuthorRepository = AuthorRepositoryImpl(quoteApi)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        quoteApi: FavQsQuoteApiService,
    ): CategoryRepository = CategoryRepositoryImpl(quoteApi)
}
