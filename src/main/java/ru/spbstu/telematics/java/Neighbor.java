package ru.spbstu.telematics.java;
class BerryException extends Exception {
    public BerryException(String message) {
        super(message);
    }
}

public class Neighbor implements Runnable {

    // поднят ли флаг
    public volatile boolean flag_raised;
    int m_id;
    // кол-во собранных ягод
    int m_progress;
    Neighbor m_other;

    Neighbor(int in_id, Neighbor other) {
        flag_raised = false;
        m_id = in_id;
        m_progress = 0;
        this.m_other = other;
    }

    public void setM_other(Neighbor other) {
        this.m_other = other;
    }

    @Override
    public void run() {
        boolean flag = false;
        while (true) {
            try {
                Thread.sleep(150);
                flag = attemptToEnterField();
                if (flag) {
                    if (Field.number_of_berries > 0) {
                        Field.number_of_berries -= 100;
                        m_progress += 100;
                        System.out.println("Сосед " + m_id + " собрал ягоды. Всего: " + m_progress);
                    } else {
                        System.out.println("Сосед " + m_id + " покинул поле. Ягод больше нет");
                        flag_raised = false;
                        break;
                    }
                    System.out.println("Сосед " + m_id + " покинул поле.");
                    // flag_raised = false;
                }
                else Thread.sleep(70);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            finally {
                flag_raised = false;
            }
        }
    }

    public boolean attemptToEnterField() throws InterruptedException {
        Field.flagLock.lock();
        try {
            if (!m_other.flag_raised) {
                System.out.println("Сосед " + m_id + " выставил флаг");
                flag_raised = true;
                System.out.println("Сосед " + m_id + " на поле");
                return true;
            } else {
                System.out.println("Сосед " + m_id + " видит флаг. Попытка неудачна");
                return false;
            }
        } finally {
            Field.flagLock.unlock();
        }
    }
}



