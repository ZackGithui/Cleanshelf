package com.example.cleanshelf.di

import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebase(authViewModel: AuthViewModel): AuthViewModel {
        return authViewModel
    }

}