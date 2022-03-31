
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;


public class LibrarySim {
    public static void main(String [] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(15);

        Book[] books = new Book[Constants.NUMBER_OF_BOOKS];
        int[] students = new int[Constants.NUMBER_OF_STUDENTS];

        for(int i = 0; i < Constants.NUMBER_OF_BOOKS; i++) {
            books[i] = new Book(i + 1);
        }
        for(int i = 0; i < Constants.NUMBER_OF_STUDENTS; i++) {
            students[i] = i + 1;
        }
        for(int i = 0; i < Constants.NUMBER_OF_STUDENTS; i++) {
            service.execute(new Student(students[i], books));
        }
    }
}
class Constants {
    public static final int NUMBER_OF_STUDENTS = 15;
    public static final int NUMBER_OF_BOOKS  = 20;
    private Constants(){}
}
class Book {
    private int id;
    private Lock lock = new ReentrantLock();
    public static final int NUMBER_OF_BOOKS  = 20;
    public Book(int id){
        this.id = id;
    }
    public void read(Student student){
        lock.lock();
        try {
            System.out.println(student.toString() +" starts reading"+ toString());
            try {
                Thread.sleep(((int) (Math.random() * 5000)));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
        lock.unlock();
        System.out.println(student.toString() +" has finished reading"+ toString());
        }
    }
    @Override
    public String toString(){
        return " Book " + id;
    }
}
class Student implements Runnable{
    private int id;
    private Book[] books;
    public Student(int id, Book[] books){
        this.id = id;
        this.books = books;
    }

    @Override
    public void run() {
        while(true){
            int rand = ThreadLocalRandom.current().nextInt(0, 20);
            books[rand].read(this);
        }
    }
    public String toString(){
        return "Student " + id;
    }
}