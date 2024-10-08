package Final;

public class Bistro {
    private int seats;
    private Thread order, meal, waiter;
    public  Bistro(int n){
        seats = n;
//        waiter = new Thread();
//        waiter.start();
    }
    private synchronized void dine(int price) {
        while(seats <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        seats--;
        this.notifyAll();
        System.out.println("sat down"); //


        while(order != null){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        order = Thread.currentThread();
        System.out.println("Guest " + Thread.currentThread() + " orders for " + price + " Lari");
        serve();

        this.notifyAll();
        while(meal != Thread.currentThread()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        meal = null;
        System.out.println("Guest " + Thread.currentThread() + " eats ...");
        seats++;

        this.notifyAll();
    }
    public synchronized void serve(){
        while(order == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        meal = Thread.currentThread();
        order= null;
        System.out.println("Enjoy!");
        this.notifyAll();


    }
    public void shutdown(){

    }

    public static void main(String[] args) {
        Bistro bistro = new Bistro(2);

        Thread g1 = new Thread(()-> bistro.dine(10));
        Thread g2 = new Thread(()-> bistro.dine(20));
        Thread g3 = new Thread(()-> bistro.dine(30));
        Thread g4 = new Thread(()-> bistro.dine(40));
        g1.start();
        g2.start();
        g3.start();
        g4.start();
        try{
            g1.join();
            g2.join();
            g3.join();
            g4.join();
        } catch (InterruptedException e) {
            bistro.shutdown();
        }

    }
}
