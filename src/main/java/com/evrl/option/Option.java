package com.evrl.option;

import com.google.common.base.Function;

public abstract class Option<T> implements Iterable<T> {

    public static<A> Option<A> none() { return new None<A>(); }

    public static<A> Option<A> of(A value) {
        if (value == null) {
            return new None<A>();
        } else {
            return new Some(value);
        }
    }

    /**
     * Check whether a value is available in this option.
     *
     * @return true if a value is available, otherwise false.
     */
    public abstract boolean isAvailable();

    /**
     * Provide a default value in case this is a None.
     *
     * @param noneValue the default value to return.
     * @return the option's value, or the noneValue if the option is None
     */
    public abstract T orElse(T noneValue);


    /**
     * Returns the value, if available.
     *
     * @return the value or null if it is not available.
     */
    public abstract T get();

    /**
     * To be a proper monad, we implement bind()
     *
     * @param f the function to bind
     * @param <B> type of the returned option
     * @return an option representing the result of the bind
     */
    public abstract<B> Option<B> bind(Function<T, Option<B>> f);

}
