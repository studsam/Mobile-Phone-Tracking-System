public class LinkedList<E>{

  public static class Node<E>{

     private E element;
     private Node<E> next;
     public Node(E e, Node<E> n){
       element = e;
       next = n;
     }

     public E getElement(){return element;}
     public Node<E> getNext(){return next;}
     public boolean hasNext(){ return getNext()!=null;}
     public void setNext(Node<E> n){next = n;}

  }

  private Node<E> head = null;
  private Node<E> tail = null;
  private int size = 0;
  public LinkedList(){};

  public Node<E> head() {return head;}
  public Node<E> tail() {return tail;}
  public int size(){return size;}
  public boolean isEmpty(){return size==0;}
  public E first(){
    if(isEmpty())return null;
    return head.getElement();
  }


  public E last(){
    if(isEmpty())return null;
    return tail.getElement();
  }

  public void setSize(int siz){size = siz;}

  public void addFirst(E e){

     head = new Node<>(e,head);
     if(size==0)tail=head;
     size++;
  }

  public void addLast(E e){

     Node<E> newest = new Node<> (e,null);
     if(isEmpty())head = newest;
     else tail.setNext(newest);

     tail = newest;
     size++;
  }

  public E removeFirst(){

    if(isEmpty())return null;
    E answer = head.getElement();
    head = head.getNext();
    size--;
    if(size==0)
	    tail = null;
    return answer;
  }

  public boolean isMember(E e){
   if(head == null) return false;
   Node <E> temp = head;
   while(temp!=null){
     if(temp.getElement().equals(e))return true;
     temp = temp.getNext();
   }
   return false;
  }

  public E find(E e) throws ElementNotFoundException {
    if(head == null)throw new ElementNotFoundException();
    Node <E> temp = head;
    while(temp != null){
      if(temp.getElement().equals(e))return temp.getElement();
      temp = temp.getNext();
    }
    throw new ElementNotFoundException();

  }


}
