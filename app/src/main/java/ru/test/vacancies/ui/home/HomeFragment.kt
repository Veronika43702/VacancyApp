package ru.test.vacancies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.test.vacancies.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            if (offers.isNotEmpty()) {
                println("offer: ${offers.first().title}")
            }
        }

        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            if (vacancies.isNotEmpty()) {
                println("vac: ${vacancies.first().id}")
            }
        }

        binding.textHome.text = "Home"
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}