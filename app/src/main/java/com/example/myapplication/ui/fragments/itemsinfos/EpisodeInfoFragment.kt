package com.example.myapplication.ui.fragments.itemsinfos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEpisodeInfoBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.models.pojo.BaseItemDetails
import com.example.myapplication.states.EpisodeInfoState
import com.example.myapplication.states.RatedState
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.activities.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.recyclerview.listeners.PhotoClickListener
import com.example.myapplication.utils.Utils.Companion.formatDate
import com.example.myapplication.utils.hideAnimated
import com.example.myapplication.utils.setCurrentResource
import com.example.myapplication.utils.setImage
import com.example.myapplication.utils.showAnimated
import com.example.myapplication.viewmodel.itemsinfos.EpisodeInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeInfoFragment : Fragment(), PhotoClickListener {

    private var _binding: FragmentEpisodeInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodeInfoViewModel by viewModels()
    private var rating: Float = 0.0F
    private var tvID: Long = 0L
    private var season: Int = 0
    private var episode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tvID = it.getLong(MEDIA_ID)
            season = it.getInt(MainActivity.SEASON)
            episode = it.getInt(MainActivity.EPISODE)
        }
        viewModel.loadInfo(tvID, season, episode, USER.sessionKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenPicture(url: String) {
        val action = EpisodeInfoFragmentDirections.actionEpisodeInfoFragmentToPhotoFragment(url)
        view?.findNavController()?.navigate(action)
    }

    private fun setObserver() {
        binding.errorButton.setOnClickListener { viewModel.loadInfo(tvID, season, episode, USER.sessionKey) }
        binding.starBtn.setOnClickListener {
            if (binding.ratingBar.visibility == View.VISIBLE) {
                binding.ratingBar.hideAnimated()
            } else {
                binding.ratingBar.showAnimated()
                binding.ratingBar.rating = rating / 2
            }
        }
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                viewModel.rate(tvID, season, episode, USER.sessionKey, rating * 2)
            }
        }

        viewModel.episodeInfo.observe(viewLifecycleOwner) { state ->
            when (state) {
                EpisodeInfoState.Error -> {
                    binding.loading.hideAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.showAnimated()
                }
                EpisodeInfoState.Loading -> {
                    binding.loading.showAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.hideAnimated()
                }
                is EpisodeInfoState.Success -> {
                    binding.loading.hideAnimated()
                    binding.loaded.showAnimated()
                    binding.error.visibility = View.GONE
                    setData(state.data)
                }
            }
        }

        viewModel.ratedState.observe(viewLifecycleOwner) { state ->
            when (state) {
                RatedState.Error -> {
                    binding.ratingProgress.hideAnimated()
                    binding.ratingBar.rating = rating / 2
                    showToast()
                }
                RatedState.Loading -> binding.ratingProgress.showAnimated()
                is RatedState.Success -> {
                    if (state.result.status == 1 || state.result.status == 12 || state.result.status == 13) {
                        binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
                        rating = binding.ratingBar.rating * 2
                    } else {
                        binding.ratingBar.rating = rating / 2
                        showToast()
                    }
                    binding.ratingProgress.hideAnimated()
                }
            }
        }
    }

    private fun setData(data: BaseItemDetails.EpisodeDetails) {
        binding.apply {
            toolbar.title = data.name
            toolbar.setNavigationOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            val url = BuildConfig.BASE_BACKDROP_URL + data.stillPath
            mainImage.setImage(url)
            mainImage.setOnClickListener{ onOpenPicture(url) }

            episodeTitle.text = data.name
            episodeNumber.text = "Номер эпизода: ${data.episodeNum}"
            if (data.airDate != null) episodeDate.text = formatDate(data.airDate) else binding.episodeDate.visibility = View.GONE
            allRating.text = "Общая оценка: ${data.voteAverage}"
            if (data.overview != "") {
                annotation.text = data.overview
            } else {
                annotationTitle.visibility = View.GONE
                annotation.visibility = View.GONE
            }
            starBtn.setCurrentResource({ data.userRating.rating > 0.0 },
                R.drawable.ic_baseline_star_marked,
                R.drawable.ic_star)
            rating = data.userRating.rating
        }
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "Попробуйсте ещё раз", Toast.LENGTH_SHORT).show()
    }
}