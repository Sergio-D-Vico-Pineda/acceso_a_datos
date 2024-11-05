package casopractico1;

public class Usuario {
    String nombre;
    int edad;
    String correo;
    int talladepie;

    public Usuario(String nombre, int edad, String correo, int talladepie) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.talladepie = talladepie;
    }

    @Override
    public String toString() {
        return nombre + "," + edad + "," + correo + "," + talladepie;
    }

    public static Usuario fromString(String linea) {
        String[] props = linea.split(",");
        return new Usuario(props[0], Integer.parseInt(props[1]), props[2],
                Integer.parseInt(props[3]));
    }

}
