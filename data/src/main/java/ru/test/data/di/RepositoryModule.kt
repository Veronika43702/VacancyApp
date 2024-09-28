package ru.test.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.test.data.repository.DataRepositoryImpl
import ru.test.domain.repository.DataRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDataRepository(impl: DataRepositoryImpl): DataRepository
}