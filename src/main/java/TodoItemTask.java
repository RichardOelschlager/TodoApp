import java.util.Objects;

public class TodoItemTask {
    private int id;
    private TodoItem todoItem;
    private Person assignee;

    public TodoItemTask(int id, TodoItem todoItem, Person assignee) {
        if (todoItem == null) {
            throw new IllegalArgumentException("TodoItem cannot be null.");
        }
        this.id = id;
        this.todoItem = todoItem;
        this.assignee = assignee;
    }

    public int getId() {
        return id;
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoItem todoItem) {
        if (todoItem == null) {
            throw new IllegalArgumentException("TodoItem cannot be null.");
        }
        this.todoItem = todoItem;
    }

    public Person getAssignee() {
        return assignee;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
    }

    public boolean isAssigned() {
        return this.assignee != null;
    }

    public String getSummary() {
        return String.format("{id: %d, assigned: %b, todoItem: %s, assignee: %s}",
                id, isAssigned(), todoItem.getSummary(),
                assignee != null ? assignee.getSummary() : "null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItemTask that = (TodoItemTask) o;
        return id == that.id &&
                Objects.equals(todoItem, that.todoItem) &&
                Objects.equals(assignee, that.assignee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, todoItem, assignee);
    }

    @Override
    public String toString() {
        return "TodoItemTask{" +
                "id=" + id +
                ", todoItem=" + todoItem +
                ", assignee=" + assignee +
                "}";
    }
}
