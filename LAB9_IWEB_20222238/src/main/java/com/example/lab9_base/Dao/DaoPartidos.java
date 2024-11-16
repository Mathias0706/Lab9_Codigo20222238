package com.example.lab9_base.Dao;
import com.example.lab9_base.Bean.*;
import java.util.ArrayList;
import com.example.lab9_base.Bean.Partido;
import java.sql.*;

public class DaoPartidos extends BaseDao {
    public ArrayList<Partido> listaDePartidos() {

        ArrayList<Partido> partidos = new ArrayList<>();
        try (Connection conn = this.getConnection();
             Statement sm = conn.createStatement();
             ResultSet rs = sm.executeQuery("select p.idPartido,p.numeroJornada,p.fecha,s.nombre,v.nombre,e.nombre,a.nombre,a.pais,s.idSeleccion,v.idSeleccion from partido p, seleccion s, estadio e,arbitro a,seleccion v where s.idSeleccion=p.seleccionLocal and s.estadio_idEstadio=e.idEstadio and a.idArbitro=p.arbitro and v.idSeleccion=p.seleccionVisitante;");) {
            while (rs.next()) {
                Partido partido = new Partido();
                Seleccion seleccionLocal = new Seleccion();
                Estadio estadio = new Estadio();
                Seleccion seleccionVisitante = new Seleccion();
                Arbitro arbitro = new Arbitro();
                partido.setIdPartido(rs.getInt(1));
                partido.setNumeroJornada(rs.getInt(2));
                partido.setFecha(rs.getString(3));
                seleccionLocal.setNombre(rs.getString(4));
                seleccionLocal.setIdSeleccion(rs.getInt(9));
                seleccionVisitante.setNombre(rs.getString(5));
                seleccionVisitante.setIdSeleccion(rs.getInt(10));
                partido.setSeleccionVisitante(seleccionVisitante);
                estadio.setNombre(rs.getString(6));
                seleccionLocal.setEstadio(estadio);
                partido.setSeleccionLocal(seleccionLocal);
                arbitro.setNombre(rs.getString(7));
                arbitro.setPais(rs.getString(8));
                partido.setArbitro(arbitro);
                partidos.add(partido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidos;
    }

    public void crearPartido(Partido partido) {
        String sql = "insert into partido (seleccionLocal,seleccionVisitante,arbitro,fecha,numeroJornada) value (?,?,?,?,?);";
        try (Connection conn = this.getConnection();
             PreparedStatement psm = conn.prepareStatement(sql);) {
            psm.setInt(1, partido.getSeleccionLocal().getIdSeleccion());
            psm.setInt(2, partido.getSeleccionVisitante().getIdSeleccion());
            psm.setInt(3, partido.getArbitro().getIdArbitro());
            psm.setString(4, partido.getFecha());
            psm.setInt(5, partido.getNumeroJornada());
            psm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Partido> partidosPorJornada(int numeroJornada){
        ArrayList<Partido> partidos = new ArrayList<>();
        String sql = "select * from partido where numeroJornada=?;";
        try (Connection conn = this.getConnection();
             PreparedStatement psm = conn.prepareStatement(sql);) {
            psm.setInt(1,numeroJornada);
            try(ResultSet rs = psm.executeQuery()){
                while (rs.next()) {
                    Partido partido = new Partido();
                    Seleccion seleccionLocal = new Seleccion();
                    Seleccion seleccionVisitante = new Seleccion();
                    Arbitro arbitro = new Arbitro();
                    partido.setIdPartido(rs.getInt(1));
                    seleccionLocal.setIdSeleccion(rs.getInt(2));
                    seleccionVisitante.setIdSeleccion(rs.getInt(3));
                    partido.setSeleccionLocal(seleccionLocal);
                    partido.setSeleccionVisitante(seleccionVisitante);
                    arbitro.setIdArbitro(rs.getInt(4));
                    partido.setArbitro(arbitro);
                    partido.setFecha(rs.getString(5));
                    partidos.add(partido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidos;
    }
}
