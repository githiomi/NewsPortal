
import com.google.gson.Gson;
import models.dao.Sql2oDepNews;
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
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:4567/newsportal";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        newsDao = new Sql2oNews(sql2o);
        depNewsDao = new Sql2oDepNews(sql2o);
        usersDao = new Sql2oUsers(sql2o);
        conn = sql2o.open();

        get("/", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

    }

}
