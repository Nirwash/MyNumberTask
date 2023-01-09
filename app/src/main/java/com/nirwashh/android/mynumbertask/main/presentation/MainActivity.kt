package com.nirwashh.android.mynumbertask.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.nirwashh.android.mynumbertask.R
import com.nirwashh.android.mynumbertask.main.sl.ProvideViewModel
import com.nirwashh.android.mynumbertask.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity(), ShowFragment, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            NavigationStrategy.Replace(NumbersFragment())
                .navigate(supportFragmentManager, R.id.container)
    }

    override fun show(fragment: Fragment) =
        NavigationStrategy.Add(fragment).navigate(supportFragmentManager, R.id.container)


    override fun <T : ViewModel> provideViewModel(clasz: Class<T>, owner: ViewModelStoreOwner) =
        (application as ProvideViewModel).provideViewModel(clasz, owner)
}

interface ShowFragment {
    fun show(fragment: Fragment)

    class Empty : ShowFragment {
        override fun show(fragment: Fragment) = Unit
    }
}