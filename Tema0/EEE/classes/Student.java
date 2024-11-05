package classes;

import java.util.ArrayList;

public class Student {
    private String name;
    private int years;
    private ArrayList<Double> grades;

    public Student(String name, int years) {
        this.name = name;
        this.years = years;
        this.grades = new ArrayList<>();
    }

    public void showInfo() {
        System.out.println("Nombre: " + this.name);
        System.out.println("Años: " + this.years);
        String txtGrades = (this.grades.size() == 0) ? "Sin notas"
                : this.grades.toString().substring(1, grades.toString().length() - 1);
        System.out.println("Notas: " + txtGrades);
        String txtAverage = (this.getAverage() == -1) ? "Sin promedio" : this.getAverage() + "";
        System.out.println("Promedio: " + txtAverage);

        // Eliminar el primer y el ultimo elemento de las notas para que no aparezca el
        // parentesis []
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public boolean addGrade(double grade) {
        if (grade >= 0 && grade <= 10) {
            this.grades.add(grade);
            return false;
        }
        System.err.println("\u001B[31m" + "La nota debe estar entre 0 y 10." + "\u001B[0m");
        return true;
    }

    public double getAverage() {
        if (this.grades.size() == 0)
            return -1;
        double total = 0;
        for (int i = 0; i < this.grades.size(); i++) {
            total += this.grades.get(i);
        }
        total = Math.round(total / this.grades.size() * 100);
        return total / 100;
        // need help here
    }
    /*
     * public void delLastGrade() {
     * this.grades.remove(this.grades.size() - 1);
     * }
     */
}