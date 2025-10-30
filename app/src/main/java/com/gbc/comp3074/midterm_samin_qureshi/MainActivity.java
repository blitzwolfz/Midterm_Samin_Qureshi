package com.gbc.comp3074.midterm_samin_qureshi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private Button buttonGenerate;
    private ListView listViewTable;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> tableItems;
    private int currentNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextNumber = findViewById(R.id.editTextNumber);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        listViewTable = findViewById(R.id.listViewTable);

        // Initialize data list
        tableItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tableItems);
        listViewTable.setAdapter(adapter);

        // Set up button click listener
        buttonGenerate.setOnClickListener(v -> generateTable());

        // Set up ListView item click listener
        listViewTable.setOnItemClickListener((parent, view, position, id) -> 
            showDeleteConfirmationDialog(position)
        );
    }

    private void generateTable() {
        String input = editTextNumber.getText().toString().trim();
        
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            currentNumber = Integer.parseInt(input);
            
            // Clear previous table
            tableItems.clear();
            
            // Generate multiplication table (1 to 10)
            for (int i = 1; i <= 10; i++) {
                int result = currentNumber * i;
                String tableRow = currentNumber + " × " + i + " = " + result;
                tableItems.add(tableRow);
            }
            
            // Update ListView
            adapter.notifyDataSetChanged();
            
            // Add to history
            DataManager.addToHistory(currentNumber);
            
            Toast.makeText(this, "Table generated for " + currentNumber, Toast.LENGTH_SHORT).show();
            
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog(int position) {
        String item = tableItems.get(position);
        
        new MaterialAlertDialogBuilder(this)
            .setTitle("Delete Row")
            .setMessage("Do you want to delete this row?\n" + item)
            .setPositiveButton("Delete", (dialog, which) -> {
                tableItems.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Deleted: " + item, Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showClearAllDialog() {
        if (tableItems.isEmpty()) {
            Toast.makeText(this, "No items to clear", Toast.LENGTH_SHORT).show();
            return;
        }

        new MaterialAlertDialogBuilder(this)
            .setTitle("Clear All")
            .setMessage("Are you sure you want to delete all rows?")
            .setPositiveButton("Clear All", (dialog, which) -> {
                tableItems.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "All rows cleared", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_clear_all) {
            showClearAllDialog();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
