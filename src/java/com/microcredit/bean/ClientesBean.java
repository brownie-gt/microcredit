package com.microcredit.bean;

import com.microcredit.bll.JPA;
import com.microcredit.entity.Cliente;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "dtClientes")
@SessionScoped
public class ClientesBean {

    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    private EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(ClientesBean.class);
    public ClientesBean() {
    }
    
    @PostConstruct
    public void init(){
        em = JPA.getEntityManager();
        em.getTransaction().begin();
        clientes = em.createNamedQuery("Cliente.findAll", Cliente.class).getResultList();
        em.close();
        logger.debug("init()");
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }
    
}
