package com.evrl.option;

import java.util.Iterator;

import com.google.common.base.Function;

public class None<T> extends Option<T> {

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public T orElse(T noneValue) {
        return noneValue;
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public <B> Option<B> bind(Function<T, Option<B>> f) {
        return Option.none();
    }

    @Override
    public Iterator<T> iterator() {
        return new NoneIterator();
    }

    class NoneIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public void remove() {
        }
    }
}
