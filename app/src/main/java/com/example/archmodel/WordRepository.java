package com.example.archmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.archmodel.data.Word;
import com.example.archmodel.data.WordDao;
import com.example.archmodel.data.WordRoomDatabase;

import java.util.List;

public class WordRepository {

    private WordDao wordDao;
    private LiveData<List<Word>>mAllWords;

    WordRepository(Application application){
        WordRoomDatabase wordRoomDatabase= WordRoomDatabase.getDatabase(application);
        wordDao=wordRoomDatabase.wordDao();
        mAllWords=wordDao.getArrangedWords();
    }
    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }
    public void insertWord(Word word){
        new insertAsyncTask(wordDao).execute(word);
    }
    private static class insertAsyncTask extends AsyncTask<Word,Void,String>{
        private WordDao wordDao;

        insertAsyncTask(WordDao wordDao){
            this.wordDao=wordDao;
        }

        @Override
        protected String doInBackground(Word... words) {
            wordDao.insertWord(words[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
