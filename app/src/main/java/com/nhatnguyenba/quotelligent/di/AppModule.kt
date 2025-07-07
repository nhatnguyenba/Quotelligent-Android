package com.nhatnguyenba.quotelligent.di

import android.content.Context
import com.nhatnguyenba.quotelligent.presentation.theme.ThemeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideThemeManager(@ApplicationContext context: Context): ThemeManager {
        return ThemeManager(context)
    }
}