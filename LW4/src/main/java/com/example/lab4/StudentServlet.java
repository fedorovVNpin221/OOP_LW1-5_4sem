package com.example.lab4;

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
        String telephoneNumber = request.getParameter("telephone_number");
        Student student = new Student(name, lastname, age, departament, telephoneNumber);
        JSONArray studentList = getStudentListFromFile();
        studentList.add(student.toJSON());
        writeStudentListToFile(studentList);
        response.sendRedirect("/lab4_war_exploded");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray studentList = getStudentListFromFile();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body style=\"background-color: #e6dbb9\"><div><table class=\"table\"><thead><tr><th scope=\"col\">Имя</th><th scope=\"col\">Фамилия</th><th scope=\"col\">Возраст</th> <th scope=\"col\">Кафедра</th><th scope=\"col\">Номер телефона</th></tr></thead>");
        for (Object obj : studentList) {
            JSONObject studentJSON = (JSONObject) obj;
            Student student = Student.fromJSON(studentJSON);
            out.println("<tbody> <tr><td>" + student.getName() + "</td><td>" + student.getLastname() + "</td><td>" + student.getAge() + "</td><td>" + student.getDepartament() + "</td><td>" + student.getTelephoneNumber() + "</td>");
        }
        out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
    }
    private JSONArray getStudentListFromFile() {
        try {
            JSONParser parser = new JSONParser();
            File file = new File(filePath);
            String fullPath = file.getAbsolutePath();
            System.out.println(fullPath);
            return (JSONArray) parser.parse(new FileReader(filePath));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
    private void writeStudentListToFile(JSONArray studentList) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(studentList.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}