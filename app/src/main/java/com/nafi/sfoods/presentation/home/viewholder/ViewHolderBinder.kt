package com.nafi.sfoods.presentation.home.viewholder

import com.nafi.sfoods.data.model.Menu

interface ViewHolderBinder<T> {
    fun bind(data: Menu)
}