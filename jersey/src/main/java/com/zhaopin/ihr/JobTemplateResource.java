package com.zhaopin.ihr;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by guojun.wang on 2017/3/9.
 */
@Path("jobtemplate")
public class JobTemplateResource {
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String sayhello() {
//        return "hello";
//    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String detail(@PathParam("id") String id){
        //@PathParam("id") String id
        //String id = "aaa";
        return "this is detail for " + id;
    }

    @GET
    @Path("/jobs")
    @Produces(MediaType.TEXT_PLAIN)
    public String search(String keyword){
            return "this is search result for " + keyword;
    }
}
