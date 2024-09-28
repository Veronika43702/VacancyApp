package ru.test.data.mapper

import ru.test.data.model.Address
import ru.test.data.model.Experience
import ru.test.data.model.Salary
import ru.test.domain.models.VacancyDomain
import ru.test.data.entity.VacancyEntity
import javax.inject.Inject

class VacancyMapper @Inject constructor(
    private val addressMapper: AddressMapper,
    private val experienceMapper: ExperienceMapper,
    private val salaryMapper: SalaryMapper,
) {
    fun mapToDomain(entity: VacancyEntity): VacancyDomain {
        return VacancyDomain(
            id = entity.id,
            lookingNumber = entity.lookingNumber,
            title = entity.title,
            address = addressMapper.mapToDomain(entity.address),
            company = entity.company,
            experience = experienceMapper.mapToDomain(entity.experience),
            publishedDate = entity.publishedDate,
            isFavorite = entity.isFavorite,
            salary = salaryMapper.mapToDomain(entity.salary),
            schedules = entity.schedules,
            appliedNumber = entity.appliedNumber,
            description = entity.description,
            responsibilities = entity.responsibilities,
            questions = entity.questions
        )
    }

    fun mapToDomainList(entities: List<VacancyEntity>): List<VacancyDomain> {
        return entities.map { mapToDomain(it) }
    }
}

class AddressMapper @Inject constructor() {
    fun mapToDomain(entity: Address): ru.test.domain.models.AddressDomain {
        return entity.let {
            ru.test.domain.models.AddressDomain(it.town, it.street, it.house)
        }
    }

    fun mapToEntity(domain: ru.test.domain.models.AddressDomain): Address {
        return domain.let {
            Address(it.town, it.street, it.house)
        }
    }
}


class ExperienceMapper @Inject constructor() {
    fun mapToDomain(entity: Experience): ru.test.domain.models.ExperienceDomain {
        return entity.let {
            ru.test.domain.models.ExperienceDomain(it.previousText, it.text)
        }
    }

    fun mapToEntity(domain: ru.test.domain.models.ExperienceDomain): Experience {
        return domain.let {
            Experience(it.previousText, it.text)
        }
    }
}

class SalaryMapper @Inject constructor() {
    fun mapToDomain(entity: Salary): ru.test.domain.models.SalaryDomain {
        return entity.let {
            ru.test.domain.models.SalaryDomain(it.short, it.full)
        }
    }

    fun mapToEntity(domain: ru.test.domain.models.SalaryDomain): Salary {
        return domain.let {
            Salary(it.short, it.full)
        }
    }
}