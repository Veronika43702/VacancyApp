package ru.test.vacancies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.test.domain.models.VacancyDomain
import ru.test.vacancies.R
import ru.test.vacancies.databinding.ButtonBinding
import ru.test.vacancies.databinding.CardVacancyBinding
import ru.test.vacancies.utils.DateFormatter
import java.util.UUID

interface VacancyOnInteractionListener {
    fun onRoot(id: UUID) {}
    fun onFavoriteIcon(id: UUID) {}
}

class VacancyWithButtonAdapter(
    private val onInteractionListener: VacancyOnInteractionListener,
    private var vacancyNumber: Int = 0,
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(VacancyDiffCallback()) {
    companion object {
        private const val VIEW_TYPE_VACANCY = 0
        private const val VIEW_TYPE_BUTTON = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.VacancyItem -> VIEW_TYPE_VACANCY
            is ListItem.ButtonItem -> VIEW_TYPE_BUTTON
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_VACANCY -> {
                val binding =
                    CardVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VacancyViewHolder(binding, onInteractionListener)
            }

            VIEW_TYPE_BUTTON -> {
                val binding =
                    ButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ButtonViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VacancyViewHolder -> {
                val vacancyItem = getItem(position) as ListItem.VacancyItem
                holder.bind(vacancyItem.vacancy)
            }

            is ButtonViewHolder -> {
                val buttonItem = getItem(position) as ListItem.ButtonItem
                holder.bind(buttonItem, vacancyNumber)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            when (holder) {
                is VacancyViewHolder -> {
                    holder.bindPayload(payloads[0])
                }
            }
        }
    }

    fun setTotalVacancies(totalVacancies: Int) {
        this.vacancyNumber = totalVacancies
    }
}

class VacancyViewHolder(
    private val binding: CardVacancyBinding,
    private val onInteractionListener: VacancyOnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vacancy: VacancyDomain) {
        binding.apply {
            favouriteIcon.isChecked = vacancy.isFavorite

            val context = root.context
            if (vacancy.lookingNumber != null) {

                val lookingNumber = vacancy.lookingNumber
                val peopleWatchingText = context.resources.getQuantityString(
                    R.plurals.people_watching,
                    lookingNumber ?: 0,
                    lookingNumber
                )

                viewedBy.text = peopleWatchingText
            } else {
                viewedBy.text = null
            }

            title.text = vacancy.title
            salary.text = vacancy.salary.full
            address.text = vacancy.address.town
            company.text = vacancy.company
            experience.text = vacancy.experience.previewText

            val dateFormatter = DateFormatter()
            val publishedDayAndMonth = dateFormatter.formatDate(vacancy.publishedDate)
            published.text = context.resources.getString(R.string.published, publishedDayAndMonth)

            root.setOnClickListener { onInteractionListener.onRoot(vacancy.id) }
            favouriteIcon.setOnClickListener { onInteractionListener.onFavoriteIcon(vacancy.id) }
            button.setOnClickListener { }
        }
    }

    fun bindPayload(payload: Any?) {
        if (payload is Boolean) {
            binding.favouriteIcon.isChecked = payload
        }
    }
}

class ButtonViewHolder(
    private val binding: ButtonBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(buttonItem: ListItem.ButtonItem, vacancyNumber: Int) {
        val context = binding.root.context
        val vacancyNumberText = context.resources.getQuantityString(
            R.plurals.more_vacancies,
            vacancyNumber - 3,
            vacancyNumber - 3
        )

        binding.buttonViewMore.text = vacancyNumberText
        binding.buttonViewMore.setOnClickListener {
            buttonItem.onClick()
        }
    }
}

class VacancyDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return when {
            oldItem is ListItem.VacancyItem && newItem is ListItem.VacancyItem -> {
                oldItem.vacancy == newItem.vacancy
            }
            oldItem is ListItem.ButtonItem && newItem is ListItem.ButtonItem -> oldItem == newItem
            else -> false
        }
    }

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return when {
            oldItem is ListItem.VacancyItem && newItem is ListItem.VacancyItem -> oldItem.vacancy.id == newItem.vacancy.id
            oldItem is ListItem.ButtonItem && newItem is ListItem.ButtonItem -> oldItem == newItem
            else -> false
        }
    }

    override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any? {
        return when {
            oldItem is ListItem.VacancyItem && newItem is ListItem.VacancyItem -> {
                if (oldItem.vacancy.isFavorite != newItem.vacancy.isFavorite) {
                    return newItem.vacancy.isFavorite
                }
                null
            }
            else -> null
        }
    }
}

sealed class ListItem {
    data class VacancyItem(val vacancy: VacancyDomain) : ListItem()
    data class ButtonItem(val onClick: () -> Unit) : ListItem()
}