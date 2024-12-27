package com.example.mealmate.ui.home;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealmate.ui.weeklyMealPlan.WeeklyMealPlan;
import com.example.mealmate.ui.weeklyMealPlan.WeeklyMealPlanDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MealRecipe.class, Ingredient.class, RecipeIngredient.class, WeeklyMealPlan.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Singleton instance of AppDatabase
    private static volatile AppDatabase instance;

    // Abstract methods to retrieve DAOs
    public abstract MealRecipeDao mealRecipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract RecipeIngredientDao recipeIngredientDao();
    public abstract WeeklyMealPlanDao weeklyMealPlanDao();

    // Executor service for background tasks
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    /**
     * Retrieves the singleton instance of AppDatabase.
     * Uses double-checked locking to ensure thread safety.
     *
     * @param context Application context
     * @return Singleton instance of AppDatabase
     */
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "meal_mate_final1")
                            .fallbackToDestructiveMigration() // Handle migrations gracefully
                            .build();
                }
            }
        }
        return instance;
    }
}


// DAO for WeeklyMealPlan


// Singleton to prevent multiple instances
//    public static synchronized AppDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, "meal_mate_final1")
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//        return instance;
//    }