//package com.example.mealmate;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GroupedIngredientAdapter extends RecyclerView.Adapter<GroupedIngredientAdapter.ViewHolder> {
//    private List<IngredientGroupedByType> groupedIngredientList = new ArrayList<>();
//
//    // Method to set the data and refresh the RecyclerView
//    public void setGroupedIngredientList(List<IngredientGroupedByType> ingredients) {
//        this.groupedIngredientList = ingredients;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        IngredientGroupedByType ingredient = groupedIngredientList.get(position);
//
//        // Set the ingredient type
//        holder.type.setText(ingredient.ingredient_type);
//        holder.name.setText(ingredient.ingredient_name);
//        holder.quantity.setText(String.valueOf(ingredient.total_quantity));
//
//        // Optional: Hide the type if the same type repeats consecutively
//        if (position > 0 && groupedIngredientList.get(position - 1).ingredient_type.equals(ingredient.ingredient_type)) {
//            holder.type.setVisibility(View.GONE);
//        } else {
//            holder.type.setVisibility(View.VISIBLE);
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return groupedIngredientList.size();
//    }
//
//    // ViewHolder Class
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView type, name, quantity;
//        CheckBox purchased;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            type = itemView.findViewById(R.id.tv_ingredient_type); // New addition
//            name = itemView.findViewById(R.id.tv_ingredient_name);
//            quantity = itemView.findViewById(R.id.tv_ingredient_quantity);
//            purchased = itemView.findViewById(R.id.checkbox_purchased);
//        }
//    }
//
//}
package com.example.mealmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroupedIngredientAdapter extends RecyclerView.Adapter<GroupedIngredientAdapter.ViewHolder> {

    private List<IngredientGroupedByType> groupedIngredientList = new ArrayList<>();
    private OnIngredientCheckedListener onIngredientCheckedListener;

    // Constructor to accept listener
    public GroupedIngredientAdapter(OnIngredientCheckedListener listener) {
        this.onIngredientCheckedListener = listener;
    }

    // Method to set the data and refresh the RecyclerView
    public void setGroupedIngredientList(List<IngredientGroupedByType> ingredients) {
        this.groupedIngredientList = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientGroupedByType ingredient = groupedIngredientList.get(position);

        // Set ingredient details
        holder.type.setText(ingredient.getIngredientType()); // Use getIngredientType()
        holder.name.setText(ingredient.getIngredientName()); // Use getIngredientName()
        holder.quantity.setText(String.valueOf(ingredient.getTotalQuantity())); // Use getTotalQuantity()

        // Set the checkbox state
        holder.purchased.setOnCheckedChangeListener(null); // Prevent unintended behavior during recycling
        holder.purchased.setChecked(ingredient.isPurchased()); // Use isPurchased()

        // Optional: Hide the type if the same type repeats consecutively
        if (position > 0 && groupedIngredientList.get(position - 1).getIngredientType().equals(ingredient.getIngredientType())) {
            holder.type.setVisibility(View.GONE);
        } else {
            holder.type.setVisibility(View.VISIBLE);
        }

        // Set listener for the checkbox
        holder.purchased.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onIngredientCheckedListener != null) {
                onIngredientCheckedListener.onIngredientChecked(ingredient.getIngredientId(), isChecked); // Use getIngredientId()
            }
        });
    }


    @Override
    public int getItemCount() {
        return groupedIngredientList.size();
    }

    // ViewHolder Class
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type, name, quantity;
        CheckBox purchased;

        ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tv_ingredient_type);
            name = itemView.findViewById(R.id.tv_ingredient_name);
            quantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            purchased = itemView.findViewById(R.id.checkbox_purchased);
        }
    }

    // Define the interface for CheckBox events
    public interface OnIngredientCheckedListener {
        void onIngredientChecked(int ingredientId, boolean isChecked);
    }
    public List<IngredientGroupedByType> getGroupedIngredientList() {
        return groupedIngredientList;
    }

}
