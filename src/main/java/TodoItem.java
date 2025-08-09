import java.time.LocalDate;
import java.util.Objects;

public class TodoItem {
    private int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private Person creator;

    public TodoItem(int id, String title, String description, LocalDate deadLine, Person creator) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null.");
        }
        if (creator == null) {
            throw new IllegalArgumentException("Creator cannot be null.");
        }
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = false; // Default to false
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null.");
        }
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Creator cannot be null.");
        }
        this.creator = creator;
    }

    public String getSummary() {
        return String.format("{id: %d, title: %s, description: %s, deadLine: %s, done: %b, creator: %s}",
                id, title, description, deadLine, done, creator.getSummary());
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(deadLine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return id == todoItem.id &&
                done == todoItem.done &&
                title.equals(todoItem.title) &&
                Objects.equals(description, todoItem.description) &&
                deadLine.equals(todoItem.deadLine) &&
                creator.equals(todoItem.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadLine, done, creator);
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title=\'" + title + "\'" +
                ", description=\'" + description + "\'" +
                ", deadLine=" + deadLine +
                ", done=" + done +
                ", creator=" + creator +
                "}";
    }
}
