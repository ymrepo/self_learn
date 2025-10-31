package com.ym.learn.data.di

import com.ym.learn.data.repository.user.IUserRepository
import com.ym.learn.data.repository.user.NormalUserRepository
import com.ym.learn.data.repository.user.VipUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalUser

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class VipUser

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @NormalUser
    @Binds
    internal abstract fun provideNormalRepo(
        userDataRepository: NormalUserRepository,
    ): IUserRepository

    @VipUser
    @Binds
    internal abstract fun provideVipRepo(
        userDataRepository: VipUserRepository,
    ): IUserRepository
}