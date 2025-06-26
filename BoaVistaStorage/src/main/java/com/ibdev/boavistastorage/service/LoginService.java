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
        Funcionario funcionarioLogado = null;
        if(atendenteService.findByLoginAndSenha(login, senha) != null){
            funcionarioLogado = atendenteService.findByLoginAndSenha(login, senha);
        }
        if(gerenteService.findByLoginAndSenha(login, senha) != null){
            funcionarioLogado = gerenteService.findByLoginAndSenha(login, senha);
        }

        return funcionarioLogado;
    }

}
