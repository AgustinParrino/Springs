package com.parrino.CRUD.controlador;
import com.parrino.CRUD.modelo.Cliente;
import com.parrino.CRUD.servicio.IClienteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.util.List;

@Component
@Data
@ViewScoped
@Named
public class IndexControlador implements Serializable {
@Autowired
IClienteServicio clienteServicio;
    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    private static final Logger logger =
            LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct //constructor que se va a utilizar despues
    public void init() {
        cargarDatos();
    }

    public void cargarDatos() {
        this.clientes = this.clienteServicio.listarClientes();
        this.clientes.forEach(cliente -> logger.info(cliente.toString()));
        //lo que estamos diciendo es que  vamos a listar por cada cliente
    }

    public void agregarCliente() {
        this.clienteSeleccionado = new Cliente();
    } //Lo llamamos en el Index que esta en la linea 19 al 23

    public void guardarCliente() {
        if (!validarNombre(clienteSeleccionado.getNombre()) || !validarNombre(clienteSeleccionado.getApellido())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre y apellido solo deben contener letras."));
            return;
        }

        logger.info("Cliente A guardar: " + this.clienteSeleccionado);
        if (this.clienteSeleccionado.getId() == null) {
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            this.clientes.add(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Agregado"));
        } else {
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Editado"));
        }

        PrimeFaces.current().executeScript("PF('ventanaModalCliente').hide()");
        PrimeFaces.current().ajax().update("forma-clientes:mensajes", "forma-clientes:clientes-tabla");
        this.clienteSeleccionado = null;
    }


    public void eliminarCliente(){
        logger.info("Cliente a eliminar: " + clienteSeleccionado);
        this.clienteServicio.eliminarCliente(this.clienteSeleccionado);
        this.clientes.remove(clienteSeleccionado);
        this.clienteSeleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Eliminado"));
        PrimeFaces.current().ajax().update("forma-clientes:mensajes", "forma-clientes:clientes-tabla");
    }

    private boolean validarNombre(String texto) {
        return texto != null && texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*");
    }

}
