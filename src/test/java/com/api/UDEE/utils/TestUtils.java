package com.api.UDEE.utils;

import com.api.UDEE.domain.Model;
import com.api.UDEE.domain.Rate;
import com.api.UDEE.domain.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class TestUtils {

    public static String aRateJSON() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aRate());
    }

    public static Rate aRate(){
        Rate r= new Rate();
        r.setId(1);
        r.setPrice(350.5F);
        return r;
    }

    public static Page<Rate> aRatePage(){
        return new PageImpl<>(List.of(aRate()));
    }

/*
    public static String aModelJSON() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();

        return gson.toJson(aModel());
    }
/*
    public static Model aModel(){
        Model m= new Model();
        m.setId(1);
        m.setName("a10");
        return m;
    }
*/
    public static Pageable aPageable(){
        return PageRequest.of(0,10);
    }

    /*
    public static Page<Usuario> aUsuarioPage(){
        return new PageImpl<>(List.of(aUsuario()));
    }
    //aClientPage
*/
   /* public static Usuario aUsuario(){
        Usuario u = new Usuario();
        u.setId(1);
        u.setUsername("nico");
        u.setPassword("1234");
        return u;
    }*/
/*
    public static Client aClient() {
        Client c = new Client();
        c.setEmail("nicolasroldan31@gmail.com");
        c.setId(1);
        c.setName("nicolas");
        c.setLast_name("roldan");
        return c;
    }
    /*
    public static Persona aPersona() {
        Persona p = new Persona();
        p.setApellido("apellido");
        p.setNombre("nombre");
        p.setDireccion("calle falsa 123");
        p.setDni("dni");
        p.setVehiculoList(new ArrayList<>());
        return p;
    }
     */
}
