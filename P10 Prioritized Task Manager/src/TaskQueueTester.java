import java.util.Arrays;
import java.util.NoSuchElementException;
public class TaskQueueTester {
    /**
     * Tests the correctness of a Task compareTo() method implementation when the criteria parameter is TITLE.
     * @return true if all the implementation passes all test cases, false otherwise
     */
    public static boolean testCompareToTitle(){
        //basic case a > b
        {
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);

            if (item.compareTo(item2, CompareCriteria.TITLE) <= 0) {
                return false;
            }
            //test if the other way is true b < a
            if (item2.compareTo(item, CompareCriteria.TITLE) >= 0){
                return false;
            }
        }
        //basic case A > a
        {
            Task item = new Task("A", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 2, PriorityLevel.LOW);

            if (item.compareTo(item2, CompareCriteria.TITLE) <= 0){
                return false;
            }
            //test if the other way is true b < a
            if (item2.compareTo(item, CompareCriteria.TITLE) >= 0){
                return false;
            }
        }
        //basic case a == a
        {
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 2, PriorityLevel.LOW);

            if (item.compareTo(item2, CompareCriteria.TITLE) != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the correctness of a Task compareTo() method implementation when the criteria parameter is LEVEL.
     * @return true if all the implementation passes all test cases, false otherwise
     */
    public static boolean testCompareToLevel(){
        //low < high
        {
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 2, PriorityLevel.HIGH);

            if (item.compareTo(item2, CompareCriteria.LEVEL) >= 0) {
                return false;
            }

            if (item2.compareTo(item, CompareCriteria.LEVEL) <= 0){
                return false;
            }
        }
        //basic base case urgent > high
        {
            Task item = new Task("a", "no", 2, PriorityLevel.URGENT);
            Task item2 = new Task("a", "no", 2, PriorityLevel.HIGH);

            if (item.compareTo(item2, CompareCriteria.LEVEL) <= 0){
                return false;
            }
            if (item2.compareTo(item, CompareCriteria.LEVEL) >= 0){
                return false;
            }
        }
        //basic base case MEDIUM > Low
        {
            Task item = new Task("a", "no", 2, PriorityLevel.MEDIUM);
            Task item2 = new Task("a", "no", 2, PriorityLevel.LOW);

            if (item.compareTo(item2, CompareCriteria.LEVEL) <= 0){
                return false;
            }
            if (item2.compareTo(item, CompareCriteria.LEVEL) >= 0){
                return false;
            }
        }
        //basic base case MEDIUM == MEDIUM
        {
            Task item = new Task("a", "no", 2, PriorityLevel.MEDIUM);
            Task item2 = new Task("a", "no", 2, PriorityLevel.MEDIUM);

            if (item.compareTo(item2, CompareCriteria.LEVEL) != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the correctness of a Task compareTo() method implementation when the criteria parameter is TIME.
     * @return true if all the implementation passes all test cases, false otherwise
     */
    public static boolean testCompareToTime(){
        //basic case 40 > 20
        {
            Task item = new Task("a", "no", 20, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 40, PriorityLevel.LOW);

            if (item.compareTo(item2, CompareCriteria.TIME) >= 0){
                return false;
            }
            if (item2.compareTo(item, CompareCriteria.TIME) <= 0){
                return false;
            }
        }
        //basic case 20 == 20
        {
            Task item = new Task("a", "no", 20, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 20, PriorityLevel.LOW);

            if (item.compareTo(item2, CompareCriteria.TIME) != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the correctness of a TaskQueue enqueue() method implementation including exceptions and edge cases
     * (if applicable).
     * @return true if all the implementation passes all test cases, false otherwise
     */
    public static boolean testEnqueue(){
        //IllegalArgumentException test case
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            item.markCompleted();
            boolean caught = false;

            try {
                test.enqueue(item);
            }
            catch (IllegalArgumentException e){
                caught = true;
            }

            if (!caught){
                return false;
            }
        }
        //IllegalStateException test case
        {
            TaskQueue test = new TaskQueue(3, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);
            Task item4 = new Task("d", "no", 2, PriorityLevel.LOW);
            boolean caught = false;

            test.enqueue(item);
            test.enqueue(item2);
            test.enqueue(item3);

            try {
                test.enqueue(item4);
            }
            catch (IllegalStateException e){
                caught = true;
            }

            if (!caught){
                return false;
            }
        }
        // enqueuing in order a b c. Expect a b c
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);

            Task[] correct = new Task[5];
            correct[0] = item;
            correct[1] = item2;
            correct[2] = item3;

            test.enqueue(item);
            test.enqueue(item2);
            test.enqueue(item3);


            if (test.size() != 3){
                for (int i = 0; i < test.size(); i++){
                    System.out.println(test.getHeapData()[i]);
                }
                return false;
            }
            if (!Arrays.deepEquals(test.getHeapData(), correct)){
                for (int i = 0; i < test.size(); i++){
                    System.out.println(test.getHeapData()[i]);
                }
                return false;
            }
        }
        //enqueuing out of order  e c b d a. Expect a b c e d
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);
            Task item4 = new Task("d", "no", 2, PriorityLevel.LOW);
            Task item5 = new Task("e", "no", 2, PriorityLevel.LOW);

            Task[] correct = new Task[5];
            correct[0] = item;
            correct[1] = item2;
            correct[2] = item3;
            correct[3] = item5;
            correct[4] = item4;

            test.enqueue(item5);
            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item4);
            test.enqueue(item);

            if (test.size() != 5){
                return false;
            }
            if (!Arrays.deepEquals(test.getHeapData(), correct)){
                return false;
            }
        }
        //testing Capital letters c b a Z. Expecting Z a b c
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);
            Task item4 = new Task("Z", "no", 2, PriorityLevel.LOW);

