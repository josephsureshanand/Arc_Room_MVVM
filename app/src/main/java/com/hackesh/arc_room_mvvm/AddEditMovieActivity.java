package com.hackesh.arc_room_mvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE="com.hackesh.arc_room_mvvm.EXTRA_MOVIE";
    public static final String EXTRA_ID="com.hackesh.arc_room_mvvm.EXTRA_ID";
    public static final String EXTRA_GENRE="com.hackesh.arc_room_mvvm.EXTRA_GENRE";
    public static final String EXTRA_RATING="com.hackesh.arc_room_mvvm.EXTRA_RATING";

    private EditText movie_name;
    private EditText movie_genre;
    private NumberPicker movie_rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        movie_name=findViewById(R.id.movie_name_text);
        movie_genre=findViewById(R.id.genre_text);
        movie_rating=findViewById(R.id.rating_no_pick);

        movie_rating.setMaxValue(10);
        movie_rating.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent=getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Movie");
            movie_name.setText(intent.getStringExtra(EXTRA_MOVIE));
            movie_genre.setText(intent.getStringExtra(EXTRA_GENRE));
            movie_rating.setValue(intent.getIntExtra(EXTRA_RATING,1));
        }
        else
        {   setTitle("Add Movie");}

    }

    private void saveMovie(){

        String moviename=movie_name.getText().toString();
        String moviegenre=movie_genre.getText().toString();
        int movierating=movie_rating.getValue();

        if(moviename.trim().isEmpty()||moviegenre.trim().isEmpty())
        {
            Toast.makeText(this, "Please Fill out All Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data=new Intent();
        data.putExtra(EXTRA_MOVIE,moviename);
        data.putExtra(EXTRA_GENRE,moviegenre);
        data.putExtra(EXTRA_RATING,movierating);

        int id=getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1)
        {
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_menus,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_btn:saveMovie();
            return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}