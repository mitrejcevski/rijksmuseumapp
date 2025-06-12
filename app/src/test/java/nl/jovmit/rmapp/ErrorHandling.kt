package nl.jovmit.rmapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import okio.IOException
import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Path

//region Network
@Serializable
data class UserResponse(
  val id: String,
  val name: String,
  val address: String,
  val age: String,
  val avatar: String
)

interface UsersApi {
  @GET("/v1/users/{userId}")
  suspend fun loadUser(@Path("userId") userId: String): UserResponse

  @GET("/v1/users/{userId}")
  fun fetchUser(@Path("userId") userId: String): Flow<UserResponse>
}
//endregion

data class User(
  val id: String,
  val name: String,
  val avatar: String
)

class UseCaseOrRepository(
  private val api: UsersApi
) {

  suspend fun getUser(userId: String): Result<User> {
    return createNetworkCall { api.loadUser(userId).toUser() }
  }

  fun loadUser(userId: String): Flow<Result<User>> = flow {
    api.fetchUser(userId).map { it.toUser() }
      .catch { exception ->

      }
  }

  private fun UserResponse.toUser(): User {
    return User(id = id, name = name, avatar = avatar)
  }
}

suspend fun <T> createNetworkCall(block: suspend () -> T): Result<T> {
  return try {
    Result.Success(block())
  } catch (httpException: HttpException) {
    when (httpException.code()) {
      401 -> Result.Error.BackendError.Unauthorized
      404 -> Result.Error.BackendError.NotFound
      else -> Result.Error.BackendError.Unavailable
    }
  } catch (_: IOException) {
    Result.Error.OfflineError
  }
}

sealed class Result<out T> {

  data class Success<T>(val data: T) : Result<T>()

  sealed class Error : Result<Nothing>() {

    sealed class BackendError : Error() {
      data object Unauthorized : BackendError()
      data object NotFound : BackendError()
      data object Unavailable : BackendError()
    }

    data object OfflineError : Error()
  }
}

class UserViewModel(
  private val useCaseOrRepo: UseCaseOrRepository
) : ViewModel() {

  private val _state = MutableStateFlow<User?>(null)
  val state = _state.onStart {
    loadUser()
  }.catch { exception ->

  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000),null)

  fun loadUser() {
    viewModelScope.launch {
      val userResult = useCaseOrRepo.getUser("")
      when (userResult) {
        is Result.Success -> { println(userResult.data) }
        is Result.Error -> {  }
      }
    }
  }
}