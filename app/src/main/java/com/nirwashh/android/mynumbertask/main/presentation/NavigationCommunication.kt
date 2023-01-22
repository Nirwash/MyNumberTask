package com.nirwashh.android.mynumbertask.main.presentation

import com.nirwashh.android.mynumbertask.numbers.presentation.Communication

interface NavigationCommunication {
    interface Observe : Communication.Observe<NavigationStrategy>
    interface Mutate : Communication.Mutate<NavigationStrategy>
    interface Mutable : Observe, Mutate

    class Base : Communication.SingleUi<NavigationStrategy>(), Mutable
}