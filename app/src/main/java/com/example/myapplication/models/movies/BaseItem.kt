package com.example.myapplication.models.movies

import com.google.gson.annotations.SerializedName

sealed class BaseItem()
/* Реализации классов
* для получения
* информации о фильмах
* */
    data class Film(
        @SerializedName("id")
        val id: Long,

        @SerializedName("title")
        val title: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("backdrop_path")
        val backdropPath: String,

        @SerializedName("vote_average")
        val rating: Float,

        @SerializedName("release_date")
        val releaseDate: String,
    ) : BaseItem() {
        constructor() : this(
            0,
            "Non",
            "Non",
            "Non",
            "Non",
            0.0F,
            "Non"
        )

        fun releaseYear(): String {
            return releaseDate.substringBefore("-")
        }
    }

    data class FilmDetails(

        @SerializedName("adult")
        val adult: Boolean,

        @SerializedName("belongs_to_collection")
        val collection: MovieCollection?,

        @SerializedName("budget")
        val budget: Int,

        @SerializedName("genres")
        val genres: List<MovieGenres>,

        @SerializedName("homepage")
        val homepage: String,

        @SerializedName("id")
        val id: Long,

        @SerializedName("imdb_id")
        val imdbId: String,

        @SerializedName("original_language")
        val originalLanguage: String,

        @SerializedName("original_title")
        val originalTitle: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("popularity")
        val popularity: Double,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("production_companies")
        val companies: List<ProductionCompanies>,

        @SerializedName("production_countries")
        val countries: List<ProductionCounties>,

        @SerializedName("release_date")
        val releaseDate: String,

        @SerializedName("revenue")
        val revenue: Int,

        @SerializedName("runtime")
        val runtime: Int?,

        @SerializedName("spoken_languages")
        val languages: List<SpokenLanguages>,

        @SerializedName("status")
        val status: String,

        @SerializedName("tagline")
        val tagline: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("video")
        val video: Boolean,

        @SerializedName("vote_average")
        val rating: Double,

        @SerializedName("vote_count")
        val average: Int
    ) : BaseItem() {
        constructor() : this(
            false,
            null,
            0,
            emptyList<MovieGenres>(),
            "",
            0L,
            "",
            "",
            "",
            "",
            0.0,
            "",
            emptyList<ProductionCompanies>(),
            emptyList<ProductionCounties>(),
            "",
            0,
            null,
            emptyList<SpokenLanguages>(),
            "",
            "",
            "",
            false,
            0.0,
            0
        )
    }

/* Реализации классов
* для получения
* информации о сериалах
* */
    data class TV(
        @SerializedName("id")
        val id: Long,

        @SerializedName("name")
        val name: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("backdrop_path")
        val backdropPath: String,

        @SerializedName("vote_average")
        val rating: Float
    ) : BaseItem() {
        constructor() : this(
            0,
            "Non",
            "Non",
            "Non",
            "Non",
            0.0F
        )
    }

    data class TvDetails(
        @SerializedName("backdrop_path")
        val backdropPath: String,

        @SerializedName("created_by")
        val createdBy: List<TvProducer>,

        @SerializedName("episode_run_time")
        val runtime: List<Int>,

        @SerializedName("first_air_date")
        val firstAirDate: String,

        @SerializedName("genres")
        val genres: List<MovieGenres>,

        @SerializedName("homepage")
        val homepage: String,

        @SerializedName("id")
        val id: Long,

        @SerializedName("in_production")
        val inProduction: Boolean,

        @SerializedName("languages")
        val languages: List<String>,

        @SerializedName("last_air_date")
        val lastAirDate: String,

        @SerializedName("last_episode_to_air")
        val lastEpisode: Episode,

        @SerializedName("name")
        val name: String,

        @SerializedName("networks")
        val networks: List<MovieNetwork>,

        @SerializedName("number_of_episodes")
        val episodes: Int,

        @SerializedName("number_of_seasons")
        val numOfSeasons: Int,

        @SerializedName("origin_country")
        val country: List<String>,

        @SerializedName("original_language")
        val originalLanguage: String,

        @SerializedName("original_name")
        val originalName: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("popularity")
        val popularity: Double,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("production_companies")
        val companies: List<ProductionCompanies>,

        @SerializedName("production_countries")
        val countries: List<ProductionCounties>,

        @SerializedName("seasons")
        var seasons: List<Season>,

        @SerializedName("spoken_languages")
        val spokenLanguages: List<SpokenLanguages>,

        @SerializedName("status")
        val status: String,

        @SerializedName("tagline")
        val tagline: String,

        @SerializedName("type")
        val type: String,

        @SerializedName("vote_average")
        val rating: Double,

        @SerializedName("vote_count")
        val average: Int
    ): BaseItem() {
        constructor() : this(
            "",
            emptyList<TvProducer>(),
            emptyList<Int>(),
            "",
            emptyList<MovieGenres>(),
            "",
            0L,
            false,
            emptyList<String>(),
            "",
            Episode(),
            "",
            emptyList<MovieNetwork>(),
            0,
            0,
            emptyList<String>(),
            "",
            "",
            "",
            0.0,
            "",
            emptyList<ProductionCompanies>(),
            emptyList<ProductionCounties>(),
            emptyList<Season>(),
            emptyList<SpokenLanguages>(),
            "",
            "",
            "",
            0.0,
            0
        )
    }

