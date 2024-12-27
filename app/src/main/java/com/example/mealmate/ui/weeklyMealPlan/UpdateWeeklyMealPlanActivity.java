//package com.example.mealmate.ui.weeklyMealPlan;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.mealmate.R;
//import com.example.mealmate.ui.home.AppDatabase;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class UpdateWeeklyMealPlanActivity extends AppCompatActivity {
//
//    private int recipeId;
//    private ImageView recipeImageView;
//    private EditText recipeNameEditText;
//    private EditText recipeInstructionEditText;
//    private Spinner recipeTypeSpinner;
//    private Button saveButton;
//    private Map<String, Integer> ingredientCheckBoxMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_weekly_meal_plan);
//
//        // Initialize views
//        recipeImageView = findViewById(R.id.updateRecipeImageView);
//        recipeNameEditText = findViewById(R.id.updateRecipeNameEditText);
//        recipeInstructionEditText = findViewById(R.id.updateRecipeInstructionEditText);
//        recipeTypeSpinner = findViewById(R.id.updateRecipeTypeSpinner);
//        saveButton = findViewById(R.id.saveUpdatedRecipeButton);
//
//        // Map ingredient names to checkbox IDs
//        ingredientCheckBoxMap = createIngredientCheckBoxMap();
//
//        // Get the recipeId passed from the intent
//        recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
//
//        if (recipeId != -1) {
//            fetchRecipeDetails(recipeId);
//        } else {
//            Toast.makeText(this, "Invalid Recipe ID", Toast.LENGTH_SHORT).show();
//        }
//
//        // Save button click listener (optional implementation)
//        saveButton.setOnClickListener(v -> saveUpdatedRecipe());
//    }
//
//    private Map<String, Integer> createIngredientCheckBoxMap() {
//        Map<String, Integer> map = new HashMap<>();
//        // Map ingredient names to checkbox IDs
//        map.put("Carrot", R.id.checkBoxCarrot);
//        map.put("Onion", R.id.checkBoxOnion);
//        map.put("Potato", R.id.checkBoxPotato);
//        map.put("Tomato", R.id.checkBoxTomato);
//        map.put("Spinach", R.id.checkBoxSpinach);
//        map.put("Broccoli", R.id.checkBoxBroccoli);
//        map.put("Cauliflower", R.id.checkBoxCauliflower);
//        map.put("Cucumber", R.id.checkBoxCucumber);
//        map.put("Zucchini", R.id.checkBoxZucchini);
//        map.put("Capsicum (Bell Pepper)", R.id.checkBoxCapsicum);
//        map.put("Chicken", R.id.checkBoxChicken);
//        map.put("Beef", R.id.checkBoxBeef);
//        map.put("Pork", R.id.checkBoxPork);
//        map.put("Fish", R.id.checkBoxFish);
//        // Add other ingredients here
//        return map;
//    }
//
//    private void fetchRecipeDetails(int recipeId) {
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            WeeklyMealPlanDao dao = AppDatabase.getInstance(getApplicationContext()).weeklyMealPlanDao();
//
//            // Fetch recipe details and ingredients from the database
//            List<RecipeWithIngredients> recipeWithIngredients = dao.getRecipeWithIngredients(recipeId);
//
//            runOnUiThread(() -> {
//                if (recipeWithIngredients != null && !recipeWithIngredients.isEmpty()) {
//                    // Display recipe details
//                    RecipeWithIngredients recipe = recipeWithIngredients.get(0);
//                    recipeNameEditText.setText(recipe.getRecipeName());
//                    recipeInstructionEditText.setText(recipe.getInstruction());
//
//                    // Decode and display recipe image
//                    if (recipe.getPhoto() != null) {
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(recipe.getPhoto(), 0, recipe.getPhoto().length);
//                        recipeImageView.setImageBitmap(bitmap);
//                    }
//
//                    // Check the appropriate ingredient checkboxes
//                    checkIngredientCheckBoxes(recipeWithIngredients);
//                }
//            });
//        });
//    }
//
//    private void checkIngredientCheckBoxes(List<RecipeWithIngredients> recipeWithIngredients) {
//        for (RecipeWithIngredients ingredient : recipeWithIngredients) {
//            String ingredientName = ingredient.getIngredientName();
//            Integer checkBoxId = ingredientCheckBoxMap.get(ingredientName);
//
//            if (checkBoxId != null) {
//                CheckBox checkBox = findViewById(checkBoxId);
//                if (checkBox != null) {
//                    checkBox.setChecked(true);
//                }
//            }
//        }
//    }
//
//    private void saveUpdatedRecipe() {
//        // Save updated recipe details and ingredient selections here
//        Toast.makeText(this, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
//    }
//}

