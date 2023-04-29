package pl.wsb.todoapp.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;

import pl.wsb.todoapp.R;
import pl.wsb.todoapp.main.todo.CreateToDoData;

public class AddTodoDialog extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText todoEditText;
    private AddTodoDialogListener listener;
    private CheckBox sendNotificationCheckbox;
    private Button notificationDatePickerButton;

    private int savedYear;
    private int savedMonth;
    private int savedDay;
    private int savedHour;
    private int savedMinute;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_todo_dialog, null);

        builder.setView(view)
                .setTitle("Add new ToDo")
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    String todoValue = todoEditText.getText().toString();
                    if (todoValue.length() == 0) {
                        Toast.makeText(getContext(), "Please enter text", Toast.LENGTH_SHORT).show();
                    } else {
                        CreateToDoData data = new CreateToDoData(todoValue, sendNotificationCheckbox.isChecked(), savedYear, savedMonth, savedDay, savedHour, savedMinute);
                        listener.addTodo(data);
                    }
                });

        todoEditText = view.findViewById(R.id.add_todo_edit_text);

        sendNotificationCheckbox = view.findViewById(R.id.send_notification_checkbox);
        notificationDatePickerButton = view.findViewById(R.id.notification_date_button);

        sendNotificationCheckbox.setOnCheckedChangeListener(((checkbox, isChecked) -> notificationDatePickerButton.setEnabled(isChecked)));
        notificationDatePickerButton.setOnClickListener(this::openDatePicker);

        return builder.create();
    }

    private void openDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        datePickerDialog.show();
    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddTodoDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement AddTodoDialogListener");
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        savedYear = year;
        savedMonth = month;
        savedDay = day;

        openTimePicker();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        savedHour = hour;
        savedMinute = minute;
    }

    public interface AddTodoDialogListener {
        void addTodo(CreateToDoData createToDoData);
    }
}
