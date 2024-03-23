package com.nafi.sfoods.data.datasource

import com.nafi.sfoods.R
import com.nafi.sfoods.data.model.Category

interface CategoryDataSource{
    fun getCategoryData(): List<Category>
}

class CategoryDataSourceImpl(): CategoryDataSource {
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category( name = "Nasi",img = R.drawable.ic_rice),
            Category( name = "Mie",img = R.drawable.ic_noodle,),
            Category( name = "Minuman",img = R.drawable.ic_drink,),
            Category( name = "Roti",img = R.drawable.ic_bread),
            Category( name = "Snack",img = R.drawable.ic_snack)
        )
    }

}