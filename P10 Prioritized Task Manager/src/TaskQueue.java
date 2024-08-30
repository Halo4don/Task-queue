import java.util.Arrays;
import java.util.NoSuchElementException;

public class TaskQueue {
    private Task[] heapData; //oversized array that holds all of Tasks in the heap
    private CompareCriteria priorityCriteria; //the criteria used to determine how to prioritize Tasks in the queue
    private int size; //the number of items in the TaskQueue

    /**
     * Creates an empty TaskQueue with the given capacity and priority criteria.
     * @param capacity - the max number of Tasks this priority queue can hold
     * @param priorityCriteria - the criteria for the queue to use to determine a Task's priority
     * @throws IllegalArgumentException - with a descriptive message if the capacity is non-positive
     */
    public TaskQueue(int capacity, CompareCriteria priorityCriteria){
        if (capacity <= 0){
            throw new IllegalArgumentException("Capacity cant be negative");
        }
        this.priorityCriteria = priorityCriteria;
        heapData = new Task[capacity];
        size = 0;
    }

    /**
     * Gets the criteria use to prioritize tasks in this a TaskQueue.
     * @return the prioritization criteria of this TaskQueue
     */
    public CompareCriteria getPriorityCriteria(){
        return this.priorityCriteria;
    }

    /**
     * Creates and returns a deep copy of the heap's array of data.
     * @return the deep copy of the array holding the heap's data
     */
    public Task[] getHeapData(){
        Task[] heapCopy = Arrays.copyOf(heapData, heapData.length);
        return heapCopy;
    }

    /**
     * Reports the size of a TaskQueue.
     * @return the number of Tasks in this TaskQueue
     */
    public int size(){
        return size;
    }

    /**
     * Reports if a TaskQueue is empty.
     * @return true if this TaskQueue is empty, false otherwise
     */
    public boolean isEmpty(){
        if (heapData[0] == null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Gets the Task in a TaskQueue that has the highest priority WITHOUT removing it. The Task that has the highest
     * priority may differ based on the current priority criteria.
     * @return the Task in this queue with the highest priority
     * @throws NoSuchElementException - with descriptive message if this TaskQueue is empty
     */
    public Task peekBest(){
        if (isEmpty()){
            throw new NoSuchElementException("This task queue empty");
        }
        return heapData[0];
    }

    /**S
     * Adds the newTask to this priority queue.
     * @param newTask - the task to add to the queue
     * @throws IllegalArgumentException - with a descriptive message if the Task is already completed
     * @throws IllegalStateException - with a descriptive message if the priority queue is full
     */
    public void enqueue(Task newTask){
        boolean isFull = true;

        for (int i = 0; i < heapData.length; i++){
            if (heapData[i] == null){
                isFull = false;
            }
        }
        if (newTask.isCompleted()){
            throw new IllegalArgumentException("Task has already been completed");
        }
        if (isFull){
            throw new IllegalStateException("Hey this queue is full");
        }

        heapData[size] = newTask;
        percolateUp(size );
        size++;
    }

    /**
     * Fixes one heap violation by moving it up the heap.
     * @param index index - the of the element where the violation may be
     */
    protected void percolateUp(int index){
        Task temp;
        int parentIndex;

        while (index > 0) {
            parentIndex = (index - 1) / 2;
            if (heapData[index].compareTo(heapData[parentIndex], this.priorityCriteria) <= 0) {
                return;
            } else {
                //swap
                temp = heapData[index];
                heapData[index] = heapData[parentIndex];
                heapData[parentIndex] = temp;

                index = parentIndex;
            }
        }
    }

    /**
     * Gets and removes the Task that has the highest priority. The Task that has the highest priority may differ based
     * on the current priority criteria.
     * @return the Task in this queue with the highest priority
     * @throws NoSuchElementException - with descriptive message if this TaskQueue is empty
     */
    public Task dequeue(){
        if (isEmpty()){
            throw new NoSuchElementException("this queue is empty");
        }
        Task temp = heapData[0];
        heapData[0] = heapData[size() - 1];
        heapData[size - 1] = null;

        size--;
        percolateDown(0);

        return temp;
    }

    /**
     * Fixes one heap violation by moving it down the heap.
     * @param index - the of the element where the violation may be
     */
    protected void percolateDown(int index){
        int childIndex = 2 * index + 1;
        Task value = heapData[index];

        while (childIndex <= size){
            Task maxValue = value;
            int maxIndex = -1;

            for (int i = 0; i < 2 && i + childIndex < size; i ++){
                if (heapData[i + childIndex].compareTo(maxValue, priorityCriteria) > 0){
                    maxValue = heapData[i + childIndex];
                    maxIndex =  i + childIndex;
                }
            }

            if (maxValue == value){
                return;
            }
            else {
                Task temp = heapData[index];
                heapData[index] = heapData[maxIndex];
                heapData[maxIndex] = temp;

                index = maxIndex;
                childIndex = 2 * index + 1;
            }
        }
    }

    /**
     * Changes the priority criteria of this priority queue and fixes it so that is is a proper priority queue based on
     * the new criteria.
     * @param priorityCriteria - the (new) criteria that should be used to prioritize the Tasks in this queue
     */
    public void reprioritize(CompareCriteria priorityCriteria){
        this.priorityCriteria = priorityCriteria;
        for (int i = size / 2 - 1; i >= 0; i--){
            percolateDown(i);
        }
        for (int i = size - 1; i >= 0; i--){
            Task temp = heapData[0];
            heapData[0] = heapData[i];
            heapData[i] = temp;

            percolateDown(0);
        }
    }


}
