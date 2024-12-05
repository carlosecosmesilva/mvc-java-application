package model;

import entidade.Relatorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    // Método para gerar o relatório de disciplinas
    public List<Relatorio> gerarRelatorioDisciplinas() {
        List<Relatorio> relatorios = new ArrayList<>();
        String sql = "SELECT d.nome AS disciplina, t.nome AS turma, a.nome AS aluno, i.nota "
                + "FROM disciplina d "
                + "JOIN turma t ON d.id = t.disciplina_id "
                + "JOIN inscricao i ON t.id = i.turma_id "
                + "JOIN aluno a ON i.aluno_id = a.id";

        // Conexão com o banco de dados usando a classe Conexao
        Conexao conexao = new Conexao();
        try (Connection conn = conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            // Processa os resultados da consulta SQL
            while (rs.next()) {
                Relatorio rel = new Relatorio();
                rel.setDisciplina(rs.getString("disciplina"));
                rel.setTurma(rs.getString("turma"));
                rel.setAluno(rs.getString("aluno"));
                rel.setNota(rs.getDouble("nota"));
                relatorios.add(rel);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar relatório de disciplinas.", e);
        } finally {
            conexao.closeConexao();  // Fecha a conexão após o uso
        }

        return relatorios;
    }
}
