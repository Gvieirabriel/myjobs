/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.Atividade;
import Beans.Departamento;
import Beans.Endereco;
import Beans.Funcionario;
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
public class AtividadeDAO {
    Connection con = null;
    PreparedStatement stmt = null;
    PreparedStatement stmt2 = null;
    PreparedStatement stmt3 = null;
    ResultSet rs = null;
    private String cadastrarAtividade = "insert into Atividade (idTipoAtividade, idFuncionario, idDepartamento, inicio, fim, descricao) values (?, ?, ?, curdate(), null, ?)";
    private String finalizarAtividade = "update Atividade set fim = curdate(), idEstado = 2 where idAtividade = ?";
    private String buscarAtividadeIdEstado = "select idAtividade from Atividade where idFuncionario = ? and idEstado in (1, 5)";
    private String buscaAtividadePorFuncionario = "select a.idAtividade, e.idEstado, e.nome, t.idTipoAtividade, t.nomeTipo, a.inicio, a.fim, a.descricao from Atividade a inner join Estado e on a.idEstado = e.idEstado inner join TipoAtividade t on a.idTipoAtividade = t.idTipoAtividade where a.idFuncionario = ? order by a.idAtividade";
    private String buscaAtividadePorFuncionarioEId ="select a.idAtividade, e.idEstado, e.nome, t.idTipoAtividade, t.nomeTipo, a.inicio, a.fim, a.descricao from Atividade a inner join Estado e on a.idEstado = e.idEstado inner join TipoAtividade t on a.idTipoAtividade = t.idTipoAtividade where a.idFuncionario = ? and a.idAtividade = ? order by a.idAtividade";
    private String alterarAtividade = "update Atividade set idEstado = ?, idTipoAtividade = ?, inicio = ?, fim = ?, descricao = ? where idAtividade = ?"; 
    private String buscarAtividadePorDepartamento = "select a.idAtividade, e.idEstado, e.nome, t.idTipoAtividade, t.nomeTipo, a.idFuncionario, a.inicio, a.fim, a.descricao from Atividade a inner join Estado e on a.idEstado = e.idEstado inner join TipoAtividade t on a.idTipoAtividade = t.idTipoAtividade where a.idDepartamento = ? and e.idEstado in (1, 2) order by a.idAtividade";
    private String alterarEstado = "update Atividade set idEstado = ? where idAtividade = ?";
    private String alterarEstadoTodos = "update Atividade set idEstado = ? where idDepartamento = ? and idEstado = ?";
    private String consolidarAtividade = "update Atividade set fim = curdate(), idEstado = 3 where idAtividade = ?";
    private String buscarAtividadePorDepartamentoEId = "select a.idAtividade, e.idEstado, e.nome, t.idTipoAtividade, t.nomeTipo, a.inicio, a.fim, a.descricao from Atividade a inner join Estado e on a.idEstado = e.idEstado inner join TipoAtividade t on a.idTipoAtividade = t.idTipoAtividade where a.idDepartamento = ? and e.idEstado = ? order by a.idAtividade";
    private String buscarTodas = "select a.idAtividade, e.idEstado, e.nome, t.idTipoAtividade, t.nomeTipo, a.idFuncionario, a.idDepartamento, a.inicio, a.fim, a.descricao from Atividade a inner join Estado e on a.idEstado = e.idEstado inner join TipoAtividade t on a.idTipoAtividade = t.idTipoAtividade order by a.idAtividade";
    private String aviso = "select idAviso from Aviso where idDepartamento = ?";
    private String deletaAviso = "delete from Aviso where idDepartamento = ?";
    private String insereAviso = "insert into Aviso (idDepartamento) values (?)";
    
