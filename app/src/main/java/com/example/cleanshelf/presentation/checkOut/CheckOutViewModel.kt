package com.example.cleanshelf.presentation.checkOut

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.remote.Dto.StkRequest
import com.example.cleanshelf.data.remote.Dto.StkResponse

import com.example.cleanshelf.data.repository.MpesaRepository
import com.squareup.moshi.Json
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckOutViewModel @Inject constructor(private val mpesaRepository: MpesaRepository): ViewModel(){


    private val _paymentResponse = MutableStateFlow<StkResponse?>(null)
    val paymentResponse: StateFlow<StkResponse?> = _paymentResponse


    fun makePayment(phoneNumber:String,amount:Int){
        viewModelScope.launch {
            val request = StkRequest(amount,phoneNumber)
            Log.d("MpesaRepository", "Request: $request")

            _paymentResponse.value = mpesaRepository.initiatePayments(request)
        }
    }



}

