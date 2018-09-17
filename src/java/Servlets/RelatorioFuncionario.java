/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Departamento;
import Beans.Funcionario;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author guilh
 */
@WebServlet(name = "RelatorioFuncionario", urlPatterns = {"/RelatorioFuncionario"})
public class RelatorioFuncionario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("funcionarioatoa") == null) {
            request.setAttribute("msg", "Acesso negado!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
            rd.forward(request, response);
        }
        Connection con = null;
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjobs?autoReconnect=true&useSSL=false", "root", "root");
                String jrxml = "/home/gqueiroz/NetBeansProjects/MYJOBS/build/web/Atividade_doFuncionario.jrxml";
                HashMap params = new HashMap();
                Funcionario funcionario = new Funcionario();
                funcionario = (Funcionario)session.getAttribute("funcionarioatoa");
                int idFun =  Integer.valueOf(request.getParameter("funcionario"));
                String idD = String.valueOf(idFun);
                Client client = ClientBuilder.newClient();
                funcionario = client.target("http://localhost:8080/RHACTS/webresources/funcionarios/nome/" + idD).request(MediaType.APPLICATION_JSON).get(Funcionario.class);
                params.put("a.idFuncionario", idFun);
                params.put("nomeFuncionario", funcionario.getNomeFuncionario());
                String jasper = JasperCompileManager.compileReportToFile(jrxml);
                JasperPrint print = JasperFillManager.fillReport(jasper, params, con);
                OutputStream saida = response.getOutputStream();
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, saida);
                exporter.exportReport();
            }
            catch(SQLException e) {
                request.setAttribute("msg", "Erro de conexão ou query: " + e.getMessage());
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
            catch(JRException e) {
                request.setAttribute("msg", "Erro no Jasper : " + e.getMessage());
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(RelatorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
            finally {
                if (con!=null)
                try { con.close(); } catch(Exception e) {}
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
        processRequest(request, response);
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
        processRequest(request, response);
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
