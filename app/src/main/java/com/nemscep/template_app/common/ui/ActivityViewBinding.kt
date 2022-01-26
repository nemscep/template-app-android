package com.nemscep.template_app.common.ui

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBinding<T : ViewBinding>(
    activity: AppCompatActivity,
    private val initializer: (LayoutInflater) -> T
) : ReadOnlyProperty<AppCompatActivity, T> {

    private var _binding: T? = null

    init {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                _binding = null
            }
        })
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        if (_binding != null) return _binding!!

        _binding = initializer(thisRef.layoutInflater)
        return _binding!!
    }
}

fun <T : ViewBinding> AppCompatActivity.viewBinding(initializer: (LayoutInflater) -> T) =
    ActivityViewBinding<T>(this, initializer)

