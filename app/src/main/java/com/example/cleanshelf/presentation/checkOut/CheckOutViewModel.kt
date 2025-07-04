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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckOutViewModel @Inject constructor(private val mpesaRepository: MpesaRepository): ViewModel(){


    private val _paymentResponse = MutableStateFlow<StkResponse?>(null)
    val paymentResponse: StateFlow<StkResponse?> = _paymentResponse

    val checkOutState : MutableStateFlow<CheckOutState> = MutableStateFlow(CheckOutState())
    val _checkOutState = checkOutState.asStateFlow()

    fun onEvents(events: CheckOutEvents){
        when(events){
            is CheckOutEvents.phone -> {
                checkOutState.value = checkOutState.value.copy(
                    phoneNumber = events.phone
                )
            }
        }
    }


    fun makePayment(phoneNumber: String,amount:Int){
        viewModelScope.launch {


            if(!(phoneNumber.startsWith("07") || phoneNumber.startsWith("254"))){
                checkOutState.value = checkOutState.value.copy(
                    phoneNumberError = "Enter a valid phoneNumber"
                )


            }
            else null
            val request = StkRequest(amount,phoneNumber)
            Log.d("MpesaRepository", "Request: $request")

            _paymentResponse.value = mpesaRepository.initiatePayments(request)
        }
    }



}

data class CheckOutState(
    var phoneNumber: String = "",
    val phoneNumberError : String ?= null
)

sealed class CheckOutEvents{
    data class phone(val phone:String): CheckOutEvents()
}