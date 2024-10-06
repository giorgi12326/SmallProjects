public class ThreadCount {
    static final Object mutex = new Object();
    static boolean firstTurn = true;
    public static void main(String[] args) {


        new Thread(()->{
            for (int i = 1; i <= 20; i+=2) {
                synchronized (mutex){
                    while(!firstTurn) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(i);
                    firstTurn = false;
                    mutex.notify();
                }
            }
        }).start();
        new Thread(()->{
            for (int i = 2; i <= 20; i+=2) {
                synchronized (mutex){
                    while(firstTurn) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(i);
                    firstTurn = true;
                    mutex.notify();
                }
            }
        }).start();

    }
}
