package com.api.UDEE.controller;

import com.api.UDEE.AbstractController;
import com.api.UDEE.domain.Brand;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.service.BrandService;
import com.api.UDEE.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static com.api.UDEE.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BrandControllerTest extends AbstractController {

     BrandService brandService;
     UsuarioService usuarioService;
     BrandController brandController;
     Authentication auth;
     Usuario user= aUsuarioEmp();

    private static List<Brand> EMPTY_LIST = Collections.emptyList();
    private static List<Brand> BRAND_LIST =  List.of(Brand.builder().id(1).name("nokia").build());

    @BeforeEach
    public void setUp() {
        brandService= mock(BrandService.class);
        brandController = new BrandController(brandService,usuarioService);
        auth= mock(Authentication.class);
    }

    
    @Test
    public void newBrandOk()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(brandService.newBrand(aBrand())).thenReturn(aBrand());

        ResponseEntity responseEntity = brandController.newBrand(aBrand());

        assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
    }
/*
    private void validateRol_IsEmployee() {

        when(auth.getPrincipal()).thenReturn(Usuario.builder().typeUser(TypeUser.EMPLOYEE).build());

        //when(Usuario.builder().typeUser(TypeUser.EMPLOYEE))
        assertEquals(user.getTypeUser().getName(),"EMPLOYEE");
        /*
        Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
        if(user.getTypeUser().getName().equals(EMPLOYEE)){

    }

    private void validateRol_IsClient() {

        when(auth.getPrincipal()).thenReturn(Usuario.builder().typeUser(TypeUser.EMPLOYEE).build());
        //when(Usuario.builder().typeUser(TypeUser.EMPLOYEE))
        assertEquals(user.getTypeUser().getName(),"CLIENT");
    }

*/
/*

        Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
        if(user.getTypeUser().getName().equals(EMPLOYEE)){
            isEmployee= true;
        }
        return isEmployee;
    }

    Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
    if(user.getTypeUser().getName().equals(EMPLOYEE)){
            isEmployee= true;
        }


*/
    @Test
    public void testAllBrandsHttpStatus200() {

        //given
        Pageable pageable = PageRequest.of(1, 10);
        Page<Brand> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(BRAND_LIST);
        when(brandService.allBrands(pageable)).thenReturn(mockedPage);

        //Then
        ResponseEntity<List<Brand>> response = brandController.allBrands(pageable);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100L, Long.parseLong(response.getHeaders().get("X-Total-Count").get(0)) );
        assertEquals(10, Integer.parseInt(response.getHeaders().get("X-Total-Pages").get(0)) );
        assertEquals(BRAND_LIST, response.getBody());
    }

    @Test
    public void testAllBrandsNoContent() {
        //given
        Pageable pageable = PageRequest.of(50, 10);
        Page<Brand> mockedPage = mock(Page.class);
        when(mockedPage.getContent()).thenReturn(EMPTY_LIST);
        when(brandService.allBrands(pageable)).thenReturn(mockedPage);

        //Then
        ResponseEntity<List<Brand>> response = brandController.allBrands(pageable);

        //Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testBrandById() throws AddressNotExistsException {

        when(brandService.getBrandById(1)).thenReturn(aBrand());

        ResponseEntity<Brand> response = brandController.brandByCode(1);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(aBrand().getId(), response.getBody().getId());
    }
}
