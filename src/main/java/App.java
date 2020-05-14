
import com.google.gson.Gson;
import models.DepNews;
import models.Departments;
import models.News;
import models.Users;
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

            List<Departments> allDepartments = sql2oDepartments.getAll();
            List<News> allNews = newsDao.getAllNewsForCompany();

            model.put("news", allNews);
            model.put("departments", allDepartments);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "home.hbs");

        }, new HandlebarsTemplateEngine());

        get("home", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List<Departments> allDepartments = sql2oDepartments.getAll();
            List<News> allNews = newsDao.getAllNewsForCompany();

            model.put("news", allNews);
            model.put("username", req.session().attribute("username"));
            model.put("departments", allDepartments);
            return new ModelAndView(model, "home.hbs");
        }, new HandlebarsTemplateEngine());

        get("/", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("departments", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List<Departments> allDepartments = sql2oDepartments.getAll();

            model.put("username", req.session().attribute("username"));
            model.put("departments", allDepartments);
            return new ModelAndView(model, "departments.hbs");
        }, new HandlebarsTemplateEngine());

        get("departments/:id", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));

            Departments departments = sql2oDepartments.findById(id);
            int users = usersDao.getAllInDepartment(id).size();
            List<Users> allUsers = usersDao.getAllInDepartment(id);

            model.put("departments", departments);
            model.put("users", allUsers);
            model.put("employees", users);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "departmentdetails.hbs");
        }, new HandlebarsTemplateEngine());

        post("/departments/:id", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));

            String name = req.queryParams("name");
            String gender = req.queryParams("gender");
            String position = req.queryParams("position");
            String role = req.queryParams("role");
            int depId = Integer.parseInt(req.queryParams("departmentid"));

            Users newUser = new Users(name, gender, position, role, depId);
            usersDao.add(newUser);

            Departments departments = sql2oDepartments.findById(id);
            int users = usersDao.getAllInDepartment(id).size();
            List<Users> allUsers = usersDao.getAllInDepartment(id);

            model.put("departments", departments);
            model.put("users", allUsers);
            model.put("employees", users);
            model.put("username", req.session().attribute("username"));
            res.redirect("/departments/" + id);
            return null;
        }, new HandlebarsTemplateEngine());


        get("companynews/:id", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));

            News retrieved = newsDao.findById(id);
            Users users = usersDao.findById(retrieved.getUserid());

            model.put("news", retrieved);
            model.put("users", users);
            model.put("username", req.session().attribute("username"));
            return  new ModelAndView(model, "news.hbs");
        }, new HandlebarsTemplateEngine());

        post("companynews/:id/delete", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));

            newsDao.deleteById(id);

            List<Departments> allDepartments = sql2oDepartments.getAll();
            List<News> allNews = newsDao.getAllNewsForCompany();

            model.put("news", allNews);
            model.put("departments", allDepartments);
            res.redirect("/home");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/companynews", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<News> allNews = newsDao.getAllNewsForCompany();

            model.put("news", allNews);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "companynews.hbs");
        }, new HandlebarsTemplateEngine());

        post("/companynews", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            String title = req.queryParams("title");
            String urgency = req.queryParams("urgency");
            String content = req.queryParams("content");
            int userid = Integer.parseInt(req.queryParams("userid"));

            News news = new News(title, urgency, content, userid);
            newsDao.add(news);

            List<News> allNews = newsDao.getAllNewsForCompany();

            model.put("news", allNews);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "companynews.hbs");
        }, new HandlebarsTemplateEngine());

//        get("/companynews/new", "application/json", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//
//            List<Users> allUsers = usersDao.getAll();
//
//            model.put("users", allUsers);
//            model.put("username", req.session().attribute("username"));
//            return new ModelAndView(model, "newcompanynews.hbs");
//        }, new HandlebarsTemplateEngine());

        get("/departments/:ic/users/:id", "application/json", (req, res) -> {
            Map<String,Object> model = new HashMap<>();
            int depId = Integer.parseInt(req.params("ic"));
            int userid = Integer.parseInt(req.params("id"));

            Departments departments = sql2oDepartments.findById(depId);
            Users users = usersDao.findById(userid);
            String gender = users.getGender();

            if(gender.equals("Male")){
                model.put("male", gender);
            }

            model.put("username", req.session().attribute("username"));
            model.put("user", users);
            model.put("department", departments);
            return new ModelAndView(model, "employee.hbs");
        }, new HandlebarsTemplateEngine());

        post("/departments/:ic/users/:id/delete", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int userId = Integer.parseInt(req.params("id"));
            int depId = Integer.parseInt(req.params(":ic"));

            usersDao.deleteById(userId);
            res.redirect("/departments/" + depId);
            return null;
        }, new HandlebarsTemplateEngine());

        get("/departments/:id/users", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int depId = Integer.parseInt(req.params("id"));

            Departments retrieved = sql2oDepartments.findById(depId);

            model.put("departments", retrieved);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "newemployee.hbs");
        }, new HandlebarsTemplateEngine());

        post("/departments/:id/news", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int depId = Integer.parseInt(req.params(":id"));

            String title = req.queryParams("title");
            String urgency = req.queryParams("urgency");
            String content = req.queryParams("content");
            int departmentid = Integer.parseInt(req.queryParams("departmentid"));
            int userid = Integer.parseInt(req.queryParams("userid"));

            DepNews depNews = new DepNews(title, urgency, content, departmentid, userid);
            depNewsDao.addDep(depNews);

            Departments departments = sql2oDepartments.findById(depId);
            List<DepNews> allDepNews = depNewsDao.getAllNewsForDepartment(depId);

            model.put("depNews", allDepNews);
            model.put("departments", departments);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "depNews.hbs");
        }, new HandlebarsTemplateEngine());

        get("/departments/:id/news", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int depId = Integer.parseInt(req.params(":id"));

            Departments departments = sql2oDepartments.findById(depId);
            List<DepNews> allDepNews = depNewsDao.getAllNewsForDepartment(depId);

            model.put("depNews", allDepNews);
            model.put("departments", departments);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "depNews.hbs");
        }, new HandlebarsTemplateEngine());

        get("/departments/:id/news/new", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int depId = Integer.parseInt(req.params(":id"));

            Departments departments = sql2oDepartments.findById(depId);
            List<Users> users = usersDao.getAllInDepartment(depId);

            model.put("users", users);
            model.put("departments", departments);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "newDepNews.hbs");
        }, new HandlebarsTemplateEngine());

        get("/departments/:ic/news/:id", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int depId = Integer.parseInt(req.params(":ic"));
            int depNewsId = Integer.parseInt(req.params(":id"));

            Departments departments = sql2oDepartments.findById(depId);
            DepNews retrieved = depNewsDao.findByIdDep(depNewsId);
            Users users = usersDao.findById(retrieved.getUserid());

            model.put("departments", departments);
            model.put("news", retrieved);
            model.put("users", users);
            model.put("username", req.session().attribute("username"));
            return  new ModelAndView(model, "depnewsdetails.hbs");
        }, new HandlebarsTemplateEngine());

        post("/departments/:ic/news/:id/delete", "application/json", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int depId = Integer.parseInt(req.params(":ic"));
            int newsId = Integer.parseInt(req.params(":id"));

            depNewsDao.deleteByIdDep(newsId);

            List<Departments> allDepartments = sql2oDepartments.getAll();
            List<News> allNews = newsDao.getAllNewsForCompany();

            model.put("news", allNews);
            model.put("departments", allDepartments);
            res.redirect("/departments/" + depId + "/news");
            return null;
        }, new HandlebarsTemplateEngine());

    }

}
