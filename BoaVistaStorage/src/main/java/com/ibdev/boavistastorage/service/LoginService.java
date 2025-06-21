package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Funcionario;

public class LoginService {
    private final AtendenteService atendenteService;
    private final GerenteService gerenteService;

    public LoginService(AtendenteService atendenteService, GerenteService gerenteService) {
        this.atendenteService = atendenteService;
        this.gerenteService = gerenteService;
    }

    public Funcionario atutenticar(String login, String senha) {
        Funcionario funcionarioLogado = atendenteService.findByLoginAndSenha(login, senha);

        if (funcionarioLogado != null) {
            return funcionarioLogado;
        }

        return funcionarioLogado = gerenteService.findByLoginAndSenha(login, senha);
    }

}
