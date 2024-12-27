//package com.example.mealmate.ui.home;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.ImageDecoder;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.content.ContextCompat;
//
//import com.example.mealmate.R;
//import com.google.android.material.snackbar.Snackbar;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//public class ShareNewRecipeActivity extends AppCompatActivity {
//    private ImageView imageViewAddImage;
//    private EditText recipeNameEditText, recipeInstructionEditText;
//    private Spinner recipeTypeSpinner;
//    private ConstraintLayout constraintLayout;
//    private ActivityResultLauncher<String[]> permissionsResultLauncher;
//    private ActivityResultLauncher<Intent> photoPickerResultLauncher;
//    private Bitmap selectedImage;
//    private Button addorShareToDatabase;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share_new_recipe);
//
//        // Initialize UI components
//        constraintLayout = findViewById(R.id.mainAddImage); // Root layout ID
//        imageViewAddImage = findViewById(R.id.shareNewRecipeImageView);
//        recipeNameEditText = findViewById(R.id.nameOfRecipeEditTextsnr);
//        recipeInstructionEditText = findViewById(R.id.instructionOfRecipeEditTextsnr);
//        recipeTypeSpinner = findViewById(R.id.recipeTypeSpinner);
//        addorShareToDatabase=findViewById(R.id.shareYourNewRecipeButtonsnr);
//
//        addorShareToDatabase.setOnClickListener(v -> {
//            if (selectedImage == null) {
//                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
//            } else {
//                // Retrieve data from input fields
//                String recipeName = recipeNameEditText.getText().toString().trim();
//                String rcipeInstruction = recipeInstructionEditText.getText().toString().trim();
//                String recipeType = recipeTypeSpinner.getSelectedItem().toString();
//
//                // Validate inputs
//                if (recipeName.isEmpty() || rcipeInstruction.isEmpty()) {
//                    Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Compress the image
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                Bitmap scaledImage = makeSmall(selectedImage, 300); // Reduce dimensions
//                scaledImage.compress(Bitmap.CompressFormat.JPEG, 30, outputStream); // Aggressive compression
//                byte[] image = outputStream.toByteArray();
//
//                // Check the size of the compressed image
//                if (image.length > 1024 * 1024) { // If larger than 1MB
//                    Toast.makeText(this, "Image too large, please select a smaller image", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Prepare data to pass back via Intent
//                Intent intent = new Intent();
//                intent.putExtra("recipeName", recipeName);
//                intent.putExtra("rcipeInstruction", rcipeInstruction);
//                intent.putExtra("recipeType", recipeType);
//                intent.putExtra("image", image);
//
//                // Send result back to the calling activity
//                setResult(RESULT_OK, intent);
//                finish(); // Close the activity
//            }
//        });
//
//
//        Toolbar toolbar = findViewById(R.id.toolbarAddImage); // Ensure the ID matches your XML layout
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back arrow
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24); // Optional
//            getSupportActionBar().setTitle("Share New Recipe");
//        }
//
//
//        // Set up Spinner
//        String[] recipeTypes = {"Chicken", "Pork", "Beef", "Fish", "Noodle", "Appetizers"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipeTypes);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        recipeTypeSpinner.setAdapter(adapter);
//
//        // Handle Spinner selection
//        recipeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedType = parent.getItemAtPosition(position).toString();
//               // Toast.makeText(ShareNewRecipeActivity.this, "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Do nothing
//            }
//        });
//
//        // Set up permissions and photo picker
//        setupPermissions();
//        setupPhotoPicker();
//
//        // Handle ImageView click for photo picker
//        imageViewAddImage.setOnClickListener(v -> {
//            if (hasPermission()) {
//                openPhotoPicker();
//            } else {
//                requestPermissions();
//            }
//        });
//
//
//    }
//
//    public Bitmap makeSmall(Bitmap image,int maxSize){
//        int width=image.getWidth();
//        int height=image.getHeight();
//
//        float ratio=(float) width/ (float)  height;
//
//        if(ratio>1){
//            width=maxSize;
//            height =(int) (width/ratio);
//        }
//        else{
//            height=maxSize;
//            width=(int) (height*ratio);
//        }
//        return Bitmap.createScaledBitmap(image,width,height,true);
//
//    }
//
//    private void setupPermissions() {
//        permissionsResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
//            boolean allGranted = true;
//            for (Boolean isGranted : result.values()) {
//                if (!isGranted) {
//                    allGranted = false;
//                    break;
//                }
//            }
//            if (allGranted) {
//                openPhotoPicker();
//            } else {
//                showPermissionRationale();
//            }
//        });
//    }
//
//    private void setupPhotoPicker() {
//        photoPickerResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                Uri selectedImageUri = result.getData().getData();
//                if (selectedImageUri != null) {
//                    try {
//                        selectedImage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
//                                ? ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), selectedImageUri))
//                                : MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//                        imageViewAddImage.setImageBitmap(selectedImage);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    private boolean hasPermission() {
//        if (Build.VERSION.SDK_INT >= 33) {
//            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
//        } else {
//            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
//        }
//    }
//
//    private void requestPermissions() {
//        if (Build.VERSION.SDK_INT >= 33) {
//            permissionsResultLauncher.launch(new String[]{Manifest.permission.READ_MEDIA_IMAGES});
//        } else {
//            permissionsResultLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
//        }
//    }
//
//    private void openPhotoPicker() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        photoPickerResultLauncher.launch(intent);
//    }
//
//    private void showPermissionRationale() {
//        Snackbar.make(constraintLayout, "Please grant permission to access photos.", Snackbar.LENGTH_INDEFINITE)
//                .setAction("OK", v -> requestPermissions())
//                .show();
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed(); // Navigate back to the previous activity
//        return true;
//    }
//}


