package to_do_list;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import org.slf4j.Logger;

import io.javalin.Javalin;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(App.class);

        logger.info("This is an information message");

        ArrayList<Todo> todos = getDummies();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/static");
        })
            .get("/", ctx -> ctx.redirect("index.html"))
            .start(8080);
        
        app.get("/todos", ctx -> {
            logger.info("Entering GET: /todos");
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < todos.size(); i++) {
                String style = "";
                if(!todos.get(i).getDone()){
                    style += "style='color:red'>";
                }
                else{
                    style += "style='color:green'>";
                }
                res.append("<li").append(" hx-post='/mark/" + (i+1) + "' hx-trigger='click' hx-target='#lists' ").append(style).append(todos.get(i).getTask()).append("</li>");
            }
            ctx.result(res.toString());
        });

        app.post("/add", ctx -> {
            logger.info("Entering POST: /add");
            todos.add(new Todo(ctx.formParam("task")));
            ctx.redirect("/todos");
        });

        app.post("/mark/{id}", ctx ->{
            logger.info("Entering POST: /mark");
            int taskId = Integer.parseInt(ctx.pathParam("id")) - 1;
            todos.get(taskId).setDone(true);
            ctx.redirect("/todos");
        });

        app.post("/delete", ctx -> {
            logger.info("Entering POST: /delete");
            int idx = Integer.parseInt(ctx.formParam("num"));
            todos.remove(idx-1);
            ctx.redirect("/todos");
        });
    }

    public static ArrayList<Todo> getDummies(){
        ArrayList<Todo> arrList = new ArrayList<>();
        arrList.add(new Todo("Wash dishes"));
        arrList.add(new Todo("Conquer the world"));
        return arrList;
    }
}

