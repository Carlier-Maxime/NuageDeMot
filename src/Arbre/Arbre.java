package Arbre;

import java.util.Iterator;
import java.util.function.ToIntBiFunction;

public class Arbre<E> implements Iterable<E>{
    //attribute
    private Feuille<E> root;
    private ToIntBiFunction<E, E> compare;

    //constructor
    public Arbre(ToIntBiFunction<E, E> compare) {
        root = null;
        this.compare = compare;
    }

    public Arbre(ToIntBiFunction<E, E> compare, E[] array){
        this(compare);
        add(array);
    }

    //methode
    public boolean add(E object){
        if (root == null){root = new Feuille<>(object, null);}
        Feuille<E> feuille = root;
        Feuille<E> parent = null;
        int i=0;
        while (feuille != null){
            i = compare.applyAsInt(object,feuille.getObject());
            if (i==0){return false;}
            else if (i<0){
                parent = feuille;
                feuille = feuille.getLeftChild();
            }
            else {
                parent = feuille;
                feuille = feuille.getRightChild();
            }
        }
        feuille = new Feuille<E>(object, parent);
        if (i<0){parent.setLeftChild(feuille);}
        else {parent.setRightChild(feuille);}
        equilibre(feuille);
        return true;
    }

    public E get(E object){
        Feuille<E> feuille = root;
        while (feuille != null && feuille.getObject() != null){
            int i = compare.applyAsInt(object,feuille.getObject());
            if (i==0){return feuille.getObject();}
            else if (i<0){feuille = feuille.getLeftChild();}
            else {feuille = feuille.getRightChild();}
        }
        return null;
    }

    public boolean add(E[] array){
        boolean noProblem = true;
        if (array==null){return false;}
        for (E elem : array){
            if (!add(elem)){
                noProblem = false;
            }
        }
        return noProblem;
    }

    public void remove(E object){
        Feuille<E> feuille = root;
        while (feuille != null && feuille.getObject() != null){
            int i = compare.applyAsInt(object,feuille.getObject());
            if (i==0){break;}
            else if (i<0){feuille = feuille.getLeftChild();}
            else {feuille = feuille.getRightChild();}
        }
        if (feuille != null && object==feuille.getObject()) {
            remove(feuille);
        }
    }

    public void remove(Feuille<E> feuille){
        boolean isRemove = true;
        Feuille<E> parent = feuille.getParent();
       if (feuille.getLeftChild() == null && feuille.getRightChild() == null){
           if (feuille.getParent() == null){
               root = null;
           } else {
               if (feuille == parent.getLeftChild()){
                   parent.setLeftChild(null);
               } else {
                   parent.setRightChild(null);
               }
           }
       } else if (feuille.getLeftChild() == null || feuille.getRightChild() == null){
           Feuille<E> child;
           if (feuille.getLeftChild() != null){
               child = feuille.getLeftChild();
           } else {
               child = feuille.getRightChild();
           }
           child.setParent(parent);
           if (parent == null){
               root = child;
           } else {
               if (feuille == parent.getLeftChild()){
                   parent.setLeftChild(child);
               } else {
                   parent.setRightChild(child);
               }
           }
       } else {
           Feuille<E> child = feuille.getRightChild();
           while (child.getLeftChild() != null){
               child = child.getLeftChild();
           }
           feuille.setObject(child.getObject());
           remove(child);
           isRemove = false;
       }
       if (isRemove && parent != null){
           equilibre(parent);
       }
    }

    private void rotateLeft(Feuille<E> feuille){
        Feuille<E> parent = feuille.getParent();
        Feuille<E> rightChild = feuille.getRightChild();
        Feuille<E> leftChildOfRightChild = feuille.getRightChild().getLeftChild();
        if (leftChildOfRightChild != null){leftChildOfRightChild.setParent(feuille);}
        rightChild.setLeftChild(feuille);
        rightChild.setParent(parent);
        feuille.setRightChild(leftChildOfRightChild);
        finishRotate(feuille, parent, rightChild);
    }

