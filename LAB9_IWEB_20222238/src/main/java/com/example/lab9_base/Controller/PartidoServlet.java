package com.example.lab9_base.Controller;
import com.example.lab9_base.Bean.*;
import com.example.lab9_base.Dao.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet", ""})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        DaoPartidos daoPartidos = new DaoPartidos();
        switch (action) {
            case "guardar":
                int numJornada = Integer.parseInt(request.getParameter("jornada"));
                int visitanteID= Integer.parseInt(request.getParameter("visitante"));
                int arbitroID = Integer.parseInt(request.getParameter("arbitro"));
                int localID= Integer.parseInt(request.getParameter("local"));
                String fecha = request.getParameter("fecha");
                boolean bandera1=localID!=visitanteID;
                ArrayList<Partido> historial = daoPartidos.partidosPorJornada(numJornada);
                boolean bandera2 = true;
                if(!(historial.isEmpty())){
                    for (Partido p : historial) {
                        if((p.getSeleccionLocal().getIdSeleccion() == localID && p.getSeleccionVisitante().getIdSeleccion() == visitanteID)||(p.getSeleccionLocal().getIdSeleccion()==visitanteID && p.getSeleccionVisitante().getIdSeleccion()==localID)){
                            bandera2=false;
                            break;
                        }
                    }
                }
                Partido partido = new Partido();
                partido.setNumeroJornada(numJornada);
                partido.setFecha(fecha);
                Seleccion local = new Seleccion();
                local.setIdSeleccion(localID);
                partido.setSeleccionLocal(local);
                Seleccion visitante = new Seleccion();
                visitante.setIdSeleccion(visitanteID);
                partido.setSeleccionVisitante(visitante);
                Arbitro arbitro  = new Arbitro();
                arbitro.setIdArbitro(arbitroID);
                partido.setArbitro(arbitro);
                if (bandera1 && bandera2) {
                    daoPartidos.crearPartido(partido);
                    response.sendRedirect(request.getContextPath()+"/PartidoServlet");
                }
                else{
                    response.sendRedirect(request.getContextPath()+"/PartidoServlet?action=crear");
                }
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        DaoPartidos daoPart = new DaoPartidos();
        DaoSelecciones daoSel = new DaoSelecciones();
        DaoArbitros daoArb = new DaoArbitros();
        switch (action) {
            case "lista":
                ArrayList<Partido> list = daoPart.listaDePartidos();
                request.setAttribute("lista", list);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "crear":
                request.setAttribute("selecciones",daoSel.listarSelecciones());
                request.setAttribute("arbitros",daoArb.listarArbitros());
                request.getRequestDispatcher("partidos/form.jsp").forward(request,response);
                break;

        }

    }
}
