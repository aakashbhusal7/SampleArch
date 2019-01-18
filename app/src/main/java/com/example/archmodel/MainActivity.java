package com.example.archmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.archmodel.data.Word;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_DATA_UPDATE_WORD = "extra_word_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar= findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



        RecyclerView recyclerView= findViewById(R.id.recycler_view);
        final WordListAdapter wordListAdapter= new WordListAdapter(this);
        recyclerView.setAdapter(wordListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                wordListAdapter.setWords(words);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
        ItemTouchHelper touchHelper= new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT){
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position=viewHolder.getAdapterPosition();
                        Word myWord=wordListAdapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                "deleted word " + myWord.getWord(),Toast.LENGTH_SHORT).show();
                        wordViewModel.deleteSingleWord(myWord);
                    }
                });
        touchHelper.attachToRecyclerView(recyclerView);
        wordListAdapter.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Word word=wordListAdapter.getWordAtPosition(position);
                launchUpdateActivity(word);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.clear_data){
            Toast.makeText(this,"Clearing the data",Toast.LENGTH_SHORT).show();
            wordViewModel.deleteAllWords();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            wordViewModel.insertWord(word);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String word_data = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NewWordActivity.EXTRA_REPLY_ID, -1);
            if (id != -1) {
                wordViewModel.updateWord(new Word(id, word_data));
            } else {
                Toast.makeText(this, "Unable to update", Toast.LENGTH_SHORT).show();
            }
        }
        else{
                Toast.makeText(
                       this,
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }

    public void launchUpdateActivity(Word word){
        Bundle extras= new Bundle();
        extras.putString(EXTRA_DATA_UPDATE_WORD,word.getWord());
        extras.putInt(EXTRA_DATA_ID,word.getId());
        Intent intent= new Intent(this,NewWordActivity.class);
        intent.putExtras(extras);
        startActivityForResult(intent,UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }
}
