package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private List<T> elements;
    private Predicate<T> filter;
    
    public IterableWithPolicyImpl(T[] elements) {
        this(elements, x -> true);
    }

    public IterableWithPolicyImpl(T[] elements, Predicate<T> filter) {
        this.elements = List.of(elements);
        this.filter = filter;
    }

    @Override
    public void setIterationPolicy(Predicate filter) {
        this.filter = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new FilteredIteratorImpl();
    }

    private class FilteredIteratorImpl implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            while (index < elements.size() && !filter.test(elements.get(index))) {
                index++;
            }
            return index < elements.size();
        }

        @Override
        public T next() {
            return elements.get(index++);
        }

    }
    
}
