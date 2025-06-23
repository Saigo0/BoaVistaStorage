package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Fornecedor;
import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.entity.Compra;
import com.ibdev.boavistastorage.repository.GerenteRepository;
import jakarta.persistence.NoResultException;

public class GerenteService {
    private final GerenteRepository gerenteRepository;

    public GerenteService(GerenteRepository gerenteRepository) {
        this.gerenteRepository = gerenteRepository;
    }

    public void salvarGerente(String nome, String CPF, String telefone, String email, String login, String senha) {
        if (nome == null || nome.isEmpty() || CPF == null || CPF.isEmpty() || telefone == null || telefone.isEmpty() ||
            email == null || email.isEmpty() || login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }

        Gerente gerente = new Gerente(nome, CPF, telefone, email, login, senha);
        gerenteRepository.create(gerente);

    }

    public Gerente buscarGerentePorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do gerente inválido.");
        }
        Gerente gerente = gerenteRepository.findById(id);
        if (gerente == null) {
            throw new RuntimeException("Gerente não encontrado com o ID: " + id);
        }
        return gerente;
    }

    public Gerente buscarGerentePorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do gerente não pode ser vazio.");
        }
        Gerente gerente = gerenteRepository.findByNome(nome);
        if (gerente == null) {
            throw new RuntimeException("Gerente não encontrado com o nome: " + nome);
        }
        return gerente;
    }

    public Gerente buscarGerentePorTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone do gerente não pode ser vazio.");
        }
        if (!telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone deve conter 10 ou 11 dígitos numéricos.");
        }
        Gerente gerente = gerenteRepository.findByTelefone(telefone);
        if (gerente == null) {
            throw new RuntimeException("|Gerente não encontrado com o telefone: " + telefone);
        }
        return gerente;
    }

    public Gerente buscarGerentePorCPF(String CPF) {
        if (CPF == null || CPF.isEmpty()) {
            throw new IllegalArgumentException("CPF do gerente não pode ser vazio.");
        }
        if (!CPF.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos numéricos.");
        }
        Gerente gerente = gerenteRepository.findByCPF(CPF);
        if (gerente == null) {
            throw new RuntimeException("Gerente não encontrado com o CPF: " + CPF);
        }
        return gerente;
    }

    public Gerente buscarGerentePorEndereco(String endereco) {
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço do gerente não pode ser vazio.");
        }
        Gerente gerente = gerenteRepository.findByEndereco(endereco);
        if (gerente == null) {
            throw new RuntimeException("Gerente não encontrado com o endereço: " + endereco);
        }
        return gerente;
    }

    public Gerente findByLoginAndSenha(String login, String senha) {
        try {
            Gerente gerente = gerenteRepository.findByLogin(login);
            if (gerente != null && gerente.getSenha().equals(senha)) {
                return gerente;
            } else {
                throw new RuntimeException("Login ou senha inválidos");
            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a consulta por login e senha: " + e.getMessage());
        }
    }
}
