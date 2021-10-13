package com.dolatkia.horizontallycardslibrary

interface CardChangeListener {
    fun onExpandChanged(isExpanded: Boolean)
    fun onScroll(offset: Int)
}