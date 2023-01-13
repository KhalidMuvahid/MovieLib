package com.example.movielib;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.movielib.databinding.FragmentContainerLayoutBinding;
import com.example.movielib.fragments.AddMovieFragment;
import com.example.movielib.fragments.InfoFragment;
import com.example.movielib.fragments.LovelyFragment;
import com.example.movielib.fragments.MainFragment;
import com.example.movielib.fragments.MovieMapsFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener,NavigationView.OnNavigationItemSelectedListener{

    public static final String MOVIE_NAME = "movie_name";
    public static final String MOVIE_IMG = "movie_img";
    public static final String MOVIE_DESC = "movie_disc";
    public static final int REQUIRE_CODE = 222;

    FragmentContainerLayoutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentContainerLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.main.toolbar);
        binding.navView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new MainFragment())
                .commit();

        binding.main.bottomNavigation.setOnItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.main.toolbar,R.string.open,R.string.close);

        binding.main.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.main.collapsingToolBar.setTitle(getResources().getString(R.string.app_name));
                    toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
                    isShow = true;
                } else if(isShow) {
                    binding.main.collapsingToolBar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.yellow));
                    isShow = false;
                }
            }
        });

        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.main.fab.setOnClickListener(v->{
            onClickShareListener();
        });




    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:{
                createExitDialog().show();
                break;
            }
            case R.id.ab_application:{
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new InfoFragment())
                        .commit();
                break;
            }
            case R.id.bottom_home_item:{
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new MainFragment())
                        .commit();
                break;
            }
            case R.id.bottom_add_item:{
                onAddMovieClick();
                break;
            }
            case R.id.bottom_lovelies_item:{
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LovelyFragment())
                        .commit();
                break;
            }
            case R.id.maps:{
                Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MovieMapsFragment.class);
                startActivity(intent);

                break;
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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

    public void onAddMovieClick(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new AddMovieFragment())
                .addToBackStack(null)
                .commit();
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



    public void onClickShareListener() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_message));
        intent.setType("text/plain");
        Intent chooser = Intent.createChooser(intent,getString(R.string.title_of_share_chooser));

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }

    }


}