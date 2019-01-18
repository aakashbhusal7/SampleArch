package com.example.archmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.archmodel.data.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(Application application){
        super(application);
        wordRepository=new WordRepository(application);
        allWords=wordRepository.getAllWords();
    }

    LiveData<List<Word>> getAllWords(){
        return allWords;
    }
    public void insertWord(Word word){
        wordRepository.insertWord(word);
    }
    public void deleteAllWords(){
        wordRepository.deleteAllWords();
    }
    public void deleteSingleWord(Word word){
        wordRepository.deleteWord(word);
    }
    public void updateWord(Word words){
        wordRepository.updateWord(words);
    }
}
