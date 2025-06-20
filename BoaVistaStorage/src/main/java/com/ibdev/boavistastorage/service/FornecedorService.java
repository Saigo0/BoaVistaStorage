package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Fornecedor;
import com.ibdev.boavistastorage.repository.FornecedorRepository;
import java.util.List;

public class FornecedorService {
    private FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public boolean salvarFornecedor(String nome, String CNPJ, String telefone, String email, String endereco, String cidade) {
        if (nome == null || nome.isEmpty() || CNPJ == null || CNPJ.isEmpty() || telefone == null || telefone.isEmpty() ||
            email == null || email.isEmpty() || endereco == null || endereco.isEmpty() || cidade == null || cidade.isEmpty()) {
            throw new IllegalArgumentException ("Todos os campos devem ser preenchidos.");
        }

        Fornecedor fornecedor = new Fornecedor(nome, CNPJ, telefone, email, endereco, cidade);
        fornecedorRepository.create(fornecedor);
        return true;
    }

    public Fornecedor buscarFornecedorPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do fornecedor inválido.");
        }
        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o ID: " + id);
        }
        return fornecedor;
    }

    public Fornecedor buscarFornecedorPorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do fornecedor não pode ser vazio.");
        }
        Fornecedor fornecedor = fornecedorRepository.findByNome(nome);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o nome: " + nome);
        }
        return fornecedor;
    }

    public Fornecedor buscarFornecedorPorCNPJ(String CNPJ) {
        if (CNPJ == null || CNPJ.isEmpty()) {
            throw new IllegalArgumentException("CNPJ do fornecedor não pode ser vazio.");
        }
        Fornecedor fornecedor = fornecedorRepository.findByCNPJ(CNPJ);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o CNPJ: " + CNPJ);
        }
        return fornecedor;
    }

    public Fornecedor buscarFornecedorPorTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone do fornecedor não pode ser vazio.");
        }
        Fornecedor fornecedor = fornecedorRepository.findByTelefone(telefone);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o telefone: " + telefone);
        }
        return fornecedor;
    }

    public Fornecedor buscarFornecedorPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email do fornecedor não pode ser vazio.");
        }
        Fornecedor fornecedor = fornecedorRepository.findByEmail(email);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o email: " + email);
        }
        return fornecedor;
    }

    public Fornecedor buscarFornecedorPorEndereco(String endereco) {
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço do fornecedor não pode ser vazio.");
        }
        Fornecedor fornecedor = fornecedorRepository.findByEndereco(endereco);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o endereço: " + endereco);
        }
        return fornecedor;
    }

    public List<Fornecedor> buscarFornecedorPorCidade(String cidade) {
        if (cidade == null || cidade.isEmpty()) {
            throw new IllegalArgumentException("Cidade do fornecedor não pode ser vazia.");
        }
        List<Fornecedor> fornecedores = fornecedorRepository.findByCidade(cidade);
        if (fornecedores == null) {
            throw new RuntimeException("Nenhum fornecedor encontrado com a cidade: " + cidade);
        }
        return fornecedores;
    }

    public boolean atualizarFornecedor(Long id, Fornecedor fornecedor) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do fornecedor inválido.");
        }
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor não pode ser nulo.");
        }
        return fornecedorRepository.update(id, fornecedor);
    }

    public void listarTodosFornecedores() {
        fornecedorRepository.readAll();
    }

    public boolean deletarFornecedor(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do fornecedor inválido.");
        }
        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com o ID: " + id);
        }
        return fornecedorRepository.delete(id);
    }


}
