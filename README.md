# innoxapp

Home screen
-> http://188.166.207.190/innox/api/v1.0/home/data_list

Category and Sub-Category
-> http://188.166.207.190/innox/api/v1.0/category/list
-> http://188.166.207.190/innox/api/v1.0/sub_category/list_by_category?category_id=1
[category_id : required]
otepad
Product List
-> http://188.166.207.190/innox/api/v1.0/product/list_by_sub_category?sub_category_id=1
[sub_category_id : required]

Product Search
-> http://188.166.207.190/innox/api/v1.0/product/list/search?keyword=Man&startPrice=0&endPrice=15000&category_id=1&sub_category_id=4
[keyword : optional, startPrice : optional, endPrice : optional, category_id : optional, sub_category_id : optional]

Product Detail
-> http://188.166.207.190/innox/api/v1.0/product/detail?product_id=2
[product_id : required]