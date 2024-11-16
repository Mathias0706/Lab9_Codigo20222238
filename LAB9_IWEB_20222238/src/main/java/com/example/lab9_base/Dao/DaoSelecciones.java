package com.example.lab9_base.Dao;
import com.example.lab9_base.Bean.Seleccion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoSelecciones extends BaseDao{

    public ArrayList<Seleccion> listarSelecciones() {

        ArrayList<Seleccion> selecciones = new ArrayList<>();
        try (Connection conn = this.getConnection();
             Statement sm = conn.createStatement();
             ResultSet rs = sm.executeQuery("select * from seleccion;");) {
            while (rs.next()) {
                Seleccion seleccion = new Seleccion();
                seleccion.setIdSeleccion(rs.getInt(1));
                seleccion.setNombre(rs.getString(2));
                selecciones.add(seleccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selecciones;
    }

}