    public void cadastrarAtividade(Atividade atividade, Funcionario funcionario) throws SQLException, ClassNotFoundException {
        int idAtividade = 0;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarAtividadeIdEstado);
            stmt2 = con.prepareStatement(finalizarAtividade);
            stmt3 = con.prepareStatement(cadastrarAtividade);
            stmt.setInt(1, funcionario.getIdFuncionario());
            rs = stmt.executeQuery();
            if (rs.next()) {
                idAtividade = rs.getInt("idAtividade");
            }
            if (idAtividade != 0) {
                stmt2.setInt(1, idAtividade);
                stmt2.executeUpdate();
            }
            stmt3.setInt(1, atividade.getTipoAtividade().getIdTipoAtividade());
            stmt3.setInt(2, funcionario.getIdFuncionario());
            stmt3.setInt(3, funcionario.getDepartamento().getIdDepartamento());
            stmt3.setString(4, atividade.getDescricao());
            stmt3.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao cadastrar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            stmt2.close();
            stmt3.close();
            con.close();
        }
    }
    
    public List<Atividade> buscaAtividadePorFuncionario(Funcionario funcionario) throws SQLException, ClassNotFoundException {
        List<Atividade> lista = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscaAtividadePorFuncionario);
            stmt.setInt(1, funcionario.getIdFuncionario());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Atividade a = new Atividade();
                TipoAtividade t = new TipoAtividade();
                a.setIdAtividade(rs.getInt("a.idAtividade"));
                a.setDescricao(rs.getString("a.descricao"));
                a.setInicio(rs.getDate("a.inicio"));
                a.setFim(rs.getDate("a.fim"));
                a.setEstado(rs.getString("e.nome"));
                a.setIdEstado(rs.getInt("e.idEstado"));
                t.setIdTipoAtividade(rs.getInt("t.idTipoAtividade"));
                t.setNome(rs.getString("t.nomeTipo"));
                a.setTipoAtividade(t);
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            out.println("Erro ao listar Atividades: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
        return lista;
    }
    
    public void finalizarAtividade(Atividade atividade) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(finalizarAtividade);
            stmt.setInt(1, atividade.getIdAtividade());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao cadastrar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    public Atividade buscaAtividadePorFuncionarioEId(Funcionario funcionario,int id) throws SQLException, ClassNotFoundException {
        Atividade a = new Atividade();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscaAtividadePorFuncionarioEId);
            stmt.setInt(1, funcionario.getIdFuncionario());
            stmt.setInt(2, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                TipoAtividade t = new TipoAtividade();
                a.setIdAtividade(rs.getInt("a.idAtividade"));
                a.setDescricao(rs.getString("a.descricao"));
                a.setInicio(rs.getDate("a.inicio"));
                a.setFim(rs.getDate("a.fim"));
                a.setEstado(rs.getString("e.nome"));
                a.setIdEstado(rs.getInt("e.idEstado"));
                t.setIdTipoAtividade(rs.getInt("t.idTipoAtividade"));
                t.setNome(rs.getString("t.nomeTipo"));
                a.setTipoAtividade(t);
                return a;
            }
            return a;
        } catch (SQLException ex) {
            out.println("Erro ao listar Atividades: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
        return a;
    }
    
    public void alterarAtividade(Atividade atividade, Funcionario funcionario) throws SQLException, ClassNotFoundException {
        int idAtividade = 0;
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarAtividadeIdEstado);
            stmt2 = con.prepareStatement(finalizarAtividade);
            stmt3 = con.prepareStatement(alterarAtividade);
            if (atividade.getIdEstado() == 1){
                stmt.setInt(1, funcionario.getIdFuncionario());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    idAtividade = rs.getInt("idAtividade");
                }
                if (idAtividade != 0) {
                    stmt2.setInt(1, idAtividade);
                    stmt2.executeUpdate();
                }
            }
            stmt3.setInt(1, atividade.getIdEstado());
            stmt3.setInt(2, atividade.getTipoAtividade().getIdTipoAtividade());
            stmt3.setDate(3, atividade.getInicio());
            stmt3.setDate(4, atividade.getFim());
            stmt3.setString(5, atividade.getDescricao());
            stmt3.setInt(6, atividade.getIdAtividade());
            stmt3.executeUpdate();            
        } catch (SQLException ex) {
            out.println("Erro ao alterar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            stmt2.close();
            stmt3.close();
            con.close();
        }
    }
    
    public List<Atividade> buscaAtividadePorDepartamento(Funcionario funcionario) throws SQLException, ClassNotFoundException {
        List<Atividade> lista = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarAtividadePorDepartamento);
            stmt.setInt(1, funcionario.getDepartamento().getIdDepartamento());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Atividade a = new Atividade();
                TipoAtividade t = new TipoAtividade();
                Funcionario f = new Funcionario();
                a.setIdAtividade(rs.getInt("a.idAtividade"));
                a.setDescricao(rs.getString("a.descricao"));
                a.setInicio(rs.getDate("a.inicio"));
                a.setFim(rs.getDate("a.fim"));
                a.setEstado(rs.getString("e.nome"));
                a.setIdEstado(rs.getInt("e.idEstado"));
                f.setIdFuncionario(rs.getInt("a.idFuncionario"));
                a.setFuncionario(f);
                t.setIdTipoAtividade(rs.getInt("t.idTipoAtividade"));
                t.setNome(rs.getString("t.nomeTipo"));
                a.setTipoAtividade(t);
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            out.println("Erro ao listar Atividades: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
        return lista;
    }
    
    public void alterarEstado(int idEstado, int idAtividade) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(alterarEstado);
            stmt.setInt(1, idEstado);
            stmt.setInt(2, idAtividade);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao alterar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    public void alterarEstadoTodos(Funcionario f) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(alterarEstado);
            stmt.setInt(1, 5);
            stmt2 = con.prepareStatement(buscarAtividadePorDepartamentoEId);
            stmt2.setInt(1, f.getDepartamento().getIdDepartamento());
            stmt2.setInt(2, 1);
            rs = stmt2.executeQuery();
            while (rs.next()) {
                stmt.setInt(2, rs.getInt("idAtividade"));
                stmt.executeUpdate();
            }
            rs.close();
            stmt.setInt(1, 3);
            stmt2.setInt(2, 2);
            rs = stmt2.executeQuery();
            while (rs.next()) {
                stmt.setInt(2, rs.getInt("idAtividade"));
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            out.println("Erro ao alterar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            stmt2.close();
            con.close();
        }
    }
    
    public void consolidarAtividade(Atividade atividade) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(consolidarAtividade);
            stmt.setInt(1, atividade.getIdAtividade());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao cadastrar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
        }
    }
    
    public List<Atividade> buscarTodas() throws SQLException, ClassNotFoundException {
        List<Atividade> lista = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(buscarTodas);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Atividade a = new Atividade();
                TipoAtividade t = new TipoAtividade();
                Departamento d = new Departamento();
                Funcionario f = new Funcionario();
                a.setIdAtividade(rs.getInt("a.idAtividade"));
                a.setDescricao(rs.getString("a.descricao"));
                a.setInicio(rs.getDate("a.inicio"));
                a.setFim(rs.getDate("a.fim"));
                a.setEstado(rs.getString("e.nome"));
                a.setIdEstado(rs.getInt("e.idEstado"));
                d.setIdDepartamento(rs.getInt("a.idDepartamento"));
                f.setIdFuncionario(rs.getInt("a.idFuncionario"));
                t.setIdTipoAtividade(rs.getInt("t.idTipoAtividade"));
                t.setNome(rs.getString("t.nomeTipo"));
                a.setTipoAtividade(t);
                a.setDepartamento(d);
                a.setFuncionario(f);
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            out.println("Erro ao listar Atividades: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
            rs.close();
        }
        return lista;
    }
    
    public boolean aviso(int id) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(aviso);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next())
                return true;
            return false;
        } catch (SQLException ex) {
            out.println("Erro ao cadastrar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
            rs.close();
        }
        return false;
    }
    
    public void deletaAviso(int id) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(deletaAviso);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao cadastrar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
            rs.close();
        }
    }
    
    public void insereAviso(int id) throws SQLException, ClassNotFoundException {
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(insereAviso);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            out.println("Erro ao cadastrar Atividade: " + ex.getMessage());
        } finally {
            stmt.close();
            con.close();
            rs.close();
        }
    }
}
