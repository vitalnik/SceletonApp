package com.example.skeletonapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skeletonapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
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

        val currencyCode = "EUR"

        mapOf<String, Locale>(
            "US" to Locale.US,
            "Canada" to Locale.CANADA,
            "UK" to Locale.UK,
            "Italy" to Locale.ITALY,
            "Japan" to Locale.JAPAN,
            "Canada FR" to Locale.CANADA_FRENCH,
        ).forEach { (name, locale) ->

            var currencySymbol =
                Currency.getInstance(locale).getSymbol(locale)

            tmpStr += locale.displayName + "\n" +
                    100.99.toCurrencyString(currencySymbol) + "\n\n"
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

    fun Double?.toCurrencyString(currencySymbol: String): String {

        val valueToFormat = this ?: return ""

        val currencyFormat = NumberFormat.getCurrencyInstance() as DecimalFormat
        currencyFormat.minimumIntegerDigits = 1
        currencyFormat.minimumFractionDigits = 2
        currencyFormat.maximumFractionDigits = 2

        val decimalFormatSymbols = currencyFormat.decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = currencySymbol

        currencyFormat.decimalFormatSymbols = decimalFormatSymbols

        return currencyFormat.format(valueToFormat)
    }

}