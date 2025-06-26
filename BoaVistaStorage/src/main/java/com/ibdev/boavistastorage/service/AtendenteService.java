package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Atendente;
import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.repository.AtendenteRepository;
import jakarta.persistence.NoResultException;

public class AtendenteService {
    private AtendenteRepository atendenteRepository;

    public AtendenteService(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }

    public void salvarAtendente(String nome, String CPF, String telefone, String email, String login, String senha) {
        if (nome == null || nome.isEmpty() || CPF == null || CPF.isEmpty() || telefone == null || telefone.isEmpty() ||
                email == null || email.isEmpty() || login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }

        Atendente atendente = new Atendente(nome, CPF, telefone, email, login, senha);
        atendenteRepository.create(atendente);

    }

    public Atendente buscarAtendentePorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do atendente inválido.");
        }
        Atendente atendente = atendenteRepository.findById(id);
        if (atendente == null) {
            throw new RuntimeException("Atendente não encontrado com o ID: " + id);
        }
        return atendente;
    }

    public Atendente buscarAtendentePorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do atendente não pode ser vazio.");
        }
        Atendente atendente = atendenteRepository.findByNome(nome);
        if (atendente == null) {
            throw new RuntimeException("Atendente não encontrado com o nome: " + nome);
        }
        return atendente;
    }

    public Atendente buscarAtendentePorTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone do atendente não pode ser vazio.");
        }
        if (!telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone deve conter 10 ou 11 dígitos numéricos.");
        }
        Atendente atendente = atendenteRepository.findByTelefone(telefone);
        if (atendente == null) {
            throw new RuntimeException("Atendente não encontrado com o telefone: " + telefone);
        }
        return atendente;
    }

    public Atendente buscarAtendentePorCPF(String CPF) {
        if (CPF == null || CPF.isEmpty()) {
            throw new IllegalArgumentException("CPF do atendente não pode ser vazio.");
        }
        if (!CPF.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos numéricos.");
        }
        Atendente atendente = atendenteRepository.findByCPF(CPF);
        if (atendente == null) {
            throw new RuntimeException("Atendente não encontrado com o CPF: " + CPF);
        }
        return atendente;
    }

    public Atendente buscarAtendentePorEndereco(String endereco) {
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço do atendente não pode ser vazio.");
        }
        Atendente atendente = atendenteRepository.findByEndereco(endereco);
        if (atendente == null) {
            throw new RuntimeException("Atendente não encontrado com o endereço: " + endereco);
        }
        return atendente;
    }

    public Atendente findByLoginAndSenha(String login, String senha) {
        try {
            return atendenteRepository.findByLoginAndSenha(login,senha);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a consulta por login e senha: " + e.getMessage());
        }
    }
}
