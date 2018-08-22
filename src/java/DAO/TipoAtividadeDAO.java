/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.TipoAtividade;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guilh
 */
public class TipoAtividadeDAO {
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    private String cadastrarTipoAtividade = "insert into TipoAtividade (nomeTipo, descricao) values (?, ?)";
    private String buscarTodos = "select idTipoAtividade, nomeTipo, descricao from TipoAtividade order by idTipoAtividade";
    private String buscarPorNome = "select idTipoAtividade, nomeTipo, descricao from TipoAtividade where nomeTipo like ? order by idTipoAtividade";
    private String remover = "delete from TipoAtividade where idTipoAtividade = ?";
    private String buscarPorId = "select idTipoAtividade, nomeTipo, descricao from TipoAtividade where idTipoAtividade = ?";
    private String alterar = "update TipoAtividade set nomeTipo = ?, descricao = ? where idTipoAtividade = ?";
    private String buscarNomes = "select idTipoAtividade, nomeTipo from TipoAtividade order by idTipoAtividade";
    
    public void cadastrarTipoAtividade(TipoAtividade tipo) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(cadastrarTipoAtividade);
            stmt.setString(1, tipo.getNome());
            stmt.setString(2, tipo.getDescricao());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao cadastrar Tipo: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    public List<TipoAtividade> buscarTodos() throws SQLException, ClassNotFoundException {
        List<TipoAtividade> lista = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarTodos);
            rs = stmt.executeQuery();
            while (rs.next()) {
                TipoAtividade d = new TipoAtividade();
                d.setIdTipoAtividade(rs.getInt("idTipoAtividade"));
                d.setNome(rs.getString("nomeTipo"));
                d.setDescricao(rs.getString("descricao"));
                lista.add(d);
            }
            return lista;
        } catch (SQLException ex) {
            out.println("Erro ao listar Tipo Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
        return lista;
    }
    
    public List<TipoAtividade> buscarPorNome(String nome) throws SQLException, ClassNotFoundException {
        List<TipoAtividade> lista = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarPorNome);
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                TipoAtividade d = new TipoAtividade();
                d.setIdTipoAtividade(rs.getInt("idTipoAtividade"));
                d.setNome(rs.getString("nomeTipo"));
                d.setDescricao(rs.getString("descricao"));
                lista.add(d);
            }
            return lista;
        } catch (SQLException ex) {
            out.println("Erro ao listar Tipo Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
        return lista;
    }
    
    public void remover(int id) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(remover);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao remover Tipo: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    public TipoAtividade buscarPorId(int id) throws SQLException, ClassNotFoundException {
        TipoAtividade d = new TipoAtividade();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarPorId);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                d.setIdTipoAtividade(rs.getInt("idTipoAtividade"));
                d.setNome(rs.getString("nomeTipo"));
                d.setDescricao(rs.getString("descricao"));
            }
            return d;
        } catch (SQLException ex) {
            out.println("Erro ao listar Departamentos: " + ex.getMessage());
        } finally {
            stmt.close();
            rs.close();
            con.close();
        }
        return d;
    }
    
    public void alterar(TipoAtividade d) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(alterar);
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getDescricao());
            stmt.setInt(3, d.getIdTipoAtividade());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao alterar Tipo: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    public List<TipoAtividade> buscarNomes() throws SQLException, ClassNotFoundException {
        List<TipoAtividade> lista = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarNomes);
            rs = stmt.executeQuery();
            while (rs.next()) {
                TipoAtividade d = new TipoAtividade();
                d.setIdTipoAtividade(rs.getInt("idTipoAtividade"));
                d.setNome(rs.getString("nomeTipo"));
                lista.add(d);
            }
            return lista;
        } catch (SQLException ex) {
            out.println("Erro ao listar Tipo Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
        return lista;
    }
}
