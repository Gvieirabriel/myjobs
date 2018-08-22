/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Beans.Funcionario;
import DAO.AtividadeDAO;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author guilh
 */
@Path("aviso")
public class AvisoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AvisoResource
     */
    public AvisoResource() {
    }

    /**
     * Retrieves representation of an instance of WebServices.AvisoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AvisoResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void insereFuncionario(Funcionario f) throws SQLException, ClassNotFoundException {
        AtividadeDAO a = new AtividadeDAO();
        a.insereAviso(f.getDepartamento().getIdDepartamento());
    }
}
