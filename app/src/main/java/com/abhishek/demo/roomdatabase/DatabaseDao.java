package com.abhishek.demo.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.abhishek.demo.model.Product;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Insert
    void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllOrders(List<Product> products);

    @Query("DELETE FROM product WHERE id = :id")
    void delete(Integer id);



    @Query("UPDATE product SET   name = :name , description =:desc , regularPrice =:regularPrice, salePrice=:salesPrice  WHERE id = :id")
    void updateQuantity(int id , String name , String desc , Integer regularPrice , Integer salesPrice);
}