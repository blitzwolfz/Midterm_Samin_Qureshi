package com.gbc.comp3074.midterm_samin_qureshi;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistory;
    private ArrayAdapter<Integer> adapter;
    private ArrayList<Integer> historyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        listViewHistory = findViewById(R.id.listViewHistory);

        // Get history data from DataManager
        historyItems = DataManager.getHistory();
        
        // Set up adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
        listViewHistory.setAdapter(adapter);

        // Show message if history is empty
        if (historyItems.isEmpty()) {
            Toast.makeText(this, "No history available", Toast.LENGTH_SHORT).show();
        }

        // Optional: Add click listener to show table again
        listViewHistory.setOnItemClickListener((parent, view, position, id) -> {
            int number = historyItems.get(position);
            Toast.makeText(HistoryActivity.this, 
                "Table was generated for: " + number, 
                Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        adapter.notifyDataSetChanged();
    }
}
