package org.uabc.ic.Modelo;

public class Gato extends Tamagotchi implements TamagotchiActions {
    private boolean ronroneando;

    public Gato(String nombre, int hambre, int salud, int felicidad, int energia, boolean ronroneando) {
        super(nombre, hambre, salud, felicidad, energia);
        this.ronroneando = ronroneando;
    }

    public Gato() {
        super("Andres", 0, 10, 5, 5);
    }

    @Override
    public void comer(int alimento) {
       System.out.println("Comiendo" + alimento);
        hambre--;
        energia++;
        
    }

    @Override
    public int dormir(int horasSueno) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int jugar(int juego) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean morir() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int sanar() {
        // TODO Auto-generated method stub
        return 0;
    }

    



    
}
