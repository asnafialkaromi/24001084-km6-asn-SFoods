package com.nafi.sfoods.data.datasource.category

import com.nafi.sfoods.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategories(): List<Category> {
        return mutableListOf(
            Category(
                name = "Nasi",
                imgUrl = "https://github.com/asnafialkaromi/sfoods-assets/blob/main/category_img/ic_rice.png?raw=true"
            ),
            Category(
                name = "Mie",
                imgUrl = "https://github.com/asnafialkaromi/sfoods-assets/blob/main/category_img/ic_noodle.png?raw=true"
            ),
            Category(
                name = "Minuman",
                imgUrl = "https://github.com/asnafialkaromi/sfoods-assets/blob/main/category_img/ic_drink.png?raw=true"
            ),
            Category(
                name = "Roti",
                imgUrl = "https://github.com/asnafialkaromi/sfoods-assets/blob/main/category_img/ic_bread.png?raw=true"
            ),
            Category(
                name = "Snack",
                imgUrl = "https://github.com/asnafialkaromi/sfoods-assets/blob/main/category_img/ic_snack.png?raw=true"
            )
        )
    }
}