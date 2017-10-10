import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArraySet.java.
 *
 * Provides an implementation of the Set interface using an
 * array as the underlying data structure. Values in the array
 * are kept in ascending natural order and, where possible,
 * methods take advantage of this. Many of the methods with parameters
 * of type ArraySet are specifically designed to take advantage
 * of the ordered array implementation.
 *
 * @author Your Name (yourTigerMail@auburn.edu)
 * @author	Dean Hendrix (dh@auburn.edu)
 * @version	2017-10-02
 *
 */
public class ArraySet<T extends Comparable<? super T>> implements Set<T> {

   ////////////////////////////////////////////
   // DO NOT CHANGE THE FOLLOWING TWO FIELDS //
   ////////////////////////////////////////////
   T[] elements;
   int size;

   ////////////////////////////////////
   // DO NOT CHANGE THIS CONSTRUCTOR //
   ////////////////////////////////////
   /**
    * Instantiates an empty set.
    */
   @SuppressWarnings("unchecked")
   public ArraySet() {
      elements = (T[]) new Comparable[1];
      size = 0;
   }

   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   @Override
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements,
    *               false otherwise.
    */
   @Override
   public boolean isEmpty() {
      return size == 0;
   }
   
   private boolean isFull() {
      return size == elements.length;
   }

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this ArraySet.
    *
    * @return a string representation of this ArraySet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }

   /**
    * Ensures the collection contains the specified element. Elements are
    * maintained in ascending natural order at all times. Neither duplicate nor
    * null values are allowed.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   @Override
   public boolean add(T element) {
   
      if (this.contains(element)) {
         return false;
      }
   
      if(isFull()) {
         resize(size * 2);
      }
   
      int i = 0;
      while(i < size && element.compareTo(elements[i]) > 0) {
         i++;
      }
   
      for (int j = size; j > i; j--)
      {
         elements[j] = elements[j - 1];
      }
      elements[i] = element;
      size++;
      return true;
      
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. Elements are maintained in ascending natural
    * order at all times.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
    
   private boolean isSparse() {
      return (size > 0) && (size < elements.length / 4);
   }
   private int locate(T element) {
      for (int i = 0; i < size; i++) {
         if (elements[i].equals(element)) {
            return i;
         }
      }
      return -1;
   }

   

   @Override
   public boolean remove(T element) {
      if (!this.contains(element)) {
         return false;
      }
   
      if(isSparse()) {
         resize(size / 2);
      }
      
      int index = this.locate(element);
   
      for (int j = (index + 1); j < size; j++)
      {
         elements[j - 1] = elements[j];
         
      }
      elements[size] = null;
      size--;
      return true;
      
   }

   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection
    *                   is to be tested.
    * @return  true if this collection contains the specified element,
    *               false otherwise.
    */
   @Override
   public boolean contains(T element) {
      int min = 0;
      int max = size;
      while(min > max) {
      
         int mid = (min + max) / 2;
      
         if (elements[mid].equals(element)) {
            return true;
         }
         
         else if (element.compareTo(elements[mid]) < 0) {
            max = mid - 1;
         }
         else {
            min = mid + 1;
         }  
      }
      return false;
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    */
   @Override
   public boolean equals(Set<T> s) {
   
      if (s.size() != size) {
         return false;
      }
   
      Iterator<T> itr = s.iterator();
   
      while (itr.hasNext()) {
         T element = itr.next();
         if (!this.contains(element)) {
         
            return false;
         }
      }
      return true;
      
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    */
   public boolean equals(ArraySet<T> s) {
   
      if(s.size() != size) {
         return false;
      }
      Iterator<T> itr = s.iterator();
   
      for (int i = 0; i < size; i++) {
         if (!s.equals(this)) {
            return false;
         }
      
      }
   
      return true;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    */
   @Override
   public Set<T> union(Set<T> s) {
   
      ArraySet<T> a = new ArraySet<T>();
   
      Iterator<T> itr = this.iterator();
      while(itr.hasNext()) {
         a.add(itr.next());
      }
      
      Iterator<T> i = s.iterator();
      while(i.hasNext()) {
         a.add(i.next());
      }
      return a;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    */
   public Set<T> union(ArraySet<T> s) {
   
      ArraySet<T> a = new ArraySet<T>();
   
      Iterator<T> itr = this.iterator();
      while(itr.hasNext()) {
         a.add(itr.next());
      }
      
      Iterator<T> i = s.iterator();
      while(i.hasNext()) {
         a.add(i.next());
      }
      return a;
   
      
   }


   /**
    * Returns a set that is the intersection of this set
    * and the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   @Override
   public Set<T> intersection(Set<T> s) {
      return null;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(ArraySet<T> s) {
   
      ArraySet<T> as = new ArraySet<T>();
   
      for (T val : elements) {
      
         if (s.contains(val)) {
         
            as.add(val);
         }
      }
      
      return as;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   @Override
   public Set<T> complement(Set<T> s) {
      return null;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(ArraySet<T> s) {
   
      ArraySet<T> as = new ArraySet<T>();
   
      for (T val : s) {
      
         if (!this.contains(val)) {
         
            as.add(val);
         }
      }
      
      return as;
   }

    
   


   /**
    * Returns an iterator over the elements in this ArraySet.
    * No specific order can be assumed.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   @Override
   public Iterator<T> iterator() {
   
      // ALMOST ALL THE TESTS DEPEND ON THIS METHOD WORKING CORRECTLY.
      // MAKE SURE YOU GET THIS ONE WORKING FIRST.
      // HINT: JUST USE THE SAME CODE/STRATEGY AS THE ARRAYBAG CLASS
      // FROM LECTURE. THE ONLY DIFFERENCE IS THAT YOU'LL NEED THE
      // ARRAYITERATOR CLASS TO BE NESTED, NOT TOP-LEVEL.
      return new ArrayIterator<T>(elements,size);
   }

   /**
    * Returns an iterator over the elements in this ArraySet.
    * The elements are returned in descending order.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   public Iterator<T> descendingIterator() {
      return null;
   }

   /**
    * Returns an iterator over the members of the power set
    * of this ArraySet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return null;
   }
   
   private void resize(int newSize) {
      assert newSize > 0;
      @SuppressWarnings("unchecked")
         T[] newArray = (T[]) new Object[newSize];
      System.arraycopy(elements, 0, newArray, 0, size);
      elements = newArray;
   }
   
   class ArrayIterator<T> implements Iterator<T> {
      
      // the array of elements to iterate over
      private T[] elements;
      // the number of elements in the array, beginning at index zero
      private int count;
      // the index of the next element in the iteration sequence
      private int current;
      
      /**
      * Construct a properly initialized iterator.
      *
      * @param  elem the array to be iterated over
      * @param  size the number of elements in the array
      */
      public ArrayIterator(T[] elem, int size) {
         elements = elem;
         count = size;
         current = 0;
      }
      
      /**
      * Returns true if there is at least one more element remaining
      * in the iteration sequence.
      *
      * @return true if there is a next element to iterate over
      */
      public boolean hasNext() {
         return current < count;
      }
      
      /**
      * Returns the next element in the iteration sequence.
      * @return the next element in the iteration sequence
      */
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         return elements[current++];
      }
      
      /**
      * Unsupported operation.
      */
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
   }



}
