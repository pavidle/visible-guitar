package com.example.visible_guitar.model.events

sealed class BarEvent {
    data class Show(val message: String) : BarEvent()
}