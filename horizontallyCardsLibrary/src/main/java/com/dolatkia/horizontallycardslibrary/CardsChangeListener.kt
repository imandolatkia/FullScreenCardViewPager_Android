package com.dolatkia.horizontallycardslibrary

interface CardsChangeListener {
    fun onExpandChanged(isExpanded: Boolean)
    fun onScroll(offset: Int)
}