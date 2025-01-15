package com.pmob.tiketgbk

data class Ticket(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val tribun: String = "",
    val quantity: Int = 0,
    val matchTitle: String = "",
    val matchDate: String = "",
    val matchTime: String = "",
    val totalPrice: Int = 0,
    val paymentMethod: String = ""
)
