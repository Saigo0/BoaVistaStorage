package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.repository.GerenteRepository;
import jakarta.persistence.NoResultException;

public class GerenteService {
    private final GerenteRepository gerenteRepository;

    public GerenteService(GerenteRepository gerenteRepository) {
        this.gerenteRepository = gerenteRepository;
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
