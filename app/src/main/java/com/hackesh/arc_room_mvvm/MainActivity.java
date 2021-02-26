package com.hackesh.arc_room_mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_CODE_REF = 1;
    private static final int EDIT_CODE_REF = 2;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton=findViewById(R.id.add_btn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddEditMovieActivity.class);
                startActivityForResult(intent,ADD_CODE_REF);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.movie_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final RecycleAdapter adapter=new RecycleAdapter();


        recyclerView.setAdapter(adapter);

        appViewModel= new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.getListLiveData().observe(this, new Observer<List<SQLEntity>>() {
            @Override
            public void onChanged(List<SQLEntity> sqlEntities) {

                adapter.setMovies(sqlEntities);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                appViewModel.delete(adapter.getEntry(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SQLEntity sqlEntity) {
                Intent intent=new Intent(MainActivity.this, AddEditMovieActivity.class);
                intent.putExtra(AddEditMovieActivity.EXTRA_ID,sqlEntity.getId());
                intent.putExtra(AddEditMovieActivity.EXTRA_MOVIE,sqlEntity.getMovie());
                intent.putExtra(AddEditMovieActivity.EXTRA_GENRE,sqlEntity.getGenere());
                intent.putExtra(AddEditMovieActivity.EXTRA_RATING,sqlEntity.getRating());

                startActivityForResult(intent,EDIT_CODE_REF);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_CODE_REF&&resultCode==RESULT_OK)
        {
            String movie=data.getStringExtra(AddEditMovieActivity.EXTRA_MOVIE);
            String genre=data.getStringExtra(AddEditMovieActivity.EXTRA_GENRE);
            int rating=data.getIntExtra(AddEditMovieActivity.EXTRA_RATING,1);

            SQLEntity sqlEntity=new SQLEntity(movie,rating,genre);
            appViewModel.insert(sqlEntity);
            Toast.makeText(this, movie+" Added", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==EDIT_CODE_REF && resultCode==RESULT_OK)
        {
            int id=data.getIntExtra(AddEditMovieActivity.EXTRA_ID,-1);
            if (id==-1)
            {
                Toast.makeText(this, "Note Cannot be Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String movie=data.getStringExtra(AddEditMovieActivity.EXTRA_MOVIE);
            String genre=data.getStringExtra(AddEditMovieActivity.EXTRA_GENRE);
            int rating=data.getIntExtra(AddEditMovieActivity.EXTRA_RATING,1);

            SQLEntity sqlEntity=new SQLEntity(movie,rating,genre);
            sqlEntity.setId(id);
            appViewModel.update(sqlEntity);
            Toast.makeText(this, movie+" Updated", Toast.LENGTH_SHORT).show();

        }
        else Toast.makeText(this, "Movie Not Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_all:appViewModel.deleteAll();
                Toast.makeText(this, "Deleted All", Toast.LENGTH_SHORT).show();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}