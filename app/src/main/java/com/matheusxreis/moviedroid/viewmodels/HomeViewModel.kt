package com.matheusxreis.moviedroid.viewmodels

import androidx.lifecycle.ViewModel
import com.matheusxreis.moviedroid.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {



}