# Escola Web Application - Trabalho de Desenvolvimento Web 2024.2

## Introdução
Este repositório contém o projeto desenvolvido como parte do trabalho de Desenvolvimento Web – 2024.2. A aplicação, chamada **Escola**, é construída em Java utilizando servlets e JSP, seguindo o padrão MVC. O sistema registra e gerencia dados de professores, alunos, disciplinas e turmas, atendendo a requisitos funcionais e não funcionais especificados.

---

## Visão Geral da Solução

A aplicação destina-se ao gerenciamento de informações de uma escola, contemplando os seguintes usuários e funcionalidades:

- **Alunos**: visualizam disciplinas e se matriculam.
- **Professores**: ministram turmas e lançam notas.
- **Administradores**: gerenciam cadastros e geram relatórios.

### Funcionalidades principais:
1. **Área pública**: exibição das disciplinas disponíveis e suas vagas.
2. **Área privada**:
   - Cadastro e gestão de professores, alunos, disciplinas, turmas e administradores.
   - Geração de relatórios sobre turmas, alunos e notas.

---

## Requisitos da Solução

### Requisitos Não Funcionais (RNF)
- **RNF1**: Interface responsiva obrigatoriamente implementada com Bootstrap.
- **RNF2**: Todas as dependências (bibliotecas, scripts, imagens) devem ser locais, sem uso de recursos externos.

### Requisitos Funcionais (RF)
#### Administradores
- **RF1**: Login na área privada.
- **RF2**: CRUD de alunos.
- **RF3**: CRUD de professores.
- **RF4**: CRUD de administradores.
- **RF5**: CRUD de disciplinas.
- **RF6**: CRUD de turmas.
- **RF7**: Geração de relatórios (disciplinas/turmas, alunos matriculados e suas notas).

#### Alunos
- **RF8**: Login na área privada.
- **RF9**: Inscrição em disciplinas/turmas.
- **RF10**: Visualização de histórico (notas).

#### Professores
- **RF11**: Login na área privada.
- **RF12**: Lançamento de notas dos alunos.
- **RF13**: Visualização das notas dos alunos de suas disciplinas/turmas.

---

## Limites e Restrições

1. **Banco de Dados**: 
   - O script de criação está disponível na pasta `dumpsql/dump.sql`.
   - Tabelas predefinidas não podem ser alteradas.
2. **Login Padrão**: 
   - Administrador inicial: `CPF: 249.252.810-38`, `Senha: 111`.
3. **Validações**:
   - **Servidor**: Exigência de preenchimento correto (sem campos vazios ou valores inválidos).
   - **Cliente**: Validações adicionais para melhorar a experiência do usuário.
4. **Base de desenvolvimento**: A aplicação **deve** ser construída a partir da base fornecida `aplicacaoMVC`.

---

## Regras de Negócio

### Alunos
- **RN1**: Podem se inscrever em qualquer disciplina/turma.
- **RN2**: Não podem apagar inscrições após o lançamento de notas.
- **RN3**: Inscrição só permitida se houver vagas (limite: 2 por turma).

### Professores
- **RN4**: Podem ministrar no máximo 2 disciplinas/turmas.

### Administradores
- **RN5**: Devem realizar login utilizando CPF e senha.

---

## Cronograma de Entregas

1. **Primeira Entrega**: 20/12/2024
   - Requisitos: **RF1 a RF7** (administradores).

2. **Segunda Entrega**: 23/01/2025
   - Requisitos: **RF8 a RF13** (alunos e professores).

---

## Tecnologias Utilizadas
- **Java**: Servlets e JSP
- **Bootstrap**: Interface responsiva
- **Banco de Dados**: Script localizado em `dumpsql/dump.sql`
- **Padrão Arquitetural**: MVC

---

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/usuario/escola-web.git
   ```
2. Importe o projeto em sua IDE (Eclipse/IntelliJ).
3. Configure o servidor (Tomcat) e o banco de dados utilizando o script localizado em:
   ```
   dumpsql/dump.sql
   ```
4. Inicie a aplicação no servidor local.

---

## Autor

- Desenvolvido como parte do trabalho acadêmico da disciplina **Desenvolvimento Web** – 2024.2.
