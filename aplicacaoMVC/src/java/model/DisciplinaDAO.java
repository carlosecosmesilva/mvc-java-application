package model;

import entidade.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    // Inserir uma nova disciplina no banco de dados
    public void inserir(Disciplina disciplina) throws SQLException {
        String sql = "INSERT INTO disciplina (nome, requisito, ementa, carga_horaria) VALUES (?, ?, ?, ?)";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getRequisito());
            stmt.setString(3, disciplina.getEmenta());
            stmt.setInt(4, disciplina.getCargaHoraria());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir disciplina: " + e.getMessage(), e);
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
    }

    // Buscar uma disciplina pelo ID
    public Disciplina getDisciplina(int id) throws SQLException {
        String sql = "SELECT * FROM disciplina WHERE id = ?";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("id"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setRequisito(rs.getString("requisito"));
                disciplina.setEmenta(rs.getString("ementa"));
                disciplina.setCargaHoraria(rs.getInt("carga_horaria"));
                return disciplina;
            }
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
        return null;
    }

    // Listar todas as disciplinas
    public List<Disciplina> listar() throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("id"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setRequisito(rs.getString("requisito"));
                disciplina.setEmenta(rs.getString("ementa"));
                disciplina.setCargaHoraria(rs.getInt("carga_horaria"));
                disciplinas.add(disciplina);
            }
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
        return disciplinas;
    }

    // Atualizar os dados de uma disciplina
    public void atualizar(Disciplina disciplina) throws SQLException {
        String sql = "UPDATE disciplina SET nome = ?, requisito = ?, ementa = ?, carga_horaria = ? WHERE id = ?";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getRequisito());
            stmt.setString(3, disciplina.getEmenta());
            stmt.setInt(4, disciplina.getCargaHoraria());
            stmt.setInt(5, disciplina.getId());
            stmt.executeUpdate();
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
    }

    // Excluir uma disciplina pelo ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id = ?";

        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            conexao.closeConexao();  // Fechar a conexão
        }
    }

    // Listar as disciplinas com vagas
    public List<Disciplina> listarDisciplinasComVagas() throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT id, nome, (vagas_totais - vagas_ocupadas) AS vagas_disponiveis FROM disciplina";
        Conexao conexao = new Conexao();
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("id"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setVagasDisponiveis(rs.getInt("vagas_disponiveis"));
                disciplinas.add(disciplina);
            }
        }
        return disciplinas;
    }

}
