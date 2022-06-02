package com.example.myapplication.ui.fragments.itemsinfos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.FragmentSeasonInfoBinding
import com.example.myapplication.models.pojo.BaseItemDetails
import com.example.myapplication.states.SeasonInfoState
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.activities.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.recyclerview.adapters.EpisodeRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.EpisodeClickListener
import com.example.myapplication.ui.recyclerview.listeners.PhotoClickListener
import com.example.myapplication.utils.*
import com.example.myapplication.viewmodel.itemsinfos.SeasonInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeasonInfoFragment : Fragment(), EpisodeClickListener, PhotoClickListener {

    private var _binding: FragmentSeasonInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SeasonInfoViewModel by viewModels()
    private var tvID: Long = 0L
    private var season: Int = 0
    private lateinit var episodeAdapter: EpisodeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tvID = it.getLong(MEDIA_ID)
            season = it.getInt(MainActivity.SEASON)
        }
        viewModel.execute(tvID, season)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSeasonInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        setObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenEpisode(tvId: Long, seasonNum: Int, episode: Int) {
        val action =
            SeasonInfoFragmentDirections.actionSeasonInfoFragmentToEpisodeInfoFragment(
                tvId,
                seasonNum,
                episode)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPicture(url: String) {
        val action = SeasonInfoFragmentDirections.actionSeasonInfoFragmentToPhotoFragment(url)
        view?.findNavController()?.navigate(action)
    }

    private fun setObserver() {
        binding.errorButton.setOnClickListener { viewModel.execute(tvID, season) }
        viewModel.seasonInfo.observe(viewLifecycleOwner) { state ->
            when (state) {
                SeasonInfoState.Error -> {
                    binding.loading.hideAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.showAnimated()
                }
                SeasonInfoState.Loading -> {
                    binding.loading.showAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.hideAnimated()
                }
                is SeasonInfoState.Success -> {
                    binding.loading.hideAnimated()
                    binding.loaded.showAnimated()
                    binding.error.visibility = View.GONE
                    setData(state.data)
                }
            }
        }
    }

    private fun setData(data: BaseItemDetails.SeasonDetails) {
        binding.apply {
            toolbar.title = data.name
            toolbar.setNavigationOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            loadingToolbar.setNavigationOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            errorToolbar.setNavigationOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            val url = BuildConfig.BASE_BACKDROP_URL + data.posterPath
            mainImage.setImage(url)
            mainImage.setOnClickListener { onOpenPicture(url) }

            seasonTitle.text = data.name
            seasonNumber.text = "Номер сезона - ${data.number}"
            seasonDate.text = Utils.formatDate(data.airDate)
            episodeAdapter.appendMovies(data.episodes)
        }
    }

    private fun initRecyclerView() {
        episodeAdapter = EpisodeRecyclerAdapter(this)
        binding.episodes.setConfigVerticalWithInnerAndOuterDivs(episodeAdapter,
            requireContext(),
            32,
            32)
    }
}