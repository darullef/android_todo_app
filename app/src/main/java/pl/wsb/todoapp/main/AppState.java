package pl.wsb.todoapp.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pl.wsb.todoapp.main.todo.ToDo;

public class AppState {

    private static AppState instance = null;

    private List<ToDo> todos = new ArrayList<>(List.of(
            new ToDo("First ToDo", LocalDateTime.now()),
            new ToDo("Second ToDo", LocalDateTime.now()),
            new ToDo("Third ToDo", LocalDateTime.now())
    ));

    private AppState() {
        if (instance != null) {
            throw new RuntimeException("Not allowed. Please use getInstance() method");
        }
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void addTodo(ToDo toDo) {
        todos.add(toDo);
    }

    public List<String> getTodosTitles() {
        return todos.stream()
                .sorted(Comparator.comparing(ToDo::getCreatedAt))
                .map(ToDo::getTitle)
                .collect(Collectors.toList());
    }

    public void removeTodo(long index) {
        todos.remove(Math.toIntExact(index));
    }

    public void readTodos(Set<String> data) {
        todos.clear();
        for (String d : data) {
            todos.add(new ToDo(d, LocalDateTime.now()));
        }
    }
}