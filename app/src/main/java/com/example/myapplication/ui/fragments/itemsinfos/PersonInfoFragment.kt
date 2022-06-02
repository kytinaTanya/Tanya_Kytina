package com.example.myapplication.ui.fragments.itemsinfos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.FragmentPersonInfoBinding
import com.example.myapplication.models.pojo.view.PersonView
import com.example.myapplication.states.PersonInfoState
import com.example.myapplication.ui.activities.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.recyclerview.RegularDividerItemDecoration
import com.example.myapplication.ui.recyclerview.adapters.ImagesRecyclerAdapter
import com.example.myapplication.ui.recyclerview.adapters.SimpleListAdapter
import com.example.myapplication.ui.recyclerview.listeners.PhotoClickListener
import com.example.myapplication.utils.*
import com.example.myapplication.viewmodel.itemsinfos.PersonInfoViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonInfoFragment : Fragment(), PhotoClickListener {

    private var _binding: FragmentPersonInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonInfoViewModel by viewModels()
    private var personId: Long = 0L

    //Адаптеры для всех recyclerView
    private lateinit var alsoKnowAsAdapter: SimpleListAdapter
    private lateinit var photosAdapter: ImagesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            personId = it.getLong(MEDIA_ID)
        }
        viewModel.execute(personId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPersonInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerViews()
        setObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenPicture(url: String) {
        val action = PersonInfoFragmentDirections.actionPersonInfoFragmentToPhotoFragment(url)
        view?.findNavController()?.navigate(action)
    }

    private fun initRecyclerViews() {
        alsoKnowAsAdapter = SimpleListAdapter()
        photosAdapter = ImagesRecyclerAdapter(this)

        val alsoKnownAsLayoutManager = FlexboxLayoutManager(requireContext())
        alsoKnownAsLayoutManager.justifyContent = JustifyContent.FLEX_START
        binding.alsoKnownAs.apply {
            layoutManager = alsoKnownAsLayoutManager
            adapter = alsoKnowAsAdapter
            addItemDecoration(RegularDividerItemDecoration(8))
        }

        binding.photosList.setConfigHorizontalWithInnerAndOuterDivs(photosAdapter, requireContext(), 16, 32)
    }

    private fun setObserver() {
        binding.errorButton.setOnClickListener { viewModel.execute(personId) }
        viewModel.personInfo.observe(viewLifecycleOwner) { state ->
            when (state) {
                PersonInfoState.Error -> {
                    binding.loading.hideAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.showAnimated()
                }
                PersonInfoState.Loading -> {
                    binding.loading.showAnimated()
                    binding.loaded.visibility = View.GONE
                    binding.error.hideAnimated()
                }
                is PersonInfoState.Success -> {
                    binding.loading.hideAnimated()
                    binding.loaded.showAnimated()
                    binding.error.visibility = View.GONE
                    setData(state.data)
                }
            }
        }
    }

    private fun setData(data: PersonView) {
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
            val url = BuildConfig.BASE_PROFILE_URL + data.profilePath
            mainImage.setImage(url)
            mainImage.setOnClickListener { onOpenPicture(url) }

            personName.text = data.name
            personAge.text = Utils.formatDate(data.birthday)
            alsoKnowAsAdapter.addStrings(data.alsoKnowsAs)
            if (data.biography != "") {
                personBiography.text = data.biography
            } else {
                personBiography.visibility = View.GONE
                biographyTitle.visibility = View.GONE
            }
            photosList.isVisible = data.profilesPhoto.isNotEmpty()
            photosTitle.isVisible = data.profilesPhoto.isNotEmpty()
            photosAdapter.setImages(data.profilesPhoto)
        }
    }
}