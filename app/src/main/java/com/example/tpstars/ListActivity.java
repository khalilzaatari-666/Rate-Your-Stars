package com.example.tpstars;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpstars.adapter.StarAdapter;
import com.example.tpstars.beans.Star;
import com.example.tpstars.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarService service;
    private StarAdapter starAdapter = null;
    private Toolbar myToolbar;

    public ListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Legends");
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        stars = service.findAll();
        starAdapter = new StarAdapter(this, stars);
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void filterList(String s) {
        List<Star> filtredList = new ArrayList<>();
        for (Star star : stars) {
            if (star.getName().toLowerCase().startsWith(s.toLowerCase().trim())) {
                filtredList.add(star);
            }
            if (filtredList.isEmpty()) {
                Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            }
            starAdapter.setFiltredList(filtredList);
        }
    }

    public void init() {
        service.create(new Star("Kanye West", "https://upload.wikimedia.org/wikipedia/commons/0/0c/Kanye_west.webp", 4.5f));
        service.create(new Star("Michael Jackson", "https://www.enjpg.com/img/2020/michael-jackson-1.webp", 3.5f));
        service.create(new Star("Kendrick Lamar", "https://upload.wikimedia.org/wikipedia/commons/3/32/Pulitzer2018-portraits-kendrick-lamar.jpg", 5));
        service.create(new Star("Tame Impala", "https://upload.wikimedia.org/wikipedia/commons/1/13/Tame_Impala_at_Flow_Festival_Helsinki_Aug_10_2019_-24.jpg", 1));
        service.create(new Star("Bon Iver", "https://wallpapers.com/images/high/peaky-blinders-2000-x-3000-picture-3zuazxlvc1yk6s27.webp", 5));
        service.create(new Star("Tame Impala", "https://upload.wikimedia.org/wikipedia/commons/1/13/Tame_Impala_at_Flow_Festival_Helsinki_Aug_10_2019_-24.jpg", 1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);


    }
}