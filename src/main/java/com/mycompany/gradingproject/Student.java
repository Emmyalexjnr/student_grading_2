/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gradingproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author c2888413
 */
public class Student {

    private String firstName;
    private String lastName;
    private String studentNumber;
    
    private ArrayList<String> moduleNames = new ArrayList<>();
    private HashMap<String, Integer> marks = new HashMap<>();
    private HashMap<String, String> grades = new HashMap<>();
    
    
    public Student(String firstName, String lastName, String studentNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getFullName() {
//        String fullName = firstName + ", " + lastName.charAt(0);
        String fullName = firstName + " " + lastName;
        return fullName;
    }
    
    public ArrayList<String> getModuleNames() {
        return moduleNames;
    }
    
    public void setModuleNames(String name) {
        moduleNames.add(name);
    }
    
    public void addModuleMark(String moduleCode, int mark) {
//        System.out.println("Module code and mark " + moduleCode + mark);
        String receivedModule = checkModule(moduleCode);
        if (receivedModule.isEmpty()) {
            String grade = calculateMarks(mark);
            marks.put(moduleCode, mark);
            grades.put(moduleCode, grade);
        }
    }

    
    public HashMap<String, Integer> getModuleMarks() {
        return marks;
    }
    
    
    public HashMap<String, String> getModuleGrades() {
        return grades;
    }
    
    public String checkModule(String name){
        for (String moduleName : moduleNames) {
            if(moduleName.equals(name)) {
                return moduleName;
            }
        }
        return "";
    }

    public String calculateMarks(int mark) {
        if (mark >= 0 && mark <= 39) {
            return "FAIL";
        } else if (mark <= 49) {
            return "D";
        } else if (mark <= 59) {
            return "C";
        } else if (mark <= 69) {
            return "B";
        } else if (mark <= 79) {
            return "A";
        } else if (mark <= 89) {
            return "A*";
        } else {
            return "A**";
        }
    }

    public void displayGrade() {
        String fullName = getFullName();
        System.out.print(fullName + ". ");

        for(HashMap.Entry<String, String> grade : grades.entrySet()) {
            String key = grade.getKey();
            String value = grade.getValue();
            System.out.print(key + ": " + value + " ");
        }
    }
}
