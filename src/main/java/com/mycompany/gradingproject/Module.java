/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gradingproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author c2888413
 */
public class Module {

    private String title;
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();
    
    public Module( String title ) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public ArrayList<Integer> getScores(){
        return scores;
    }

    public void setScore(int score){
        scores.add(score);
    }  
    
    public void addScores(ArrayList<Integer> values) {
        for(int v : values) {
            scores.add(v);
        }
    }
    
    /**
     * 
     * @param student 
     */
    public void addStudents(Student student) {
        if (!checkIfStudentsExist(student)) {
            students.add(student);
        }

    }

    private boolean checkIfStudentsExist(Student student) {
        return students.contains(student);
    }
    
    public ArrayList<Student> getStudents() {
        return students;
    }
    
    /**
     * The highest score in each module
     * @return 
     */
    public Integer getHighestScore() {
        Collections.sort(scores);
        int score = scores.get(scores.size() - 1);
        return score;
    }
    
    /**
     * Get the lowest score in this module
     * @return 
     */
    public Integer getLowestScore() {
        Collections.sort(scores);
        int score = scores.get(0);
        return score;
    }
    
    /**
     * Get the average score in this module
     * @return 
     */
    public Double getAverageScore(){
        double total = 0.00;
        for(int i = 0; i < scores.size(); i++){
            total = total + scores.get(i);
        }
        double average = total/scores.size();
        return average;
    }
    
    /**
     * Get the total number of people that failed this module
     * @return 
     */
    public int getTotalFailed() {
        ArrayList failed = new ArrayList<>();
        for(int i = 0; i < scores.size(); i++){
            int score = scores.get(i);
            if(score < 40) {
                failed.add(score);
            }
        }
        return failed.size();
    }
    
    
    /**
     * Get the total number of people that passed this module
     * @return 
     */
    public int getTotalPassed() {
        ArrayList passed = new ArrayList<>();
        for(int i = 0; i < scores.size(); i++){
            int score = scores.get(i);
            if(score >= 40) {
                passed.add(score);
            }
        }
        return passed.size();
    }
}
