package com.example.lab9_base.Controller;
import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Dao.DaoArbitros;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "ArbitroServlet", urlPatterns = {"/ArbitroServlet"})
public class ArbitroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");
        DaoArbitros daoArb = new DaoArbitros();
        switch (action) {

            case "buscar":
                ArrayList<Arbitro> listaArb = new ArrayList<>();
                String tipo = request.getParameter("tipo");
                String buscar = request.getParameter("buscar");
                if(tipo.equals("nombre")){
                    listaArb =daoArb.busquedaNombre(buscar);
                } else if (tipo.equals("pais")) {
                    listaArb =daoArb.busquedaPais(buscar);
                }
                request.setAttribute("lista",listaArb);
                request.setAttribute("busqueda",buscar);
                request.setAttribute("opciones",opciones);
                request.getRequestDispatcher("/arbitros/list.jsp").forward(request, response);
                break;

            case "guardar":
                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(request.getParameter("nombre"));
                arbitro.setPais(request.getParameter("pais"));
                daoArb.crearArbitro(arbitro);
                response.sendRedirect(request.getContextPath()+"/ArbitroServlet");
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");
        DaoArbitros daoArb = new DaoArbitros();
        switch (action) {
            case "lista":
                ArrayList<Arbitro> lista = daoArb.listarArbitros();
                request.setAttribute("lista", lista);
                request.setAttribute("opciones", opciones);
                request.getRequestDispatcher("/arbitros/list.jsp").forward(request, response);
                break;
            case "crear":
                request.setAttribute("paises", paises);
                request.getRequestDispatcher("/arbitros/form.jsp").forward(request, response);
                break;
            case "borrar":
                String idB = request.getParameter("id");
                int id=Integer.parseInt(idB);
                Arbitro arbitro = daoArb.buscarArbitro(id);
                if(arbitro != null){
                    daoArb.borrarArbitro(id);
                }
                response.sendRedirect(request.getContextPath() + "/ArbitroServlet");
                break;
        }
    }
}
