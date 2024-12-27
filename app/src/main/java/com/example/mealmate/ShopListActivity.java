//package com.example.mealmate;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.mealmate.ui.weeklyMealPlan.WeeklyMealPlanViewModel;
//
//public class ShopListActivity extends AppCompatActivity {
//
//    Button save,sendSms,map;
//    CheckBox checkBox;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shop_list);
//
//        // Set up the Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbarShopList);
//        setSupportActionBar(toolbar);
//
//        // Enable the back arrow
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        // Handle back arrow click
//        toolbar.setNavigationOnClickListener(view -> onBackPressed());
//        save= findViewById(R.id.btn_save_shop_list);
//        sendSms=findViewById(R.id.btn_send_message);
//        map=findViewById(R.id.btn_map_for_shop_list);
//
//
//
//
//        // Initialize RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.recycler_ingredient_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Initialize Adapter
//        GroupedIngredientAdapter adapter = new GroupedIngredientAdapter();
//        recyclerView.setAdapter(adapter);
//
//        // Initialize ViewModel
//        WeeklyMealPlanViewModel viewModel = new ViewModelProvider(this).get(WeeklyMealPlanViewModel.class);
//
//        // Observe data from ViewModel
//        viewModel.getIngredientsGroupedByType().observe(this, groupedIngredients -> {
//            if (groupedIngredients != null && !groupedIngredients.isEmpty()) {
//                Log.d("ShopListActivity", "Data received: " + groupedIngredients.toString());
//                adapter.setGroupedIngredientList(groupedIngredients);
//            } else {
//                Log.d("ShopListActivity", "No data received.");
//            }
//        });
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ShopListActivity.this, "Save btn", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        sendSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ShopListActivity.this, "Send SMS btn", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ShopListActivity.this, "Map  btn", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//
//}
package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.ui.weeklyMealPlan.WeeklyMealPlanViewModel;

import java.util.List;

public class ShopListActivity extends AppCompatActivity {

    private Button save, sendSms, map;
    private GroupedIngredientAdapter adapter;
    private WeeklyMealPlanViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarShopList);
        setSupportActionBar(toolbar);

        // Enable the back arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Handle back arrow click
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        save = findViewById(R.id.btn_save_shop_list);
        sendSms = findViewById(R.id.btn_send_message);
        map = findViewById(R.id.btn_map_for_shop_list);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_ingredient_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Adapter with the listener for checkbox changes
        adapter = new GroupedIngredientAdapter((ingredientId, isChecked) -> {
            // Update the purchase state in the database when checkbox is toggled
            viewModel.updateIngredientPurchaseState(ingredientId, isChecked);
        });

        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(WeeklyMealPlanViewModel.class);

        // Observe data from ViewModel
        viewModel.getIngredientsGroupedByType().observe(this, groupedIngredients -> {
            if (groupedIngredients != null && !groupedIngredients.isEmpty()) {
                Log.d("ShopListActivity", "Data received: " + groupedIngredients.toString());
                adapter.setGroupedIngredientList(groupedIngredients);
            } else {
                Log.d("ShopListActivity", "No data received.");
            }
        });

        // Save button click listener
        save.setOnClickListener(v -> {
          //  savePurchaseState();
            Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show();


        });

//        sendSms.setOnClickListener(v -> {
//            Toast.makeText(ShopListActivity.this, "Send SMS button clicked!", Toast.LENGTH_SHORT).show();
//        });

        sendSms.setOnClickListener(v -> {
            // Get the list of ingredients with their purchase state
            List<IngredientGroupedByType> ingredientList = adapter.getGroupedIngredientList();

            // Build the message string with each ingredient on a new line
            StringBuilder message = new StringBuilder();
            for (IngredientGroupedByType ingredient : ingredientList) {
                message.append(ingredient.getIngredientName())
                        .append(" - ")
                        .append(ingredient.isPurchased() ? "Purchased" : "Not Purchased")
                        .append("\n");
            }

            // Convert the message to a String
            String messageString = message.toString();

            // Pass the message string to the next activity
            Intent intent = new Intent(ShopListActivity.this, SendMessageActivity.class);
            intent.putExtra("ingredient_message", messageString);
            startActivity(intent);
        });


//    private void savePurchaseState() {
//        // Get the updated ingredient list from the adapter
//        List<IngredientGroupedByType> updatedIngredients = adapter.getGroupedIngredientList();
//
//        // Update the purchase state in the database
//        for (IngredientGroupedByType ingredient : updatedIngredients) {
//            viewModel.updateIngredientPurchaseState(ingredient.getIngredientId(), ingredient.isPurchased());
//        }
//
//        // Show a success message
//        Toast.makeText(this, "Purchase states saved successfully!", Toast.LENGTH_SHORT).show();
//    }

        map.setOnClickListener(v -> {
            // Navigate to ShopLocationsActivity
            Intent intent = new Intent(ShopListActivity.this, ShopLocationsActivity.class);
            startActivity(intent);
        });

    }

}
