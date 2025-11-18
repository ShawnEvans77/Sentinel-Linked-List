import java.util.*;
public class SentinelDList implements Iterable<String> {

    private static class DListNode {
        public String data;
        public DListNode next;
        public DListNode previous;
    }

    private DListNode nil;
    private int size;

    /**
     * Creates an empty Sentinel Doubly Linked List.
     */
    public SentinelDList() { //The Empty List.
        nil = new DListNode(); //The Sentinel Node. 
        nil.previous = nil; //In the empty list, the sentinel node's previous is itself. "nil.previous" is the tail.
        nil.next = nil; //In the empty list, the sentinel node's next is itself. "nil.next" is the head.

        //The Sentinel Linked List is kind of circular. The tail's next is sentinel, the head's previous is sentinel, nil's previous is the tail, nil's next is the head.
        //By using this sentinel approach, we will be eliminating quite a few error checks that were needed for NullPointerExceptions.

        nil.data = null; //The Sentinel Node has no data.
    }

    /**
     * Adds an element to the front of the Sentinel Doubly Linked List
     * @param elem The {@code String} to be added to the front of the Sentinel Doubly Linked List.
     */
    public void addFirst(String elem) {
        DListNode newNode = new DListNode(); //Make a new Node.
        newNode.data = elem; //Set the data of the new node to the method parameter.

        newNode.previous = nil; //Set the previous of the new node to "nil." As this newnode will be the head, what comes before the head? nil.
        newNode.next = nil.next; //Set the next of the new node to nil.next --- also known as the "head." If this is the new first thing in the list,
        //Then the thing after it is gonna be nil.next.

        nil.next.previous = newNode; //Set the previous of what used to be the head to the new-node.
        nil.next = newNode; //Set nil.next (head) to the newNode.

        size++;
    }

    /**
     * Adds an element to the end of the Sentinel Doubly Linked List
     * @param elem The {@code String} to be added to the end of the Sentinel Doubly Linked List.
     */
    public void addLast(String elem) {
        DListNode newNode = new DListNode(); //Make a new Node.
        newNode.data = elem; //Set its data to the method parameter.
        
        newNode.previous = nil.previous; //Set the previous of the newnode to nil.previous ---- also known as the "tail."
        //As this new node will be the tail, we set its previous to the old tail.
        newNode.next = nil; //Set the next of the newNode to nil. As this is going to be the new tail, what comes after the tail? nil.

        nil.previous.next = newNode; //Set the next of what used to be the tail to the newNode.
        nil.previous = newNode; //Set nil.previous (tail) to the newNode.

        size++;
    }

    /**
     * Returns the first element of the Doubly Linked List.
     * @return The {@code String} at the 'head' of the Doubly Linked List, also known as {@code nil.next}.
     * @throws NoSuchElementException If the list is of size 0.
     */
    public String getFirst() {
        if (size == 0) 
            throw new NoSuchElementException("cannot invoke getFirst() because list is empty");

        return nil.next.data;
    }

    /**
     * Returns the last element of the Doubly Linked List.
     * @return The {@code String} at the 'head' of the Doubly Linked List, also known as {@code nil.previous}.
     * @throws NoSuchElementException If the list is empty.
     */
    public String getLast() {
        if (size == 0)
            throw new NoSuchElementException("cannot invoke getLast() because list is empty");
        
        return nil.previous.data;
    }


    /**
     * Removes the front element or 'head' of the Doubly Linked List, then returns it.
     * @return The {@code String} that was at the beginning of the Linked List prior to the call of removeFirst().
     * @throws NoSuchElementException If the list is empty.
     */
    public String removeFirst() {
        if (size == 0)
            throw new NoSuchElementException("cannot invoke removeFirst() because list is empty");
        
        DListNode old = nil.next;

        nil.next = nil.next.next; //Basically, "head = head.next." In the list of size 1, this is like "nil.next = nil."
        //How do I remove the head? I make the new head the thing after it.
        nil.next.previous = nil; //Basically, "head.previous = null." Making the previous of this new head null. In the list of size 1, this is like "nil.previous = nil."
        //No error checking done as the above logic flow works in list of size 1.

        size--;
        return old.data;
    }

