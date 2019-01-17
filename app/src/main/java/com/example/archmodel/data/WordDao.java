package com.example.archmodel.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Query("Select * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getArrangedWords();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();
}
