package com.example.archmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archmodel.data.Word;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Word> wordList;
    private static ClickListener clickListener;

    public WordListAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.layout_recycler_view,viewGroup,false);
        return new WordViewHolder((itemView));

    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder wordViewHolder, int position) {
        if(wordList!=null){
            Word currentWord=wordList.get(position);
            wordViewHolder.wordView.setText(currentWord.getWord());
        }
        else {
            wordViewHolder.wordView.setText("no word");
        }

    }

    public Word getWordAtPosition(int position){
        return wordList.get(position);

    }

    void setWords(List<Word> words){
        wordList = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(wordList!=null) {
            return wordList.size();
        }
        else
            return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder{
        private final TextView wordView;

        private  WordViewHolder(View view){
            super(view);
            wordView=view.findViewById(R.id.textView);
            wordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        WordListAdapter.clickListener = clickListener;
    }
//    public interface ClickListener{
//        void onItemClick(View view, int position);
//    }
}
