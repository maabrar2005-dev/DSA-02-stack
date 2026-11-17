
package oy.tol.tra;

/**
 * An implementation of the StackInterface.
 * 
 * The stack uses an Object array to store elements.
 * Elements are added and removed from the top of the stack.
 */
public class StackImplementation<E> implements StackInterface<E> {

   private Object[] itemArray;
   private int capacity;
   private int currentIndex = -1;
   private static final int DEFAULT_STACK_SIZE = 10;

   /**
    * Allocates a stack with a default capacity.
    * @throws StackAllocationException if allocation fails.
    */
   public StackImplementation() throws StackAllocationException {
      this(DEFAULT_STACK_SIZE);
   }

   /**
    * Allocates a stack with the specified capacity.
    *
    * @param capacity The capacity of the stack.
    * @throws StackAllocationException If the capacity is invalid
    * or the internal array cannot be allocated.
    */
   public StackImplementation(int capacity) throws StackAllocationException {

      if (capacity < 2) {
         throw new StackAllocationException("Stack capacity must be at least 2.");
      }

      try {
         itemArray = new Object[capacity];
         this.capacity = capacity;
      } catch (OutOfMemoryError e) {
         throw new StackAllocationException("Unable to allocate stack storage.");
      }
   }

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {

      if (element == null) {
         throw new NullPointerException("Stack cannot contain null elements.");
      }

      // Increase capacity if the array is full.
      if (currentIndex == capacity - 1) {
         int newCapacity;

         try {
            newCapacity = Math.multiplyExact(capacity, 2);
            Object[] newArray = new Object[newCapacity];

            // Copy existing elements into the larger array.
            for (int i = 0; i <= currentIndex; i++) {
               newArray[i] = itemArray[i];
            }

            itemArray = newArray;
            capacity = newCapacity;

         } catch (OutOfMemoryError | ArithmeticException e) {
            throw new StackAllocationException("Unable to increase stack capacity.");
         }
      }

      // Add the element to the top of the stack.
      itemArray[++currentIndex] = element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {

      if (isEmpty()) {
         throw new StackIsEmptyException("Cannot pop from an empty stack.");
      }

      // Retrieve the top element and remove its reference.
      E element = (E) itemArray[currentIndex];
      itemArray[currentIndex] = null;
      currentIndex--;

      return element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {

      if (isEmpty()) {
         throw new StackIsEmptyException("Cannot peek at an empty stack.");
      }

      // Return the top element without removing it.
      return (E) itemArray[currentIndex];
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @Override
   public void clear() {
      // Remove references to all stored elements.
      for (int i = 0; i <= currentIndex; i++) {
         itemArray[i] = null;
      }

      currentIndex = -1;
   }

   @Override
   public boolean isEmpty() {
      return currentIndex == -1;
   }

   @Override
   public String toString() {

      StringBuilder builder = new StringBuilder("[");

      for (int index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index].toString());

         if (index < currentIndex) {
            builder.append(", ");
         }
      }

      builder.append("]");
      return builder.toString();
   }
}
