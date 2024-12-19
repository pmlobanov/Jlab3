package ru.spbstu.telematics.java;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Field {
    //блокировка, что только один может быть на поле
    public static final Lock flagLock = new ReentrantLock();
    public static int number_of_berries;
    Field(int input) {
        number_of_berries = input;}
}
