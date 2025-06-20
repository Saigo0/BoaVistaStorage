package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.repository.GerenteRepository;

public class GerenteService {
    private final GerenteRepository gerenteRepository;

    public GerenteService(GerenteRepository gerenteRepository) {
        this.gerenteRepository = gerenteRepository;
    }

    public Gerente findByLoginAndSenha(String login, String senha) {
        Gerente gerente = gerenteRepository.findByLogin(login);
        if (gerente != null && gerente.getSenha().equals(senha)) {
            return gerente;
        } else {
            throw new RuntimeException("Login ou senha inválidos");
        }
    }
}
