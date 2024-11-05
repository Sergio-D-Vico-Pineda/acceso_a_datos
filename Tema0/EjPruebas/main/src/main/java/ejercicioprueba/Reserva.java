package ejercicioprueba;

public class Reserva {

    private String customerName;
    private int roomNumber;
    private int nightsCount;
    private double costPerNight;

    public Reserva(String customerName, int roomNumber, int nightsCount, double costPerNight) {

        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.nightsCount = nightsCount;
        this.costPerNight = costPerNight;
    };

    public String getCusName() {
        return this.customerName;
    }

    public String showInfo() {

        String txt = " - Nombre del cliente: " + this.customerName + " - Habitación: " + this.roomNumber
                + "\n - Noches: " + this.nightsCount + " - Costo por noche: " + this.costPerNight + " €"
                + "\n - Costo total: " + nightsCount * costPerNight + " €";
        return txt;
    }

    public double totalCost() {
    return 1;
    }
}
