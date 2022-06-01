package com.example.myapplication.ui.fragments.itemsinfos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.FragmentCollectionInfoBinding
import com.example.myapplication.models.pojo.BaseItemDetails
import com.example.myapplication.states.CollectionInfoState
import com.example.myapplication.ui.activities.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.recyclerview.adapters.MoviesListAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.ui.recyclerview.listeners.PhotoClickListener
import com.example.myapplication.utils.hideAnimated
import com.example.myapplication.utils.setConfigVerticalWithInnerAndOuterDivs
import com.example.myapplication.utils.setImage
import com.example.myapplication.utils.showAnimated
import com.example.myapplication.viewmodel.itemsinfos.CollectionInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionInfoFragment : Fragment(), MovieClickListener, PhotoClickListener {

    private var _binding: FragmentCollectionInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CollectionInfoViewModel by viewModels()
    private lateinit var collectionListAdapter: MoviesListAdapter
    private var collectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionId = it.getInt(MEDIA_ID)
        }
        viewModel.execute(collectionId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCollectionInfoBinding.inflate(inflater, container, false)
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

    override fun onOpenPicture(url: String) {
        val action = CollectionInfoFragmentDirections.actionCollectionInfoFragmentToPhotoFragment(url)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenMovie(id: Long) {
        val action = CollectionInfoFragmentDirections.actionCollectionInfoFragmentToFilmInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    private fun setObserver() {
        binding.errorButton.setOnClickListener { viewModel.execute(collectionId) }
        viewModel.collectionInfo.observe(viewLifecycleOwner) { state ->
            when (state) {
                CollectionInfoState.Error -> {
                    binding.loading.hideAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.showAnimated()
                }
                CollectionInfoState.Loading -> {
                    binding.loading.showAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.hideAnimated()
                }
                is CollectionInfoState.Success -> {
                    binding.loading.hideAnimated()
                    binding.loaded.showAnimated()
                    binding.error.visibility = View.GONE
                    setData(state.data)
                }
            }
        }

    }

    private fun setData(data: BaseItemDetails.MovieCollection) {
        binding.apply {
            val url = BuildConfig.BASE_BACKDROP_URL + data.backdrop
            mainImage.setImage(url)
            mainImage.setOnClickListener{ onOpenPicture(url) }

            collectionTitle.text = data.name
            collectionAnnotation.text = data.overview
            collectionListAdapter.addMovies(data.parts)
        }
    }

    private fun initRecyclerView() {
        collectionListAdapter = MoviesListAdapter(this)
        binding.collectionComposition.setConfigVerticalWithInnerAndOuterDivs(
            collectionListAdapter,
            requireContext(),
            32,
            32
        )
    }
}