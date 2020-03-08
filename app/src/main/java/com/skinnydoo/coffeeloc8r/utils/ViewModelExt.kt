package com.skinnydoo.coffeeloc8r.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

fun <T> ViewModel.mutableLiveDataOf() = MutableLiveData<T>()
