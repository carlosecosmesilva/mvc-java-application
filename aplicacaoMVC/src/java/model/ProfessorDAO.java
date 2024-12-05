package model;

import entidade.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public void inserir(Professor professor) throws SQLException {
        String sql = "INSERT INTO professores (nome, email, cpf, senha) VALUES (?, ?, ?, ?)";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getCpf());
            stmt.setString(4, professor.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir professor: " + e.getMessage(), e);
        } finally {
            conexao.closeConexao(); 
        }
    }

    public Professor getProfessor(int id) throws SQLException {
        String sql = "SELECT * FROM professores WHERE id = ?";
        
        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setEmail(rs.getString("email"));
                professor.setCpf(rs.getString("cpf"));
                professor.setSenha(rs.getString("senha"));
                return professor;
            }
        } finally {
            conexao.closeConexao();
        }
        return null;
    }

    public ArrayList<Professor> getAll()  throws SQLException {
        ArrayList<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM professores";
        
        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setEmail(rs.getString("email"));
                professor.setCpf(rs.getString("cpf"));
                professor.setSenha(rs.getString("senha"));
                professores.add(professor);
            }
        } finally {
            conexao.closeConexao(); 
        }
        return professores;
    }

    public void atualizar(Professor professor) throws SQLException {
        String sql = "UPDATE professores SET nome = ?, email = ?, cpf = ?, senha = ? WHERE id = ?";
        
        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getCpf());
            stmt.setString(4, professor.getSenha());
            stmt.setInt(5, professor.getId());
            stmt.executeUpdate();
        } finally {
            conexao.closeConexao();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM professores WHERE id = ?";
        
        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            conexao.closeConexao();
        }
    }
    
}
