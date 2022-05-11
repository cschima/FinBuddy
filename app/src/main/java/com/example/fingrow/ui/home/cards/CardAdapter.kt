package com.example.fingrow.ui.home.cards

import androidx.cardview.widget.CardView

interface CardAdapter {
    fun getBaseElevation(): Float
    fun getCardViewAt(position: Int): CardView?
    fun getItemCount(): Int

    companion object {
        const val MAX_ELEVATION_FACTOR = 8
    }
}