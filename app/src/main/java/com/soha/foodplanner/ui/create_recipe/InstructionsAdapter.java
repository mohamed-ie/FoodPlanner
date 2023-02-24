package com.soha.foodplanner.ui.create_recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.soha.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.ViewHolder> {
    private final List<String> instructions = new ArrayList<>(List.of(""));

    public InstructionsAdapter() {

    }

    public List<String> getInstructions() {
        return instructions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_instruction_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewDelete.setOnClickListener(v -> deleteInstruction(position));
        holder.textViewStep.setText(holder.itemView.getContext().getString(R.string.step, position + 1));

        holder.textInputLayoutInstruction.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            holder.textInputLayoutInstruction.setCounterEnabled(hasFocus || holder.textInputLayoutInstruction.getEditText().getText().length() > 0);
        });

    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    private void deleteInstruction(int position) {
        instructions.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, instructions.size()-position);
    }

    public void newInstruction() {
        instructions.add("");
        notifyItemInserted(instructions.size());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewStep;
        private final TextView textViewDelete;
        private final TextInputLayout textInputLayoutInstruction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStep = itemView.findViewById(R.id.textViewStep);
            textViewDelete = itemView.findViewById(R.id.textViewDelete);
            textInputLayoutInstruction = itemView.findViewById(R.id.textInputLayoutInstruction);
        }
    }
}
