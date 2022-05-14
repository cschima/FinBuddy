package com.example.fingrow.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.R
import com.example.fingrow.data.goals.Goal
import com.example.fingrow.data.goals.GoalViewModel
import com.example.fingrow.databinding.FragmentHomeBinding
import com.example.fingrow.ui.home.cards.CardPagerAdapter
import com.example.fingrow.ui.home.cards.GoalCard
import com.example.fingrow.ui.home.cards.ShadowTransformer
import com.google.android.material.tabs.TabLayoutMediator
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardAdapter: CardPagerAdapter
    private lateinit var goalViewModel: GoalViewModel

    private var adapterSetup = false

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                addGoal(result.data)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        cardAdapter = CardPagerAdapter(requireContext(), this, binding.newGoalsTextView)
        adapterSetup = false

        goalViewModel = ViewModelProvider(this)[GoalViewModel::class.java]

        val email = requireActivity().getSharedPreferences(
            "login",
            AppCompatActivity.MODE_PRIVATE
        ).getString("email", "")!!
        val pref = requireActivity().getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)
        binding.textHome.text = getString(R.string.main_heading, pref.getString("name", ""))

        val total = NumberFormat.getCurrencyInstance()
            .format((pref.getString("total_saved", "")!!.toDouble()))
        binding.overallSavings.text = total.substring(0, total.length - 3)

        val thisMonth = NumberFormat.getCurrencyInstance()
            .format((pref.getString("this_month_saved", "")!!.toDouble()))
        binding.thisMonthSavings.text = thisMonth.substring(0, thisMonth.length - 3)

        val activeGoals = goalViewModel.activeUserGoalCount(email)
        binding.activeSavings.text = if (activeGoals == 0) "$0" else
            getString(R.string.dollar_value, String.format("%,d", goalViewModel.getActiveSavings(email)))
        binding.activeGoal.text = if (activeGoals == 0) "/0" else
            getString(R.string.slash_value, String.format("$%,d", goalViewModel.getActiveTotal(email)))

        loadGoals()

        val progressBack = binding.progressBack
        val progress = binding.progress
        progress.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                progressBack.viewTreeObserver
                    .removeOnGlobalLayoutListener(this)
                progressBack.indicatorSize = progressBack.width
                progress.indicatorSize = progress.width
            }
        })

        binding.addGoalButton.setOnClickListener {
            val intent = Intent(context, NewGoalActivity::class.java)
            resultLauncher.launch(intent)
        }

        return binding.root
    }

    private fun loadGoals() {
        val email = requireActivity().getSharedPreferences(
            "login",
            AppCompatActivity.MODE_PRIVATE
        ).getString("email", "")!!
        if (goalViewModel.activeUserGoalCount(email) == 0
        ) {
            return
        }

        val formatter = SimpleDateFormat.getDateInstance()

        for (g in goalViewModel.getAllActiveUserGoals(email)) {
            var gMonth = -1
            var gYear = -1
            if (g.end_date != "") {
                val c = Calendar.getInstance()
                c.time = formatter.parse(g.end_date)!!
                gMonth = c.get(Calendar.MONTH) + 1
                gYear = c.get(Calendar.YEAR)
            }
            cardAdapter.addCardItem(
                GoalCard(
                    g.title,
                    g.total_amount,
                    gMonth,
                    gYear,
                    g.current_amount
                )
            )
        }

        if (!adapterSetup) {
            setupCardAdapter()
        }

        binding.pager.adapter = cardAdapter
        TabLayoutMediator(binding.tabDots, binding.pager) { _, _ -> }.attach()

        binding.newGoalsTextView.text = ""
    }

    private fun addGoal(data: Intent?) {
        if (data != null) {
            val title = data.getStringExtra("title")!!
            val amount = data.getStringExtra("amount")!!.toDouble()
            val month = data.getStringExtra("month")!!.toInt()
            val year = data.getStringExtra("year")!!.toInt()

            val startDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateInstance() //or use getDateTimeInstance()
            val formattedDate = formatter.format(startDate)

            var formattedEndDate = ""
            if (month != -1 && year != -1) {
                val c = Calendar.getInstance()
                c.set(year, month - 1, 1)
                formattedEndDate = formatter.format(c.time)
            }

            val email = requireActivity().getSharedPreferences(
                "login",
                AppCompatActivity.MODE_PRIVATE
            ).getString("email", "")!!
            cardAdapter.addCardItem(GoalCard(title, amount, month, year))
            goalViewModel.addGoal(
                Goal(
                    0, email,
                    title, amount, formattedDate, formattedEndDate, 0.0, true
                )
            )

            if (!adapterSetup) {
                setupCardAdapter()
            }

            binding.pager.adapter = cardAdapter
            TabLayoutMediator(binding.tabDots, binding.pager) { _, _ -> }.attach()

            binding.newGoalsTextView.text = ""

            binding.activeSavings.text =
                getString(R.string.dollar_value, String.format("%,d", goalViewModel.getActiveSavings(email)))
            binding.activeGoal.text =
                getString(R.string.slash_value, String.format("$%,d", goalViewModel.getActiveTotal(email)))
        }
    }

    private fun setupCardAdapter() {
        val cardShadowTransformer = ShadowTransformer(binding.pager, cardAdapter)
        cardShadowTransformer.enableScaling(true)
        binding.pager.setPageTransformer(cardShadowTransformer)
        binding.pager.offscreenPageLimit = 3
        adapterSetup = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}