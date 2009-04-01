/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.util;

/**
 * Represents a tuple of 4
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class Quadruple<T1, T2, T3, T4> implements ITuple {
    private T1 first;
    private T2 second;
    private T3 third;
    private T4 fourth;

    public Quadruple() {
    }

    public Quadruple(T1 first, T2 second, T3 third, T4 fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public T1 getFirst() {
        return first;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public T3 getThird() {
        return third;
    }

    public void setThird(T3 third) {
        this.third = third;
    }

    public T4 getFourth() {
        return fourth;
    }

    public void setFourth(T4 fourth) {
        this.fourth = fourth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quadruple)) return false;

        Quadruple quadruple = (Quadruple) o;

        if (first != null ? !first.equals(quadruple.first) : quadruple.first != null) return false;
        if (fourth != null ? !fourth.equals(quadruple.fourth) : quadruple.fourth != null) return false;
        if (second != null ? !second.equals(quadruple.second) : quadruple.second != null) return false;
        if (third != null ? !third.equals(quadruple.third) : quadruple.third != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (third != null ? third.hashCode() : 0);
        result = 31 * result + (fourth != null ? fourth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quadruple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                ", fourth=" + fourth +
                '}';
    }
}