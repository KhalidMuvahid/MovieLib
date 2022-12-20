package com.example.movielib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movielib.data.Data;
import com.example.movielib.data.Movie;
import com.example.movielib.recycler.MainRecycleAdapter;
import com.example.movielib.recycler.RecyclerItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemClickListener, NavigationBarView.OnItemSelectedListener {

    public static final String MOVIE_NAME = "movie_name";
    public static final String MOVIE_IMG = "movie_img";
    public static final String MOVIE_DESC = "movie_disc";
    public static final int REQUIRE_CODE = 222;
    private MainRecycleAdapter recycler;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        fab = findViewById(R.id.fab);
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.navView);

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnItemSelectedListener(this);

        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
                    toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.yellow));
                    isShow = false;
                }
            }
        });

        RecyclerView rc = findViewById(R.id.recyclerView);
        recycler =new MainRecycleAdapter(Data.getInstance().movies(),this);
        rc.setAdapter(recycler);
        rc.setLayoutManager(new LinearLayoutManager(this));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fab.setOnClickListener(v->{
            onClickShareListener();
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        recycler.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.share: {
                onClickShareListener();
                break;
            }
            case R.id.add_movie:{
                onAddMovieClick();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return true;
    }

    public void onClickShareListener() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_message));
        intent.setType("text/plain");
        Intent chooser = Intent.createChooser(intent,getString(R.string.title_of_share_chooser));

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }

    }

    public void onAddMovieClick(){
        Intent intent = new Intent(this,AddMovieActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUIRE_CODE){
            if (resultCode == RESULT_OK){
                Boolean liked = data.getBooleanExtra("liked",false);
                String comment = data.getStringExtra("comment");
                System.out.println(liked);
                System.out.println(comment);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:{
                createExitDialog().show();
                break;
            }
            case R.id.ab_application:{
                Toast.makeText(this,"About Application",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.bottom_add_item:{
                onAddMovieClick();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private AlertDialog createExitDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(R.string.exit_dialog_title);
        dialog.setPositiveButton(getString(R.string.positiv_dialog_button),  new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton(getString(R.string.negativ_dilaog_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return dialog.create();
    }


    @Override
    public void onClickRecyclerItem(int position,int id) {

        switch (id){
            case R.id.editImBt:{
                openEditBottomSheet(position);
                break;
            }

            case R.id.detailBt:

            case R.id.movieImage: {
                openDetail(Data.getInstance().getMovie(position));
                break;
            }
        }

    }

    private void openEditBottomSheet(int position){
        EditText editText= findViewById(R.id.change_movie_name);
        editText.setText(Data.getInstance().getMovie(position).getName());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void openDetail(Movie movie){
        movie.setVisited(true);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MainActivity.MOVIE_NAME,movie.getName());
        intent.putExtra(MainActivity.MOVIE_DESC,movie.getDescription());
        intent.putExtra(MainActivity.MOVIE_IMG,movie.getImage());
        startActivityForResult(intent,MainActivity.REQUIRE_CODE);
    }

}