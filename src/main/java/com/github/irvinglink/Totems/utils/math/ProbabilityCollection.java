package com.github.irvinglink.Totems.utils.math;

import com.github.irvinglink.Totems.utils.conditions.Preconditions;

import java.util.*;


public final class ProbabilityCollection<E> {

    private final NavigableSet<ProbabilitySetElement<E>> collection;
    private final SplittableRandom random = new SplittableRandom();

    private int totalProbability;


    public ProbabilityCollection() {
        this.collection = new TreeSet<>(Comparator.comparingInt(ProbabilitySetElement::getIndex));
        this.totalProbability = 0;
    }


    public int size() {
        return this.collection.size();
    }


    public boolean isEmpty() {
        return this.collection.isEmpty();
    }


    public boolean contains(E object) {

        if (object == null) throw new IllegalArgumentException("Cannot check if null object is contained in this collection");

        return this.collection.stream().anyMatch(entry -> entry.getObject().equals(object));
    }


    public Iterator<ProbabilitySetElement<E>> iterator() {
        return this.collection.iterator();
    }


    public void add(E object, int probability) {

        if (Preconditions.checkNotNull(object)) throw new IllegalArgumentException("Cannot add null object");
        if (probability <= 0) throw new IllegalArgumentException("Probability must be greater than 0");


        ProbabilitySetElement<E> entry = new ProbabilitySetElement<>(object, probability);
        entry.setIndex(this.totalProbability + 1);

        this.collection.add(entry);
        this.totalProbability += probability;
    }


    public boolean remove(E object) {
        if (Preconditions.checkNotNull(object)) throw new IllegalArgumentException("Cannot remove null object");


        Iterator<ProbabilitySetElement<E>> it = this.iterator();
        boolean removed = false;

        // Remove all instances of the object
        while (it.hasNext()) {
            ProbabilitySetElement<E> entry = it.next();
            if (entry.getObject().equals(object)) {
                this.totalProbability -= entry.getProbability();
                it.remove();
                removed = true;
            }
        }

        // Recalculate remaining elements "block" of space: i.e 1-5, 6-10, 11-14
        if (removed) {
            int previousIndex = 0;
            for (ProbabilitySetElement<E> entry : this.collection) {
                previousIndex = entry.setIndex(previousIndex + 1) + (entry.getProbability() - 1);
            }
        }

        return removed;
    }


    public void clear() {
        this.collection.clear();
        this.totalProbability = 0;
    }


    public E get() {
        if (this.isEmpty()) throw new IllegalStateException("Cannot get an object out of a empty collection");

        ProbabilitySetElement<E> toFind = new ProbabilitySetElement<>(null, 0);
        toFind.setIndex(this.random.nextInt(1, this.totalProbability + 1));

        return Objects.requireNonNull(Objects.requireNonNull(this.collection.floor(toFind)).getObject());

    }


    public int getTotalProbability() {
        return this.totalProbability;
    }


    public final static class ProbabilitySetElement<T> {
        private final T object;
        private final int probability;
        private int index;


        protected ProbabilitySetElement(T object, int probability) {
            this.object = object;
            this.probability = probability;
        }

        public T getObject() {
            return this.object;
        }

        public int getProbability() {
            return this.probability;
        }

        private int getIndex() {
            return this.index;
        }

        private int setIndex(int index) {
            this.index = index;
            return this.index;
        }

    }

}