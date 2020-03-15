package de.soenke.limit.di

import android.view.View
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.soenke.limit.ui.home.HomeFragment
import de.soenke.limit.ui.home.HomeViewModel

@Module
abstract class MainModule {
    @ContributesAndroidInjector(modules = [
    ViewModelBuilder::class
    ])
    internal abstract fun homeFragment():HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindesHomeViewModel(viewModel: HomeViewModel):ViewModel
}