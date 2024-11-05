import classes.Student;

import java.util.Scanner;

import java.util.InputMismatchException;
import java.util.ArrayList;

/**
 * ejercicio1
 */

public class ejercicio1 {

    static int option;
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static Scanner pause = new Scanner(System.in);

    public static void main(String[] args) {

        /*
         * students.add(new Student("Sergio", 22));
         * students.get(0).addGrade(10);
         * students.get(0).addGrade(5.5);
         * students.get(0).addGrade(3.2);
         */

        do {
            for (int i = 0; i < 25; i++) {
                System.out.println(" ");
            }
            System.out.println("Ejercicio Entregable Evaluable - Sergio David Vico Pineda");

            System.out.println();
            System.out.println("-------- Menú --------");
            System.out.println();
            System.out.println(" 1 - Registrar estudiante.");
            System.out.println(" 2 - Añadir calificaciones a un alumno.");
            System.out.println(" 3 - Mostrar info de todos.");
            System.out.println(" 4 - Mostrar info de un estudiante.");
            System.out.println(" 5 - Mostrar estudiantes con promedio mayor a un valor dado.");
            System.out.println();
            System.out.println(" 0 - Salir del programa.");
            System.out.println();
            System.out.print("Por favor, elige una opción: ");

            try {
                option = input.nextInt();
            } catch (InputMismatchException e) {
                option = -1;
                input.next();
            }

            System.out.println();
            input.nextLine();

            switch (option) {
                case 0 -> ExitProgram();
                case 1 -> RegisterStudent();
                case 2 -> AddGrades();
                case 3 -> ShowAllInfo();
                case 4 -> SearchInfoStudent();
                case 5 -> StudentMoreAverage();
                default -> InvalidOption();
            }

            if (option != 0) {
                System.out.println();
                System.out.print("Presiona ENTER para continuar...");
                pause.nextLine();
            }

        } while (option != 0);

        input.close();
        pause.close();
    }

    static public boolean FindStudent(String nameStudent) {
        // Deberia devolver el objeto estudiante
        for (Student student : students) {
            if (student.getName().equals(nameStudent)) {
                return true;
            }
        }
        return false;
    }

    static public void ExitProgram() {
        System.err.println("Programa finalizado. Bye.");
    }

    static public void RegisterStudent() {

        int years = -1;

        System.out.println("Nombre del estudiante: ");
        String name = input.nextLine();

        if (FindStudent(name)) {
            System.err.println("\u001B[31m" + "Ya existe un estudiante con ese nombre." + "\u001B[0m");
            return;
        }

        do {
            try {
                System.out.println("Años del estudiante: ");
                years = input.nextInt();

            } catch (InputMismatchException e) {
                input.next();

                System.out.println(" ");
                System.err.println("\u001B[31m" + "Años invalidos." + "\u001B[0m");
                System.out.println(" ");
            }

        } while (years <= 0);

        Student newStudent = new Student(name, years);
        students.add(newStudent);
    }

    static public void AddGrades() {

        Student tempStudent = null;
        boolean moreInput = true;

        if (students.size() == 0) {
            System.err.println("\u001B[31m" + "No hay estudiantes." + "\u001B[0m");
            return;
        }

        System.out.println("Nombre del estudiante para introducir calificaciones: ");
        String txtNameString = input.nextLine();

        if (!FindStudent(txtNameString)) {
            System.err.println("\u001B[31m" + "El estudiante introducido no existe." + "\u001B[0m");
            return;
        }

        for (Student student : students) {
            if (student.getName().equals(txtNameString)) {
                tempStudent = student;
                break;
            }
        }

        do {
            try {
                System.out.println("Calificación a añadir: ");
                moreInput = tempStudent.addGrade(input.nextDouble());
                if (!moreInput) {
                    input.nextLine();
                    System.out.println("Calificación añadida. ¿Quieres añadir más? Pon S para continuar.");
                    try {
                        moreInput = input.nextLine().toUpperCase().charAt(0) == 'S';
                    } catch (IndexOutOfBoundsException e) {
                        moreInput = false;
                    }
                }
            } catch (InputMismatchException e) {
                input.next();
                System.err.println("\u001B[31m" + "Opción invalida." + "\u001B[0m");
                moreInput = true;
            }
        } while (moreInput);

        System.out.println(" ");
        System.out.println("No se introducirán más calificaciones.");
    }

    static public void ShowAllInfo() {

        if (students.size() == 0) {
            System.err.println("\u001B[31m" + "No hay estudiantes." + "\u001B[0m");
            return;
        }

        for (Student student : students) {
            student.showInfo();
            System.out.println(" ");
        }
    }

    static public void SearchInfoStudent() {

        if (students.size() == 0) {
            System.err.println("\u001B[31m" + "No hay estudiantes." + "\u001B[0m");
            return;
        }

        System.out.println("Nombre del estudiante para mostrar info: ");
        String nameStudent = input.nextLine();

        System.out.println(" ");

        if (!FindStudent(nameStudent)) {
            System.err.println("\u001B[31m" + "El estudiante introducido no existe." + "\u001B[0m");
            return;
        }

        for (Student student : students) {
            if (student.getName().equals(nameStudent)) {
                student.showInfo();
                break;
            }
        }
    }

    static public void StudentMoreAverage() {

        int minGrade = -1;
        boolean thereIsStudent = false;

        if (students.size() == 0) {
            System.err.println("\u001B[31m" + "No hay estudiantes." + "\u001B[0m");
            return;
        }

        System.out.println("Introduce la calificación mínima: ");

        try {
            minGrade = input.nextInt();
        } catch (InputMismatchException e) {
            input.next();
            /* input.nextLine(); */
            System.err.println("\u001B[31m" + "Calificación inválida." + "\u001B[0m");
            return;
        }

        if (minGrade < 0 || minGrade > 10) {
            System.err.println("\u001B[31m" + "La calificación debe estar entre 0 y 10." + "\u001B[0m");
            return;
        }

        System.out.println("");

        for (Student student : students) {
            if (student.getAverage() > minGrade) {
                student.showInfo();
                System.out.println(" ");
                thereIsStudent = true;
            }
        }

        if (!thereIsStudent) {
            System.err.println("\u001B[31m" + "No hay estudiantes con un promedio superior al indicado." + "\u001B[0m");
        }
    }

    static public void InvalidOption() {
        System.out.println("\u001B[31m" + "Opción inválida." + "\u001B[0m");
    }
}
