package com.everlastingseo.organicpandit.orderjson;

import java.util.List;

public class ProductInformation {
    public List<BuyProduct> getBuyProductList() {
        return BuyProduct;
    }

    public void setBuyProductList(List<BuyProduct> buyProductList) {
        this.BuyProduct = buyProductList;
    }

    List<BuyProduct> BuyProduct;

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public List<Shop> getShops() {
        return shops;
    }

    List<Shop> shops;

    public List<OrganicPandit> getOrganicPandit() {
        return organicPandit;
    }

    public void setOrganicPandit(List<OrganicPandit> organicPandit) {
        this.organicPandit = organicPandit;
    }

    List<OrganicPandit> organicPandit;
}
