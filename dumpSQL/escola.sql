-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 07/11/2024 às 19:35
-- Versão do servidor: 5.7.31
-- Versão do PHP: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `escola`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `administrador`
--

DROP TABLE IF EXISTS `administrador`;
CREATE TABLE IF NOT EXISTS `administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8 NOT NULL,
  `senha` varchar(255) CHARACTER SET utf8 NOT NULL,
  `aprovado` varchar(1) CHARACTER SET utf8 NOT NULL,
  `endereco` varchar(100) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura para tabela `alunos`
--

DROP TABLE IF EXISTS `alunos`;
CREATE TABLE IF NOT EXISTS `alunos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `celular` char(14) CHARACTER SET utf8 NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8 NOT NULL,
  `senha` varchar(255) CHARACTER SET utf8 NOT NULL,
  `endereco` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `cidade` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `bairro` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `cep` varchar(9) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura para tabela `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
CREATE TABLE IF NOT EXISTS `disciplina` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `requisito` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ementa` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `carga_horaria` smallint(5) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura para tabela `professores`
--

DROP TABLE IF EXISTS `professores`;
CREATE TABLE IF NOT EXISTS `professores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8 NOT NULL,
  `senha` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura para tabela `turmas`
--

DROP TABLE IF EXISTS `turmas`;
CREATE TABLE IF NOT EXISTS `turmas` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `professor_id` int(11) NOT NULL,
  `disciplina_id` int(10) UNSIGNED NOT NULL,
  `aluno_id` int(11) NOT NULL,
  `codigo_turma` varchar(2) NOT NULL,
  `nota` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `turmas_FKIndex1` (`disciplina_id`),
  KEY `professor_FKIndex2` (`professor_id`),
  KEY `aluno_fk` (`aluno_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `turmas`
--
ALTER TABLE `turmas`
  ADD CONSTRAINT `aluno_fk` FOREIGN KEY (`aluno_id`) REFERENCES `alunos` (`id`),
  ADD CONSTRAINT `professor_fk` FOREIGN KEY (`professor_id`) REFERENCES `professores` (`id`),
  ADD CONSTRAINT `turmas_fk` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
