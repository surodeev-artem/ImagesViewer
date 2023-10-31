package com.surodeevartem.imageviewer.utils

sealed interface Result<T> {

    data class Success<T>(val result: T): Result<T>

    data class Failure<T>(val error: Throwable): Result<T>
}

inline fun <T, R> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (error: Throwable) -> R,
): R = when (this) {
    is Result.Success -> onSuccess(this.result)
    is Result.Failure -> onFailure(this.error)
}
