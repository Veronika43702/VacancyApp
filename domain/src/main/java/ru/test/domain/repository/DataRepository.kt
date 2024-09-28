package ru.test.domain.repository

import androidx.lifecycle.LiveData
import ru.test.domain.models.OfferDomain
import ru.test.domain.models.VacancyDomain
import java.util.UUID

interface DataRepository {
    fun getAllOffers(): LiveData<List<OfferDomain>>
    fun getAllVacancies(): LiveData<List<VacancyDomain>>

    suspend fun getDataFromApi()
    suspend fun changeFavouriteState(id: UUID)
}