package com.example.fingrow.ui.home.cards

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fingrow.R
import com.example.fingrow.ui.home.ExistingGoalActivity


class CardPagerAdapter(private val context: Context, val parent: Fragment, private val emptyTextView: TextView? = null) :
    RecyclerView.Adapter<CardPagerAdapter.MyHolder>(), CardAdapter {
    private val mViews: MutableList<CardView?>
    private val mData: MutableList<GoalCard>
    private var baseElevation = 0f

    fun addCardItem(item: GoalCard) {
        mViews.add(null)
        mData.add(item)
    }

    override fun getBaseElevation(): Float {
        return baseElevation
    }

    override fun getCardViewAt(position: Int): CardView? {
        return mViews[position]
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    init {
        mData = ArrayList()
        mViews = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(context).inflate(R.layout.card_adapter, parent, false)
        )
    }

//    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val goalCard = mData[position]

        if (goalCard.isFlexible()) {
            holder.youHaveText.text = context.getString(R.string.this_is_a)
            holder.timeLeftText.text = context.getString(R.string.flexible_deadline)
            holder.tryToSaveText.text = context.getString(R.string.dont_forget_to_keep_saving)
            holder.savingsNeeded.text = ""
        } else {
            holder.youHaveText.text = context.getString(R.string.you_have)
            holder.timeLeftText.text =
                context.getString(R.string.value_left, goalCard.getTimeLeft())
            holder.tryToSaveText.text = context.getString(R.string.try_to_save)
            holder.savingsNeeded.text =
                context.getString(R.string.dollar_value, goalCard.getSavingsNeeded())
        }
        holder.title.text = goalCard.getTitle()
        holder.currentAmount.text =
            context.getString(R.string.dollar_value, goalCard.getCurrentAmount())
        holder.totalAmount.text = context.getString(R.string.slash_value, goalCard.getTotalAmount())
        holder.progressBar.progress = goalCard.getProgressPercent()
        holder.progressPercentage.text = goalCard.getProgressPercent().toString() + "%"

        if (baseElevation == 0f) {
            baseElevation = holder.card.cardElevation
        }
        holder.card.maxCardElevation = baseElevation * CardAdapter.MAX_ELEVATION_FACTOR

        holder.card.setOnClickListener {
            val intent = Intent(parent.activity, ExistingGoalActivity::class.java)
            intent.putExtra("title", goalCard.getTitle())
            intent.putExtra("current_amount", goalCard.getCurrentAmount())
            intent.putExtra("total_amount", goalCard.getTotalAmount())
            intent.putExtra("end_date", goalCard.getEndDate())
            intent.putExtra("progress", goalCard.getProgressPercent().toString())
            intent.putExtra("savings_needed", if (goalCard.isFlexible()) "" else goalCard.getSavingsNeeded())
            parent.startActivity(intent)
//            mData.removeAt(position)
//            notifyDataSetChanged()
        }
        mViews[position] = holder.card
    }

    override fun onViewRecycled(holder: MyHolder) {
        super.onViewRecycled(holder)
        mViews[mViews.indexOf(holder.card)] = null
        if (itemCount == 0 && emptyTextView != null) {
            emptyTextView.text = context.getString(R.string.new_goals_will_appear_here)
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cardView)
        val youHaveText: TextView = itemView.findViewById(R.id.youHaveTextView)
        val timeLeftText: TextView = itemView.findViewById(R.id.timeLeftTextView)
        val tryToSaveText: TextView = itemView.findViewById(R.id.tryToSaveTextView)
        val savingsNeeded: TextView = itemView.findViewById(R.id.savingAmountTextView)
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val currentAmount: TextView = itemView.findViewById(R.id.currentAmountTextView)
        val totalAmount: TextView = itemView.findViewById(R.id.totalAmountTextView)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress)
        val progressPercentage: TextView = itemView.findViewById(R.id.progressPercentageTextView)
    }
}