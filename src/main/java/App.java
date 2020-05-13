
import com.google.gson.Gson;
import models.Departments;
import models.dao.Sql2oDepNews;
import models.dao.Sql2oDepartments;
import models.dao.Sql2oNews;
import models.dao.Sql2oUsers;
import org.sql2o.*;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        staticFileLocation("/public");
        Sql2oNews newsDao;
        Sql2oDepNews depNewsDao;
        Sql2oUsers usersDao;
        Sql2oDepartments sql2oDepartments;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/newsportal";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        newsDao = new Sql2oNews(sql2o);
        depNewsDao = new Sql2oDepNews(sql2o);
        usersDao = new Sql2oUsers(sql2o);
        sql2oDepartments = new Sql2oDepartments(sql2o);
        conn = sql2o.open();

        post("/home", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String username = req.queryParams("username");
                req.session().attribute("username", username);
            int password = Integer.parseInt(req.queryParams("password"));

            if (password != 123456) {
                res.redirect("/");
                model.put("username", req.session().attribute("username"));
            }

            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "home.hbs");

        }, new HandlebarsTemplateEngine());

        get("/", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/departments", "application/json", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();

            res.type("application/json");
            return gson.toJson(sql2oDepartments.getAll());

//            model.put("username", req.session().attribute("username"));
//            model.put("departments", gson.toJson(sql2oDepartments.getAll()));
//            return new ModelAndView(model, "departments.hbs");
        });

    }

}
