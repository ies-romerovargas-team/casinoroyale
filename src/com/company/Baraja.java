package com.company;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Baraja {
    List<Carta> listaCartas;

    public Baraja() {
        this.listaCartas = new ArrayList();
    }

    public Baraja(int tipoBaraja) {
        this.listaCartas = new ArrayList();
        if (tipoBaraja != 1 && tipoBaraja != 2) {
            throw new InvalidParameterException("Tipo no válido");
        } else {
            for(int i = 1; i <= 40; ++i) {
                Carta a = new Carta(i);
                this.listaCartas.add(a);
                if (tipoBaraja == 2) {
                    this.listaCartas.add(a);
                }
            }
        }
    }

    public Baraja(int tipoBaraja, boolean barajar) {
        Baraja mazo = new Baraja(tipoBaraja);
        mazo.barajar();
    }

    public void barajar() {
        Random r = new Random();
        ArrayList mazo1 = new ArrayList();

        while(this.listaCartas.size() != 0) {
            int azar = r.nextInt(this.listaCartas.size());
            mazo1.add((Carta)this.listaCartas.get(azar));
            this.listaCartas.remove(azar);
        }

        this.listaCartas.addAll(mazo1);
    }

    public void cortar(int numeroCartas) {
        for(int i = 0; i < numeroCartas; ++i) {
            new Carta();
            Carta a = (Carta)this.listaCartas.get(0);
            this.listaCartas.remove(0);
            this.listaCartas.add(a);
        }
    }

    public Carta robar() {
        new Carta();
        Carta a = (Carta)this.listaCartas.get(0);
        this.listaCartas.remove(0);
        return a;
    }

    public void insertaCartaFinal(int idCarta) {
        Carta a = new Carta(idCarta);
        this.listaCartas.add(a);
    }

    public void insertaCartaPrincipio(int idCarta) {
        Carta a = new Carta(idCarta);
        this.listaCartas.add(0, a);
    }

    public void insertaCartaFinal(Carta carta) {
        this.listaCartas.add(carta);
    }

    public void insertaCartaPrincipio(Carta carta) {
        this.listaCartas.add(0, carta);
    }

    public int numeroCartas() {
        return this.listaCartas.size();
    }

    public boolean vacia() {
        return this.listaCartas.size() == 0;
    }

    public Carta leeBaraja(int orden) {
        new Carta();
        Carta a = (Carta)this.listaCartas.get(orden);
        return a;
    }
}
