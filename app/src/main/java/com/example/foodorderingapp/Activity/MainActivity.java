package com.example.foodorderingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.Adapter.CategoryAdapter;
import com.example.foodorderingapp.Adapter.PopularAdapter;
import com.example.foodorderingapp.Domain.CategoryDomain;
import com.example.foodorderingapp.Domain.FoodDomain;
import com.example.foodorderingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.textView4);

        String username = getIntent().getStringExtra("keyname");
        name.setText(username);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigator();
    }

    private void bottomNavigator() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homebtn);
        LinearLayout feedbackbtn = findViewById(R.id.feedbackbtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));

            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });
        feedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                finish();
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Pizza", "cat_1"));
        category.add(new CategoryDomain("Burger", "cat_2"));
        category.add(new CategoryDomain("HotDog", "cat_3"));
        category.add(new CategoryDomain("Drinks", "cat_4"));
        category.add(new CategoryDomain("Donut", "cat_5"));


        adapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni pizza", "pop_1", "slices pepperoni,mozzerella cheese,fresh oregano,ground black pepper,pizza sauce", 250));
        foodList.add(new FoodDomain("Cheese Burger", "pop_2", "beef,gouda cheese,Special Sauce,Lettuce,tomato", 140));
        foodList.add(new FoodDomain("Vegetable pizza", "pop_3", "olive oil,Vegetable oil,pitted kalamata,cherry tomatoes,fresh oregano,basil", 120));
        foodList.add(new FoodDomain("Sausage McMuffin", "sausage", " freshly toasted English muffin, topped with a savory hot sausage patty and a slice of melty American cheese", 220));
        foodList.add(new FoodDomain("Coca-Cola", "cocacola", "Enjoy a refreshing Coke® at CityBurger’s in extra small, small, medium and large", 30));
        foodList.add(new FoodDomain("Cheeseburger \n Combo Meal", "cheeseburger", "CityBurger's Cheeseburger Meal is 2 simple, satisfying classic 100% beef burgers, served with our World Famous Fries and your choice of a medium CityBurger’s soda or soft drink.", 340));
        foodList.add(new FoodDomain("CbFlurry with \n OREO Cookies", "oreo", "CityBurger’s CbFlurry with OREO Cookies is a popular combination of creamy vanilla soft serve with crunchy pieces of OREO cookies", 150));
        foodList.add(new FoodDomain("Double Quarter \n Pounder with Cheese", "doublequarter", "Each Double Quarter Pounder with Cheese features two quarter pound* 100% fresh beef burger patties that are hot, deliciously juicy and cooked when you order", 280));

        adapter2 = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(adapter2);

    }


}