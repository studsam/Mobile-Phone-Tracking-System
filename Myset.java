public class Myset <E>{

   private LinkedList<E> elements = new LinkedList<>();

   public boolean IsEmpty(){
     return elements.isEmpty();
   }

   public LinkedList<E> getElementList(){return elements;}
   public void setElementList(LinkedList<E> ll){elements = ll;}
   public void Insert(E o){
     try{E element = find(o);
     }
     catch(ElementNotFoundException e){
     elements.addLast(o);
     }
   }
   public E find(E e)throws ElementNotFoundException{
     E element= elements.find(e);
     if(element == null) throw new ElementNotFoundException();
     return element;
   }

   public void Delete(E o) throws ElementNotFoundException {

        LinkedList.Node  tempnode = elements.head();



      	   if(tempnode==null)throw new ElementNotFoundException();

      	   if( tempnode.getElement().equals(o)) {elements.removeFirst();return;}

      	   else{

      	     while(tempnode.getNext()!=null){
      	       if(tempnode.getNext().getElement().equals(o)){
      	                 tempnode.setNext(tempnode.getNext().getNext());
                         elements.setSize(elements.size()-1);
                 return;

          	   }
      	        tempnode = tempnode.getNext();
      	     }

      	   }
           throw new ElementNotFoundException();
   }

   public Myset Union(Myset a){

      Myset union = new Myset();

     LinkedList.Node  node = elements.head();
     if(elements.size()!=0){
           while( node!= null){

      	    union.getElementList().addLast(node.getElement());
      	     node = node.getNext();
      	   }

     }
     node = a.getElementList().head();
     if(node!=null){

        while(node!=null){

      	  if(!union.getElementList().isMember(node.getElement()))union.getElementList().addLast(node.getElement());
      	  node = node.getNext();
      	}
     };

      return union;
   }

   public Myset<E> Intersection(Myset<E> a){

      Myset intersection = new Myset();

      LinkedList.Node  node1 = elements.head();
      LinkedList.Node  node2 = a.getElementList().head();

      if(node1==null || node2==null) return intersection;

      while(node1!=null){
        node2 = a.getElementList().head();
            while(node2!= null){
            	 if( node1.getElement().equals(node2.getElement())){
            	   intersection.getElementList().addLast(node1.getElement());

            	   }
            	 node2 = node2.getNext();
        	  }
             node1 = node1.getNext();
       }
           return intersection;
   }

}
