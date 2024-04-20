package com.nafi.sfoods.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.category.CategoryApiDataSource
import com.nafi.sfoods.data.datasource.category.CategoryDataSource
import com.nafi.sfoods.data.datasource.menu.MenuApiDataSource
import com.nafi.sfoods.data.datasource.menu.MenuDataSource
import com.nafi.sfoods.data.datasource.user.UserDataSource
import com.nafi.sfoods.data.datasource.user.UserDataSourceImpl
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.CategoryRepositoryImpl
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.MenuRepositoryImpl
import com.nafi.sfoods.data.repository.UserRepositoryPreference
import com.nafi.sfoods.data.repository.UserRepositoryPreferenceImpl
import com.nafi.sfoods.data.source.local.pref.UserPreference
import com.nafi.sfoods.data.source.local.pref.UserPreferenceImpl
import com.nafi.sfoods.data.source.network.services.SFoodsApiService
import com.nafi.sfoods.databinding.FragmentHomeBinding
import com.nafi.sfoods.presentation.detailmenu.DetailMenuActivity
import com.nafi.sfoods.presentation.home.adapter.CategoryListAdapter
import com.nafi.sfoods.presentation.home.adapter.MenuListAdapter
import com.nafi.sfoods.presentation.home.adapter.OnItemClickedListener
import com.nafi.sfoods.utils.GenericViewModelFactory
import com.nafi.sfoods.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val service = SFoodsApiService.invoke()
        val userPreference : UserPreference = UserPreferenceImpl(requireContext())
        val userDataSource : UserDataSource = UserDataSourceImpl(userPreference)
        val userRepositoryPreference : UserRepositoryPreference = UserRepositoryPreferenceImpl(userDataSource)
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val menuDataSource: MenuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository, userRepositoryPreference))
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter(){
            getMenuData(it.name)
        }
    }
    private var menuAdapter: MenuListAdapter? = null
    private var isUsingGridMode : Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun setAdapterCategory() {
        binding.layoutCategory.rvCategory.adapter = this@HomeFragment.categoryAdapter
    }

    private fun setAdapterMenu() {
        binding.layoutListMenu.rvMenuGrid.adapter = this@HomeFragment.menuAdapter
    }

    private fun applyGridMode(){
        val currentMode = viewModel.isUsingGridMode()
        if (currentMode){
            isUsingGridMode = true
        } else {
            isUsingGridMode = false
        }
    }

    private fun observeCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutCategory.rvCategory.isVisible = false
                    binding.layoutCategory.progressBar.isVisible = true
                    binding.layoutCategory.textEmpty.isVisible = false
                    binding.layoutCategory.textError.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutCategory.rvCategory.isVisible = true
                    binding.layoutCategory.progressBar.isVisible = false
                    binding.layoutCategory.textEmpty.isVisible = false
                    binding.layoutCategory.textError.isVisible = false
                    result.payload?.let {
                        categoryAdapter.insertData(it)
                    }
                },
                doOnError = {
                    binding.layoutCategory.rvCategory.isVisible = false
                    binding.layoutCategory.progressBar.isVisible = false
                    binding.layoutCategory.textEmpty.isVisible = false
                    binding.layoutCategory.textError.isVisible = true
                },
                doOnEmpty = {
                    binding.layoutCategory.rvCategory.isVisible = true
                    binding.layoutCategory.progressBar.isVisible = false
                    binding.layoutCategory.textEmpty.isVisible = true
                    binding.layoutCategory.textError.isVisible = false
                    result.payload?.let {
                        categoryAdapter.insertData(it)
                    }
                }
            )
        }
    }

    private fun observeMenuData() {
        viewModel.getMenus().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = false
                    binding.layoutListMenu.progressBar.isVisible = true
                    binding.layoutListMenu.textEmpty.isVisible = false
                    binding.layoutListMenu.textError.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = true
                    binding.layoutListMenu.progressBar.isVisible = false
                    binding.layoutListMenu.textEmpty.isVisible = false
                    binding.layoutListMenu.textError.isVisible = false
                    result.payload?.let {
                        menuAdapter?.insertData(it)
                    }
                },
                doOnError = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = false
                    binding.layoutListMenu.progressBar.isVisible = false
                    binding.layoutListMenu.textEmpty.isVisible = false
                    binding.layoutListMenu.textError.isVisible = true
                },
                doOnEmpty = {
                    binding.layoutListMenu.rvMenuGrid.isVisible = true
                    binding.layoutListMenu.progressBar.isVisible = false
                    binding.layoutListMenu.textEmpty.isVisible = true
                    binding.layoutListMenu.textError.isVisible = false
                    result.payload?.let {
                        menuAdapter?.insertData(it)
                    }
                }
            )
        }
    }

    private fun setClickActionMenu() {
        binding.layoutListMenu.btnChangeMode.setOnClickListener {
            if (isUsingGridMode == true) {
                viewModel.setUsingGridMode(false)
                applyGridMode()
            } else {
                viewModel.setUsingGridMode(true)
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
        menuAdapter = MenuListAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    navigateToDetail(item)
                }
            }
        )
        binding.layoutListMenu.rvMenuGrid.apply {
            adapter = this@HomeFragment.menuAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGridMode == true) 2 else 1)
        }
        observeMenuData()
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }

    private fun getMenuData(categoryParams: String? = null) {
        viewModel.getMenus(categoryParams).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindMenuData(data) }
                }
            )
        }
    }

    private fun bindMenuData(data: List<Menu>) {
        menuAdapter?.insertData(data)
    }
}