package com.example.alex.nilbsoftapp


interface LifeCicle<V> {
    fun bind(view: V)

    fun unbind()
}