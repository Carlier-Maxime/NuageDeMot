package Arbre;

public class Feuille<E> {
    //attribute
    private Feuille<E> parent;
    private Feuille<E> leftChild;
    private Feuille<E> rightChild;
    private E object;
    private int depth;

    //constructor
    public Feuille(E object, Feuille<E> parent, Feuille<E> leftChild, Feuille<E> rightChild) {
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.object = object;
        this.depth = 0;
    }

    public Feuille(E object, Feuille<E> parent) {
        this(object, parent, null, null);
    }

    //methode
    public E getObject() {
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }

    public Feuille<E> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Feuille<E> leftChild) {
        this.leftChild = leftChild;
    }

    public Feuille<E> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Feuille<E> rightChild) {
        this.rightChild = rightChild;
    }

    public Feuille<E> getParent() {
        return parent;
    }

    public void setParent(Feuille<E> parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }
    
    private int getLeftDepth(){
        int leftDepth;
        if (leftChild == null){leftDepth=0;}
        else {leftDepth=leftChild.getDepth()+1;}
        return leftDepth;
    }

    private int getRightDepth(){
        int rightDepth;
        if (rightChild == null){rightDepth=0;}
        else {rightDepth=rightChild.getDepth()+1;}
        return rightDepth;
    }

    public void updateDepth(){
        int leftDepth = getLeftDepth();
        int rightDepth = getRightDepth();
        depth = Math.max(leftDepth, rightDepth);
    }

    public int getDiffDepth(){
        int leftDepth = getLeftDepth();
        int rightDepth = getRightDepth();
        return rightDepth-leftDepth;
    }

    @Override
    public String toString() {
        Feuille<E> parent = this;
        while (parent.getParent() != null){
            parent = parent.getParent();
        }
        int n = parent.depth-depth;
        return "Feuille{" +
                '\n'+"\t".repeat(n)+"object=" + object +
                '\n'+"\t".repeat(n)+"leftChild=" + leftChild +
                '\n'+"\t".repeat(n)+"rightChild=" + rightChild +
                '\n'+"\t".repeat(n)+"}";
    }
}