    /**
     * Removes the last element or 'tail' of the Doubly Linked List, then returns it. 
     * @return The {@code String} that was at the end of the Linked List prior to the call of removeLast().
     * @throws NoSuchElementException If the list is empty.
     */
    public String removeLast() {
        if (size == 0)
            throw new NoSuchElementException("cannot invoke removeLast() because list is empty");
        
        DListNode old = nil.previous;

        nil.previous = nil.previous.previous; //Basically, "tail = tail.previous." In list of size 1, this is like "nil.previous = nil."
        //How do I remove the tail? I make the new tail the thing before it.
        nil.previous.next = nil; //Basically, "tail.next = null." Making the next of this new tail nill. In the list of size 1, this is like "nil.next = nil.""
        //No error checking done as the above logic flow works in the list of size 1.

        size--;
        return old.data;
    }

    /**
     * Returns the value at the specified index. 
     * @param index The {@code int} representing the index to look for and return.
     * @return The {@code String} at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range ( {@code < 0} or {@code >= size()} ).
     */
    public String get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index " + index + " out of bounds for length " + size);
        
        DListNode pointer = nil.next;
        int count = 0;

        while (count < index) {
            count++;
            pointer = pointer.next;
        }

        return pointer.data;
    }

    /**
     * Changes the value at a specified index, and returns the value previously at that index.
     * @param index The {@code int} representing the index to change.
     * @param value The {@code String} to be placed at the specified index.
     * @return The value previously at the {@code int} <STRONG>index</STRONG> parameter before the call of set().
     * @throws IndexOutOfBoundsException If the index is out of range {@code < 0} or {@code >= size()} ).
     */
    public String set(int index, String value) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index " + index + " out of bounds for length " + size);
        
        DListNode pointer = nil.next; //A pointer starting at nil.next (the head) that will walk to the specified index.
        int count = 0; //A counter that will help determine when we arrive at the index.

        while (count < index) {
            count++;
            pointer = pointer.next;
        }

        String old = pointer.data;
        pointer.data = value;
        return old;
    }

    /**
     * Returns true if the specified object appears in the list and false otherwise.
     * @param obj The {@code Object} to search for in the Linked List.
     * @return {@code true} if the object is in the list, {@code false} if it does not.
     */
    public boolean contains(Object obj) {
        DListNode pointer = nil.next; //A pointer starting at nil.next (the head) that will continue until the end of the list.

        while (pointer != nil) { //If the pointer has made it to nil, then it has reached the end of the list.
            if (pointer.data.equals(obj)) { //If the pointer's data is same as the parameter, return true.
                return true;
            }
            pointer = pointer.next; //Otherwise, just advance the pointer.
        }

        return false;
    }

    /**
     * Returns the size of the Sentinel Linked List.
     * @return The size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the index of the specified object if it is in the list and -1 otherwise. 
     * @param obj The {@code Object} to search for in the Linked List.
     * @return A positive {@code int} representing the posistion of the object in the list, {@code -1} if it does not appear in the list.
     */

    public int indexOf(Object obj) {
        DListNode pointer = nil.next;
        int index = 0; //Counter to represent the index of the object.

        while (pointer != nil) {
            if (pointer.data.equals(obj)) { //Similar to contains.
                return index; //Return the index of the object.
            }
            index++; //Increment the index counter.
            pointer = pointer.next;
        }

        return -1;
    }

    /**
     * An iterator to be used on Sentinel Doubly Linked Lists. 
     * It uses a pointer that starts at {@code nil.next}, or the 'head' of the List.
     */
    private class DListIterator implements Iterator<String> {
        private DListNode pointer;
   
        public DListIterator() {
            pointer = nil.next;
        }

        public String next() {
            String curr = pointer.data;
            pointer = pointer.next;
            return curr;
        }

        public boolean hasNext() {
            return pointer != nil;
        }
    }

    public Iterator<String> iterator() {
        return new DListIterator();
    }
}
