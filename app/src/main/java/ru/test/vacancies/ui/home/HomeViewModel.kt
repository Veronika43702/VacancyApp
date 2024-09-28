package ru.test.vacancies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.test.domain.models.OfferDomain
import ru.test.domain.models.VacancyDomain
import ru.test.domain.repository.DataRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DataRepository
): ViewModel() {
    // полный список рекомендаций (offer)
    val offers: LiveData<List<OfferDomain>> = repository.getAllOffers()
    // полный список вакансий
    val vacancies: LiveData<List<VacancyDomain>> = repository.getAllVacancies()
    // список избранных вакансий
    val favouriteVacancies: LiveData<List<VacancyDomain>> = repository.getFavouriteVacancies()

    init {
        getData()
    }

    // получение данных по сети
    private fun getData() {
        viewModelScope.launch {
            try {
                repository.getDataFromApi()
            } catch (_: Exception) {

            }
        }
    }

    // изменение поля isFavorite по id вакансии
    fun changeFavouriteStateById(id: UUID) {
        viewModelScope.launch {
            try {
                repository.changeFavouriteState(id)
            } catch (_: Exception) {

            }
        }
    }
}