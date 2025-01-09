package model;

import entidade.Turma;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    // Inserir uma nova turma no banco de dados
    public void inserir(Turma turma) throws SQLException {
        String sql = "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigo_turma, nota) VALUES (?, ?, ?, ?, ?)";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, turma.getProfessorId());
            stmt.setInt(2, turma.getDisciplinaId());
            stmt.setInt(3, turma.getAlunoId());
            stmt.setString(4, turma.getCodigoTurma());
            stmt.setDouble(5, turma.getNota());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir turma: " + e.getMessage(), e);
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
    }

    // Buscar uma turma pelo ID
    public Turma getTurma(int id) throws SQLException {
        String sql = "SELECT * FROM turmas WHERE id = ?";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setProfessorId(rs.getInt("professor_id"));
                turma.setDisciplinaId(rs.getInt("disciplina_id"));
                turma.setAlunoId(rs.getInt("aluno_id"));
                turma.setCodigoTurma(rs.getString("codigo_turma"));
                turma.setNota(rs.getDouble("nota"));
                return turma;
            }
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
        return null;
    }

    // Listar todas as turmas
    public List<Turma> listar() throws SQLException {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT * FROM turmas";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setProfessorId(rs.getInt("professor_id"));
                turma.setDisciplinaId(rs.getInt("disciplina_id"));
                turma.setAlunoId(rs.getInt("aluno_id"));
                turma.setCodigoTurma(rs.getString("codigo_turma"));
                turma.setNota(rs.getDouble("nota"));
                turmas.add(turma);
            }
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
        return turmas;
    }

    // Atualizar os dados de uma turma
    public void atualizar(Turma turma) throws SQLException {
        String sql = "UPDATE turmas SET professor_id = ?, disciplina_id = ?, aluno_id = ?, codigo_turma = ?, nota = ? WHERE id = ?";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, turma.getProfessorId());
            stmt.setInt(2, turma.getDisciplinaId());
            stmt.setInt(3, turma.getAlunoId());
            stmt.setString(4, turma.getCodigoTurma());
            stmt.setDouble(5, turma.getNota());
            stmt.setInt(6, turma.getId());
            stmt.executeUpdate();
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
    }

    // Excluir uma turma pelo ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM turmas WHERE id = ?";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
    }

    public void lancarNota(Turma turma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Turma> getNotasPorTurma(int turmaId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Turma> getTurmasPorProfessor(int professorId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void adicionarAluno(int turmaId, int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Turma> getTurmasAluno(int alunoId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
