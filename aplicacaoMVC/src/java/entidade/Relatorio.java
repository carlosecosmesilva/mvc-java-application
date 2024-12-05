package entidade;

public class Relatorio {
    private String disciplina;
    private String turma;
    private String aluno;
    private double nota;

    public Relatorio(String disciplina, String turma, String aluno, double nota) {
        this.disciplina = disciplina;
        this.turma = turma;
        this.aluno = aluno;
        this.nota = nota;
    }

    public Relatorio() {
    }

    // Getters e Setters
    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
