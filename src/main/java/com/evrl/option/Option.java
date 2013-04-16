package com.evrl.option;

import com.google.common.base.Function;

public abstract class Option<T> implements Iterable<T> {

    /**
     * Explicitely create a None value.
     * @param <A>
     * @return
     */
    public static<A> Option<A> none() { return new None<A>(); }

    /**
     * Create an option.
     * @param value
     * @param <A>
     * @return
     */
    public static<A> Option<A> of(A value) {
        if (value == null) {
            return new None<A>();
        } else {
            return new Some(value);
        }
    }

    /**
     * Syntactic sugar for of(). So you can write "Option.of(foo)" or "some(foo)".
     */
    public static<A> Option<A> some(A value) { return of(value); }

    /**
     * Lift a function A -> B to A -> Option<B>
     */
    public static<A,B> Function<A,Option<B>> lift(final Function<A,B> f) {
        return new Function<A, Option<B>>() {
            @Override
            public Option<B> apply(A a) {
                return some(f.apply(a));
            }
        };
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