// new code .....


package com.example.mealmate.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.mealmate.R;
import com.example.mealmate.ui.home.AppDatabase;
import com.example.mealmate.ui.home.MealRecipe;
import com.example.mealmate.ui.home.RecipeIngredient;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareNewRecipeActivity extends AppCompatActivity {
    private ImageView imageViewAddImage;
    private EditText recipeNameEditText, recipeInstructionEditText;
    private Spinner recipeTypeSpinner;
    private ConstraintLayout constraintLayout;
    private ActivityResultLauncher<String[]> permissionsResultLauncher;
    private ActivityResultLauncher<Intent> photoPickerResultLauncher;
    private Bitmap selectedImage;
    private Button addorShareToDatabase;

    // New: Database instance
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_new_recipe);

        // Initialize UI components
        constraintLayout = findViewById(R.id.mainAddImage);
        imageViewAddImage = findViewById(R.id.shareNewRecipeImageView);
        recipeNameEditText = findViewById(R.id.nameOfRecipeEditTextsnr);
        recipeInstructionEditText = findViewById(R.id.instructionOfRecipeEditTextsnr);
        recipeTypeSpinner = findViewById(R.id.recipeTypeSpinner);
        addorShareToDatabase = findViewById(R.id.shareYourNewRecipeButtonsnr);

        // New: Initialize database
        db = AppDatabase.getInstance(this);

        addorShareToDatabase.setOnClickListener(v -> saveRecipeAndIngredients());

        Toolbar toolbar = findViewById(R.id.toolbarAddImage);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
            getSupportActionBar().setTitle("Share New Recipe");
        }

        // Set up Spinner
        String[] recipeTypes = {"Chicken", "Pork", "Beef", "Fish", "Noodle", "Appetizers"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipeTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeTypeSpinner.setAdapter(adapter);

        setupPermissions();
        setupPhotoPicker();

        imageViewAddImage.setOnClickListener(v -> {
            if (hasPermission()) {
                openPhotoPicker();
            } else {
                requestPermissions();
            }
        });
    }

    // New: Save Recipe and Ingredients