/* Реализации классов
* для получения
* информации о сезонах
* */

    data class Season(
        @SerializedName("air_date")
        val airDate: String,

        @SerializedName("episode_count")
        val episodes: Int,

        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("show_id")
        var showId: Long = 0L,

        @SerializedName("season_number")
        val number: Int
    ): BaseItem() {
        constructor(): this(
            "",
            0,
            0,
            "",
            "",
            "",
            0L,
            0
        )
    }

    data class SeasonDetails(
        @SerializedName("air_date")
        val airDate: String,

        @SerializedName("episodes")
        var episodes: List<Episode>,

        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("poster_path")
        val posterPath: String,

        @SerializedName("show_id")
        var showId: Long = 0L,

        @SerializedName("season_number")
        val number: Int
    ): BaseItem() {
        constructor(): this(
            "",
            emptyList(),
            0,
            "",
            "",
            "0L",
            0L,
            0
        )
    }


/* Реализации классов
* для получения
* информации об эпизодах
* */
    data class Episode(
        @SerializedName("id")
        val id: Long,

        @SerializedName("episode_number")
        val episodeNum: Int,

        @SerializedName("season_number")
        val seasonNum: Int,

        @SerializedName("show_id")
        var showId: Long = 0L,

        @SerializedName("name")
        val name: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("still_path")
        val stillPath: String,

        @SerializedName("vote_average")
        val rating: Double
    ) : BaseItem() {
        constructor() : this(
            0,
            0,
            0,
            0,
            "Non",
            "Non",
            "Non",
            0.0
        )
    }

    data class EpisodeDetails(
        @SerializedName("air_date")
        val airDate: String,

        @SerializedName("crew")
        val crew: List<Crew>,

        @SerializedName("episode_number")
        val episodeNum: Int,

        @SerializedName("guest_stars")
        val guestStars: List<GuestStar>,

        @SerializedName("name")
        val name: String,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("id")
        val id: Long,

        @SerializedName("show_id")
        var showId: Long = 0L,

        @SerializedName("production_code")
        val productionCode: String,

        @SerializedName("season_number")
        val seasonNum: Int,

        @SerializedName("still_path")
        val stillPath: String,

        @SerializedName("vote_average")
        val rating: Double,

        @SerializedName("vote_count")
        val votes: Int

    ) : BaseItem() {
        constructor() : this(
            "",
            emptyList<Crew>(),
            0,
            emptyList<GuestStar>(),
            "",
            "",
            0L,
            0L,
            "",
            0,
            "",
            0.0,
            0
        )
    }

/* Реализации классов
* для получения
* информации о людях
* */
data class Person(
        @SerializedName("id")
        val id: Long,

        @SerializedName("name")
        val name: String,

        @SerializedName("profile_path")
        val profilePath: String,

        @SerializedName("adult")
        val adult: Boolean,

        @SerializedName("biography")
        val biography: String,

        @SerializedName("popularity")
        val popularity: Number
    ) : BaseItem() {
        constructor() : this(
            0,
            "Non",
            "Non",
            false,
            "Non",
            0
        )
    }

    data class PersonDetails(
        @SerializedName("birthday")
        val birthday: String,

        @SerializedName("known_for_department")
        val knownFor: String,

        @SerializedName("deathday")
        val deathday: String,

        @SerializedName("id")
        val id: Long,

        @SerializedName("name")
        val name: String,

        @SerializedName("also_known_as")
        val alsoKnowsAs: List<String>,

        @SerializedName("gender")
        val gender: Int,

        @SerializedName("biography")
        val biography: String,

        @SerializedName("popularity")
        val popularity: Number,

        @SerializedName("place_of_birth")
        val placeOfBirth: String,

        @SerializedName("profile_path")
        val profilePath: String,

        @SerializedName("adult")
        val adult: Boolean,

        @SerializedName("imdb_id")
        val imdbId: String,

        @SerializedName("homepage")
        val homepage: String

    ): BaseItem() {
        constructor() : this(
            "",
            "",
            "",
            0,
            "Non",
            emptyList<String>(),
            0,
            "",
            0,
            "",
            "",
            false,
            "",
            ""
        )
    }

data class Cast(
    @SerializedName("id")
    val id: Long,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("character")
    val character: String,

    @SerializedName("known_for_department")
    val knownFor: String,

    @SerializedName("profile_path")
    val profile: String?
) : BaseItem()

data class Crew(
    @SerializedName("id")
    val id: Long,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("job")
    val job: String,

    @SerializedName("profile_path")
    val profile: String
) : BaseItem()

data class GuestStar(
    @SerializedName("id")
    val id: Int,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("character")
    val character: String,

    @SerializedName("order")
    val order: Int,

    @SerializedName("profile_path")
    val profile: String
): BaseItem()

data class TvProducer(
    @SerializedName("id")
    val id: Int,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("profile_path")
    val profile: String
): BaseItem()

