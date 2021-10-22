package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityItemInfoBinding
import com.example.myapplication.models.movies.Episode
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.example.myapplication.ui.MainActivity.Companion.EPISODE
import com.example.myapplication.ui.MainActivity.Companion.ITEM_TYPE
import com.example.myapplication.ui.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.MainActivity.Companion.SEASON
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.ItemInfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemInfoBinding
    private val viewModel: ItemInfoViewModel by viewModels()
    private var film: Film? = null
    private var tv: TV? = null
    private var listId = USER.historyListID
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private var currentUser: FirebaseUser? = null
    private var isFavorite: Boolean = false
    private var isInWatchlist: Boolean = false
    private var rating: Float = 0.0F
    private var currentRating = rating
    //Дополнительная переменная currentRating нужна, чтобы при неудачной попытке оченить фильм/сериал, можно было откатить назад

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        reference = Firebase.database.reference
        currentUser = auth.currentUser

        val id: Long = (intent.extras?.get(MEDIA_ID) ?: 1L) as Long
        when(intent.extras?.get(ITEM_TYPE)) {
            MainActivity.MOVIE_TYPE -> {
                viewModel.loadFilmDetails(id)
                viewModel.loadMovieStates(id, USER.sessionKey)
            }
            MainActivity.TV_TYPE -> {
                viewModel.loadTVDetails(id)
                viewModel.loadTvStates(id, USER.sessionKey)
            }
            MainActivity.PERSON_TYPE -> {
                viewModel.loadPersonDetails(id)
                binding.watchlistBtn.visibility = View.GONE
                binding.loveBtn.visibility = View.GONE
                binding.starBtn.visibility = View.GONE
                binding.ratingBar.visibility = View.GONE
            }
            MainActivity.EPISODE_TYPE -> {
                val season: Int = (intent.extras?.get(SEASON) ?: 1) as Int
                val episode: Int = (intent.extras?.get(EPISODE) ?: 1) as Int
                viewModel.loadEpisodeDetails(id, season, episode)
                binding.loveBtn.visibility = View.GONE
                binding.watchlistBtn.visibility = View.GONE
            }
        }
        initComponents()
    }

    private fun initComponents() {
        binding.loveBtn.setOnClickListener {
            if(film != null) {
                viewModel.markAsFavorite(film!!.id, "movie", !isFavorite, USER.sessionKey)
            } else if(tv != null) {
                viewModel.markAsFavorite(tv!!.id, "tv", !isFavorite, USER.sessionKey)
            }
        }

        binding.watchlistBtn.setOnClickListener {
            if(film != null) {
                viewModel.addToWatchlist(film!!.id, "movie", !isInWatchlist, USER.sessionKey)
            } else if(tv != null) {
                viewModel.addToWatchlist(tv!!.id, "tv", !isInWatchlist, USER.sessionKey)
            }
        }

        binding.starBtn.setOnClickListener {
            if(binding.ratingBar.visibility == View.VISIBLE) {
                binding.ratingBar.visibility = View.GONE
            } else {
                binding.ratingBar.visibility = View.VISIBLE
                binding.ratingBar.rating = rating / 2
            }
        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if(fromUser) {
                if(film != null) {
                    viewModel.rateMovie(film!!.id, USER.sessionKey, rating * 2)
                }

                if(tv != null) {
                    viewModel.rateTv(tv!!.id, USER.sessionKey, rating * 2)
                }
                currentRating = rating * 2
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.movieDetails.observe(this) { item ->
            when(item) {
                is Film -> {
                    film = item
                    //addToHistory()
                    Log.d("InitImage", "${BuildConfig.BASE_POSTER_URL}${item.posterPath}")
                    binding.movieImage.setImage(BuildConfig.BASE_POSTER_URL + item.posterPath)
                    binding.movieTitle.text = item.title
                    binding.yearOfMovie.text = item.releaseYear()
                    binding.movieRating.text = item.rating.toString()
                    binding.movieAnnotation.text = item.overview
                }
                is TV -> {
                    tv = item
                    binding.movieImage.setImage(BuildConfig.BASE_POSTER_URL + item.posterPath)
                    binding.movieTitle.text = item.name
                    binding.movieAnnotation.text = item.overview
                    binding.movieRating.text = item.rating.toString()
                }
                is Person -> {
                    binding.movieImage.setImage(BuildConfig.BASE_PROFILE_URL + item.profilePath)
                    binding.movieTitle.text = item.name
                    binding.movieAnnotation.text = item.biography
                    binding.movieRating.text = item.popularity.toString()
                }
                is Episode -> {
                    binding.movieImage.visibility = View.GONE
                    binding.stillImage.visibility = View.VISIBLE
                    binding.stillImage.setImage(BuildConfig.BASE_STILL_URL + item.stillPath)
                    binding.movieTitle.text = item.name
                    binding.movieAnnotation.text = item.overview
                    binding.movieRating.text = item.rating.toString()
                }
            }
        }

        viewModel.movieStates.observe(this) { movie ->
            isFavorite = movie.favorite
            isInWatchlist = movie.watchlist
            rating = movie.rating.rating
            if(isInWatchlist) {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_bookmark_marked)
            } else {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_turned_in)
            }
            if(isFavorite) {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite_marked)
            } else {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite)
            }
            if(rating > 0.0) {
                binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
            } else {
                binding.starBtn.setBackgroundResource(R.drawable.ic_star)
            }
        }

        viewModel.addToWatchlistState.observe(this) { status ->
            if(status.status == 1 || status.status == 12 || status.status == 13) {
                isInWatchlist = !isInWatchlist
            } else {
                Toast.makeText(this, "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
            }
            if(isInWatchlist) {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_bookmark_marked)
            } else {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_turned_in)
            }
        }

        viewModel.markAsFavState.observe(this) { status ->
            if(status.status == 1 || status.status == 12 || status.status == 13) {
                isFavorite = !isFavorite
            } else {
                Toast.makeText(this, "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
            }
            if(isFavorite) {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite_marked)
            } else {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite)
            }
        }

        viewModel.ratedState.observe(this) {
            if(it.status == 1 || it.status == 12 || it.status == 13) {
                rating = currentRating
            } else {
                currentRating = rating
                Toast.makeText(this, "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
            }
            binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
            binding.ratingBar.rating = rating / 2
        }
    }

    private fun addToHistory() {
        if(film != null) {
            if(listId == 0) {
                viewModel.createList(MainActivity.USER.sessionKey)

                viewModel.listId.observe(this) { id ->
                    listId = id
                    reference.child("users").child(currentUser?.uid.toString()).child("historyListID").setValue(listId).addOnCompleteListener {
                        if(it.isSuccessful) {
                            Toast.makeText(this, "Создание списка истории прошла прошло успешно", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("AUTH", "${it.exception}")
                            Toast.makeText(this, "Не получилось: ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            //viewModel.removeMovie(USER.historyListID, USER.sessionKey, film!!.id)
            //viewModel.addMovie(MainActivity.USER.historyListID, MainActivity.USER.sessionKey, film!!.id)
        }
    }
}