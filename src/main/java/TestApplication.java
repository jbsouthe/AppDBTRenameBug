import java.util.Random;

public class TestApplication {
    Random rn = new Random();

    public void runBusinessTransaction() {
        int seconds = getRandomSleepSeconds();
        System.out.print(String.format("Sleeping for %d seconds ", seconds));
        try {
            for( int i=0; i< seconds; i++) {
                System.out.print(".");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            //ignored
        }
        System.out.println(" done");
    }

    public int getRandomSleepSeconds() {
        return rn.nextInt(10)+1;
    }

    public static void main( String... args ) {
        TestApplication testApplication = new TestApplication();
        while(true){
            testApplication.runBusinessTransaction();
        }
    }
}
