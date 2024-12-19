package ru.spbstu.telematics.java;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldTest {

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testNoDeadlock() throws InterruptedException {
        Field field = new Field(1000);
        Neighbor neighbor1 = new Neighbor(1, null);
        Neighbor neighbor2 = new Neighbor(2, neighbor1);
        neighbor1.setM_other(neighbor2);

        Thread thread1 = new Thread(neighbor1);
        Thread thread2 = new Thread(neighbor2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Проверка, что оба потока завершились
        assertTrue(!thread1.isAlive() && !thread2.isAlive());
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testRaceCondition() throws InterruptedException {
        Field field = new Field(1000);
        Neighbor neighbor1 = new Neighbor(1, null);
        Neighbor neighbor2 = new Neighbor(2, neighbor1);
        neighbor1.setM_other(neighbor2);

        Thread thread1 = new Thread(neighbor1);
        Thread thread2 = new Thread(neighbor2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Проверка, что количество собранных ягод корректно
        int totalBerriesCollected = neighbor1.m_progress + neighbor2.m_progress;
        assertTrue(totalBerriesCollected <= 1000);
    }

    @Test
    public void testBasicFunctionality() throws InterruptedException {
        Field field = new Field(1000);
        Neighbor neighbor1 = new Neighbor(1, null);
        Neighbor neighbor2 = new Neighbor(2, neighbor1);
        neighbor1.setM_other(neighbor2);

        Thread thread1 = new Thread(neighbor1);
        Thread thread2 = new Thread(neighbor2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Проверка, что соседи собрали ягоды
        assertTrue(neighbor1.m_progress > 0 || neighbor2.m_progress > 0);
    }
}
