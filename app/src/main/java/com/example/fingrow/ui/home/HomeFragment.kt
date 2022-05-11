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
import com.example.fingrow.R
import com.example.fingrow.databinding.FragmentHomeBinding
import com.example.fingrow.ui.home.cards.CardPagerAdapter
import com.example.fingrow.ui.home.cards.GoalCard
import com.example.fingrow.ui.home.cards.ShadowTransformer
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardAdapter: CardPagerAdapter

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

        cardAdapter = CardPagerAdapter(requireContext(), binding.newGoalsTextView)

        val pref = requireActivity().getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)
        binding.textHome.text = getString(R.string.main_heading, pref.getString("name", ""))
        binding.overallSavings.text =
            getString(R.string.dollar_value, pref.getString("total_saved", ""))
        binding.lastMonthSavings.text =
            getString(R.string.dollar_value, pref.getString("last_month_saved", ""))
        binding.thisMonthSavings.text =
            getString(R.string.dollar_value, pref.getString("this_month_saved", ""))

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

    private fun addGoal(data: Intent?) {
        if (data != null) {
            val title = data.getStringExtra("title")!!
            val amount = data.getStringExtra("amount")!!.toInt()
            val month = data.getStringExtra("month")!!.toInt()
            val year = data.getStringExtra("year")!!.toInt()

            cardAdapter.addCardItem(GoalCard(title, amount, month, year))

            if (!adapterSetup) {
                setupCardAdapter()
            }

            binding.pager.adapter = cardAdapter
            TabLayoutMediator(binding.tabDots, binding.pager) { _, _ -> }.attach()

            binding.newGoalsTextView.text = ""
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