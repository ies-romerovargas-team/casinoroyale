package com.company;

import java.security.InvalidParameterException;

public class Carta {
    int numero;
    int palo;

    public Carta() {
    }

    public Carta(int numero, int palo) {
        this.numero = numero;
        this.palo = palo;
    }

    public Carta(int id) {
        if (id >= 1 && id <= 40) {
            if (id % 10 == 0) {
                this.palo = id / 10 - 1;
                this.numero = 10;
            } else {
                this.palo = id / 10;
                this.numero = id % 10;
            }

        } else {
            throw new InvalidParameterException("Valor no válido");
        }
    }

    public int getNumero() {
        return this.numero;
    }

    public int getPalo() {
        return this.palo;
    }

    public String nombreNumero() {
        String[] nombres = new String[]{"", "A", "2", "3", "4", "5", "6", "7", "J", "Q", "K"};
        return nombres[this.numero];
    }

    public String nombrePalo() {
        String[] nombres = new String[]{"oros", "copas", "espadas", "bastos"};
        return nombres[this.palo];
    }

    public String nombreCarta() {
        String var10000 = this.nombreNumero();
        return var10000 + " de " + this.nombrePalo();
    }

    public int valorTute() {
        int[] valor = new int[]{0, 11, 0, 10, 0, 0, 0, 0, 2, 3, 4};
        return valor[this.numero];
    }

    public int valorMus() {
        int[] valor = new int[]{0, 1, 1, 10, 4, 5, 6, 7, 10, 10, 10};
        return valor[this.numero];
    }

    public double valor7yMedia() {
        double[] valor = new double[]{0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 0.5D, 0.5D, 0.5D};
        return valor[this.numero];
    }

}