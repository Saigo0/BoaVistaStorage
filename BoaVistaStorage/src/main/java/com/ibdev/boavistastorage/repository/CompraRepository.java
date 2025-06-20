package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Cliente;
import com.ibdev.boavistastorage.entity.Compra;
import com.ibdev.boavistastorage.entity.ItemCompra;
import com.ibdev.boavistastorage.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;


import java.util.List;

public class CompraRepository {
    private EntityManager em;
    private ItemCompraRepository itemCompraRepository;

    public CompraRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Compra compra) {
        try {
            em.getTransaction().begin();
            em.persist(compra);
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Fornecedor já cadastrado com os mesmos dados.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar a compra: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select c from Compra c", Compra.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Compra findById(Long id) {
        try {
            return em.find(Compra.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar compra por ID: " + e.getMessage());
        }
    }

    public void findByDataCompra(String dataCompra) {
        try {
            em.createQuery("select c from Compra c where c.dataCompra = :dataCompra", Compra.class)
                    .setParameter("dataCompra", dataCompra)
                    .getResultList()
                    .forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar compra por data: " + e.getMessage());
        }
    }

    public Compra findByFornecedor(String fornecedor) {
        try {
            return em.createQuery("select c from Compra c where c.fornecedor.nome = :fornecedor", Compra.class)
                    .setParameter("fornecedor", fornecedor)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar compra por fornecedor: " + e.getMessage());
        }
    }

    public Compra findByGerente(String gerente) {
        try {
            return em.createQuery("select c from Compra c where c.gerente.nome = :gerente", Compra.class)
                    .setParameter("gerente", gerente)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar compra por gerente: " + e.getMessage());
        }
    }

    public Compra findByItemCompra(List<ItemCompra> itensCompra) {
        try {
            return em.createQuery("select c from Compra c join c.itensCompra ic where ic.compra = :itensCompra", Compra.class)
                    .setParameter("itensCompra", itensCompra)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar compra por item: " + e.getMessage());
        }
    }

    public Compra findByValorTotalCompra(double valorTotalCompra) {
        try {
            return em.createQuery("select c from Compra c where c.valorTotalCompra = :valorTotalCompra", Compra.class)
                    .setParameter("valorTotalCompra", valorTotalCompra)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar compra por valor total: " + e.getMessage());
        }
    }

    public void update(Long idCompra, Compra compra) {
        try {
            em.getTransaction().begin();

            Compra compraDB = em.find(Compra.class, idCompra);

            if (compraDB != null) {
                compraDB.setDataCompra(compra.getDataCompra());
                compraDB.setFornecedor(compra.getFornecedor());
                compraDB.setGerente(compra.getGerente());
                if(compraDB.getItensCompra() != null && !compraDB.getItensCompra().isEmpty()) {
                    for (ItemCompra itemCompra : compraDB.getItensCompra()) {
                        if(itemCompra != null) {
                            compraDB.removeItensCompra(itemCompra);
                            itemCompraRepository.delete(itemCompra.getId());
                        }
                    }
                }
                if(compra.getItensCompra() != null && !compra.getItensCompra().isEmpty()) {
                    for (ItemCompra itemCompra : compra.getItensCompra()) {
                        if(itemCompra != null) {
                            itemCompra.setCompra(compra);
                            compra.addItensCompra(itemCompra);
                        }
                    }
                }
                compraDB.setValorTotalCompra(compra.getValorTotalCompra());
                compraDB.setFinanceiro(compra.getFinanceiro());
            } else {
                throw new RuntimeException("Erro ao realizar a consulta por ID.");
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao realizar a consulta por ID." + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Compra.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar a compra: " + e.getMessage());
        }
    }
}
