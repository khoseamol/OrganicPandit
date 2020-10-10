package com.everlastingseo.organicpandit.helper.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    @Query("SELECT * FROM AppEntity")
    List<AppEntity> getAll();

    @Insert
    void insert(AppEntity task);

    @Query("DELETE FROM appentity WHERE id = :id")
    void delete(int id);

    @Query("UPDATE appentity SET itemcount=:count WHERE id = :id")
    void update(String count, int id);

    @Query("SELECT * FROM appentity WHERE ProductId = :productName")
    boolean isDataExist(String productName);
}
