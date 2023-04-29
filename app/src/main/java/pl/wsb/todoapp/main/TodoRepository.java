package pl.wsb.todoapp.main;

import android.content.Context;

import java.util.List;

import pl.wsb.todoapp.main.todo.ToDo;

public class TodoRepository {
    private static final String FILENAME = "storage.json";

    public static void save(Context context, List<ToDo> todos) {

    }

    public List<ToDo> read(Context context) {
        return List.of();
    }
}
