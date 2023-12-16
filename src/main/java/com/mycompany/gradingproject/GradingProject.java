/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.gradingproject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.*;

/**
 *
 * @author c2888413
 */
public class GradingProject {

    //create class level generic variables
    static ArrayList<Student> students = new ArrayList<>();
    static HashMap<String, ArrayList<Integer>> modulesFromCsv = new HashMap<>();
    static ArrayList<Module> modules = new ArrayList<>();

    // class level declarations
    private static final String DELIMITER = ",";
    public static void main(String[] args) {

        int userChoice = -1;
        Scanner sc = new Scanner(System.in);

        try {
            loadStudents();
        } catch (FileNotFoundException e) {
            // warn user and exit
            System.out.println("\n\n!!! Unable to open file !!!!!\n" + e.getMessage() + "\n");
            System.exit(0);
        } catch (IOException e) {
            // warn user and exit
            System.out.println("\n\n!!!! File read error !!!!\n" + e.getMessage() + "\n");
            System.exit(0);
        }

        // check whether students loaded
        if (students.size() == 0) {
            System.out.println("\n\n!!! Data Error: unable to proceed !!!!\n");
            System.exit(0);
        }

        while (userChoice != 0) {
            //Show the user the menu
            displayMenu();
            if (sc.hasNextInt()) {
                
                userChoice = sc.nextInt();
                switch (userChoice) {
                    case 1:
                        displayStudentGrades();
                        break;
                    case 2:
                        calculateHighestModuleMark();
                        break;
                    case 3:
                        calculateLowestModuleMark();
                        break;
                    case 4:
                        calculateAverageModuleMark();
                        break;
                    case 5:
                        calculateTotalFailed();
                        break;
                    case 6:
                        calculateTotalPassed();
                        break;
                    case 7:
                        calculateHighestModuleMark();
                        calculateLowestModuleMark();
                        calculateAverageModuleMark();
                        calculateTotalFailed();
                        calculateTotalPassed();
                        break;
                    case 0:
                        quit();
                        break;
                }
            } else {
                String dummy = sc.nextLine();
                userChoice = -1;
                System.out.println("!!!! Please select a valid menu option !!!!");
            }
        }

    } // main method ends here

    /**
     * function to display the menu options for the user
     */
    public static void displayMenu() {
        System.out.println("\n MENU");
        System.out.println("\t[1] View Students and their grades");
        System.out.println("\t[2] Show highest module score");
        System.out.println("\t[3] Show lowest module score");
        System.out.println("\t[4] Show average module score");
        System.out.println("\t[5] Show total passed in a module");
        System.out.println("\t[6] Show total failed in a module");
        System.out.println("\t[7] Show all module statistics");
        System.out.println("\t[0] Quit");
        System.out.println("\nPlease select a menu option: ");
    }


    /**
     * Reads students information a CSV file add the information to student
     * class also adds the grades to Module class for statistics
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void loadStudents() throws IOException, FileNotFoundException {
        final String INPUT_FILE_PATH = "student-results.csv";
        File inputFile = new File(INPUT_FILE_PATH);
        if (inputFile.exists() && inputFile.isFile()) {
            //load file into scanner to the file object
            Scanner fileScanner = new Scanner(inputFile);

            while (fileScanner.hasNextLine()) {
                // read each line of the csv and trim out white spaces
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String studentCode = line.split(DELIMITER)[0];
                    String firstName = line.split(DELIMITER)[1];
                    String lastName = line.split(DELIMITER)[2];
                    Student student = new Student(firstName, lastName, studentCode);

                    // get the course module name and their grades
                    String moduleOne = line.split(DELIMITER)[3];
                    int moduleOneScore = Integer.parseInt(line.split(DELIMITER)[4]);
                    student.addModuleMark(moduleOne, moduleOneScore);

                    addScoreToModules(moduleOne, moduleOneScore);

                    String moduleTwo = line.split(DELIMITER)[5];
                    int moduleTwoScore = Integer.parseInt(line.split(DELIMITER)[6]);
                    student.addModuleMark(moduleTwo, moduleTwoScore);

                    addScoreToModules(moduleTwo, moduleTwoScore);

                    String moduleThree = line.split(DELIMITER)[7];
                    int moduleThreeScore = Integer.parseInt(line.split(DELIMITER)[8]);
                    student.addModuleMark(moduleThree, moduleThreeScore);

                    addScoreToModules(moduleThree, moduleThreeScore);

                    String moduleFour = line.split(DELIMITER)[9];
                    int moduleFourScore = Integer.parseInt(line.split(DELIMITER)[10]);
                    student.addModuleMark(moduleFour, moduleFourScore);
                    addScoreToModules(moduleFour, moduleFourScore);

                    String moduleFive = line.split(DELIMITER)[11];
                    int moduleFiveScore = Integer.parseInt(line.split(DELIMITER)[12]);
                    student.addModuleMark(moduleFive, moduleFiveScore);
                    addScoreToModules(moduleFive, moduleFiveScore);

                    students.add(student);
                }
            } // end of the while loop

            loadToModuleClass();

            // close file stream by closing the scanner
            fileScanner.close();
        } else {
            System.out.println("\n!!! Error: '" + inputFile.getName() + "' does not exist as data file. !!!!\n");
        }
    } // end of loadStudents function

    public static void addScoreToModules(String moduleName, int moduleScore) {
        ArrayList moduleScores = new ArrayList<>();

        if (modulesFromCsv.containsKey(moduleName)) {
            moduleScores = modulesFromCsv.get(moduleName);
            moduleScores.add(moduleScore);
            modulesFromCsv.put(moduleName, moduleScores);

        } else {
            moduleScores.add(moduleScore);
            modulesFromCsv.put(moduleName, moduleScores);
        }
    }

    public static void loadToModuleClass() {
        for (HashMap.Entry<String, ArrayList<Integer>> moduleValue : modulesFromCsv.entrySet()) {
            String title = moduleValue.getKey();
            ArrayList values = moduleValue.getValue();
            Module module = new Module(title);
            module.addScores(values);
            modules.add(module);
        }
    }

    public static void displayStudentGrades() {
        displayHeader("Student Grades");
        for (Student value : students) {
            value.displayGrade();
            System.out.println("\n");
        }
    }

    /**
     * calculate the highest mark in each module
     */
    public static void calculateHighestModuleMark() {

        displayHeader("Highest module score");
        for (Module module : modules) {
            System.out.print(module.getTitle() + ": " + module.getHighestScore() + "\n");
        }
    }

