public class ThreadSort{
    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() ->{

        });
        thread.start();
        Thread.sleep(500);

        flag = false;
        System.out.println("turned");


    }
}
