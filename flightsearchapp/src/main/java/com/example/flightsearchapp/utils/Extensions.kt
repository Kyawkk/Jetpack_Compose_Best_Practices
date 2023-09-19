package com.example.flightsearchapp.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> List<T>.toFlowList() : Flow<List<T>> = flow {
    emit(this@toFlowList)
}