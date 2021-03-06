package com.example.testproject.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.AddNewCategory;
import com.example.testproject.MainActivity;
import com.example.testproject.Model.CategoryModel;
import com.example.testproject.R;
import com.example.testproject.Utilities.DBHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder>{

    private List<CategoryModel> categories;
    private MainActivity activity;
    private DBHelper mDb;

    public ToDoAdapter(DBHelper db, MainActivity activity){
        this.activity = activity;
        this.mDb = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        System.out.println("Oncreateview");
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println("OnBindview");
        final CategoryModel item = categories.get(position);
        holder.categoryName.setText(item.getCategoryName());
        holder.categoryName.setChecked(toBoolean(item.getStatus()));
        holder.categoryName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    System.out.println("Oncreateviewv");
                    mDb.updateStatus(item.getId(), 1);
                }else {
                    System.out.println("Oncreateview");
                    mDb.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public Context getContext(){
        return activity;
    }

    public void setCategories(List<CategoryModel> mList){
        this.categories = mList;
        notifyDataSetChanged();
    }

    public void deleteCategory(int position) {
        CategoryModel cat = categories.get(position);
        mDb.deleteCategory(cat.getId());
        categories.remove(position);
        notifyItemRemoved(position);
    }

    public void editCategory(int position){
        CategoryModel cat = categories.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", cat.getId());
        bundle.putString("categoryname", cat.getCategoryName());

        AddNewCategory category = new AddNewCategory();
        category.setArguments(bundle);
        category.show(activity.getSupportFragmentManager(), category.getTag());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox categoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
