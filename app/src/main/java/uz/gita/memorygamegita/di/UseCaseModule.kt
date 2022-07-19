package uz.gita.memorygamegita.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygamegita.domain.usecase.GetDataByLevelUseCase
import uz.gita.memorygamegita.domain.usecase.impl.GetDataByLevelUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    @Singleton
    fun getUseCase(impl: GetDataByLevelUseCaseImpl): GetDataByLevelUseCase
}