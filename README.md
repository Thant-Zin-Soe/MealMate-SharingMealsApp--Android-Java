
# **MealMate App**

MealMate is a mobile application designed to simplify meal planning and grocery shopping. It helps users manage their recipes, create weekly meal plans, and generate organized shopping lists. The app also features secure user account management powered by Firebase.

---

## **Features**

### **1. User Authentication (Powered by Firebase)**
- Secure user registration and login using Firebase Authentication.
- Store user account information securely.
- Supports email/password authentication.

### **2. Recipe Management**
- Add, edit, or delete recipes.
- Categorize recipes by types: Meat, Vegetables, Grains, Spices, etc.
- Store recipe details including ingredients, instructions, and quantities.

### **3. Weekly Meal Plan**
- Plan your meals for the week by selecting recipes.
- Automatically generate a shopping list from selected recipes.

### **4. Shopping List**
- View ingredients grouped by category.
- Check off purchased items directly in the app.
- Save progress with a "Save" button that updates the database.
- Share your shopping list via SMS for collaboration.

### **5. Map Integration**
- Locate nearby stores for quick shopping.

---

## **Tech Stack**

- **Language:** Java
- **Database:** SQLite (using Room Persistence Library)
- **Backend:** Firebase Authentication
- **UI Components:** RecyclerView, Material Design Toolbar
- **Architecture:** MVVM (Model-View-ViewModel)
- **IDE:** Android Studio
- **Frameworks and Libraries:**
  - Room Database
  - Android Jetpack Components (LiveData, ViewModel)
  - Firebase Authentication
  - RecyclerView for dynamic lists

---

## **Setup and Installation**

### **Prerequisites**
- Android Studio installed on your system.
- Firebase project set up (see Firebase setup instructions below).
- Minimum SDK version: 21 (Lollipop).
- Recommended device: Android 6.0 or above.

### **Steps to Run the App**

 Open the project in Android Studio.
 Sync Gradle files.
 Set up Firebase for the project:
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new Firebase project.
   - Add your Android app to the project and download the `google-services.json` file.
   - Place the `google-services.json` file in the `/app` directory.
   - Enable Firebase Authentication and configure the sign-in methods (e.g., email/password).
 Build and run the app on an emulator or a physical device.

---

## **Database Schema**

### **1. Firebase (User Account Data)**
- User authentication data is stored securely in Firebase.
- Additional user profile data can be added (optional).

### **2. SQLite Tables (Local Data)**
#### Ingredients Table
| Column         | Type    | Description                          |
|----------------|---------|--------------------------------------|
| `ingredientId` | INTEGER | Unique ID for each ingredient.      |
| `name`         | TEXT    | Name of the ingredient.             |
| `typeOfIngredient` | TEXT | Category (Meat, Vegetable, etc.).   |
| `price`        | REAL    | Price of the ingredient.            |

#### Recipes Table
| Column      | Type    | Description                   |
|-------------|---------|-------------------------------|
| `recipeId`  | INTEGER | Unique ID for each recipe.    |
| `name`      | TEXT    | Name of the recipe.           |
| `type`      | TEXT    | Recipe category (e.g., Main). |
| `photo`     | BLOB    | Recipe image.                |
| `instruction` | TEXT   | Recipe preparation steps.    |

#### Recipe Ingredients Table
| Column        | Type    | Description                          |
|---------------|---------|--------------------------------------|
| `id`          | INTEGER | Unique ID for each entry.           |
| `recipeId`    | INTEGER | ID of the associated recipe.         |
| `ingredientId`| INTEGER | ID of the associated ingredient.     |
| `quantity`    | TEXT    | Required quantity.                  |
| `isPurchased` | INTEGER | Indicates if the item is purchased. |

#### Weekly Meal Plan Table
| Column     | Type    | Description                      |
|------------|---------|----------------------------------|
| `planId`   | INTEGER | Unique ID for the meal plan.     |
| `recipeId` | INTEGER | Associated recipe ID.            |
| `isPurchased` | INTEGER | Indicates if the recipe is completed. |

---

## **App Screens**

### **1. User Authentication**
- Login and registration screens powered by Firebase.

### **2. Home Screen**
- Displays all recipes categorized by type.

### **3. Weekly Meal Plan**
- Allows users to create and manage their weekly meal plans.

### **4. Shopping List**
- Organized shopping list with checkboxes for purchased items.

---

## **Usage**

1. Register or log in using Firebase Authentication.
2. Add or edit recipes with their ingredients and instructions.
3. Plan weekly meals by selecting recipes.
4. View an auto-generated shopping list grouped by ingredient type.
5. Check off purchased items in the shopping list and save the progress.
6. Use the map to locate nearby stores for your shopping needs.
7. Share the shopping list via SMS.

---

## **Future Enhancements**
- Extend user profiles with Firebase Firestore for additional data storage.
- Add push notifications for meal reminders and shopping tasks.
- Integrate image upload functionality for user recipes.

---

## **Contributors**
- **[ThantZinSoe]** - Developer

---


