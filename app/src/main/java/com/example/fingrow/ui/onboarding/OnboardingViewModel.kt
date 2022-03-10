package com.example.fingrow.ui.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    private val _pos = MutableLiveData<Int>()
    val pos: LiveData<Int> = _pos

    private val _valid = MutableLiveData<Boolean>()
    val valid: LiveData<Boolean> = _valid

    fun setPos(newPos: Int) {
        _pos.value = newPos
    }

    fun setValid(newVal: Boolean) {
        _valid.value = newVal
    }
}
