package ru.test.data.mapper

import ru.test.domain.models.OfferDomain
import ru.test.data.entity.OfferEntity
import javax.inject.Inject

class OfferMapper @Inject constructor(private val buttonMapper: ButtonMapper){
    fun mapToDomain(entity: OfferEntity): OfferDomain {
        return OfferDomain(
            id = entity.id,
            title = entity.title,
            button = buttonMapper.mapToDomain(entity.button),
            link = entity.link
        )
    }

    fun mapToDomainList(entities: List<OfferEntity>): List<OfferDomain> {
        return entities.map { mapToDomain(it) }
    }
}

class ButtonMapper @Inject constructor() {
    fun mapToDomain(entity: ru.test.data.model.Button?): ru.test.domain.models.ButtonDomain? {
        return entity?.let {
            ru.test.domain.models.ButtonDomain(it.text)
        }
    }

    fun mapToEntity(domain: ru.test.domain.models.ButtonDomain?): ru.test.data.model.Button? {
        return domain?.let {
            ru.test.data.model.Button(it.text)
        }
    }
}