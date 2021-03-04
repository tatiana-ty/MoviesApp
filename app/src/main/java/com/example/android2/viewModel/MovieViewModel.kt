package com.example.android2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android2.model.MovieDTO
import com.example.android2.repository.DetailsRepository
import com.example.android2.repository.DetailsRepositoryImpl
import com.example.android2.repository.RemoteDataSource
import com.example.android2.utils.convertDtoToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MovieViewModel(
private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getLiveData() = detailsLiveData

    fun getMovieFromRemoteSource(movieId: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(movieId, callBack)
    }

    private val callBack = object :
        Callback<MovieDTO> {

        override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
            val serverResponse: MovieDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    getCheckedResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        @Suppress("NullChecksToSafeCall")
        private fun getCheckedResponse(serverResponse: MovieDTO): AppState {
            val title = serverResponse.title
            val rating = serverResponse.rating
            val year = serverResponse.year
            val country = serverResponse.countries
            val genre = serverResponse.genres
            val description = serverResponse.overview
            val image = serverResponse.image
            return if (title == null || rating == null || year == null ||
                country == null || genre == null || description == null || image == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(serverResponse))
            }
        }
    }
}