    private void rotateRight(Feuille<E> feuille){
        Feuille<E> parent = feuille.getParent();
        Feuille<E> leftChild = feuille.getLeftChild();
        Feuille<E> rightChildOfLeftChild = feuille.getLeftChild().getRightChild();
        if (rightChildOfLeftChild != null){rightChildOfLeftChild.setParent(feuille);}
        leftChild.setRightChild(feuille);
        leftChild.setParent(parent);
        feuille.setLeftChild(rightChildOfLeftChild);
        finishRotate(feuille, parent, leftChild);
    }

    private void finishRotate(Feuille<E> feuille, Feuille<E> parent, Feuille<E> child) {
        feuille.setParent(child);
        if (parent != null){
            if (parent.getLeftChild() == feuille){parent.setLeftChild(child);}
            else {parent.setRightChild(child);}
        } else {
            root = child;
        }
        feuille.updateDepth();
        while (feuille.getParent() != null){
            feuille = feuille.getParent();
            feuille.updateDepth();
        }
    }

    private void equilibre(Feuille<E> feuille){
        feuille.updateDepth();
        if (feuille.getDiffDepth()==-2){
            if (feuille.getLeftChild().getDiffDepth() == -1){
                rotateRight(feuille);
            } else if (feuille.getLeftChild().getDiffDepth() == 1){
                rotateLeft(feuille.getLeftChild());
                rotateRight(feuille);
            } else if (feuille.getLeftChild().getDiffDepth() == 0){
                rotateRight(feuille);
            }
        } else if (feuille.getDiffDepth()==2) {
            if (feuille.getRightChild().getDiffDepth() == -1) {
                rotateRight(feuille.getRightChild());
                rotateLeft(feuille);
            } else if (feuille.getRightChild().getDiffDepth() == 1) {
                rotateLeft(feuille);
            } else if (feuille.getRightChild().getDiffDepth() == 0){
                rotateLeft(feuille);
            }
        }
        while (feuille.getParent() != null){
            feuille = feuille.getParent();
            feuille.updateDepth();
            if (feuille.getDiffDepth()<-1 || feuille.getDiffDepth()>1){
                equilibre(feuille);
            }
        }
    }

    private Feuille<E> getMinFeuille(){
        Feuille<E> feuille = root;
        while (feuille.getLeftChild() != null){
            feuille  = feuille.getLeftChild();
        }
        return feuille;
    }

    @Override
    public String toString() {
        return "Arbre :\n"+root.toString()+"\n";
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Feuille<E> feuille = getMinFeuille();
            private boolean right = false;

            @Override
            public boolean hasNext() {
                Feuille<E> child;
                while (right && feuille.getParent() != null){
                    child = feuille;
                    feuille = feuille.getParent();
                    if (feuille.getRightChild() != child){
                        right = false;
                    }
                }
                return !(feuille.getParent() == null && right);
            }

            @Override
            public E next() {
                E object = feuille.getObject();
                if (!right){
                    if (feuille.getRightChild() != null){
                        feuille = feuille.getRightChild();
                        while (feuille.getLeftChild() != null){
                            feuille = feuille.getLeftChild();
                        }
                    } else {
                        right = true;
                    }
                }
                return object;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public E getRootObject(){
        return root.getObject();
    }

    public static void main(String[] args) {
        Arbre<Integer> arbre = new Arbre<>(Integer::compare);
        arbre.add(10);
        arbre.add(5);
        arbre.add(6);
        arbre.add(12);
        arbre.add(14);
        arbre.add(8);
        arbre.add(4);
        arbre.add(3);
        System.out.println(arbre);
        System.out.println(arbre.get(8));
        System.out.println(arbre.get(69));
        arbre.remove(8);
        arbre.remove(12);
        arbre.remove(4);
        arbre.remove(10);
        System.out.println(arbre);
    }
}
