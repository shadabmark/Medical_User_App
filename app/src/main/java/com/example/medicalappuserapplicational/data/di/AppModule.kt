package com.example.medicalappuserapplicational.data.di

import android.content.Context
import com.example.medicalappuserapplicational.UserPreferenceManager
import com.example.medicalappuserapplicational.utility.BASE_URL
import com.example.medicalappuserapplicational.data.repoImp.MedicalRepositoryImp
import com.example.medicalappuserapplicational.data.remote.MedicalApi
import com.example.medicalappuserapplicational.domain.repository.MedicalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMedicalApi(): MedicalApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MedicalApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMedicalRepository(api: MedicalApi): MedicalRepository {
        return MedicalRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun providePreferenceManager(
        @ApplicationContext context: Context
    ) = UserPreferenceManager(context = context)
}