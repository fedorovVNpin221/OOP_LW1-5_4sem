package com.example.lab5;
import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private final static String URL = "jdbc:mysql://127.0.0.1:3306/mydb";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "123123123";
    private Connection connection;
    //private static final String filePath = "students.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        int age = Integer.parseInt(request.getParameter("age"));
        String departament = request.getParameter("departament");
        int telephoneNumber = Integer.parseInt(request.getParameter("telephone_number"));

        PreparedStatement ps = null;
        String INSERT_NEW = "INSERT INTO students (name, lastname, age, departament, telephone_number) VALUES (?, ?, ?, ?, ?)";
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ps = connection.prepareStatement(INSERT_NEW); // Initialize the PreparedStatement here
            ps.setString(1, name);
            ps.setString(2, lastname);
            ps.setInt(3, age);
            ps.setString(4, departament);
            ps.setInt(5, telephoneNumber);
            System.out.println(ps);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/lab5_war_exploded");

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DBWoker worker = new DBWoker();

        String query = "Select * from students";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body style=\"background-color: #e6dbb9\"><div><table class=\"table\"><thead><tr><th scope=\"col\">Имя</th><th scope=\"col\">Фамилия</th><th scope=\"col\">Возраст</th> <th scope=\"col\">Группа</th><th scope=\"col\">Номер телефона</th></tr></thead>");
        try{
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                out.println("<tbody> <tr><td>" + resultSet.getString("name") + "</td><td>" + resultSet.getString("lastname") + "</td><td>" + resultSet.getInt("age") + "</td><td>" + resultSet.getString("departament") + "</td><td>" + resultSet.getInt("telephone_number") + "</td>");
                Student student = new Student(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getInt("age"), resultSet.getString("departament"), resultSet.getInt("telephone_number"));
                System.out.println(student.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
     }
}