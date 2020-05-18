package com.abhishek.demo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "product")
public class Product {
@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "name")
    @NonNull
    @SerializedName("name")
    @Expose
    private String name;
    @ColumnInfo(name = "description")
    @NonNull
    @SerializedName("description")
    @Expose
    private String description;
    @ColumnInfo(name = "regularPrice")
    @NonNull
    @SerializedName("regularPrice")
    @Expose
    private Integer regularPrice;

    @ColumnInfo(name = "salePrice")
    @NonNull
    @SerializedName("salePrice")
    @Expose
    private Integer salePrice;
    /*
    @SerializedName("productPhoto")
    @Expose
    @ColumnInfo(name = "productPhoto")
    @NonNull
    private String productPhoto;

    @SerializedName("colors")
    @Expose
    private List<ColorModel> colors = null;
    @SerializedName("stores")
    @Expose
    @ColumnInfo(name = "stores")
    @NonNull
    private String stores;*/

    public Product(Integer id, @NonNull String name, @NonNull String description, @NonNull Integer regularPrice, @NonNull Integer salePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
     //   this.productPhoto = productPhoto;
    /*    this.colors = colors;
        this.stores = stores;*/
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Integer regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

/*    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }*/
/*
    public List<ColorModel> getColors() {
        return colors;
    }

    public void setColors(List<ColorModel> colors) {
        this.colors = colors;
    }

    public String getStores() {
        return stores;
    }

    public void setStores(String stores) {
        this.stores = stores;
    }*/


}