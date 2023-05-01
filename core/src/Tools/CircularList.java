package Tools;


import java.util.ArrayList;

public class CircularList<E> {

    ArrayList<E> elements;
    int currentSize;
    int maxSize;
    int currentIndex;
    int lastIndex;

    public CircularList(int maxSize){
        this.maxSize=maxSize;
        currentSize=0;
        currentIndex=-1;
        lastIndex=-1;
        elements=new ArrayList<>();
    }

    public void addElement(E item){

        lastIndex++;
        elements.add(item);
        currentSize++;

        if (currentIndex==-1){currentIndex=0;}
    }

    public E removeElement(){
        if (Empty()){ return null;}
        E toReturn=elements.get(lastIndex);
        lastIndex--;
        currentSize--;
        elements.remove(toReturn);
        return toReturn;
    }

    public E nextItem(){
        if (Empty()) {return null;}
        E toReturn=elements.get(currentIndex);
        currentIndex++;
        if (currentIndex >= currentSize){
            currentIndex=0;
        }
        return toReturn;
    }
    public E peekNextItem(){
        if (Empty()) {return null;}
        return elements.get(currentIndex);
    }
    public int getCurrentSize() {return currentSize;}
    public ArrayList<E> allElems(){
        return elements;
    }

    public boolean Empty(){
        return (currentSize==0);
    }
}
