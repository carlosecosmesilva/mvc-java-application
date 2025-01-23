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

    public int contarTurmasDoProfessor(int professorId) throws SQLException {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT COUNT(*) AS total FROM turmas WHERE professor_id = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar turmas do professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public boolean isAlunoNaTurma(int alunoId, int turmaId) throws SQLException {
        Conexao conexao = new Conexao();
        String sql = "SELECT COUNT(*) FROM turmas WHERE id = ? AND aluno_id = ?";
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, turmaId);
            stmt.setInt(2, alunoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar turmas do professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
            return false;
        }
    }

    public boolean atualizarNota(int alunoId, int turmaId, double nota) throws SQLException {
        Conexao conexao = new Conexao();
        String sql = "UPDATE turmas SET nota = ? WHERE id = ? AND aluno_id = ?";
        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setDouble(1, nota);
            stmt.setInt(2, turmaId);
            stmt.setInt(3, alunoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar turmas do professor: " + e.getMessage());
        } finally {
            conexao.closeConexao();
            return false;
        }
    }

    public ArrayList<Turma> getTurmasAluno(int alunoId) throws SQLException {
        Conexao conexao = new Conexao();
        ArrayList<Turma> turmas = new ArrayList<>();

        String sql;
        sql = "SELECT t.id, t.professor_id, t.disciplina_id, t.aluno_id, t.codigo_turma, t.nota,"
                + " p.nome AS professor_nome, d.nome AS disciplina_nome "
                + " FROM turmas t "
                + " INNER JOIN professores p ON t.professor_id = p.id"
                + " INNER JOIN disciplina d ON t.disciplina_id = d.id "
                + " WHERE t.aluno_id = ?";

        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Turma turma = new Turma();
                    turma.setId(rs.getInt("id"));
                    turma.setProfessorId(rs.getInt("professor_id"));
                    turma.setDisciplinaId(rs.getInt("disciplina_id"));
                    turma.setAlunoId(rs.getInt("aluno_id"));
                    turma.setCodigoTurma(rs.getString("codigo_turma"));
                    turma.setNota(rs.getDouble("nota"));
                    turma.setProfessorNome(rs.getString("professor_nome"));
                    turma.setDisciplinaNome(rs.getString("disciplina_nome"));

                    turmas.add(turma);
                }
            }
        }

        return turmas;
    }

    public ArrayList<Turma> getTurmasProfessor(int professorId) throws SQLException {
        ArrayList<Turma> turmas = new ArrayList<>();
        Conexao conexao = new Conexao();
        String sql = " SELECT t.id, t.professor_id, t.disciplina_id, t.aluno_id, t.codigo_turma, t.nota, "
                + " a.nome AS aluno_nome, d.nome AS disciplina_nome "
                + " FROM turmas t "
                + " INNER JOIN alunos a ON t.aluno_id = a.id "
                + " INNER JOIN disciplina d ON t.disciplina_id = d.id "
                + " WHERE t.professor_id = ?";

        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, professorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Turma turma = new Turma();
                    turma.setId(rs.getInt("id"));
                    turma.setProfessorId(rs.getInt("professor_id"));
                    turma.setDisciplinaId(rs.getInt("disciplina_id"));
                    turma.setAlunoId(rs.getInt("aluno_id"));
                    turma.setCodigoTurma(rs.getString("codigo_turma"));
                    turma.setNota(rs.getDouble("nota"));

                    // Adicionais para exibição
                    turma.setAlunoNome(rs.getString("aluno_nome"));
                    turma.setDisciplinaNome(rs.getString("disciplina_nome"));

                    turmas.add(turma);
                }
            }
        }

        return turmas;
    }

}
