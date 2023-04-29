package pl.wsb.todoapp.activities;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import pl.wsb.todoapp.R;
import pl.wsb.todoapp.main.AppState;
import pl.wsb.todoapp.main.ReminderBroadcast;
import pl.wsb.todoapp.main.todo.CreateToDoData;
import pl.wsb.todoapp.main.todo.ToDo;

public class MainActivity extends AppCompatActivity implements AddTodoDialog.AddTodoDialogListener {

    private static final AppState APP_STATE = AppState.getInstance();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        initData();

        ListView todosListView = findViewById(R.id.todo_list);
        FloatingActionButton openDialogButton = findViewById(R.id.add_todo_dialogue_button);
        openDialogButton.setOnClickListener(this::openDialog);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, APP_STATE.getTodosTitles());
        todosListView.setAdapter(adapter);

        todosListView.setOnItemLongClickListener(this::removeTodo);
    }

    private boolean removeTodo(AdapterView<?> adapterView, View view, int i, long l) {
        APP_STATE.removeTodo(l);
        reloadData();
        saveData();
        return true;
    }

    private void openDialog(View view) {
        AddTodoDialog addTodoDialog = new AddTodoDialog();
        addTodoDialog.show(getSupportFragmentManager(), "add todo dialog");
    }

    @Override
    public void addTodo(CreateToDoData createToDoData) {
        APP_STATE.addTodo(new ToDo(createToDoData.getText(), LocalDateTime.now()));
        if (createToDoData.isSendNotification()) {
            Intent intent = new Intent(this, ReminderBroadcast.class);
            intent.putExtra("TODO_TEXT", createToDoData.getText());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, FLAG_MUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long triggerAtMillis = createToDoData.getNotificationDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
        reloadData();
        saveData();
    }

    private void initData() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Set<String> todos = sharedPreferences.getStringSet("TODOS", Set.of());
        APP_STATE.readTodos(todos);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("TODOS", new HashSet<>(APP_STATE.getTodosTitles()));
        editor.apply();
    }

    private void reloadData() {
        adapter.clear();
        adapter.addAll(APP_STATE.getTodosTitles());
        adapter.notifyDataSetChanged();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("TODO_APP_NOTIFICATION_CHANNEL_ID", "TODO_APP_NOTIFICATION_NAME", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}