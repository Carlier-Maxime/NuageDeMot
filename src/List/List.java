package List;

import java.util.Iterator;

public class List<E> implements Iterable<E>{
    private E element;
    private List<E> next;

    public List(E element, List<E> next) {
        this.element = element;
        this.next = next;
    }

    public List(E element) {
        this (element, null);
    }

    public List() {
        this(null,null);
    }

    public void add(E element){
        if (this.element==null){this.element = element; return;}
        List<E> prec = null;
        List<E> next = this.next;
        while (next!=null && next.getElement()!=null){prec = next; next = next.next;}
        if (prec==null){this.next = new List<>(element);}
        else if (next==null) {prec.next = new List<>(element);}
        else {next.element = element;}
    }

    public E getElement() {
        return element;
    }

    public E getElement(int i){
        if (next==null){return null;}
        return i>0? next.getElement(i - 1) : element;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private List<E> l = List.this;
            private boolean elemPassed = false;

            @Override
            public boolean hasNext() {
                return l!=null && ((!elemPassed && l.element!=null)|| l.next!=null);
            }

            @Override
            public E next() {
                if (elemPassed) {
                    l = l.next;
                    elemPassed = false;
                }
                elemPassed = true;
                return l.element;
            }
        };
    }

    public static void main(String[] args) {
        List<Integer> l = new List<Integer>();
        /*l.add(5);
        l.add(54);
        l.add(32);
        l.add(2);
        l.add(13);*/
        for (int n : l){
            System.out.println(n);
        }
    }
}
