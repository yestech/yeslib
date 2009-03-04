/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.lang;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Implmentation of a {@link java.util.Set} backed by an {@link java.util.ArrayList}.
 *
 */
public class ArraySet<E> extends AbstractSet<E> implements Set<E>,
        Cloneable, Serializable {

    private ArrayList<E> backingList;

    public ArraySet() {
        this(0);
    }

    public ArraySet(int size) {
        super();
        backingList = new ArrayList<E>(size);
    }

    public ArraySet(Collection c) {
        this(c, 0);
        addAll(c);
    }

    public ArraySet(Collection c, int size) {
        this(size);
        addAll(c);
    }

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection.
     */
    public Iterator<E> iterator() {
        return backingList.listIterator();
    }

    /**
     * Returns the number of elements in this collection.  If the collection
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this collection.
     */
    public int size() {
        return backingList.size();
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements.
     */
    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     *
     * @param o element whose presence in this set is to be tested.
     * @return <tt>true</tt> if this set contains the specified element.
     */
    public boolean contains(Object o) {
        return backingList.contains(o);
    }

    /**
     * Adds the specified element to this set if it is not already
     * present.
     *
     * @param o element to be added to this set.
     * @return <tt>true</tt> if the set did not already contain the specified
     * element.
     */
    public boolean add(E o) {
        boolean found = contains(o);
        if (!found) {
            backingList.add(o);
        }
        return found;
    }

    /**
     * Removes the given element from this set if it is present.
     *
     * @param o object to be removed from this set, if present.
     * @return <tt>true</tt> if the set contained the specified element.
     */
    public boolean remove(Object o) {
        boolean found = contains(o);
        if (found) {
            backingList.remove(o);
        }
        return found;
    }

    /**
     * Removes all of the elements from this set.
     */
    public void clear() {
        backingList.clear();
    }

    /**
     * Returns a shallow copy of this <tt>ArraySet</tt> instance: the elements
     * themselves are not cloned.
     *
     * @return a shallow copy of this set.
     */
    public Object clone() {
        try {
            ArraySet newSet = (ArraySet) super.clone();
            newSet.backingList = (ArrayList<Object>) backingList.clone();
            return newSet;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    /**
     * Save the state of this <tt>ArraySet</tt> instance to a stream (that is,
     * serialize this set).
     *
     * @serialData The size of the set (the number of elements it contains)
     *		   (int), followed by all of its elements (each an Object) in
     *             no particular order.
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out size
        s.writeInt(backingList.size());

        // Write out all elements in the proper order.
        for (Iterator<E> i = backingList.listIterator(); i.hasNext();)
            s.writeObject(i.next());
    }

    /**
     * Reconstitute the <tt>ArraySet</tt> instance from a stream (that is,
     * deserialize it).
     */
    private synchronized void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in size
        int size = s.readInt();
        backingList = new ArrayList<E>(size);

        // Read in all elements in the proper order.
        for (int i = 0; i < size; i++) {
            E e = (E)s.readObject();
            backingList.add(e);
        }
    }

}
