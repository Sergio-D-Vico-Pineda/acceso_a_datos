
import java.util.ArrayList;

public class Student {
    private String name;
    private int years;
    private ArrayList<Integer> grades;

    public Student(String name, int years) {
        this.name = name;
        this.years = years;
        this.grades = new ArrayList<>();
    }

    public void showInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Years: " + this.years);
        System.out.println("Grades: " + this.grades.toString().substring(1, grades.toString().length() - 1));
        // Eliminar el primer y el ultimo elemento de las notas para que no aparezca el
        // parentesis []
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void addGrade(int grade) {
        this.grades.add(grade);
    }

    public String getAverage() {
        int total = 0;
        String result = "";
        for (int i = 0; i < this.grades.size(); i++) {
            total += this.grades.get(i);
        }
        result = "Average: " + total / this.grades.size();
        return result;
    }
    /*
     * public void delLastGrade() {
     * this.grades.remove(this.grades.size() - 1);
     * }
     */
}