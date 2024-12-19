package ru.spbstu.telematics.java;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) {
        Field field = new Field(1000);
        Neighbor neighbor1 = new Neighbor(1, null);
        Neighbor neighbor2 = new Neighbor(2, neighbor1);
        neighbor1.setM_other( neighbor2);

        Thread thread1 = new Thread(neighbor1);
        Thread thread2 = new Thread(neighbor2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nИтоги\nСосед " + neighbor1.m_id + " собрал " + neighbor1.m_progress+" ягод");
        System.out.println("Сосед " + neighbor2.m_id + " собрал " + neighbor2.m_progress+" ягод");
    }
}