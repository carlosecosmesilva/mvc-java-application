package entidade;

public class Professor {

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;

    // Construtor padrão
    public Professor() {
        this.id = 0;
        this.nome = "";
        this.email = "";
        this.cpf = "";
        this.senha = "";
    }

    // Construtor com todos os campos
    public Professor(int id, String nome, String email, String cpf, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Professor(int id) {
        this.id = id;
    }
    
    //Construtor para login
    public Professor(String cpf_user, String senha_user) {
        this.cpf = cpf_user;
        this.senha = senha_user;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
