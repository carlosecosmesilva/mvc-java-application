package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Aluno;
import java.util.List;

public class AlunoDAO implements Dao<Aluno> {

    @Override
    public Aluno get(int id) {
        Conexao conexao = new Conexao();
        Aluno aluno = new Aluno();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM alunos WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null && resultado.next()) {
                aluno.setId(resultado.getInt("id"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setCelular(resultado.getString("celular"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setSenha(resultado.getString("senha"));
                aluno.setEndereco(resultado.getString("endereco"));
                aluno.setCidade(resultado.getString("cidade"));
                aluno.setBairro(resultado.getString("bairro"));
                aluno.setCep(resultado.getString("cep"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return aluno;
    }

    @Override
    public void insert(Aluno aluno) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                    "INSERT INTO alunos (nome, email, celular, cpf, senha, endereco, cidade, bairro, cep) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            sql.setString(1, aluno.getNome());
            sql.setString(2, aluno.getEmail());
            sql.setString(3, aluno.getCelular());
            sql.setString(4, aluno.getCpf());
            sql.setString(5, aluno.getSenha());
            sql.setString(6, aluno.getEndereco());
            sql.setString(7, aluno.getCidade());
            sql.setString(8, aluno.getBairro());
            sql.setString(9, aluno.getCep());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Aluno aluno) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement(
                    "UPDATE alunos SET nome = ?, email = ?, celular = ?, cpf = ?, senha = ?, endereco = ?, cidade = ?, bairro = ?, cep = ? "
                    + "WHERE id = ?"
            );
            sql.setString(1, aluno.getNome());
            sql.setString(2, aluno.getEmail());
            sql.setString(3, aluno.getCelular());
            sql.setString(4, aluno.getCpf());
            sql.setString(5, aluno.getSenha());
            sql.setString(6, aluno.getEndereco());
            sql.setString(7, aluno.getCidade());
            sql.setString(8, aluno.getBairro());
            sql.setString(9, aluno.getCep());
            sql.setInt(10, aluno.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM alunos WHERE id = ?");
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Aluno> getAll() {
        ArrayList<Aluno> alunos = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM alunos ORDER BY nome";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(resultado.getInt("id"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setCelular(resultado.getString("celular"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setSenha(resultado.getString("senha"));
                aluno.setEndereco(resultado.getString("endereco"));
                aluno.setCidade(resultado.getString("cidade"));
                aluno.setBairro(resultado.getString("bairro"));
                aluno.setCep(resultado.getString("cep"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return alunos;
    }

    public Aluno Logar(Aluno aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            String sql = "SELECT * FROM alunos WHERE cpf = ? AND senha = ? LIMIT 1";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getSenha());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Aluno al = new Aluno();
                al.setId(rs.getInt("id"));
                al.setNome(rs.getString("nome"));
                al.setCpf(rs.getString("cpf"));
                al.setEndereco(rs.getString("endereco"));
                al.setSenha(rs.getString("senha"));
                return al;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao logar: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public List<Aluno> getAlunosDaTurma(int turmaIdLancar, int professorId) throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        Conexao conexao = new Conexao();
        String sql = "SELECT a.id, a.nome "
                + "FROM alunos a "
                + "INNER JOIN turmas t ON a.id = t.aluno_id "
                + "WHERE t.id = ? AND t.professor_id = ?";

        try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, turmaIdLancar);
            stmt.setInt(2, professorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setId(rs.getInt("id"));
                    aluno.setNome(rs.getString("nome"));

                    alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao obter os alunos da turma: " + e.getMessage(), e);
        }

        return alunos;
    }

}