//    private void saveRecipeAndIngredients() {
//        // Validate input fields
//        String recipeName = recipeNameEditText.getText().toString().trim();
//        String recipeInstruction = recipeInstructionEditText.getText().toString().trim();
//        String recipeType = recipeTypeSpinner.getSelectedItem().toString();
//
//        if (recipeName.isEmpty() || recipeInstruction.isEmpty() || selectedImage == null) {
//            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Compress the image
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Bitmap scaledImage = makeSmall(selectedImage, 300);
//        scaledImage.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
//        byte[] image = outputStream.toByteArray();
//
//        // Collect selected ingredients and quantities
//        List<Integer> selectedIngredientIds = getSelectedIngredientIds();
//        List<String> quantities = getSelectedIngredientQuantities();
//
//        if (selectedIngredientIds.isEmpty()) {
//            Toast.makeText(this, "Please select at least one ingredient", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Insert recipe and link ingredients in a background thread
//        new Thread(() -> {
//            long recipeId = insertRecipe(recipeName, recipeType, image, recipeInstruction);
//            linkIngredientsToRecipe((int) recipeId, selectedIngredientIds, quantities);
//
//            runOnUiThread(() -> Toast.makeText(this, "Recipe saved successfully!", Toast.LENGTH_SHORT).show());
//            finish();
//        }).start();
//    }

    private void saveRecipeAndIngredients() {
        // Validate input fields
        String recipeName = recipeNameEditText.getText().toString().trim();
        String recipeInstruction = recipeInstructionEditText.getText().toString().trim();
        String recipeType = recipeTypeSpinner.getSelectedItem().toString();

        if (recipeName.isEmpty() || recipeInstruction.isEmpty() || selectedImage == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Compress the image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Bitmap scaledImage = makeSmall(selectedImage, 300);
        scaledImage.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
        byte[] image = outputStream.toByteArray();

        // Collect selected ingredients
        List<Integer> selectedIngredientIds = getSelectedIngredientIds();

        if (selectedIngredientIds.isEmpty()) {
            Toast.makeText(this, "Please select at least one ingredient", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert recipe and link ingredients in a background thread
        new Thread(() -> {
            long recipeId = insertRecipe(recipeName, recipeType, image, recipeInstruction);

            // Fetch or assign quantities dynamically
            for (int ingredientId : selectedIngredientIds) {
                // Fetch the existing quantity if available
                String existingQuantity = db.recipeIngredientDao().getQuantityByRecipeAndIngredient((int) recipeId, ingredientId);

                // Use the existing quantity if found, otherwise assign a default
                String quantity = (existingQuantity != null) ? existingQuantity : "1kg"; // Assign "1kg" as default if not found

                // Create and insert RecipeIngredient
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipeId((int) recipeId);
                recipeIngredient.setIngredientId(ingredientId);
                recipeIngredient.setQuantity(quantity); // Dynamically assign quantity
                db.recipeIngredientDao().insert(recipeIngredient);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Recipe saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }


    // New: Insert Recipe
    private long insertRecipe(String name, String type, byte[] photo, String instruction) {
        MealRecipe recipe = new MealRecipe();
        recipe.setName(name);
        recipe.setType(type);
        recipe.setPhoto(photo);
        recipe.setInstruction(instruction);
        return db.mealRecipeDao().insert(recipe);
    }

    // New: Link Ingredients to Recipe
    private void linkIngredientsToRecipe(int recipeId, List<Integer> ingredientIds, List<String> quantities) {
        for (int i = 0; i < ingredientIds.size(); i++) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipeId(recipeId);
            recipeIngredient.setIngredientId(ingredientIds.get(i));
            recipeIngredient.setQuantity(quantities.get(i));
            db.recipeIngredientDao().insert(recipeIngredient);
        }
    }

    // New: Get Selected Ingredients
    private List<Integer> getSelectedIngredientIds() {
        List<Integer> ingredientIds = new ArrayList<>();

        // Map of CheckBox IDs to ingredient IDs
        Map<Integer, Integer> checkBoxIngredientMap = new HashMap<>();
        checkBoxIngredientMap.put(R.id.checkBoxCarrot, 1);
        checkBoxIngredientMap.put(R.id.checkBoxOnion, 2);
        checkBoxIngredientMap.put(R.id.checkBoxPotato, 3);
        checkBoxIngredientMap.put(R.id.checkBoxTomato, 4);
        checkBoxIngredientMap.put(R.id.checkBoxSpinach, 5);
        checkBoxIngredientMap.put(R.id.checkBoxBroccoli, 6);
        checkBoxIngredientMap.put(R.id.checkBoxCauliflower, 7);
        checkBoxIngredientMap.put(R.id.checkBoxCucumber, 8);
        checkBoxIngredientMap.put(R.id.checkBoxZucchini, 9);
        checkBoxIngredientMap.put(R.id.checkBoxCapsicum, 10);
        checkBoxIngredientMap.put(R.id.checkBoxCabbage, 11);
        checkBoxIngredientMap.put(R.id.checkBoxEggplant, 12);
        checkBoxIngredientMap.put(R.id.checkBoxOkra, 13);
        checkBoxIngredientMap.put(R.id.checkBoxSweetCorn, 14);
        checkBoxIngredientMap.put(R.id.checkBoxGreenPeas, 15);
        checkBoxIngredientMap.put(R.id.checkBoxPumpkin, 16);
        checkBoxIngredientMap.put(R.id.checkBoxRadish, 17);
        checkBoxIngredientMap.put(R.id.checkBoxBeetroot, 18);
        checkBoxIngredientMap.put(R.id.checkBoxCelery, 19);
        checkBoxIngredientMap.put(R.id.checkBoxGarlic, 20);
        checkBoxIngredientMap.put(R.id.checkBoxGinger, 21);
        checkBoxIngredientMap.put(R.id.checkBoxMushroom, 22);
        checkBoxIngredientMap.put(R.id.checkBoxChili, 23);
        checkBoxIngredientMap.put(R.id.checkBoxSpringOnion, 24);
        checkBoxIngredientMap.put(R.id.checkBoxRice, 25);
        checkBoxIngredientMap.put(R.id.checkBoxWheat, 26);
        checkBoxIngredientMap.put(R.id.checkBoxOats, 27);
        checkBoxIngredientMap.put(R.id.checkBoxQuinoa, 28);
        checkBoxIngredientMap.put(R.id.checkBoxMillet, 29);
        checkBoxIngredientMap.put(R.id.checkBoxBuckwheat, 30);
        checkBoxIngredientMap.put(R.id.checkBoxAmaranth, 31);
        checkBoxIngredientMap.put(R.id.checkBoxFarro, 32);
        checkBoxIngredientMap.put(R.id.checkBoxSpelt, 33);
        checkBoxIngredientMap.put(R.id.checkBoxTeff, 34);
        checkBoxIngredientMap.put(R.id.checkBoxCouscous, 35);
        checkBoxIngredientMap.put(R.id.checkBoxSorghum, 36);
        checkBoxIngredientMap.put(R.id.checkBoxBarleyFlakes, 37);
        checkBoxIngredientMap.put(R.id.checkBoxBarley, 38);
        checkBoxIngredientMap.put(R.id.checkBoxCorn, 39);
        checkBoxIngredientMap.put(R.id.checkBoxSalt, 40);
        checkBoxIngredientMap.put(R.id.checkBoxCinnamon, 41);
        checkBoxIngredientMap.put(R.id.checkBoxClove, 42);
        checkBoxIngredientMap.put(R.id.checkBoxNutmeg, 43);
        checkBoxIngredientMap.put(R.id.checkBoxCardamom, 44);
        checkBoxIngredientMap.put(R.id.checkBoxCoriander, 45);
        checkBoxIngredientMap.put(R.id.checkBoxBayLeaf, 46);
        checkBoxIngredientMap.put(R.id.checkBoxStarAnise, 47);
        checkBoxIngredientMap.put(R.id.checkBoxFenugreek, 48);
        checkBoxIngredientMap.put(R.id.checkBoxMustardSeeds, 49);
        checkBoxIngredientMap.put(R.id.checkBoxChiliFlakes, 50);
        checkBoxIngredientMap.put(R.id.checkBoxPepper, 51);
        checkBoxIngredientMap.put(R.id.checkBoxCumin, 52);
        checkBoxIngredientMap.put(R.id.checkBoxTurmeric, 53);
        checkBoxIngredientMap.put(R.id.checkBoxPaprika, 54);
        checkBoxIngredientMap.put(R.id.checkBoxChicken, 55);
        checkBoxIngredientMap.put(R.id.checkBoxBeef, 56);
        checkBoxIngredientMap.put(R.id.checkBoxPork, 57);
        checkBoxIngredientMap.put(R.id.checkBoxFish, 58);
        checkBoxIngredientMap.put(R.id.checkBoxLamb, 59);

        // Iterate through the map and check selected checkboxes
        for (Map.Entry<Integer, Integer> entry : checkBoxIngredientMap.entrySet()) {
            CheckBox checkBox = findViewById(entry.getKey());
            if (checkBox != null && checkBox.isChecked()) {
                ingredientIds.add(entry.getValue());
            }
        }

        return ingredientIds;
    }


    // New: Get Ingredient Quantities
    private List<String> getSelectedIngredientQuantities() {
        List<String> quantities = new ArrayList<>();

        // Map of CheckBox IDs to quantities
        Map<Integer, String> checkBoxQuantityMap = new HashMap<>();
        checkBoxQuantityMap.put(R.id.checkBoxCarrot, "500g");
        checkBoxQuantityMap.put(R.id.checkBoxOnion, "200g");
        checkBoxQuantityMap.put(R.id.checkBoxPotato, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxTomato, "300g");
        checkBoxQuantityMap.put(R.id.checkBoxSpinach, "250g");
        checkBoxQuantityMap.put(R.id.checkBoxBroccoli, "400g");
        checkBoxQuantityMap.put(R.id.checkBoxCauliflower, "1 head");
        checkBoxQuantityMap.put(R.id.checkBoxCucumber, "2 pieces");
        checkBoxQuantityMap.put(R.id.checkBoxZucchini, "2 pieces");
        checkBoxQuantityMap.put(R.id.checkBoxCapsicum, "3 pieces");
        checkBoxQuantityMap.put(R.id.checkBoxCabbage, "1 piece");
        checkBoxQuantityMap.put(R.id.checkBoxEggplant, "3 pieces");
        checkBoxQuantityMap.put(R.id.checkBoxOkra, "500g");
        checkBoxQuantityMap.put(R.id.checkBoxSweetCorn, "2 ears");
        checkBoxQuantityMap.put(R.id.checkBoxGreenPeas, "200g");
        checkBoxQuantityMap.put(R.id.checkBoxPumpkin, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxRadish, "3 pieces");
        checkBoxQuantityMap.put(R.id.checkBoxBeetroot, "500g");
        checkBoxQuantityMap.put(R.id.checkBoxCelery, "1 bunch");
        checkBoxQuantityMap.put(R.id.checkBoxGarlic, "2 bulbs");
        checkBoxQuantityMap.put(R.id.checkBoxGinger, "100g");
        checkBoxQuantityMap.put(R.id.checkBoxMushroom, "500g");
        checkBoxQuantityMap.put(R.id.checkBoxChili, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxSpringOnion, "1 bunch");
        checkBoxQuantityMap.put(R.id.checkBoxRice, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxWheat, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxOats, "500g");
        checkBoxQuantityMap.put(R.id.checkBoxQuinoa, "300g");
        checkBoxQuantityMap.put(R.id.checkBoxMillet, "400g");
        checkBoxQuantityMap.put(R.id.checkBoxBuckwheat, "400g");
        checkBoxQuantityMap.put(R.id.checkBoxAmaranth, "200g");
        checkBoxQuantityMap.put(R.id.checkBoxFarro, "300g");
        checkBoxQuantityMap.put(R.id.checkBoxSpelt, "400g");
        checkBoxQuantityMap.put(R.id.checkBoxTeff, "200g");
        checkBoxQuantityMap.put(R.id.checkBoxCouscous, "500g");
        checkBoxQuantityMap.put(R.id.checkBoxSorghum, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxBarleyFlakes, "300g");
        checkBoxQuantityMap.put(R.id.checkBoxBarley, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxCorn, "2 pieces");
        checkBoxQuantityMap.put(R.id.checkBoxSalt, "100g");
        checkBoxQuantityMap.put(R.id.checkBoxCinnamon, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxClove, "30g");
        checkBoxQuantityMap.put(R.id.checkBoxNutmeg, "20g");
        checkBoxQuantityMap.put(R.id.checkBoxCardamom, "40g");
        checkBoxQuantityMap.put(R.id.checkBoxCoriander, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxBayLeaf, "10 leaves");
        checkBoxQuantityMap.put(R.id.checkBoxStarAnise, "10 pieces");
        checkBoxQuantityMap.put(R.id.checkBoxFenugreek, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxMustardSeeds, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxChiliFlakes, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxPepper, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxCumin, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxTurmeric, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxPaprika, "50g");
        checkBoxQuantityMap.put(R.id.checkBoxChicken, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxBeef, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxPork, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxFish, "1kg");
        checkBoxQuantityMap.put(R.id.checkBoxLamb, "1kg");

        // Iterate through the map and add quantities for selected checkboxes
        for (Map.Entry<Integer, String> entry : checkBoxQuantityMap.entrySet()) {
            CheckBox checkBox = findViewById(entry.getKey());
            if (checkBox != null && checkBox.isChecked()) {
                quantities.add(entry.getValue());
            }
        }

        return quantities;
    }


    public Bitmap makeSmall(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float ratio = (float) width / (float) height;

        if (ratio > 1) {
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void setupPermissions() {
        permissionsResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            boolean allGranted = true;
            for (Boolean isGranted : result.values()) {
                if (!isGranted) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                openPhotoPicker();
            } else {
                showPermissionRationale();
            }
        });
    }

    private void setupPhotoPicker() {
        photoPickerResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();
                if (selectedImageUri != null) {
                    try {
                        selectedImage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                                ? ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), selectedImageUri))
                                : MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        imageViewAddImage.setImageBitmap(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 33) {
            permissionsResultLauncher.launch(new String[]{Manifest.permission.READ_MEDIA_IMAGES});
        } else {
            permissionsResultLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }

    private void openPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerResultLauncher.launch(intent);
    }

    private void showPermissionRationale() {
        Snackbar.make(constraintLayout, "Please grant permission to access photos.", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", v -> requestPermissions())
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
