/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.TipoAtividade;
import DAO.TipoAtividadeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guilh
 */
@WebServlet(name = "AlterarTiposAtividades", urlPatterns = {"/AlterarTiposAtividades"})
public class AlterarTiposAtividades extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("funcionarioatoa") == null) {
            request.setAttribute("msg", "Acesso negado!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
            rd.forward(request, response);
        }
        TipoAtividadeDAO tipoAtividadeDAO = new TipoAtividadeDAO();
        TipoAtividade tipoAtividade = new TipoAtividade();
        if (request.getParameter("tipo") != null) {
            int id = Integer.valueOf(request.getParameter("tipo"));            
            tipoAtividade = tipoAtividadeDAO.buscarPorId(id);
            request.setAttribute("tipoAtividade", tipoAtividade);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/alterar_tipo_atividade.jsp");
            rd.forward(request, response);
        }
        else {
            if (request.getParameter("Nome") != null && request.getParameter("Descricao") != null && !request.getParameter("Descricao").equals("") && !request.getParameter("Nome").equals("")) {
                tipoAtividade.setIdTipoAtividade(Integer.valueOf(request.getParameter("Id")));
                tipoAtividade.setNome(request.getParameter("Nome"));
                tipoAtividade.setDescricao(request.getParameter("Descricao"));
                tipoAtividadeDAO.alterar(tipoAtividade);
                request.setAttribute("msg", "Tipo " + tipoAtividade.getNome() + " alterado com sucesso!");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/manter_tipos_atividades.jsp");
                rd.forward(request, response);
            }
            else {
                request.setAttribute("msg", "Valores Invalidos!");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AlterarTiposAtividades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlterarTiposAtividades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AlterarTiposAtividades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlterarTiposAtividades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
