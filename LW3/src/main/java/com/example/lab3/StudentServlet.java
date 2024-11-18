package com.example.lab3;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private static final String filePath = "students.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        int age = Integer.parseInt(request.getParameter("age"));
        String departament = request.getParameter("departament");
        String telephone_number = request.getParameter("telephone_number");

        JSONObject student = new JSONObject();
        student.put("name", name);
        student.put("lastname", lastname);
        student.put("age", age);
        student.put("departament", departament);
        student.put("telephone_number", telephone_number);

        JSONArray studentList = new JSONArray();

        try {
            JSONParser parser = new JSONParser();
            File file = new File(filePath);
            String fullPath = file.getAbsolutePath();
            System.out.println(fullPath);
            if (file.exists()) {
                studentList = (JSONArray) parser.parse(new FileReader(filePath));
            }
            studentList.add(student);
            System.out.println("Student List: " + studentList);
            FileWriter fileWriter = new FileWriter(filePath);

            fileWriter.write(studentList.toJSONString());
            fileWriter.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/lab3_war_exploded");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JSONParser parser = new JSONParser();
            JSONArray studentList = (JSONArray) parser.parse(new FileReader(filePath));

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body style=\"background-color: #e6dbb9\"><div><table class=\"table\"><thead><tr><th scope=\"col\">Имя</th><th scope=\"col\">Фамилия</th><th scope=\"col\">Возраст</th> <th scope=\"col\">Кафедра</th><th scope=\"col\">Номер телефона</th></tr></thead>");
            for (Object obj : studentList) {
                JSONObject student = (JSONObject) obj;
                out.println("<tbody> <tr><td>" + student.get("name") + "</td><td>" + student.get("lastname") + "</td><td>" + student.get("age") + "</td><td>" + student.get("departament") + "</td><td>" + student.get("telephone_number") + "</td>");
            }

            out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}