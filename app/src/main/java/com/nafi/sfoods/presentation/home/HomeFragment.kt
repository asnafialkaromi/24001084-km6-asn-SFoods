package com.nafi.sfoods.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.category.DummyCategoryDataSource
import com.nafi.sfoods.data.datasource.menu.DummyMenuDataSource
import com.nafi.sfoods.data.model.Category
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.CategoryRepositoryImpl
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.MenuRepositoryImpl
import com.nafi.sfoods.databinding.FragmentHomeBinding
import com.nafi.sfoods.presentation.home.adapter.CategoryListAdapter
import com.nafi.sfoods.presentation.home.adapter.MenuListAdapter
import com.nafi.sfoods.presentation.home.adapter.OnItemClickedListener
import com.nafi.sfoods.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val menuDataSource = DummyMenuDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

    private val categoryAdapter = CategoryListAdapter()
    private var menuAdapter: MenuListAdapter? = null
    private var isUsingGridMode: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategoryList(viewModel.getCategories())
        bindMenuList(isUsingGridMode)
        setClickActionMenu()
    }

    private fun setClickActionMenu() {
        binding.layoutListMenu.btnChangeMode.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            setButtonImage(isUsingGridMode)
            bindMenuList(isUsingGridMode)
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
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGridMode) 2 else 1)
        }
        menuAdapter?.insertData(viewModel.getMenus())
    }

    private fun navigateToDetail(item: Menu) {
        /*DetailMenuActivity.startActivity(requireContext(),item)*/
    }

    private fun setCategoryList(data: List<Category>) {
        binding.layoutCategory.rvCategory.apply {
            adapter = categoryAdapter
        }
        categoryAdapter.insertData(data)
    }
}