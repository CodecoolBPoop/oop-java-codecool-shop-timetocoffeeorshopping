package com.codecool.shop.dao.implementation;


import com.codecool.shop.config.DataHandler;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB implements ProductCategoryDao {

//    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoDB instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoDB() {
    }

    public static ProductCategoryDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoDB();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
//        TODO implement
    }

    @Override
    public ProductCategory find(int id) {
        return DataHandler.dbQuery.getCategory(id);
    }

    @Override
    public void remove(int id) {
//TODO implement
    }

    @Override
    public List<ProductCategory> getAll() {
        return DataHandler.dbQuery.getCategories();
    }
}
