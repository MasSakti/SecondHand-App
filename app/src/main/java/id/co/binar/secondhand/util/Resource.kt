package id.co.binar.secondhand.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(code: Int?, message: String) : Resource<T>(code = code, message = message)
    class Loading<T> : Resource<T>()
}
