package pl.wsb.todoapp.main.todo;

import java.time.LocalDateTime;

public class CreateToDoData {
    private String text;
    private boolean sendNotification;
    private LocalDateTime notificationDateTime;

    public CreateToDoData(String text, boolean sendNotification, int year, int month, int day, int hour, int minute) {
        this.text = text;
        this.sendNotification = sendNotification;
        if (sendNotification) {
            this.notificationDateTime = LocalDateTime.of(year, month, day, hour, minute);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(boolean sendNotification) {
        this.sendNotification = sendNotification;
    }

    public LocalDateTime getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(LocalDateTime notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }

    @Override
    public String toString() {
        return "CreateToDoData{" +
                "text='" + text + '\'' +
                ", sendNotification=" + sendNotification +
                ", notificationDateTime=" + notificationDateTime +
                '}';
    }
}
