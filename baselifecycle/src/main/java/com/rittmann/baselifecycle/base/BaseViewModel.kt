package com.rittmann.baselifecycle.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rittmann.baselifecycle.livedata.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {

    protected var viewModelScopeGen: CoroutineScope? = null
    protected var _progress = MutableLiveData<Boolean>()
    protected var _errorCon = SingleLiveEvent<Void>()
    protected var _errorGen = SingleLiveEvent<Void>()

    val errorCon get() = _errorCon
    val errorGen get() = _errorGen
    val isLoading get() = _progress

    protected fun showProgress() {
        _progress.postValue(true)
    }

    protected fun hideProgress() {
        _progress.postValue(false)
    }

    protected open fun handleGenericFailure() {
        _errorGen.call()
    }

    protected open fun handleConnectionFailure() {
        _errorCon.call()
    }

    open fun clearViewModel() {
        _errorCon = SingleLiveEvent()
        _errorGen = SingleLiveEvent()
    }

    fun executeAsyncProgress(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        scope: CoroutineScope = GlobalScope,
        block: suspend () -> Unit
    ) {
        val s = viewModelScopeGen ?: scope
        s.launch {
            withContext(dispatcher) {
                _progress.postValue(true)
                block()
                _progress.postValue(false)
            }
        }
    }

    fun executeAsync(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        blockOnMain: Boolean = false,
        scope: CoroutineScope = GlobalScope,
        block: suspend () -> Unit
    ) {
        val s = viewModelScopeGen ?: scope
        s.launch {
            if (blockOnMain)
                withContext(Dispatchers.Main) {
                    block()
                }
            else
                block()
        }
    }

    fun executeMain(
        scope: CoroutineScope = GlobalScope,
        block: suspend () -> Unit
    ) {
        val s = viewModelScopeGen ?: scope
        s.launch {
            withContext(Dispatchers.Main) {
                block()
            }
        }
    }
}

/**
 * Convenience factory to handle ViewModels with one parameter.
 *
 * Make a factory:
 * ```
 * // Make a factory
 * val FACTORY = viewModelFactory(::MyViewModel)
 * ```
 *
 * Use the generated factory:
 * ```
 * ViewModelProviders.of(this, FACTORY(argument))
 *
 * ```
 *
 * @param constructor A function (A) -> T that returns an instance of the ViewModel (typically pass
 * the constructor)
 * @return a function of one argument that returns ViewModelProvider.Factory for ViewModelProviders
 */
fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
            (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}

fun <T : ViewModel, A, B> singleArgViewModelFactory(constructor: (A, B) -> T):
            (A, B) -> ViewModelProvider.NewInstanceFactory {
    return { arg1: A, arg2: B ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg1, arg2) as V
            }
        }
    }
}