package com.abhishek.demo.roomdatabase;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.abhishek.demo.model.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DatabaseDao taskDao();
}