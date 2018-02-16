package com.zackmatthews.rxjava.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.zackmatthews.rxjava.R;
import com.zackmatthews.rxjava.contracts.MainContract;
import com.zackmatthews.rxjava.models.DbObject;
import com.zackmatthews.rxjava.models.LpApi;
import com.zackmatthews.rxjava.presenters.MainPresenter;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LpApi.ObjectAccessCallback, MainContract.View{

    private MainPresenter presenter;
    private TextView tv_Name;
    private TextView tv_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_Id = findViewById(R.id.tv_id);
        tv_Name = findViewById(R.id.tv_firstName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(UUID.randomUUID());
                presenter.createObject(new DbObject(id, String.valueOf(System.nanoTime())));
                presenter.getObject(id, MainPresenter.ACTION.DISPLAY_OBJ, MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onObjectRetrieved(DbObject obj, MainPresenter.ACTION action) {
        if(action == MainPresenter.ACTION.DISPLAY_OBJ){
            showObject(obj);
        }
    }

    @Override
    public void showObject(DbObject obj) {
        tv_Id.setText(obj.getId());
        tv_Name.setText(obj.getName());
    }
}