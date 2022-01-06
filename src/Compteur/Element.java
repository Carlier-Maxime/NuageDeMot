package Compteur;

public class Element{
    //attribute
    public String word;
    public int nb;

    //constructor
    public Element(String word){
        this.word = word;
        this.nb = 1;
    }

    //methode
    @Override
    public String toString() {
        return "Element{" +
                "word='" + word + '\'' +
                ", nb=" + nb +
                '}';
    }

    public static int compare(Element e1, Element e2){
        return e1.word.compareTo(e2.word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (nb != element.nb) return false;
        return word.equals(element.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
