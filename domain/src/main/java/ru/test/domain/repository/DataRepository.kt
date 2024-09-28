package ru.test.domain.repository

import androidx.lifecycle.LiveData
import ru.test.domain.models.OfferDomain
import ru.test.domain.models.VacancyDomain

interface DataRepository {
    fun getAllOffers(): LiveData<List<OfferDomain>>
    fun getAllVacancies(): LiveData<List<VacancyDomain>>

    suspend fun getDataFromApi()
}