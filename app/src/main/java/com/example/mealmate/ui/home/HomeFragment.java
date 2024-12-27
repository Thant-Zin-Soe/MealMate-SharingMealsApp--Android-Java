package com.example.mealmate.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealmate.R;
import com.example.mealmate.databinding.FragmentHomeBinding;
import com.example.mealmate.ui.recipes.AppetizersRecipeList;
import com.example.mealmate.ui.recipes.BeefRecipeList;
import com.example.mealmate.ui.recipes.ChickenRecipeList;
import com.example.mealmate.ui.recipes.FishRecipeList;
import com.example.mealmate.ui.recipes.NoodleRecipeList;
import com.example.mealmate.ui.recipes.PorkRecipeList;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private Button shareNewRecipeButton;

    private FragmentHomeBinding binding;
    private ViewPager2 imageSlider;
    private WormDotsIndicator dotsIndicator;


    private Handler sliderHandler; // For auto-slide functionality


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Use View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        // Initialize Views
        imageSlider = binding.imageSlider;
        dotsIndicator = binding.dotsIndicator;
        shareNewRecipeButton = binding.shareNewRecipeButton;// binding button to the fragment


        // Set up Image Slider
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.chicken); // Replace with your drawable images
        imageList.add(R.drawable.pork);
        imageList.add(R.drawable.fish);
        imageList.add(R.drawable.appetizer);


        ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(requireContext(), imageList);
        imageSlider.setAdapter(sliderAdapter);

        // Attach Dots Indicator
        dotsIndicator.setViewPager2(imageSlider);

        // Set up auto-slide handler
        sliderHandler = new Handler(Looper.getMainLooper());

        //actionListener for tthe shareNewRecipeButton
        shareNewRecipeButton.setOnClickListener(v -> {
            handleShareNewRecipe();
        });


        setupAutoSlide(imageList.size());
        setupRecipeActionListeners(root);

        return root;
    }





    private void setupAutoSlide(int itemCount) {
        sliderHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = imageSlider.getCurrentItem();
                int nextItem = (currentItem + 1) % itemCount; // Loop back to the first item
                imageSlider.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 2000); // Repeat every 4 seconds
            }
        }, 4000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove the handler callbacks to prevent memory leaks
        if (sliderHandler != null) {
            sliderHandler.removeCallbacksAndMessages(null);
        }
        binding = null;
    }

    // this is actionListeners of images in home-fragment.class
    private void setupRecipeActionListeners(View root) {
        // Find LinearLayouts for each recipe
        LinearLayout chickenLayout = root.findViewById(R.id.chickenLayout);
        LinearLayout porkLayout = root.findViewById(R.id.porkLayout);
        LinearLayout beefLayout = root.findViewById(R.id.beefLayout);
        LinearLayout fishLayout = root.findViewById(R.id.fishLayout);
        LinearLayout noodleLayout = root.findViewById(R.id.noodleLayout);
        LinearLayout appetizersLayout = root.findViewById(R.id.appetizersLayout);

        // Attach click listeners to each layout
        chickenLayout.setOnClickListener(v -> handleRecipeClick("Chicken"));
        porkLayout.setOnClickListener(v -> handleRecipeClick("Pork"));
        beefLayout.setOnClickListener(v -> handleRecipeClick("Beef"));
        fishLayout.setOnClickListener(v -> handleRecipeClick("Fish"));
        noodleLayout.setOnClickListener(v -> handleRecipeClick("Noodle"));
        appetizersLayout.setOnClickListener(v -> handleRecipeClick("Appetizers"));
    }
    private void handleRecipeClick(String recipeName) {
        // Example: Display a toast when a recipe is clicked

        // Add navigation or other actions here if needed

        if(recipeName=="Chicken"){
            Intent intent = new Intent(getActivity(), ChickenRecipeList.class);
            startActivity(intent);
            //Toast.makeText(getContext(), "Recipe clicked: " + recipeName, Toast.LENGTH_SHORT).show();

        } else if (recipeName=="Pork") {
            Intent intent = new Intent(getActivity(), PorkRecipeList.class);
            startActivity(intent);

        }

        else if (recipeName=="Beef") {
            Intent intent = new Intent(getActivity(), BeefRecipeList.class);
            startActivity(intent);

        }
        else if (recipeName=="Fish") {
            Intent intent = new Intent(getActivity(), FishRecipeList.class);
            startActivity(intent);

        }
        else if (recipeName=="Noodle") {
            Intent intent = new Intent(getActivity(), NoodleRecipeList.class);
            startActivity(intent);

        }
        else if (recipeName=="Appetizers") {
            Intent intent = new Intent(getActivity(), AppetizersRecipeList.class);
            startActivity(intent);

        }
    }

    private void handleShareNewRecipe() {
        // Start the new activity
        Intent intent = new Intent(getActivity(), ShareNewRecipeActivity.class);
        startActivity(intent);
    }

}
