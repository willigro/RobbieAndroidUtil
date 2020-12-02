package com.rittmann.baselifecycle.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class OneObserverLiveData<T> : MutableLiveData<T>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasObservers()) {
            return
        }

        super.observe(owner, observer)
    }
}