    public static void calculateLowestModuleMark() {
        displayHeader("Lowest module score");
        for (Module module : modules) {
            System.out.print(module.getTitle() + ": " + module.getLowestScore() + "\n");
        }
    }

    public static void calculateAverageModuleMark() {
        displayHeader("Average module score");
        for (Module module : modules) {
            System.out.print(module.getTitle() + ": " + String.format("%.2f", module.getAverageScore()) + "\n");
//            String.format("%.2f", module.getAverageScore())
        }
    }

    public static void calculateTotalPassed() {
        displayHeader("Total passed in modules");
        for (Module module : modules) {
            System.out.print(module.getTitle() + ": " + module.getTotalPassed() + "\n");
        }
    }

    public static void calculateTotalFailed() {
        displayHeader("Total failed in modules");
        for (Module module : modules) {
            System.out.print(module.getTitle() + ": " + module.getTotalFailed() + "\n");
        }
    }
    
    /**
     * Reusable header function
     * @param title 
     */
    public static void displayHeader(String title) {
        System.out.println("-".repeat(20));
        System.out.println(title);
        System.out.println("-".repeat(20));
    }
    
    /**
     * function to write the module data into CSV file
     * @throws IOException
     * @throws FileNotFoundException 
     */
    public static void saveModuleStatisticsToFile() throws IOException, FileNotFoundException {
        final String OUTPUT_FILE_PATH = "module_statistics.csv";
        //create path object
        Path path = Paths.get(OUTPUT_FILE_PATH);

        Files.deleteIfExists(path);

        BufferedOutputStream buffer = new BufferedOutputStream(
          Files.newOutputStream(path, CREATE, WRITE)
        );

        String moduleReport = "";
        moduleReport += "Module Title " + DELIMITER;
        moduleReport += "Highest score " + DELIMITER;
        moduleReport += "Lowest score " + DELIMITER;
        moduleReport += "Average score " + DELIMITER;
        moduleReport += "Total passed " + DELIMITER;
        moduleReport += "Total failed " + "\r\n";
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            moduleReport += module.getTitle() + DELIMITER;
            moduleReport += module.getHighestScore() + DELIMITER;
            moduleReport += module.getLowestScore() + DELIMITER;
            moduleReport += String.format("%.2f", module.getAverageScore()) + DELIMITER;
            moduleReport += module.getTotalPassed() + DELIMITER;
            moduleReport += module.getTotalFailed() + "\r\n";
        }// end of for loop

        byte data[] = moduleReport.getBytes();

        buffer.write(data, 0, data.length);
        // close buffer so stream writes to file
        buffer.close();

        //confirm data written
        System.out.println("\n\nModule statistics written to file at: " + path.toAbsolutePath().toString());
    } // end of saveModuleStaticsToFile method
    
    public static void quit(){
        
        try {
            saveModuleStatisticsToFile();
        } catch (FileNotFoundException e) {
            //warn user
            System.out.println("\n\n!!!!! Unable to open output file !!!!!\n" + e.getMessage() + "\n");
        } catch (IOException e) {
            //warn user
            System.out.print("\n\n!!!!! File write error !!!!!\n" + e.getMessage() + "\n");
        }
        //end message
        System.out.println("\n\n***** Thank You for using this application *****\n");
        
    }

}
