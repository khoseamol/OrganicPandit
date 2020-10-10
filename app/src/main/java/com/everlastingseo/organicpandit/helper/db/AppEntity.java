package com.everlastingseo.organicpandit.helper.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class AppEntity implements Serializable {
    @ColumnInfo(name = "ProductId")
    public String ProductId;
    @ColumnInfo(name = "productType")
    public String productType;
    @ColumnInfo(name = "productName")
    public String productName;
    @ColumnInfo(name = "pPrice")
    public String pPrice;
    @ColumnInfo(name = "itemcount")
    public String itemcount;
    @ColumnInfo(name = "finished")
    public boolean isAvailable;

    @ColumnInfo(name = "BPsell_product_id")
    public String BPsell_product_id;

    @ColumnInfo(name = "EOIorganic_input_ecommerce_id")
    public String EOIorganic_input_ecommerce_id;
    @ColumnInfo(name = "EOIcategory_id")
    public String EOIcategory_id;
    @ColumnInfo(name = "EOIsub_category_id")
    public String EOIsub_category_id;
    @ColumnInfo(name = "EOIbrand")
    public String EOIbrand;

    @ColumnInfo(name = "ESuser_ecommerce_id")
    public String ESuser_ecommerce_id;
    @ColumnInfo(name = "EScategory_id")
    public String ESopcategory_id;
    @ColumnInfo(name = "ESopproduct_id")
    public String ESopproduct_id;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBPsell_product_id() {
        return BPsell_product_id;
    }

    public void setBPsell_product_id(String BPsell_product_id) {
        this.BPsell_product_id = BPsell_product_id;
    }

    public String getEOIorganic_input_ecommerce_id() {
        return EOIorganic_input_ecommerce_id;
    }

    public void setEOIorganic_input_ecommerce_id(String EOIorganic_input_ecommerce_id) {
        this.EOIorganic_input_ecommerce_id = EOIorganic_input_ecommerce_id;
    }

    public String getEOIcategory_id() {
        return EOIcategory_id;
    }

    public void setEOIcategory_id(String EOIcategory_id) {
        this.EOIcategory_id = EOIcategory_id;
    }

    public String getEOIsub_category_id() {
        return EOIsub_category_id;
    }

    public void setEOIsub_category_id(String EOIsub_category_id) {
        this.EOIsub_category_id = EOIsub_category_id;
    }

    public String getEOIbrand() {
        return EOIbrand;
    }

    public void setEOIbrand(String EOIbrand) {
        this.EOIbrand = EOIbrand;
    }

    public String getESuser_ecommerce_id() {
        return ESuser_ecommerce_id;
    }

    public void setESuser_ecommerce_id(String ESuser_ecommerce_id) {
        this.ESuser_ecommerce_id = ESuser_ecommerce_id;
    }

    public String getESopcategory_id() {
        return ESopcategory_id;
    }

    public void setESopcategory_id(String ESopcategory_id) {
        this.ESopcategory_id = ESopcategory_id;
    }

    public String getESopproduct_id() {
        return ESopproduct_id;
    }

    public void setESopproduct_id(String ESopproduct_id) {
        this.ESopproduct_id = ESopproduct_id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String task) {
        this.productName = task;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getItemcount() {
        return itemcount;
    }

    public void setItemcount(String itemcount) {
        this.itemcount = itemcount;
    }

    public boolean isFinished() {
        return isAvailable;
    }

    public void setFinished(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
