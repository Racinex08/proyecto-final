import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.*;
import java.util.concurrent.*;

// Clase Animal
class Animal {
    private String nombre;
    private int edad;
    private String especie;
    private String estadoSalud;

    public Animal(String nombre, int edad, String especie, String estadoSalud) {
        this.nombre = nombre;
        this.edad = edad;
        this.especie = especie;
        this.estadoSalud = estadoSalud;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    @Override
    public String toString() {
        return "Animal [nombre=" + nombre + ", edad=" + edad + ", especie=" + especie + ", estadoSalud=" + estadoSalud + "]";
    }
}

// Clase Refugio
class Refugio {
    private List<Animal> animales;
    private List<String> solicitudesAdopcion;
    private ExecutorService executor;

    public Refugio() {
        animales = new ArrayList<>();
        solicitudesAdopcion = new ArrayList<>();
        executor = Executors.newFixedThreadPool(10);
    }

    // Agregar nuevo animal al refugio
    public void agregarAnimal(Animal animal) {
        animales.add(animal);
    }

    // Registrar solicitud de adopción
    public void registrarSolicitudAdopcion(String nombreAnimal, String nombreAdoptante) {
        solicitudesAdopcion.add(nombreAnimal + " - " + nombreAdoptante);
    }

    // Método para actualizar el estado de salud de un animal
    public void actualizarEstadoSalud(String nombreAnimal, String nuevoEstadoSalud) {
        for (Animal animal : animales) {
            if (animal.getNombre().equals(nombreAnimal)) {
                animal.setEstadoSalud(nuevoEstadoSalud);
                break;
            }
        }
    }

    // Guardar datos del refugio en un archivo
    public void guardarDatos(String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(animales);
            oos.writeObject(solicitudesAdopcion);
        }
    }

    // Cargar datos del refugio desde un archivo
    public void cargarDatos(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            animales = (List<Animal>) ois.readObject();
            solicitudesAdopcion = (List<String>) ois.readObject();
        }
    }

    // Método para mostrar la lista de animales disponibles
    public void mostrarAnimalesDisponibles() {
        System.out.println("Animales disponibles en el refugio:");
        for (Animal animal : animales) {
            System.out.println(animal);
        }
    }

    // Método para mostrar las solicitudes de adopción pendientes
    public void mostrarSolicitudesAdopcion() {
        System.out.println("Solicitudes de adopción pendientes:");
        for (String solicitud : solicitudesAdopcion) {
            System.out.println(solicitud);
        }
    }

    // Método para cerrar el refugio
    public void cerrarRefugio() {
        executor.shutdown();
    }
}

// Clase Adoptante
class Adoptante {
    private String nombre;
    private String apellido;
    private String direccion;

    public Adoptante(String nombre, String apellido, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String toString() {
        return "Adoptante [nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion + "]";
    }
}

// Clase principal que contiene la interfaz de usuario
public class Main {
    public static void main(String[] args) {
        Refugio refugio = new Refugio();

        // Agregar algunos animales al refugio
        refugio.agregarAnimal(new Animal(" Firulais",  5, " Perro", " Bueno"));
        refugio.agregarAnimal(new Animal(" Mittens",  3, " Gato", " Regular"));
        refugio.agregarAnimal(new Animal(" Rex",  2, " Pez", " Excelente"));

        // Mostrar animales disponibles
        refugio.mostrarAnimalesDisponibles();

        // Registrar solicitud de adopción
        refugio.registrarSolicitudAdopcion("Firulais", "Juan Pérez");

        // Mostrar solicitudes de adopción
        refugio.mostrarSolicitudesAdopcion();

        // Actualizar estado de salud de un animal
        refugio.actualizarEstadoSalud("Mittens", "Bueno");

        // Guardar y cargar datos del refugio desde un archivo
        try {
            refugio.guardarDatos("datos_refugio.dat");
            refugio.cargarDatos("datos_refugio.dat");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Mostrar animales disponibles después de cargar datos
        refugio.mostrarAnimalesDisponibles();

        // Cerrar el refugio
        refugio.cerrarRefugio();
    }
}