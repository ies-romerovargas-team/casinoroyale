package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String ANSI_RED = "\u001b[31m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001b[103m";

    public static void main(String[] args) {
        Random r = new Random();
        String[] menu = {"Siete y media", "Brisca", ""};
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        int opc;
        while (!salir) {
            imprimeMenu(menu, "CASINO ROYAL", ANSI_BLUE);
            try {
                System.out.print("Eliga opción: ");
                opc = sc.nextInt();
                sc.nextLine(); // buffer
                boolean continua;
                Baraja baraja;
                List<Carta> CartasJugador = new ArrayList();
                List<Carta> CartasOrdenador = new ArrayList();
                switch (opc) {
                    case 0:
                        salir = true;
                        break;
                    case 1:
                        // SIETE Y MEDIA
                        baraja = new Baraja(1);
                        baraja.barajar();
                        String opcion = "s";
                        double punt1 = 0.0D;

                        double punt2;
                        for(punt2 = 0.0D; !opcion.equals("n"); opcion = sc.nextLine()) {
                            punt1 = 0.0D;
                            CartasJugador.add(baraja.robar());
                            PintaMesa(CartasJugador, CartasOrdenador);

                            for(int i = 0; i < CartasJugador.size(); ++i) {
                                punt1 += (CartasJugador.get(i)).valor7yMedia();
                            }

                            if (punt1 >= 7.5D) {
                                break;
                            }

                            System.out.println("¿quiere más cartas? (s/n)");
                        }

                        continua = true;

                        while(continua) {
                            punt2 = 0.0D;
                            CartasOrdenador.add(baraja.robar());
                            PintaMesa(CartasJugador, CartasOrdenador);

                            for(int i = 0; i < CartasOrdenador.size(); ++i) {
                                punt2 += (CartasOrdenador.get(i)).valor7yMedia();
                            }

                            if (punt2 < 7.5D) {
                                if (punt2 <= punt1 && punt1 <= 7.5D) {
                                    System.out.println("                                             Pidiendo otra carta...");
                                    retardo();
                                } else {
                                    System.out.println("                                             Me planto");
                                    continua = false;
                                }
                            }

                            if (punt2 == 7.5D) {
                                System.out.println("                                             Tengo 7 y media");
                                continua = false;
                            }

                            if (punt2 > 7.5D) {
                                System.out.println("                                             Me he pasado");
                                continua = false;
                            }
                        }

                        if ((punt1 < punt2 || punt1 >= 7.5D) && punt2 <= 7.5D) {
                            System.out.println("HAS PERDIDO");
                        } else {
                            System.out.println("HAS GANADO");
                        }
                    case 2:
                        // BRISCA

                        baraja = new Baraja(1);
                        baraja.barajar();
                        boolean turno;
                        // Reparto inicial
                        CartasJugador.clear();
                        CartasOrdenador.clear();
                        // Listas para guardar las bazas ganadas
                        List<Carta> BazasJugador = new ArrayList();
                        List<Carta> BazasOrdenador = new ArrayList();
                        Carta triunfo = baraja.robar();
                        for (int i = 0; i < 3; i++) {
                            CartasJugador.add(baraja.robar());
                            CartasOrdenador.add(baraja.robar());
                        }
                        // Comienza turno azar
                        turno = r.nextBoolean();
                        boolean FinJuego = false;
                        while(!FinJuego)
                        {
                            pintaMesaBrisca(CartasJugador, CartasOrdenador, triunfo, turno, null, null, BazasJugador, BazasOrdenador,baraja.numeroCartas());
                            // juegos por turno
                            if(turno) {
                                // JUGADA DEL JUGADOR
                                System.out.println();
                                System.out.print("Elige Jugada (1-"+ CartasJugador.size() +")");
                                int op = sc.nextInt();
                                while (op > CartasJugador.size())
                                {
                                    pintaMesaBrisca(CartasJugador, CartasOrdenador, triunfo, turno,null,null, BazasJugador, BazasOrdenador, baraja.numeroCartas());
                                    System.out.println("Opción no valida");
                                    System.out.println("Elige Jugada (1-"+ CartasJugador.size() +")");
                                    op = sc.nextInt();
                                }
                                Carta elegida = CartasJugador.get(op-1);
                                CartasJugador.remove(elegida);
                                pintaMesaBrisca(CartasJugador, CartasOrdenador, triunfo,turno, elegida, null, BazasJugador, BazasOrdenador, baraja.numeroCartas());
                                retardo();
                                Carta ia = IAOrdenador(CartasOrdenador, elegida, triunfo);
                                CartasOrdenador.remove(ia);
                                pintaMesaBrisca(CartasJugador, CartasOrdenador, triunfo, turno, elegida, ia, BazasJugador, BazasOrdenador, baraja.numeroCartas());
                                retardo();
                                // Ver quien gana
                                //mismo palo
                                if(elegida.palo == ia.palo)
                                {
                                    if(elegida.valorTute() >= ia.valorTute())
                                    {
                                        BazasJugador.add(elegida);
                                        BazasJugador.add(ia);
                                    }
                                    else
                                    {
                                        BazasOrdenador.add(elegida);
                                        BazasOrdenador.add(ia);
                                        turno = false;
                                    }
                                }
                                else
                                { // distinto palo
                                    if (ia.palo == triunfo.palo)
                                    {
                                        BazasOrdenador.add(elegida);
                                        BazasOrdenador.add(ia);
                                        turno = false;
                                    }
                                    else
                                    {
                                        BazasJugador.add(elegida);
                                        BazasJugador.add(ia);
                                    }
                                }
                            }
                            else
                            {
                                // JUGADA DEL ORDENADOR
                                System.out.println();
                                retardo();
                                Carta ia = IAOrdenador(CartasOrdenador, null, triunfo);
                                CartasOrdenador.remove(ia);
                                pintaMesaBrisca(CartasJugador, CartasOrdenador, triunfo, turno, ia, null, BazasJugador, BazasOrdenador, baraja.numeroCartas());
                                System.out.print("Elige Jugada (1-"+ CartasJugador.size() +")");
                                int op = sc.nextInt();
                                while (op > CartasJugador.size())
                                {
                                    pintaMesaBrisca(CartasJugador, CartasOrdenador, triunfo, turno, ia,null, BazasJugador, BazasOrdenador, baraja.numeroCartas());
                                    System.out.println("Opción no valida");
                                    System.out.println("Elige Jugada (1-"+ CartasJugador.size() +")");
                                    op = sc.nextInt();
                                }
                                Carta elegida = CartasJugador.get(op - 1);
                                CartasJugador.remove(elegida);
                                pintaMesaBrisca(CartasJugador, CartasOrdenador, triunfo, turno, ia, elegida, BazasJugador, BazasOrdenador, baraja.numeroCartas());
                                retardo();
                                // Ver quien gana
                                if(elegida.palo == ia.palo) //mismo palo
                                {
                                    if(ia.valorTute() >= elegida.valorTute()) {
                                        BazasOrdenador.add(elegida);
                                        BazasOrdenador.add(ia);
                                    }
                                    else
                                    {
                                        BazasJugador.add(elegida);
                                        BazasJugador.add(ia);
                                        turno = true;
                                    }
                                }
                                else // distinto palo
                                {
                                    if (elegida.palo == triunfo.palo)
                                    {
                                        BazasJugador.add(elegida);
                                        BazasJugador.add(ia);
                                        turno = true;
                                    }
                                    else
                                    {
                                        BazasOrdenador.add(elegida);
                                        BazasOrdenador.add(ia);
                                    }
                                }
                            }
                            if(baraja.numeroCartas() > 1)
                            {
                                CartasJugador.add(baraja.robar());
                                CartasOrdenador.add(baraja.robar());
                            }
                            else if(baraja.numeroCartas() == 1)
                            {
                                if(turno)
                                {
                                    CartasJugador.add(baraja.robar());
                                    CartasOrdenador.add(triunfo);
                                }
                                else
                                {
                                    CartasJugador.add(triunfo);
                                    CartasOrdenador.add(baraja.robar());
                                }
                            }
                            else
                            {
                                FinJuego = true;
                            }
                        }
                        System.out.println("Fin de Juego!!");
                        break;
                }
            } catch (Exception exc)
            {
                System.out.println(exc.getMessage());
            }
        }
    }

    private static Carta IAOrdenador(List<Carta> cartasOrdenador, Carta elegida, Carta triunfo)
    {
        int candidata = 0;  // preseleccion carta
        if(elegida == null){
            // Posibilidad de buscar entre las cartas ya jugadas (bazasJugador, BazasOrdenador) para ver cuántos puntos están en juego
            // En función de la previsión, jugar con la mayor, media o menor
            // Complicado
            // Por ahora, intentar ganar todas las bazas
            candidata = 3;  // preseleccion carta inexistente
            for (int i = 0; i < cartasOrdenador.size(); i++) {
                if(cartasOrdenador.get(i).palo == triunfo.palo)
                {
                    candidata = i;
                }
            }
            if(candidata!=3) return cartasOrdenador.get(candidata);
            // no hay del mismo palo, elegir la mayor
            int j = cartasOrdenador.size() - 1;
            candidata = j;
            for (int i = 0; i < cartasOrdenador.size() - 1; i++) {
                if(cartasOrdenador.get(i).valorTute() > cartasOrdenador.get(j).valorTute())
                {
                    candidata = i;
                    j = candidata;
                }
            }
        }
        else
        {
            // Jugada de respuesta al jugador
            // Intentar ganar siempre <= no es la mejor jugada
            candidata = 3;
            // buscar una mayor mismo palo
            for (int i = 0; i < cartasOrdenador.size(); i++) {
                if(cartasOrdenador.get(i).palo == elegida.palo && cartasOrdenador.get(i).valorTute() > elegida.valorTute())
                {
                    candidata = i;
                }
            }
            if(candidata!=3) return cartasOrdenador.get(candidata);
            // si la elegida tiene puntos, buscar una del palo del triunfo, la de menor puntuación
            for (int i = 0; i < cartasOrdenador.size(); i++) {
                if(cartasOrdenador.get(i).palo == triunfo.palo)
                {
                    if(candidata==3)
                    {
                        candidata = i;
                    }
                    else
                    {
                        if(cartasOrdenador.get(candidata).valorTute() < cartasOrdenador.get(i).valorTute())
                        {
                            candidata = i;
                        }
                    }
                }
            }
            if(candidata!=3) return cartasOrdenador.get(candidata);
            // jugamos la de menos puntos
            int j = cartasOrdenador.size() - 1;
            candidata = 0;
            for (int i = 0; i < cartasOrdenador.size(); i++) {
                if(cartasOrdenador.get(i).valorTute() <= cartasOrdenador.get(j).valorTute())
                {
                    candidata = i;
                    j = candidata;
                }
            }
        }
        return cartasOrdenador.get(candidata);
    }

    private static void pintaMesaBrisca(List<Carta> j, List<Carta> o, Carta t, boolean tr)
    {
        pintaMesaBrisca(j, o, t, tr, null, null, null, null, 0);


    }
    private static void pintaMesaBrisca(List<Carta> j, List<Carta> o, Carta t, boolean tr, Carta jugada1, Carta jugada2, List<Carta> b1, List<Carta> b2, int NumCartas)
    {
        String[] poker = new String[]{"♦", "♥", "♠", "♣"};
        int cont = 0;
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
        if(tr){
            System.out.println("TURNO DEL JUGADOR");
        }
        else
        {
            System.out.println("TURNO DEL ORDENADOR");
        }
        pinta(" ",10);
        for (int i = 0; i < o.size(); i++) {
            System.out.print(ANSI_YELLOW_BACKGROUND);
            System.out.print((o.get(i)).nombreNumero());
            if ((o.get(i)).palo < 2) {
                System.out.print(ANSI_RED);
            }
            System.out.print(poker[(o.get(i)).palo]);
            System.out.print(ANSI_RESET);
            System.out.print("  ");
        }
        // bazas ganadas
        if(b2 != null && b2.size() > 0)
        {
            pinta(" ", 5);
            System.out.print(ANSI_WHITE_BACKGROUND + ANSI_RED);
            System.out.print("▓▓");
            System.out.print(ANSI_RESET);
            cont = 0;
            for (int i = 0; i < b2.size(); i++) {
                System.out.print((b2.get(i)).nombreNumero());
                System.out.print(poker[(b2.get(i)).palo]);
                cont = cont + b2.get(i).valorTute();
            }
            System.out.print(" " + cont + " puntos");
        }
        System.out.println();
        System.out.println();
        // mazo y triunfo
        if(t!=null) {
            System.out.print(ANSI_WHITE_BACKGROUND + ANSI_RED);
            System.out.print("▓▓");
            System.out.print(ANSI_RESET);
            System.out.print("  ");
            System.out.print(ANSI_YELLOW_BACKGROUND);
            System.out.print(t.nombreNumero());
            if (t.palo < 2) {
                System.out.print(ANSI_RED);
            }
            System.out.print(poker[t.palo]);
            System.out.print(ANSI_RESET);
        }
        // Jugada en juego
        if(jugada1!=null)
        {
            pinta(" ",5);
            System.out.print(ANSI_YELLOW_BACKGROUND);
            System.out.print(jugada1.nombreNumero());
            if (jugada1.palo < 2) {
                System.out.print(ANSI_RED);
            }
            System.out.print(poker[jugada1.palo]);
            System.out.print(ANSI_RESET);
        }
        if(jugada2!=null)
        {
            pinta(" ",2);
            System.out.print(ANSI_YELLOW_BACKGROUND);
            System.out.print(jugada2.nombreNumero());
            if (jugada2.palo < 2) {
                System.out.print(ANSI_RED);
            }
            System.out.print(poker[jugada2.palo]);
            System.out.print(ANSI_RESET);
        }
        System.out.println();
        System.out.println("Quedan " + NumCartas + " Cartas");
        //
        System.out.println();
        pinta(" ",10);
        for (int i = 0; i < j.size(); i++) {
            System.out.print(ANSI_YELLOW_BACKGROUND);
            System.out.print((j.get(i)).nombreNumero());
            if ((j.get(i)).palo < 2) {
                System.out.print(ANSI_RED);
            }
            System.out.print(poker[(j.get(i)).palo]);
            System.out.print(ANSI_RESET);
            System.out.print("  ");
        }
        // bazas ganadas
        if(b1 != null && b1.size() > 0)
        {
            pinta(" ", 5);
            System.out.print(ANSI_WHITE_BACKGROUND + ANSI_RED);
            System.out.print("▓▓");
            System.out.print(ANSI_RESET);
            cont = 0;
            for (int i = 0; i < b1.size(); i++) {
                System.out.print((b1.get(i)).nombreNumero());
                System.out.print(poker[(b1.get(i)).palo]);
                cont = cont + b1.get(i).valorTute();
            }
            System.out.print(" " + cont + " puntos");
        }
        System.out.println();
    }

    private static void retardo() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException var1) {
            System.out.println(var1.getMessage());
        }

    }

    private static void PintaMesa(List<Carta> cartasJuego1, List<Carta> cartasJuego2) {
        for(int i = 0; i < 50; ++i) {
            System.out.println();
        }


        String[] poker = new String[]{"♦", "♥", "♠", "♣"};
        System.out.println("JUGADOR:                                     ORDENADOR:");

        int i;
        for(i = 0; i < cartasJuego1.size(); ++i) {
            System.out.print(ANSI_YELLOW_BACKGROUND);
            System.out.print((cartasJuego1.get(i)).nombreNumero());
            if ((cartasJuego1.get(i)).palo < 2) {
                System.out.print(ANSI_RED);
            }

            System.out.print(poker[(cartasJuego1.get(i)).palo]);
            System.out.print(ANSI_RESET);
            System.out.print("  ");
        }

        int j;
        for(j = i * 4; j < 45; ++j) {
            System.out.print(" ");
        }

        for(i = 0; i < cartasJuego2.size(); ++i) {
            System.out.print(ANSI_YELLOW_BACKGROUND);
            System.out.print((cartasJuego2.get(i)).nombreNumero());
            if ((cartasJuego2.get(i)).palo < 2) {
                System.out.print(ANSI_RED);
            }

            System.out.print(poker[(cartasJuego2.get(i)).palo]);
            System.out.print(ANSI_RESET);
            System.out.print("  ");
        }

        System.out.println();
        double punt1 = 0.0D;
        double punt2 = 0.0D;

        for(i = 0; i < cartasJuego1.size(); ++i) {
            punt1 += (cartasJuego1.get(i)).valor7yMedia();
        }

        for(i = 0; i < cartasJuego2.size(); ++i) {
            punt2 += (cartasJuego2.get(i)).valor7yMedia();
        }

        String puntuacion = "Puntos: " + punt1;
        if (punt1 > 7.5D) {
            System.out.print(ANSI_RED);
        }

        System.out.print(puntuacion);
        System.out.print(ANSI_RESET);

        for(j = puntuacion.length(); j < 45; ++j) {
            System.out.print(" ");
        }

        System.out.println("Puntos: " + punt2);
    }

    public static void imprimeMenu(String[] opciones, String titulo, String color) {
        int Max = 16; // Ancho mínimo
        Max = titulo.length();
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].length() > Max) Max = opciones[i].length();
        }
        if(Max%2 != 0) Max++;
        System.out.print(color + "╔");
        pinta("═", Max + 4);
        System.out.println("╗");
        System.out.print("║");
        pinta(" ", Max/2 - titulo.length()/2 + 2);
        System.out.print(ANSI_BLACK + ANSI_WHITE_BACKGROUND + titulo + ANSI_RESET);
        pinta(" ", Max/2 - titulo.length()/2 + 2);
        System.out.println(color + "║");
        System.out.print("╠");
        pinta("═", Max + 4);
        System.out.println("╣");
        for (int i = 1; i <= opciones.length; i++) {
            System.out.print(color + "║" + ANSI_RESET);
            if (i < 10) System.out.print(" ");
            System.out.print(i + ". " + opciones[i - 1]);
            pinta(" ", Max - opciones[i - 1].length());
            System.out.print(color + "║" + ANSI_RESET);
            System.out.println();
        }
        System.out.print(color + "╠");
        pinta("═", Max + 4);
        System.out.println("╣");
        System.out.print("║ " + ANSI_RESET + "0. Salir");
        pinta(" ", Max - 5);
        System.out.println(color + "║");
        System.out.print("╚");
        pinta("═", Max + 4);
        System.out.println("╝" + ANSI_RESET);
    }

    private static void pinta(String s, int i)
    {
        for (int j = 0; j < i; j++) {
            System.out.print(s);
        }
    }
}

