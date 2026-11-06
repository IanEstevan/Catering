package ar.edu.unahur.obj2.catering.carta;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.propuestas.Servicio;

public class Carta {

    private final Map<String, Servicio> servicios = new HashMap<>();
    private final Map<String, Menu> menus = new HashMap<>();

    public Carta() {
        cargarServicios();
        cargarMenus();
    }

    public Servicio servicio(String nombre) {
        return servicios.get(nombre);
    }

    public Menu menu(String nombre) {
        return menus.get(nombre);
    }

    private void cargarServicios() {
        registrar(new Servicio("Mesa de entradas", 80_000));
        registrar(new Servicio("Parrillada completa", 240_000));
        registrar(new Servicio("Mesa dulce", 60_000));
        registrar(new Servicio("Barra de tragos", 150_000));
        registrar(new Servicio("DJ y sonido", 120_000));
        registrar(new Servicio("Brindis de bienvenida", 50_000));
    }

    private void cargarMenus() {
        Menu parrillero = new Menu("Menú Parrillero", 10);
        parrillero.agregar(servicio("Mesa de entradas"));
        parrillero.agregar(servicio("Parrillada completa"));
        parrillero.agregar(servicio("Mesa dulce"));
        registrar(parrillero);

        Menu packFiesta = new Menu("Pack Fiesta", 5);
        packFiesta.agregar(servicio("Barra de tragos"));
        packFiesta.agregar(servicio("DJ y sonido"));
        registrar(packFiesta);

        Menu eventoCompleto = new Menu("Evento Completo", 20);
        eventoCompleto.agregar(parrillero);
        eventoCompleto.agregar(packFiesta);
        eventoCompleto.agregar(servicio("Brindis de bienvenida"));
        registrar(eventoCompleto);
    }

    private void registrar(Servicio servicio) {
        servicios.put(servicio.descripcion(), servicio);
    }

    private void registrar(Menu menu) {
        menus.put(menu.descripcion(), menu);
    }
}