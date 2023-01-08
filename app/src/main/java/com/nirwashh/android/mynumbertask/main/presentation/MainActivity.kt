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
            show(NumbersFragment(), false)
    }

    override fun show(fragment: Fragment) {
        show(fragment, true)
    }

    private fun show(fragment: Fragment, add: Boolean) {
        //TODO MAKE OOP
        val transaction = supportFragmentManager.beginTransaction()
        val container = R.id.container
        if (add)
            transaction.add(container, fragment)
                .addToBackStack(fragment.javaClass.simpleName)
        else
            transaction.replace(container, fragment)
        transaction.commit()

    }

    override fun <T : ViewModel> provideViewModel(clasz: Class<T>, owner: ViewModelStoreOwner) =
        (application as ProvideViewModel).provideViewModel(clasz, owner)


}

interface ShowFragment {
    fun show(fragment: Fragment)

    class Empty : ShowFragment {
        override fun show(fragment: Fragment) = Unit
    }
}