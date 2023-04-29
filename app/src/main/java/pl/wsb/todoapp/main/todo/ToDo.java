package pl.wsb.todoapp.main.todo;

import java.time.LocalDateTime;

public class ToDo {
    private String title;
    private LocalDateTime createdAt;

    public ToDo(String title, LocalDateTime createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

