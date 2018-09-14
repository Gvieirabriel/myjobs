/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Atividade;
import Beans.Funcionario;
import Beans.TipoAtividade;
import DAO.AtividadeDAO;
import DAO.TipoAtividadeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "CadastrarAtividade", urlPatterns = {"/CadastrarAtividade"})
public class CadastrarAtividade extends HttpServlet {

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
        if (request.getParameter("Tipo") == null) {
            TipoAtividadeDAO tipoAtividadeDAO = new TipoAtividadeDAO();
            List<TipoAtividade> listaTipoAtividade = new ArrayList<>();
            listaTipoAtividade = tipoAtividadeDAO.buscarNomes();
            request.setAttribute("lista", listaTipoAtividade);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/cadastrar_atividade.jsp");
            rd.forward(request, response);
        }
        else {
            if(request.getParameter("Descricao") == null || request.getParameter("Descricao").equals("")) {
                request.setAttribute("msg", "Valores inválidos!");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
            else {
                AtividadeDAO atividadeDAO = new AtividadeDAO();
                Atividade atividade = new Atividade();
                TipoAtividade tipoAtividade = new TipoAtividade();
                Funcionario funcionario = new Funcionario();
                funcionario = (Funcionario)session.getAttribute("funcionarioatoa");
                atividade.setDescricao(request.getParameter("Descricao"));
                tipoAtividade.setIdTipoAtividade(Integer.valueOf(request.getParameter("Tipo")));
                atividade.setTipoAtividade(tipoAtividade);
                atividadeDAO.cadastrarAtividade(atividade, funcionario);
                request.setAttribute("msg", "Atividade cadastrada!");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Atividades");
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
            Logger.getLogger(CadastrarAtividade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastrarAtividade.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CadastrarAtividade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastrarAtividade.class.getName()).log(Level.SEVERE, null, ex);
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
