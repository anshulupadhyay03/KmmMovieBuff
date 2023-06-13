package data.networking

sealed class ApiResult {
    data class Success<T>(val data: T) : ApiResult()

    data class Errorsds(val error: ApiError) : ApiResult()
}


sealed class ApiError {

    object IOError : ApiError()

    object SerializationError : ApiError()

    object UnknownError : ApiError()


}