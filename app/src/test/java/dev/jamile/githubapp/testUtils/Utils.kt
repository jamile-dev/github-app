package dev.jamile.githubapp.testUtils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> createFlowTest(valueToEmit: T): Flow<T> = flow {
    emit(valueToEmit)
}