package ru.spbstu.telematics.java;

class BerryException extends Exception {
    public BerryException(String message) {
        super(message);
    }
}

public class Neighbor implements Runnable{

    //поднят ли флаг
    public boolean flag_raised;
    int m_id;
    //кол-во собранных ягод
    int m_progress;
    Neighbor m_other;

    Neighbor(int in_id, Neighbor other)
    {
        m_id = in_id;
        m_progress = 0;
        this.m_other = other;
    }
    public void setM_other(Neighbor other) {this.m_other = other; }

    @Override
    public void run() {
        while (true)
        {
            try {
                attemptToEnterField();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            //Booker, catch!
            catch (BerryException e)
            {
                System.out.println("Сосед " + m_id + " покинул поле. Ягод больше нет" );
                break;
            }
        }
    }

    public void attemptToEnterField() throws InterruptedException, BerryException {
        if(!m_other.flag_raised)
        {
            try
            {
                Field.flagLock.lock();
                flag_raised = true;
                System.out.println("Сосед " + m_id + " на поле");
                Thread.sleep(100);
                if(Field.number_of_berries >0)
                {
                    Field.number_of_berries -= 100;
                    m_progress += 100;
                }
                else {
                    throw new BerryException("Ягоды закончились");
                }
                Thread.sleep(10);
                System.out.println("Сосед " + m_id + " покинул поле." );
            }
            finally {
                System.out.println("Cосед "+ m_id+ " собрал ягоды. Всего: " + m_progress);
                flag_raised = false;
                Field.flagLock.unlock();
            }

        }
        else {
            System.out.println("Сосед " + m_id + " видит флаг. Попытка неудачна");
            Thread.sleep(100);
        }

    }
}

