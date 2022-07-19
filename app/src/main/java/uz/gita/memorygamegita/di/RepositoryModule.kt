package uz.gita.memorygamegita.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygamegita.domain.repository.AppRepository
import uz.gita.memorygamegita.domain.repository.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun getRepository(impl: AppRepositoryImpl): AppRepository
}