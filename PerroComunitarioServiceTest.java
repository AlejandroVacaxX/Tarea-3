package com.tarea.mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tarea.mock.entidades.Perro;
import com.tarea.mock.excepciones.PerroNoEncontradoException;
import com.tarea.mock.repositorios.PerroRepository;
import com.tarea.mock.servicios.PerroComunitarioService;

public class PerroComunitarioServiceTest {

   public  PerroRepository mockRepository;
   public  PerroComunitarioService service;

    @BeforeEach
    public void inicializarPrueba(){
        // Mock del repositorio
        mockRepository = Mockito.mock(PerroRepository.class);
        // Servicio a probar
        service = new PerroComunitarioService(mockRepository);
    }

    @Test
    public void deberiaDevolverPerroCuandoElPerroExiste() { 
        String nombrePerro = "Fido";  
        Perro perromock = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre(nombrePerro)).thenReturn(perromock);
        // Verificación
        service.obtenerPerroPorNombre("Fido");    
           
    }
    
    @Test
    public void deberiaLanzarPerroNoEncontradoExceptioCuandoElPerroNoExiste() {        
        // Ejecución que lanza excepción
        String nombrePerro = "Rex";

        when(mockRepository.buscarPorNombre(nombrePerro)).thenReturn(null);
        assertThrows(PerroNoEncontradoException.class, () ->{
            service.obtenerPerroPorNombre("Rex");    
        });

    }
    
    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsNull() {
        // Ejecución que lanza excepción
        String nombrePerro = null;
        
        when(mockRepository.buscarPorNombre(nombrePerro)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            service.obtenerPerroPorNombre(null);   
        });
         
    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsVacio() {
        // Ejecución que lanza excepción
        String nombrePerro = " ";
        when(mockRepository.buscarPorNombre(nombrePerro)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            service.obtenerPerroPorNombre(null);
        });
    }

    @Test
    public void deberiaConsultarRepositorioUnaSolaVezCuandoElPerroExiste() {
        // Verificación
        String nombrePerro = "Fido";
        Perro perromock = new Perro("Fido", 3);

        when(mockRepository.buscarPorNombre(nombrePerro)).thenReturn(perromock);
        service.obtenerPerroPorNombre(nombrePerro);
        verify(mockRepository, times(1)).buscarPorNombre("Fido");
    }
}