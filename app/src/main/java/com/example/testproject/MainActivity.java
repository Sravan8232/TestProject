package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.testproject.Adapter.ToDoAdapter;
import com.example.testproject.Model.CategoryModel;
import com.example.testproject.Utilities.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    private RecyclerView mRecyclerView;
    private FloatingActionButton addCategory;
    private DBHelper db;
    private List<CategoryModel> categoriesList;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        addCategory = findViewById(R.id.addcategory);
        db = new DBHelper(MainActivity.this);
        categoriesList = new ArrayList<>();
        adapter = new ToDoAdapter(db,MainActivity.this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        db.getAllCategories();
        categoriesList = db.getAllCategories();
        Collections.reverse(categoriesList);
        adapter.setCategories(categoriesList);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewCategory.newInstance().show(getSupportFragmentManager(), AddNewCategory.TAG);
            }
        });

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        categoriesList = db.getAllCategories();
        Collections.reverse(categoriesList);
        adapter.setCategories(categoriesList);
        adapter.notifyDataSetChanged();
    }
}