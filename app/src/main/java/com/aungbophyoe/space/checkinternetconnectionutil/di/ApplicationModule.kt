package com.aungbophyoe.space.checkinternetconnectionutil.di

import android.content.Context
import android.net.ConnectivityManager
import com.aungbophyoe.space.checkinternetconnectionutil.util.NetworkConnectivityUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideNetworkConnectivityUtil(@ApplicationContext context: Context): NetworkConnectivityUtil {
        return NetworkConnectivityUtil(context)
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager{
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}