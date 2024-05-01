package com.nafi.sfoods.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nafi.sfoods.R
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.databinding.FragmentHomeBinding
import com.nafi.sfoods.presentation.detailmenu.DetailMenuActivity
import com.nafi.sfoods.presentation.home.adapter.CategoryListAdapter
import com.nafi.sfoods.presentation.home.adapter.MenuListAdapter
import com.nafi.sfoods.presentation.home.adapter.OnItemClickedListener
import com.nafi.sfoods.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModel()

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            getMenuData(it.name)
        }
    }
    private var menuAdapter: MenuListAdapter? = null
    private var isUsingGridMode: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentUserName()
        setAdapterCategory()
        setAdapterMenu()
        applyGridMode()
        isUsingGridMode?.let { setButtonImage(it) }
        observeCategoryData()
        observeMenuData()
        isUsingGridMode?.let { bindMenuList(it) }
        setClickActionMenu()
        getMenuData(null)
    }

    private fun getCurrentUserName() {
        val userName = homeViewModel.getCurrentUser()?.fullName
        binding.layoutHeader.textName.text = userName
    }

    private fun setAdapterCategory() {
        binding.layoutCategory.rvCategory.adapter = this@HomeFragment.categoryAdapter
    }

    private fun setAdapterMenu() {
        binding.layoutListMenu.rvMenuGrid.adapter = this@HomeFragment.menuAdapter
    }

    private fun applyGridMode() {
        val currentMode = homeViewModel.isUsingGridMode()
        isUsingGridMode = currentMode
    }

    private fun observeCategoryData() {
        homeViewModel.getCategories().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutCategory.rvCategory.isVisible = false
                    binding.layoutCategory.pbLoadingCategory.isVisible = true
                    binding.layoutCategory.textEmptyCategory.isVisible = false
                    binding.layoutCategory.textErrorCategory.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutCategory.rvCategory.isVisible = true
                    binding.layoutCategory.pbLoadingCategory.isVisible = false
                    binding.layoutCategory.textEmptyCategory.isVisible = false
                    binding.layoutCategory.textErrorCategory.isVisible = false
                    result.payload?.let {
                        categoryAdapter.insertData(it)
                    }
                },
                doOnError = {
                    binding.layoutCategory.rvCategory.isVisible = false
                    binding.layoutCategory.pbLoadingCategory.isVisible = false
                    binding.layoutCategory.textEmptyCategory.isVisible = false
                    binding.layoutCategory.textErrorCategory.isVisible = true
                },
                doOnEmpty = {
                    binding.layoutCategory.rvCategory.isVisible = true
                    binding.layoutCategory.pbLoadingCategory.isVisible = false
                    binding.layoutCategory.textEmptyCategory.isVisible = true
                    binding.layoutCategory.textErrorCategory.isVisible = false
                    result.payload?.let {
                        categoryAdapter.insertData(it)
                    }
                },
            )
        }
    }

    private fun observeMenuData() {
        homeViewModel.getMenus().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = false
                    binding.layoutListMenu.pbLoadingMenu.isVisible = true
                    binding.layoutListMenu.textEmptyMenu.isVisible = false
                    binding.layoutListMenu.textErrorMenu.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = true
                    binding.layoutListMenu.pbLoadingMenu.isVisible = false
                    binding.layoutListMenu.textEmptyMenu.isVisible = false
                    binding.layoutListMenu.textErrorMenu.isVisible = false
                    result.payload?.let {
                        menuAdapter?.insertData(it)
                    }
                },
                doOnError = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = false
                    binding.layoutListMenu.pbLoadingMenu.isVisible = false
                    binding.layoutListMenu.textEmptyMenu.isVisible = false
                    binding.layoutListMenu.textErrorMenu.isVisible = true
                },
                doOnEmpty = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = true
                    binding.layoutListMenu.pbLoadingMenu.isVisible = false
                    binding.layoutListMenu.textEmptyMenu.isVisible = true
                    binding.layoutListMenu.textErrorMenu.isVisible = false
                    result.payload?.let {
                        menuAdapter?.insertData(it)
                    }
                },
            )
        }
    }

    private fun setClickActionMenu() {
        binding.layoutListMenu.btnChangeMode.setOnClickListener {
            if (isUsingGridMode == true) {
                homeViewModel.setUsingGridMode(false)
                applyGridMode()
            } else {
                homeViewModel.setUsingGridMode(true)
                applyGridMode()
            }
            setButtonImage(isUsingGridMode!!)
            bindMenuList(isUsingGridMode!!)
        }
    }

    private fun setButtonImage(isUsingGrid: Boolean) {
        binding.layoutListMenu.btnChangeMode.setImageResource(if (isUsingGrid) R.drawable.ic_list else R.drawable.ic_grid)
    }

    private fun bindMenuList(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) MenuListAdapter.MODE_GRID else MenuListAdapter.MODE_LIST
        menuAdapter =
            MenuListAdapter(
                listMode = listMode,
                listener =
                    object : OnItemClickedListener<Menu> {
                        override fun onItemClicked(item: Menu) {
                            navigateToDetail(item)
                        }
                    },
            )
        binding.layoutListMenu.rvMenuGrid.apply {
            adapter = this@HomeFragment.menuAdapter
            layoutManager =
                GridLayoutManager(requireContext(), if (isUsingGridMode == true) 2 else 1)
        }
        observeMenuData()
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }

    private fun getMenuData(categoryParams: String? = null) {
        homeViewModel.getMenus(categoryParams).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindMenuData(data) }
                },
            )
        }
    }

    private fun bindMenuData(data: List<Menu>) {
        menuAdapter?.insertData(data)
    }
}
