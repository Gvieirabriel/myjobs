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
import java.sql.Date;
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
@WebServlet(name = "AlterarAtividade", urlPatterns = {"/AlterarAtividade"})
public class AlterarAtividade extends HttpServlet {

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
        if (request.getParameter("atv") != null) {
            TipoAtividadeDAO tipoAtividadeDAO = new TipoAtividadeDAO();
            List<TipoAtividade> listaTipoAtividade = new ArrayList<>();
            listaTipoAtividade = tipoAtividadeDAO.buscarNomes();
            AtividadeDAO atividadeDAO = new AtividadeDAO();
            Funcionario funcionario = new Funcionario();
            Atividade atividade = new Atividade();
            funcionario = (Funcionario)session.getAttribute("funcionarioatoa");
            int id = Integer.valueOf(request.getParameter("atv"));
            atividade = atividadeDAO.buscaAtividadePorFuncionarioEId(funcionario, id);
            request.setAttribute("lista", listaTipoAtividade);
            request.setAttribute("atividade", atividade);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/alterar_atividades.jsp");
            rd.forward(request, response);
        }
        else if (request.getParameter("check") != null) {
            AtividadeDAO atividadeDAO = new AtividadeDAO();
            Atividade atividade = new Atividade();
            TipoAtividade tipoAtividade = new TipoAtividade();
            Funcionario funcionario = new Funcionario();
            funcionario = (Funcionario)session.getAttribute("funcionarioatoa");
            atividade.setIdAtividade(Integer.valueOf(request.getParameter("check")));
            atividade.setDescricao(request.getParameter("Descricao"));
            atividade.setInicio(Date.valueOf(request.getParameter("Inicio")));
            atividade.setIdEstado(Integer.valueOf(request.getParameter("Status")));
            tipoAtividade.setIdTipoAtividade(Integer.valueOf(request.getParameter("Tipo")));
            if (atividade.getIdEstado() == 2) {
                atividade.setFim(Date.valueOf(request.getParameter("Fim")));
            }
            atividade.setTipoAtividade(tipoAtividade);
            if (atividade.validaAtividade(atividade) && validaAtividade(funcionario, atividade)) {
                atividadeDAO.alterarAtividade(atividade, funcionario);
                request.setAttribute("msg", "Atividade alterada com sucesso!");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Atividades");
                rd.forward(request, response);
            }
            else {
                request.setAttribute("msg", "Datas inválidas!");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
        }
    }
    
    private boolean validaAtividade(Funcionario funcionario, Atividade atividade) throws SQLException, ClassNotFoundException {
        AtividadeDAO atividadeDAO = new AtividadeDAO();
        List<Atividade> listaAtividade = new ArrayList<>();
        listaAtividade = atividadeDAO.buscaAtividadePorFuncionario(funcionario);
        for (Atividade a:listaAtividade) { //Funcao para nao deixar duas atividades em andamento ao mesmo tempo
            if (a.getIdAtividade() != atividade.getIdAtividade()){
                if (atividade.getFim() != null && a.getFim() != null) {
                    if (atividade.getInicio().before(a.getFim()) && atividade.getFim().after(a.getFim()))
                        return false;
                    if (atividade.getInicio().before(a.getInicio()) && atividade.getFim().after(a.getFim()))
                        return false;
                    if (atividade.getInicio().after(a.getInicio()) && atividade.getFim().before(a.getFim()))
                        return false;
                    if (atividade.getInicio().before(a.getInicio()) && atividade.getFim().after(a.getInicio()))
                        return false;
                    if (atividade.getInicio().compareTo(a.getInicio()) == 0 && atividade.getFim().compareTo(a.getFim()) == 0 && a.getInicio().compareTo(a.getFim()) != 0)
                        return false;
                    if (atividade.getInicio().compareTo(a.getInicio()) == 0 && atividade.getFim().compareTo(a.getFim()) != 0 && atividade.getInicio().compareTo(atividade.getFim()) != 0 && a.getInicio().compareTo(a.getFim()) != 0)
                        return false;
                    if (atividade.getInicio().compareTo(a.getInicio()) != 0 && atividade.getFim().compareTo(a.getFim()) == 0 && atividade.getInicio().compareTo(atividade.getFim()) != 0 && a.getInicio().compareTo(a.getFim()) != 0)
                        return false;
                }
                if (atividade.getFim() != null && a.getFim() == null) {
                    if (atividade.getInicio().before(a.getInicio()) && atividade.getFim().after(a.getInicio()))
                        return false;
                    if (a.getInicio().after(atividade.getInicio()) && a.getInicio().before(atividade.getFim()))
                        return false;
                    if (atividade.getInicio().after(a.getInicio()))
                        return false;
                    if (atividade.getFim().after(a.getInicio()))
                        return false;
                    if (atividade.getInicio().compareTo(a.getInicio()) == 0 && atividade.getInicio().compareTo(atividade.getFim()) != 0)
                        return false;
                }
                if (atividade.getFim() == null && a.getFim() != null) {
                    if (atividade.getInicio().after(a.getInicio()) && atividade.getInicio().before(a.getFim()))
                        return false;
                    if (a.getInicio().before(atividade.getInicio()) && a.getFim().after(atividade.getInicio()))
                        return false;
                    if (a.getInicio().after(atividade.getInicio()))
                        return false;
                    if (atividade.getInicio().before(a.getFim()))
                        return false;
                    if (a.getInicio().compareTo(atividade.getInicio()) == 0 && a.getInicio().compareTo(a.getFim()) != 0)
                        return false;
                }
                if (a.getFim() == null && atividade.getFim() == null) {
                    if (atividade.getInicio().after(a.getInicio()))
                        return false;
                    if (atividade.getInicio().before(a.getInicio()))
                        return false;
                }
            }
        }
        return true;
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
            Logger.getLogger(AlterarAtividade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlterarAtividade.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AlterarAtividade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlterarAtividade.class.getName()).log(Level.SEVERE, null, ex);
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