package com.example.mealmate.ui.weeklyMealPlan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mealmate.R;
import com.example.mealmate.ui.home.AppDatabase;
import com.example.mealmate.ui.home.IngredientDao;
import com.example.mealmate.ui.home.MealRecipe;
import com.example.mealmate.ui.home.MealRecipeDao;
import com.example.mealmate.ui.home.RecipeIngredient;
import com.example.mealmate.ui.home.RecipeIngredientDao;
import com.example.mealmate.ui.home.RecipeIngredientViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateWeeklyMealPlanActivity extends AppCompatActivity {

    private int recipeId;
    private ImageView recipeImageView;
    private EditText recipeNameEditText;
    private EditText recipeInstructionEditText;
    private Spinner recipeTypeSpinner;
    private Button saveButton;
    private Map<String, Integer> ingredientCheckBoxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_weekly_meal_plan);
        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbarUpdateRecipe));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        // Initialize views
        recipeImageView = findViewById(R.id.updateRecipeImageView);
        recipeNameEditText = findViewById(R.id.updateRecipeNameEditText);
        recipeInstructionEditText = findViewById(R.id.updateRecipeInstructionEditText);
        recipeTypeSpinner = findViewById(R.id.updateRecipeTypeSpinner);
        saveButton = findViewById(R.id.saveUpdatedRecipeButton);

        // Populate spinner with recipe types
        populateSpinner();

        // Map ingredient names to checkbox IDs
        ingredientCheckBoxMap = createIngredientCheckBoxMap();

        // Get the recipeId passed from the intent
        recipeId = getIntent().getIntExtra("RECIPE_ID", -1);

        if (recipeId != -1) {
            fetchRecipeDetails(recipeId); // Fetch recipe details after spinner is ready
        } else {
            Toast.makeText(this, "Invalid Recipe ID", Toast.LENGTH_SHORT).show();
        }

        saveButton.setOnClickListener(v -> saveUpdatedRecipe());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Finish the activity and go back to the previous screen
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateSpinner() {
        // Add recipe types to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.recipe_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeTypeSpinner.setAdapter(adapter);
    }

    private Map<String, Integer> createIngredientCheckBoxMap() {
        Map<String, Integer> map = new HashMap<>();
        // Map ingredient names to checkbox IDs
        // Vegetables
        map.put("Carrot", R.id.checkBoxCarrot);
        map.put("Onion", R.id.checkBoxOnion);
        map.put("Potato", R.id.checkBoxPotato);
        map.put("Tomato", R.id.checkBoxTomato);
        map.put("Spinach", R.id.checkBoxSpinach);
        map.put("Broccoli", R.id.checkBoxBroccoli);
        map.put("Cauliflower", R.id.checkBoxCauliflower);
        map.put("Cucumber", R.id.checkBoxCucumber);
        map.put("Zucchini", R.id.checkBoxZucchini);
        map.put("Capsicum (Bell Pepper)", R.id.checkBoxCapsicum);
        map.put("Cabbage", R.id.checkBoxCabbage);
        map.put("Eggplant", R.id.checkBoxEggplant);
        map.put("Okra", R.id.checkBoxOkra);
        map.put("Sweet Corn", R.id.checkBoxSweetCorn);
        map.put("Green Peas", R.id.checkBoxGreenPeas);
        map.put("Pumpkin", R.id.checkBoxPumpkin);
        map.put("Radish", R.id.checkBoxRadish);
        map.put("Beetroot", R.id.checkBoxBeetroot);
        map.put("Celery", R.id.checkBoxCelery);
        map.put("Garlic", R.id.checkBoxGarlic);
        map.put("Ginger", R.id.checkBoxGinger);
        map.put("Mushroom", R.id.checkBoxMushroom);
        map.put("Chili", R.id.checkBoxChili);
        map.put("Spring Onion", R.id.checkBoxSpringOnion);

        // Grains
        map.put("Rice", R.id.checkBoxRice);
        map.put("Wheat", R.id.checkBoxWheat);
        map.put("Oats", R.id.checkBoxOats);
        map.put("Quinoa", R.id.checkBoxQuinoa);
        map.put("Millet", R.id.checkBoxMillet);
        map.put("Buckwheat", R.id.checkBoxBuckwheat);
        map.put("Amaranth", R.id.checkBoxAmaranth);
        map.put("Farro", R.id.checkBoxFarro);
        map.put("Spelt", R.id.checkBoxSpelt);
        map.put("Teff", R.id.checkBoxTeff);
        map.put("Couscous", R.id.checkBoxCouscous);
        map.put("Sorghum", R.id.checkBoxSorghum);
        map.put("Barley Flakes", R.id.checkBoxBarleyFlakes);
        map.put("Barley", R.id.checkBoxBarley);
        map.put("Corn", R.id.checkBoxCorn);

        // Spices and Seasonings
        map.put("Salt", R.id.checkBoxSalt);
        map.put("Cinnamon", R.id.checkBoxCinnamon);
        map.put("Clove", R.id.checkBoxClove);
        map.put("Nutmeg", R.id.checkBoxNutmeg);
        map.put("Cardamom", R.id.checkBoxCardamom);
        map.put("Coriander", R.id.checkBoxCoriander);
        map.put("Bay Leaf", R.id.checkBoxBayLeaf);
        map.put("Star Anise", R.id.checkBoxStarAnise);
        map.put("Fenugreek", R.id.checkBoxFenugreek);
        map.put("Mustard Seeds", R.id.checkBoxMustardSeeds);
        map.put("Chili Flakes", R.id.checkBoxChiliFlakes);
        map.put("Pepper", R.id.checkBoxPepper);
        map.put("Cumin", R.id.checkBoxCumin);
        map.put("Turmeric", R.id.checkBoxTurmeric);
        map.put("Paprika", R.id.checkBoxPaprika);

        // Meat
        map.put("Chicken", R.id.checkBoxChicken);
        map.put("Beef", R.id.checkBoxBeef);
        map.put("Pork", R.id.checkBoxPork);
        map.put("Fish", R.id.checkBoxFish);
        // Add other ingredients here
        return map;
    }

    private void fetchRecipeDetails(int recipeId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            WeeklyMealPlanDao dao = AppDatabase.getInstance(getApplicationContext()).weeklyMealPlanDao();

            // Fetch recipe details and ingredients from the database
            List<RecipeWithIngredients> recipeWithIngredients = dao.getRecipeWithIngredients(recipeId);

            runOnUiThread(() -> {
                if (recipeWithIngredients != null && !recipeWithIngredients.isEmpty()) {
                    // Display recipe details
                    RecipeWithIngredients recipe = recipeWithIngredients.get(0);
                    recipeNameEditText.setText(recipe.getRecipeName());
                    recipeInstructionEditText.setText(recipe.getInstruction());

                    // Decode and display recipe image
                    if (recipe.getPhoto() != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(recipe.getPhoto(), 0, recipe.getPhoto().length);
                        recipeImageView.setImageBitmap(bitmap);
                    }

                    // Set the recipe type in the spinner
                    setSpinnerSelection(recipe.getRecipeType());

                    // Check the appropriate ingredient checkboxes
                    checkIngredientCheckBoxes(recipeWithIngredients);
                }
            });
        });
    }

    private void setSpinnerSelection(String recipeType) {
        if (recipeType != null && recipeTypeSpinner.getAdapter() != null) {
            for (int i = 0; i < recipeTypeSpinner.getAdapter().getCount(); i++) {
                if (recipeType.equals(recipeTypeSpinner.getAdapter().getItem(i).toString())) {
                    recipeTypeSpinner.setEnabled(false);
                    recipeTypeSpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void checkIngredientCheckBoxes(List<RecipeWithIngredients> recipeWithIngredients) {
        if (ingredientCheckBoxMap == null) {
            Toast.makeText(this, "Ingredient mapping is not initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        for (RecipeWithIngredients ingredient : recipeWithIngredients) {
            String ingredientName = ingredient.getIngredientName();
            Integer checkBoxId = ingredientCheckBoxMap.get(ingredientName);

            if (checkBoxId != null) {
                CheckBox checkBox = findViewById(checkBoxId);
                if (checkBox != null) {
                    checkBox.setChecked(true);
                }
            }
        }
    }


//    private void saveUpdatedRecipe() {
//        // Save updated recipe details and ingredient selections here
//      //  Toast.makeText(this, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
//
//    }

//    private void saveUpdatedRecipe() {
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            // Initialize the DAO
//            RecipeIngredientDao ingredientDao = AppDatabase.getInstance(getApplicationContext()).recipeIngredientDao();
//
//            // Delete old ingredients for the current recipe
//            ingredientDao.deleteIngredientsByRecipeId(recipeId);
//
//            // Add new ingredients (this logic will be implemented in the next step)
//            for (Map.Entry<String, Integer> entry : ingredientCheckBoxMap.entrySet()) {
//                String ingredientName = entry.getKey();
//                CheckBox checkBox = findViewById(entry.getValue());
//
//                if (checkBox != null && checkBox.isChecked()) {
//                    // Logic to save the ingredient to the database
//                }
//            }
//
//            runOnUiThread(() -> {
//                Toast.makeText(this, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
//            });
//        });
//    }

    private void saveUpdatedRecipe() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Initialize the DAOs
            RecipeIngredientDao ingredientDao = AppDatabase.getInstance(getApplicationContext()).recipeIngredientDao();
            IngredientDao ingredientDetailsDao = AppDatabase.getInstance(getApplicationContext()).ingredientDao();
          //  RecipeIngredientDao recipeIngredientDao = AppDatabase.getInstance(getApplicationContext()).recipeIngredientDao();
            MealRecipeDao mealRecipeDao=AppDatabase.getInstance(getApplicationContext()).mealRecipeDao();

            String updatedName = recipeNameEditText.getText().toString().trim();
            String updatedInstruction = recipeInstructionEditText.getText().toString().trim();
            // Delete old ingredients for the current recipe
            ingredientDao.deleteIngredientsByRecipeId(recipeId);

            MealRecipe updatedRecipe = new MealRecipe();
            updatedRecipe.setRecipeId(recipeId);
            updatedRecipe.setName(updatedName);
            updatedRecipe.setInstruction(updatedInstruction);

// Call the update method
            mealRecipeDao.updateRecipeNameAndInstruction(recipeId, updatedName, updatedInstruction);


            // Add new ingredients (insert rows for checked ingredients)
            for (Map.Entry<String, Integer> entry : ingredientCheckBoxMap.entrySet()) {
                String ingredientName = entry.getKey();
                CheckBox checkBox = findViewById(entry.getValue());

                if (checkBox != null && checkBox.isChecked()) {
                    // Fetch ingredientId by name
                    int ingredientId = ingredientDetailsDao.getIngredientIdByName(ingredientName);

                    // Fetch the existing quantity by ingredientId
                    String quantity = ingredientDetailsDao.getQuantityByIngredientId(ingredientId);

                    // If no quantity is found, assign a default value
                    if (quantity == null) {
                        quantity = "1kg"; // Default value
                    }

                    // Insert the ingredient into the recipe_ingredients_table
                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setRecipeId(recipeId);
                    recipeIngredient.setIngredientId(ingredientId);
                    recipeIngredient.setQuantity(quantity);

                    ingredientDao.insertRecipeIngredient(recipeIngredient);
                }
            }


            runOnUiThread(() -> {
                Toast.makeText(this, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }




}
