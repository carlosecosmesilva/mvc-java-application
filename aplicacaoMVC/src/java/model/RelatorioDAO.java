package model;

import entidade.Relatorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public static List<Relatorio> gerarRelatorioDisciplinas() throws SQLException {
        List<Relatorio> relatorios = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {

            String sql = "SELECT d.nome AS disciplina, t.codigo_turma AS turma, a.nome AS aluno, t.nota "
                    + "FROM turmas t "
                    + "JOIN alunos a ON t.aluno_id = a.id "
                    + "JOIN disciplina d ON t.disciplina_id = d.id "
                    + "WHERE 1=1 ";

            PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setDisciplina(rs.getString("disciplina"));
                relatorio.setTurma(rs.getString("turma"));
                relatorio.setAluno(rs.getString("aluno"));
                relatorio.setNota(rs.getDouble("nota"));
                relatorios.add(relatorio);
            }

            return relatorios;

        } catch (SQLException e) {
            throw new SQLException("Erro ao gerar relatório: " + e.getMessage(), e);
        } finally {
            conexao.closeConexao(); 
        }
    }

}
