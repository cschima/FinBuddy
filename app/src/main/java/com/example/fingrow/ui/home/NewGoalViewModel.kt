package com.example.fingrow.ui.home

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewGoalViewModel : ViewModel() {
    private val _pos = MutableLiveData<Int>()
    val pos: LiveData<Int> = _pos

    private val _valid = MutableLiveData<Boolean>()
    val valid: LiveData<Boolean> = _valid

    private val _data = MutableLiveData<Intent>()
    val data: LiveData<Intent> = _data

    fun setPos(newPos: Int) {
        _pos.value = newPos
    }

    fun setValid(newVal: Boolean) {
        _valid.value = newVal
    }

    fun setData(newData: Intent) {
        _data.value = newData
    }
}
