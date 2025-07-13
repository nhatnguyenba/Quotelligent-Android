package com.nhatnguyenba.quotelligent.di

import android.content.Context
import com.nhatnguyenba.quotelligent.data.local.dao.CollectionDao
import com.nhatnguyenba.quotelligent.data.local.dao.QuoteCollectionCrossRefDao
import com.nhatnguyenba.quotelligent.data.local.dao.QuoteDao
import com.nhatnguyenba.quotelligent.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideQuoteDao(appDatabase: AppDatabase): QuoteDao = appDatabase.quoteDao()

    @Provides
    @Singleton
    fun provideCollectionDao(appDatabase: AppDatabase): CollectionDao = appDatabase.collectionDao()

    @Provides
    @Singleton
    fun provideQuoteCollectionCrossRefDao(appDatabase: AppDatabase): QuoteCollectionCrossRefDao = appDatabase.crossRefDao()
}