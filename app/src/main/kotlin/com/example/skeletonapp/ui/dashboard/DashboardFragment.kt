package com.example.skeletonapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skeletonapp.databinding.FragmentDashboardBinding
import com.example.skeletonapp.ui.shared.getFormattedCurrencyString
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            binding.dashboardText.text = it
        }

//        binding.text1.text = getString(R.string.text_with_placeholder, "TEXT")
//        binding.text2.text = getString(R.string.text_without_placeholder)

        //var tmpStr = "Device locale: " + Locale.getDefault().displayName + "\n\n"
        var tmpStr = ""

        val currencySymbol = "$"
        val currencyCode = "USD"

        mapOf<String, Locale>(
            "US" to Locale.US,
            "Canada" to Locale.CANADA,
            "Canada FR" to Locale.CANADA_FRENCH,
        ).forEach { (name, locale) ->
            tmpStr += locale.displayName + "\n" + getFormattedCurrencyString(
                100.99,
                currencySymbol,
                currencyCode,
                locale
            ) + "\n\n"
        }

        binding.text1.text =
            "CurrencyCode: " + currencyCode + "\n\nLocale: " + Locale.getDefault().displayName

        binding.text2.text = tmpStr

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}