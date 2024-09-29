package ru.test.domain.usercase

import androidx.lifecycle.LiveData
import ru.test.domain.models.OfferDomain
import ru.test.domain.repository.DataRepository
import javax.inject.Inject

class GetAllOffersUseCase @Inject constructor(
    private val repository: DataRepository
) {
    operator fun invoke(): LiveData<List<OfferDomain>> {
        return repository.getAllOffers()
    }
}