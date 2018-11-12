import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        String lang = in.nextLine();
        String input = in.nextLine();
        MyThread thrd = new MyThread(lang, input);
        thrd.start();
        thrd.join();
        System.out.println(thrd.getOutput());
    }
}
