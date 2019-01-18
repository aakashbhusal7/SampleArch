package com.example.archmodel.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Query("Select * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getArrangedWords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWord(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Delete
    void deleteWord(Word word);

    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    @Update
    void update(Word... word);
}
