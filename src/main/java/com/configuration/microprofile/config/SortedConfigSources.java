package com.configuration.microprofile.config;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.*;
import java.util.stream.Stream;

public class SortedConfigSources implements List<ConfigSource> {

    private List<ConfigSource> list = new ArrayList<>();
    private Comparator<ConfigSource> configSourceComparator;

    public SortedConfigSources(Comparator<ConfigSource> configSourceComparator) {
        this.configSourceComparator = configSourceComparator;
    }


    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<ConfigSource> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray(new Object[list.size() - 1]);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(ConfigSource configSource) {
        list.add(configSource);
        Collections.sort(list, this.configSourceComparator);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ConfigSource> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends ConfigSource> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public ConfigSource get(int index) {
        return null;
    }

    @Override
    public ConfigSource set(int index, ConfigSource element) {
        return null;
    }

    @Override
    public void add(int index, ConfigSource element) {

    }

    @Override
    public ConfigSource remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<ConfigSource> listIterator() {
        return null;
    }

    @Override
    public ListIterator<ConfigSource> listIterator(int index) {
        return null;
    }

    @Override
    public List<ConfigSource> subList(int fromIndex, int toIndex) {
        return null;
    }
}
