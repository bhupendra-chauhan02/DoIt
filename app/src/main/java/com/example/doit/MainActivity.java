 package com.example.doit;

 import android.content.DialogInterface;
 import android.os.Bundle;
 import android.view.View;

 import androidx.appcompat.app.ActionBar;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.ItemTouchHelper;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.example.doit.Adapter.ToDoAdaper;
 import com.example.doit.Model.ToDoModel;
 import com.example.doit.utils.DatabaseHandler;
 import com.google.android.material.floatingactionbutton.FloatingActionButton;

 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;

 public class MainActivity extends AppCompatActivity implements DialogClosedListner{
    private RecyclerView taskRecyclerView;
    private FloatingActionButton fab;

     private ToDoAdaper taskAdapter;
     private List<ToDoModel> taskList;
     private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        // Hide the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        db = new DatabaseHandler(this);
        db.openDatabase();
        taskList = new ArrayList<>();
        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdaper(db,this);
        taskRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.fab);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);


    }
    @Override
     public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}