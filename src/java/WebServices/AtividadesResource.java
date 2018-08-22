/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Beans.Atividade;
import DAO.AtividadeDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author guilh
 */
@Path("atividades")
public class AtividadesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AtividadesResource
     */
    public AtividadesResource() {
    }

    /**
     * Retrieves representation of an instance of WebServices.AtividadesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarTodas() throws SQLException, ClassNotFoundException {
        AtividadeDAO atividadeDAO = new AtividadeDAO();
        Atividade atividade = new Atividade();
        List<Atividade> atividades = new ArrayList<>();
        atividades = atividadeDAO.buscarTodas();
        GenericEntity<List<Atividade>> lista = new GenericEntity<List<Atividade>>(atividades) {};
        return Response.status(Response.Status.OK).entity(lista).build();
    }

    /**
     * PUT method for updating or creating an instance of AtividadesResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
