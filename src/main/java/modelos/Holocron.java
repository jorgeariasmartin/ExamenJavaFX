package modelos;

import enumerados.Pasillo;
import utilidades.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Holocron {
    private int id;
    private String autor;
    private String titulo;
    private int numeroEdicion;
    private String editorial;
    private String lugarPublicacion;
    private int numeroPaginas;
    private int stock;
    private double pvp;
    private int anioPublicacion;
    private String ISBN;
    private int anioEdicion;
    private Pasillo pasillo;

    // Constructor
    public Holocron(int id, String autor, String titulo, int numeroEdicion, String editorial, String lugarPublicacion,
                    int numeroPaginas, int stock, double pvp, int anioPublicacion, String ISBN, int anioEdicion,
                    Pasillo pasillo) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.numeroEdicion = numeroEdicion;
        this.editorial = editorial;
        this.lugarPublicacion = lugarPublicacion;
        this.numeroPaginas = numeroPaginas;
        this.stock = stock;
        this.pvp = pvp;
        this.anioPublicacion = anioPublicacion;
        this.ISBN = ISBN;
        this.anioEdicion = anioEdicion;
        this.pasillo = pasillo;
    }

    public Holocron() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getLugarPublicacion() {
        return lugarPublicacion;
    }

    public void setLugarPublicacion(String lugarPublicacion) {
        this.lugarPublicacion = lugarPublicacion;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getAnioEdicion() {
        return anioEdicion;
    }

    public void setAnioEdicion(int anioEdicion) {
        this.anioEdicion = anioEdicion;
    }

    public Pasillo getPasillo() {
        return pasillo;
    }

    public void setPasillo(Pasillo pasillo) {
        this.pasillo = pasillo;
    }

    // Metodo para traer todos los holocrons de la base de datos
    public static List<Holocron> getAllHolocronsFromDatabase() {
        List<Holocron> holocrons = new ArrayList<>();
        String query = "SELECT id, autor, titulo, numeroEdicion, editorial, lugarPublicacion, numeroPaginas, stock, pvp, anioPublicacion, ISBN, anioEdicion, Pasillo FROM Holocron";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Holocron holocron = new Holocron(
                        rs.getInt("id"),
                        rs.getString("autor"),
                        rs.getString("titulo"),
                        rs.getInt("numeroEdicion"),
                        rs.getString("editorial"),
                        rs.getString("lugarPublicacion"),
                        rs.getInt("numeroPaginas"),
                        rs.getInt("stock"),
                        rs.getDouble("pvp"),
                        rs.getInt("anioPublicacion"),
                        rs.getString("ISBN"),
                        rs.getInt("anioEdicion"),
                        Pasillo.valueOf(rs.getString("Pasillo"))
                );

                holocrons.add(holocron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return holocrons;
    }

    // Método para insertar un nuevo Holocron en la base de datos
    public boolean saveToDatabase() {
        String query = "INSERT INTO Holocron (Autor, Titulo, NumeroEdicion, Editorial, LugarPublicacion, NumeroPaginas, Stock, PVP, AnioPublicacion, ISBN, AnioEdicion, Pasillo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, this.autor);
            statement.setString(2, this.titulo);
            statement.setInt(3, this.numeroEdicion);
            statement.setString(4, this.editorial);
            statement.setString(5, this.lugarPublicacion);
            statement.setInt(6, this.numeroPaginas);
            statement.setInt(7, this.stock);
            statement.setDouble(8, this.pvp);
            statement.setInt(9, this.anioPublicacion);
            statement.setString(10, this.ISBN);
            statement.setInt(11, this.anioEdicion);
            statement.setString(12, this.pasillo.toString());

            int rowsInserted = statement.executeUpdate();
            System.out.println(rowsInserted + " Holocron insertado exitosamente en la base de datos.");
            return rowsInserted > 0; // Devuelve true si al menos una fila fue insertada
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Indica fallo
        }
    }

    // To String
    @Override
    public String toString() {
        return "Holocron{" +
                "id=" + id +
                ", autor='" + autor + '\'' +
                ", titulo='" + titulo + '\'' +
                ", numeroEdicion=" + numeroEdicion +
                ", editorial='" + editorial + '\'' +
                ", lugarPublicacion='" + lugarPublicacion + '\'' +
                ", numeroPaginas=" + numeroPaginas +
                ", stock=" + stock +
                ", pvp=" + pvp +
                ", anioPublicacion=" + anioPublicacion +
                ", ISBN='" + ISBN + '\'' +
                ", anioEdicion=" + anioEdicion +
                ", pasillo=" + pasillo +
                '}';
    }
}