            Task[] correct = new Task[5];
            correct[0] = item4;
            correct[1] = item;
            correct[2] = item2;
            correct[3] = item3;

            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item);
            test.enqueue(item4);


            if (test.size() != 4){
                return false;
            }
            if (!Arrays.deepEquals(test.getHeapData(), correct)){
                return false;
            }
        }
        //testing duplicates c b a a d expecting a a b c d
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);
            Task item4 = new Task("d", "no", 2, PriorityLevel.LOW);

            Task[] correct = new Task[5];
            correct[0] = item;
            correct[1] = item;
            correct[2] = item2;
            correct[3] = item3;
            correct[4] = item4;

            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item);
            test.enqueue(item);
            test.enqueue(item4);

            if (test.size() != 5){
                return false;
            }
            if (!Arrays.deepEquals(test.getHeapData(), correct)){
                return false;
            }

        }

        //testing time 4 3 2 5, expecting 5 4 2 3
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TIME);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 3, PriorityLevel.LOW);
            Task item3 = new Task("a", "no", 4, PriorityLevel.LOW);
            Task item4 = new Task("a", "no", 5, PriorityLevel.LOW);

            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item);
            test.enqueue(item4);

            Task[] correct = new Task[5];
            correct[0] = item4;
            correct[1] = item3;
            correct[2] = item;
            correct[3] = item2;

            if (test.size() != 4){
                return false;
            }
            if (!Arrays.deepEquals(correct, test.getHeapData())){
                return false;
            }

        }
        //testing priority levels:  low, urgent, high , optional. expecting urgent, low, high, optional
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.LEVEL);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.OPTIONAL);
            Task item3 = new Task("c", "no", 2, PriorityLevel.HIGH);
            Task item4 = new Task("d", "no", 2, PriorityLevel.URGENT);

            Task[] correct = new Task[5];

            correct[0] = item4;
            correct[1] = item;
            correct[2] = item3;
            correct[3] = item2;

            test.enqueue(item);
            test.enqueue(item4);
            test.enqueue(item3);
            test.enqueue(item2);

            if (test.size() != 4){
                return false;
            }
            if (!Arrays.deepEquals(test.getHeapData(), correct)){
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the correctness of a TaskQueue dequeue() method implementation including exceptions and edge cases
     * (if applicable).
     * @return true if all the implementation passes all test cases, false otherwise
     */
    public static boolean testDequeue(){
        //exception test case
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            boolean caught = false;
            try {
                test.dequeue();
            }
            catch (NoSuchElementException e){
                caught = true;
            }

            if (!caught){
                return false;
            }
        }
        //enqueuing e c b d a and dequeue once. Expect b d c e
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);
            Task item4 = new Task("d", "no", 2, PriorityLevel.LOW);
            Task item5 = new Task("e", "no", 2, PriorityLevel.LOW);

            test.enqueue(item5);
            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item4);
            test.enqueue(item);
            Task value = test.dequeue();

            Task[] correct = new Task[5];
            correct[0] = item2;
            correct[1] = item4;
            correct[2] = item3;
            correct[3] = item5;

            if (test.size() != 4){
                return false;
            }
            if (!value.equals(item)){
                return false;
            }
            if (!Arrays.deepEquals(correct, test.getHeapData())){
                return false;
            }
        }
        // a, c, b, a ,e, d. dequeue twice expect b c e d
        {
            TaskQueue test = new TaskQueue(8, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);
            Task item4 = new Task("d", "no", 2, PriorityLevel.LOW);
            Task item5 = new Task("e", "no", 2, PriorityLevel.LOW);

            Task value;
            test.enqueue(item);
            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item);
            test.enqueue(item5);
            test.enqueue(item4);
            test.dequeue();
            value = test.dequeue();

            Task[] correct = new Task[8];
            correct[0] = item2;
            correct[1] = item3;
            correct[2] = item5;
            correct[3] = item4;


            if (test.size() != 4){
                return false;
            }
            if (!value.equals(item)){
                return false;
            }
            if (!Arrays.deepEquals(correct, test.getHeapData())){
                for (int i = 0; i < test.size(); i++){
                    System.out.println(test.getHeapData()[i]);
                }
                return false;
            }
        }
        //testing time 4 3 2 5, expecting 5 4 3 2
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TIME);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 3, PriorityLevel.LOW);
            Task item3 = new Task("a", "no", 4, PriorityLevel.LOW);
            Task item4 = new Task("a", "no", 5, PriorityLevel.LOW);

            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item);
            test.enqueue(item4);
            Task value = test.dequeue();

            Task[] correct = new Task[5];
            correct[0] = item3;
            correct[1] = item2;
            correct[2] = item;

            if (test.size() != 3){
                return false;
            }
            if (!value.equals(item4)){
                return false;
            }
            if (!Arrays.deepEquals(correct, test.getHeapData())){
                return false;
            }
        }
        //testing priority levels:  low, urgent, high , optional. expecting urgent, low, high, optional
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.LEVEL);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.OPTIONAL);
            Task item3 = new Task("c", "no", 2, PriorityLevel.HIGH);
            Task item4 = new Task("d", "no", 2, PriorityLevel.URGENT);

            Task[] correct = new Task[5];

            correct[0] = item3;
            correct[1] = item;
            correct[2] = item2;

            test.enqueue(item);
            test.enqueue(item4);
            test.enqueue(item3);
            test.enqueue(item2);
            Task value = test.dequeue();

            if (test.size() != 3){
                return false;
            }
            if (!value.equals(item4)){
                return false;
            }
            if (!Arrays.deepEquals(test.getHeapData(), correct)){
                return false;
            }
        }

        return true;
    }

    /**
     * Tests the correctness of a TaskQueue peek() method implementation including exceptions and edge cases
     * (if applicable).
     * @return true if all the implementation passes all test cases, false otherwise
     */
    public static boolean testPeek(){
        //NoSuchElementException test case
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.LEVEL);
            boolean caught = false;

            try {
                test.peekBest();
            }
            catch (NoSuchElementException e){
                caught = true;
            }

            if (!caught){
                return false;
            }
        }
        //Title test case. c a b Z expecting Z
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.LOW);
            Task item3 = new Task("c", "no", 2, PriorityLevel.LOW);
            Task item4 = new Task("Z", "no", 2, PriorityLevel.LOW);
            Task[] correct = new Task[5];


            correct[0] = item4;
            correct[1] = item;
            correct[2] = item2;
            correct[3] = item3;

            test.enqueue(item3);
            test.enqueue(item);
            test.enqueue(item2);
            test.enqueue(item4);

            if (test.size() != 4){
                return false;
            }
            if (!test.peekBest().getTitle().equals("Z")){
                return false;
            }
            if (!Arrays.deepEquals(test.getHeapData(), correct)){
                return false;
            }
        }

        //Time test case 27, 45, 20, 35. expecting 45, 35, 20, 27
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.TIME);
            Task item = new Task("a", "no", 20, PriorityLevel.LOW);
            Task item2 = new Task("a", "no", 35, PriorityLevel.LOW);
            Task item3 = new Task("a", "no", 27, PriorityLevel.LOW);
            Task item4 = new Task("a", "no", 45, PriorityLevel.LOW);

            Task[] correct = new Task[5];
            correct[0] = item4;
            correct[1] = item2;
            correct[2] = item;
            correct[3] = item3;

            test.enqueue(item3);
            test.enqueue(item4);
            test.enqueue(item);
            test.enqueue(item2);

            if (test.size() != 4){
                return false;
            }
            if (test.peekBest().getTime() != 45){
                return false;
            }
           if (!Arrays.deepEquals(test.getHeapData(), correct)){
               return false;
            }
        }
        //testing priority levels. expecting urgent
        {
            TaskQueue test = new TaskQueue(5, CompareCriteria.LEVEL);
            Task item = new Task("a", "no", 2, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 2, PriorityLevel.OPTIONAL);
            Task item3 = new Task("c", "no", 2, PriorityLevel.HIGH);
            Task item4 = new Task("d", "no", 2, PriorityLevel.URGENT);

            Task[] correct = new Task[5];

            correct[0] = item4;
            correct[1] = item;
            correct[2] = item3;
            correct[3] = item2;

            test.enqueue(item); // LOW
            test.enqueue(item4); // URGENT
            test.enqueue(item3); // HIGH
            test.enqueue(item2); // OPTIONAL

            if (test.size() != 4){
                return false;
            }
            if (!test.peekBest().getTitle().equals("d")){
                return false;
            }
            if(!Arrays.deepEquals(test.getHeapData(), correct)){
                return false;
            }
        }

        return true;
    }

    /**
     * Tests the correctness of a TaskQueue de() method implementation including exceptions and edge cases
     * (if applicable).
     * @return true if all the implementation passes all test cases, false otherwise
     */
    public static boolean testReprioritize(){
        //testing time reprioritize
        {
            TaskQueue test = new TaskQueue(10, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 20, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 12, PriorityLevel.URGENT);
            Task item3 = new Task("c", "no", 77, PriorityLevel.HIGH);
            Task item4 = new Task("d", "no", 44, PriorityLevel.MEDIUM);
            Task item5 = new Task("e", "no", 66, PriorityLevel.HIGH);
            Task item6 = new Task("f", "no", 45, PriorityLevel.LOW);
            Task item7 = new Task("g", "no", 11, PriorityLevel.OPTIONAL);

            test.enqueue(item);
            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item4);
            test.enqueue(item5);
            test.enqueue(item6);
            test.enqueue(item7);

            test.reprioritize(CompareCriteria.TIME);

            Task[] correct = new Task[10];
            correct[0] = item3; //77
            correct[1] = item5; //66
            correct[2] = item6; // 45
            correct[3] = item4; // 44
            correct[4] = item; //20
            correct[5] = item2; //12
            correct[6] = item7; //11

            if (test.size() != 7){
                return false;
            }
            if (!Arrays.deepEquals(correct, test.getHeapData())){
                System.out.println("here111");
                for (int i = 0; i < test.size(); i++){
                    System.out.println(test.getHeapData()[i]);
                }
                return false;
            }
        }
        //testing levels
        {
            TaskQueue test = new TaskQueue(10, CompareCriteria.TITLE);
            Task item = new Task("a", "no", 20, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 12, PriorityLevel.URGENT);
            Task item3 = new Task("c", "no", 77, PriorityLevel.HIGH);
            Task item4 = new Task("d", "no", 44, PriorityLevel.MEDIUM);
            Task item5 = new Task("e", "no", 66, PriorityLevel.HIGH);
            Task item6 = new Task("f", "no", 45, PriorityLevel.LOW);
            Task item7 = new Task("g", "no", 11, PriorityLevel.OPTIONAL);

            test.enqueue(item);
            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item4);
            test.enqueue(item5);
            test.enqueue(item6);
            test.enqueue(item7);

            test.reprioritize(CompareCriteria.LEVEL);
            Task[] correct = new Task[10];
            correct[0] = item2;
            correct[1] = item5;
            correct[2] = item6;
            correct[3] = item4;
            correct[4] = item3;
            correct[5] = item;
            correct[6] = item7;
            if (test.size() != 7){
                return false;
            }
            if (!Arrays.deepEquals(correct, test.getHeapData())){
                System.out.println("here22");
                for (int i = 0; i < test.size(); i++){
                    System.out.println(test.getHeapData()[i]);
                }
                return false;
            }
        }
        //testing title repriotize
        {
            TaskQueue test = new TaskQueue(10, CompareCriteria.TIME);
            Task item = new Task("a", "no", 20, PriorityLevel.LOW);
            Task item2 = new Task("b", "no", 12, PriorityLevel.URGENT);
            Task item3 = new Task("c", "no", 77, PriorityLevel.HIGH);
            Task item4 = new Task("d", "no", 44, PriorityLevel.MEDIUM);
            Task item5 = new Task("e", "no", 66, PriorityLevel.HIGH);
            Task item6 = new Task("f", "no", 45, PriorityLevel.LOW);
            Task item7 = new Task("g", "no", 11, PriorityLevel.OPTIONAL);

            test.enqueue(item);
            test.enqueue(item3);
            test.enqueue(item2);
            test.enqueue(item4);
            test.enqueue(item5);
            test.enqueue(item6);
            test.enqueue(item7);

            test.reprioritize(CompareCriteria.TITLE);

            Task[] correct = new Task[10];
            correct[0] = item;
            correct[1] = item3;
            correct[2] = item2;
            correct[3] = item5;
            correct[4] = item4;
            correct[5] = item6;
            correct[6] = item7;

            if (test.size() != 7){
                return false;
            }
            if (!Arrays.deepEquals(correct, test.getHeapData())){
                for (int i = 0; i < test.size(); i++){
                    System.out.println(test.getHeapData()[i]);
                }
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args){
        System.out.println("testCompareToTitle: " + testCompareToTitle());
        System.out.println("testCompareToLevel: " + testCompareToLevel());
        System.out.println("testCompareToTime: " + testCompareToTime());
        System.out.println("testPeek: " + testPeek());
        System.out.println("testEnqueue: " + testEnqueue());
        System.out.println("testDequeue: " + testDequeue());
        System.out.println("testReprioritize: " + testReprioritize());

    }
}
