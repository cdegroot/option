package com.evrl.option;


import java.util.Iterator;

import com.google.common.base.Function;

public class Some<A> extends Option<A> {
    final A value;

    public Some(A value) {
        this.value = value;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public A orElse(A noneValue) {
        return value;
    }

    @Override
    public A get() {
        return value;
    }

    @Override
    public <B> Option<B> bind(Function<A, Option<B>> f) {
        return f.apply(value);
    }

    @Override
    public Iterator<A> iterator() {
        return new SomeIterator();
    }

    class SomeIterator implements Iterator<A> {
        boolean hasNext = true;

        @Override
        public boolean hasNext() {
            boolean prev = hasNext;
            hasNext = false;
            return prev;
        }

        @Override
        public A next() {
            return value;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void remove() {

        }
    }
}
