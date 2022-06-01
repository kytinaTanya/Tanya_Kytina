package com.example.myapplication.states

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.pojo.BaseItemDetails
import com.example.myapplication.models.pojo.view.MovieView
import com.example.myapplication.models.pojo.view.PersonView
import com.example.myapplication.models.pojo.view.TvView

sealed class FilmInfoStates : ViewState {
    data class Success(val data: MovieView) : FilmInfoStates()
    object Error : FilmInfoStates()
    object Loading : FilmInfoStates()
}

sealed class CollectionInfoState : ViewState {
    data class Success(val data: BaseItemDetails.MovieCollection) : CollectionInfoState()
    object Error : CollectionInfoState()
    object Loading : CollectionInfoState()
}

sealed class SeasonInfoState : ViewState {
    data class Success(val data: BaseItemDetails.SeasonDetails) : SeasonInfoState()
    object Error : SeasonInfoState()
    object Loading : SeasonInfoState()
}

sealed class EpisodeInfoState : ViewState {
    data class Success(val data: BaseItemDetails.EpisodeDetails) : EpisodeInfoState()
    object Error : EpisodeInfoState()
    object Loading : EpisodeInfoState()
}

sealed class TvInfoState : ViewState {
    data class Success(val data: TvView) : TvInfoState()
    object Error : TvInfoState()
    object Loading : TvInfoState()
}

sealed class PersonInfoState : ViewState {
    data class Success(val data: PersonView) : PersonInfoState()
    object Error : PersonInfoState()
    object Loading : PersonInfoState()
}

sealed class RatedState : ViewState {
    data class Success(val result: PostResponseStatus) : RatedState()
    object Error : RatedState()
    object Loading : RatedState()
}

sealed class MarkedState : ViewState {
    data class Success(val result: PostResponseStatus) : MarkedState()
    object Error : MarkedState()
    object Loading : MarkedState()
}