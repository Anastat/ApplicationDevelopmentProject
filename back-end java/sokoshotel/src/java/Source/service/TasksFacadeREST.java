/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Source.service;

import Source.Tasks;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Kasperi
 */
@Stateless
@Path("tasksrest")
public class TasksFacadeREST extends AbstractFacade<Tasks> {

    @PersistenceContext(unitName = "sokoshotelPU")
    private EntityManager em;

    public TasksFacadeREST() {
        super(Tasks.class);
    }

    @GET
    @Path("sortnew")
    @Produces(MediaType.TEXT_HTML)
    public String sortByNew(@PathParam("id") Integer id) {
        List<Tasks> results = em.createNamedQuery("Tasks.findAll", Tasks.class).getResultList();
        List<Tasks> valid = new ArrayList();
        JSONObject obj = new JSONObject();
        JSONObject answer = new JSONObject();
        JSONArray arr = new JSONArray();
        int n = 2;
        for (Tasks result : results) {
            if (result.getTaskStatus().getStatusName().equals("New") && result.getDepartment().getDepartmentID() == n) {
                valid.add(result);
            }
        }
        for (Tasks tasks : valid) {
            obj.put("id", tasks.getTaskID());
            obj.put("duedate", tasks.getDueDate());
            obj.put("duetime", tasks.getDueTime());
            obj.put("department", tasks.getDepartment().getDepartmentID());
            obj.put("title", tasks.getTitle());
            obj.put("place", tasks.getPlace().getPlaceID());
            obj.put("details", tasks.getDetils());
            obj.put("attachment", tasks.getAttachment());
            arr.put(obj);          
            answer.put("test", obj);
            obj = new JSONObject();           
        }
        return "<p>" + arr + "</p>";
    }

    public JSONObject sortByProcess(@PathParam("id") Integer id) {
        List<Tasks> results = em.createNamedQuery("Tasks.findAll", Tasks.class).getResultList();
        List<Tasks> valid = new ArrayList();
        JSONObject obj = new JSONObject();
        for (Tasks result : results) {
            if (result.getTaskStatus().getStatusName().equals("InProcess") && (result.getDepartment().getDepartmentID().intValue() == id)) {
                valid.add(result);
            }
        }
        obj.put("valid", valid);
        return obj;
    }

    public JSONObject sortDone(@PathParam("id") Integer id) {
        List<Tasks> results = em.createNamedQuery("Tasks.findAll", Tasks.class).getResultList();
        List<Tasks> valid = new ArrayList();
        JSONObject obj = new JSONObject();
        for (Tasks result : results) {
            if (result.getTaskStatus().getStatusName().equals("Done") && (result.getDepartment().getDepartmentID().intValue() == id)) {
                valid.add(result);
            }
        }
        obj.put("valid", valid);
        return obj;
    }

    public JSONObject sortByCanceled(@PathParam("id") Integer id) {
        List<Tasks> results = em.createNamedQuery("Tasks.findAll", Tasks.class).getResultList();
        List<Tasks> valid = new ArrayList();
        JSONObject obj = new JSONObject();
        for (Tasks result : results) {
            if (result.getTaskStatus().getStatusName().equals("Canceled") && (result.getDepartment().getDepartmentID().intValue() == id)) {
                valid.add(result);
            }
        }
        obj.put("valid", valid);
        return obj;
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Tasks entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Tasks entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Tasks find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tasks> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tasks> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
