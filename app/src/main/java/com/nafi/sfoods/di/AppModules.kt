package com.nafi.sfoods.di

import android.content.SharedPreferences
import com.nafi.sfoods.data.datasource.auth.AuthDataSource
import com.nafi.sfoods.data.datasource.auth.FirebaseAuthDataSourceImpl
import com.nafi.sfoods.data.datasource.cart.CartDataSource
import com.nafi.sfoods.data.datasource.cart.CartDatabaseDataSource
import com.nafi.sfoods.data.datasource.category.CategoryApiDataSource
import com.nafi.sfoods.data.datasource.category.CategoryDataSource
import com.nafi.sfoods.data.datasource.menu.MenuApiDataSource
import com.nafi.sfoods.data.datasource.menu.MenuDataSource
import com.nafi.sfoods.data.datasource.user.UserDataSource
import com.nafi.sfoods.data.datasource.user.UserDataSourceImpl
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.data.repository.CartRepositoryImpl
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.CategoryRepositoryImpl
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.MenuRepositoryImpl
import com.nafi.sfoods.data.repository.UserRepository
import com.nafi.sfoods.data.repository.UserRepositoryImpl
import com.nafi.sfoods.data.source.firebase.FirebaseService
import com.nafi.sfoods.data.source.firebase.FirebaseServiceImpl
import com.nafi.sfoods.data.source.local.database.AppDatabase
import com.nafi.sfoods.data.source.local.database.dao.CartDao
import com.nafi.sfoods.data.source.local.pref.UserPreference
import com.nafi.sfoods.data.source.local.pref.UserPreferenceImpl
import com.nafi.sfoods.data.source.network.services.SFoodsApiService
import com.nafi.sfoods.presentation.cart.CartViewModel
import com.nafi.sfoods.presentation.checkout.CheckoutViewModel
import com.nafi.sfoods.presentation.detailmenu.DetailMenuViewModel
import com.nafi.sfoods.presentation.home.HomeViewModel
import com.nafi.sfoods.presentation.login.LoginViewModel
import com.nafi.sfoods.presentation.main.MainViewModel
import com.nafi.sfoods.presentation.profile.ProfileViewModel
import com.nafi.sfoods.presentation.register.RegisterViewModel
import com.nafi.sfoods.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val networkModule = module {
        single<SFoodsApiService> { SFoodsApiService.invoke() }
        single<FirebaseService> { FirebaseServiceImpl() }
    }

    //todo : firebase
    private val localModule = module {
        single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
        single<CartDao> { get<AppDatabase>().cartDao() }
        single<SharedPreferences> {
            SharedPreferenceUtils.createPreference(
                androidContext(),
                UserPreferenceImpl.PREF_NAME
            )
        }
        single<UserPreference> { UserPreferenceImpl(get()) }
    }

    private val datasource = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<CategoryDataSource> { CategoryApiDataSource(get()) }
        single<MenuDataSource> { MenuApiDataSource(get()) }
        single<UserDataSource> { UserDataSourceImpl(get()) }
        single<AuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repository = module {
        single<CartRepository> { CartRepositoryImpl(get()) }
        single<CategoryRepository> { CategoryRepositoryImpl(get()) }
        single<MenuRepository> { MenuRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::MainViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::ProfileViewModel)

        viewModel { params ->
            DetailMenuViewModel(
                extras = params.get(),
                cartRepository = get()
            )
        }
    }

    val modules =
        listOf<Module>(networkModule, localModule, datasource, repository, viewModelModule)
}