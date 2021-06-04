package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel : ViewModel() {
    // このgetter,setterの組み合わせを表現できるアノテーションがあってもいいと思う
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
    }

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
    }

    private val _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        // こいつだけ、initのタイミングじゃないと初期化できないのか
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    // 現実には出荷日は工場側と調整した値になるから、api(DB)から取得しないといけない
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
}