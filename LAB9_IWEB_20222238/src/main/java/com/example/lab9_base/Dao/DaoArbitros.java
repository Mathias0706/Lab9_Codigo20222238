package com.example.lab9_base.Dao;
import com.example.lab9_base.Bean.Arbitro;
import java.util.ArrayList;
import java.sql.*;

public class DaoArbitros extends BaseDao {

    public ArrayList<Arbitro> listarArbitros() {
        ArrayList<Arbitro> arbitros = new ArrayList<>();
        try (Connection conn = this.getConnection();
             Statement sm = conn.createStatement();
             ResultSet rs = sm.executeQuery("select * from arbitro;");) {
            while (rs.next()) {
                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(rs.getInt(1));
                arbitro.setNombre(rs.getString(2));
                arbitro.setPais(rs.getString(3));
                arbitros.add(arbitro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }

    public void crearArbitro(Arbitro arbitro) {
        String sql = "insert into arbitro (nombre,pais) value (?,?);";
        try (Connection conn = this.getConnection();
             PreparedStatement psm = conn.prepareStatement(sql);) {
            psm.setString(1, arbitro.getNombre());
            psm.setString(2, arbitro.getPais());
            psm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Arbitro> busquedaPais(String pais) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "select * from arbitro where lower(pais) like lower(?);";
        try (Connection conn = this.getConnection();
             PreparedStatement psm = conn.prepareStatement(sql);) {
            psm.setString(1,"%" +pais+ "%");
            try(ResultSet rs = psm.executeQuery()){
                while (rs.next()) {
                    Arbitro arbitro = new Arbitro();
                    arbitro.setIdArbitro(rs.getInt(1));
                    arbitro.setNombre(rs.getString(2));
                    arbitro.setPais(rs.getString(3));
                    arbitros.add(arbitro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }

    public ArrayList<Arbitro> busquedaNombre(String nombre) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "select * from arbitro where lower(nombre) like lower(?);";
        try (Connection conn = this.getConnection();
             PreparedStatement psm = conn.prepareStatement(sql);) {
            psm.setString(1,"%" +nombre+ "%");
            try(ResultSet rs = psm.executeQuery()){
                while (rs.next()) {
                    Arbitro arbitro = new Arbitro();
                    arbitro.setIdArbitro(rs.getInt(1));
                    arbitro.setNombre(rs.getString(2));
                    arbitro.setPais(rs.getString(3));
                    arbitros.add(arbitro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }

    public Arbitro buscarArbitro(int id) {
        Arbitro arbitro = new Arbitro();
        String sql = "select * from arbitro where idArbitro = ?;";
        try (Connection conn = this.getConnection();
             PreparedStatement psm = conn.prepareStatement(sql);) {
            psm.setInt(1,id);
            try(ResultSet rs = psm.executeQuery()){
                while (rs.next()) {
                    arbitro.setIdArbitro(rs.getInt(1));
                    arbitro.setNombre(rs.getString(2));
                    arbitro.setPais(rs.getString(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitro;
    }

    public void borrarArbitro(int id) {
        String sql = "delete from arbitro where idArbitro = ?;";
        try (Connection conn = this.getConnection();
             PreparedStatement psm = conn.prepareStatement(sql);) {
            psm.setInt(1,id);
            psm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
