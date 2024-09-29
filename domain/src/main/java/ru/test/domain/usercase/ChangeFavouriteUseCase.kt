package ru.test.domain.usercase

import ru.test.domain.repository.DataRepository
import java.util.UUID
import javax.inject.Inject

class ChangeFavouriteUseCase @Inject constructor(
    private val repository: DataRepository
) {
    suspend operator fun invoke(id: UUID) {
        repository.changeFavouriteState(id)
    }
}