package to_do_list;

public class Todo {
    String task;
    boolean done;

    public Todo(String task) {
        this.task = task;
        this.done = false;
    }

    public String getTask(){
        return this.task;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean getDone(){
        return this.done;
    }
}
