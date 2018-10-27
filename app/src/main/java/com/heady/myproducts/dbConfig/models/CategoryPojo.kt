package com.heady.myproducts.dbConfig.models

class CategoryPojo(var id : Long, var name: String,
                   var productCount: Int, var subCat: String?,var subCategory: List<SubCategoryPojo>)