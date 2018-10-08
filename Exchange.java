public class Exchange{
   private int identifier;
   private Exchange parent=null;
   private ExchangeList childlist= new ExchangeList();
   private int size = 0;

   private MobilePhoneSet residentSet = new MobilePhoneSet();

   public MobilePhoneSet  residentSet(){
     return residentSet;
   }
   public int getIdentifier(){
     return identifier;
   }
   public Exchange(int number){
     identifier = number;
   }
   public Exchange getParent(){
     return parent;
   }
   public void setParent(Exchange newparent){
     parent = newparent;
   }

   public int numChildren(){
     return size;
   }
   public MobilePhone findMobile(MobilePhone m) throws ElementNotFoundException{
     return residentSet.find(m);
   }

   public boolean hasPhone(MobilePhone m){
     try{MobilePhone mobile = residentSet.find(m);
     return true;}
     catch(Exception e)
     {return false;}

   }

   public Exchange child(int i) throws InvalidOperationException{// 1 based indexing

    if(i<0 || i>=size)throw new InvalidOperationException();

    LinkedList.Node<Exchange> temp = childlist.head();
     int count = 0;
     while(temp!=null){
       if(count == i){
         return temp.getElement();
       }
       count++;
       temp = temp.getNext();
     }
     return null;
   }
   public boolean isRoot(){
     return parent == null;
   }

   public RoutingMapTree subtree(int i) throws InvalidOperationException{
     Exchange node;
     RoutingMapTree sub_tree;

       node = this.child(i);


       sub_tree= new RoutingMapTree(node);

      return sub_tree;

   }

   public void addMobile(MobilePhone m){
     if(this == null)return;
     residentSet.Insert(m);
      Exchange node = this.getParent();
     while(node!= null){

       node.addMobile(m);
       node = node.getParent();

     }
   }
   public void deleteMobile(MobilePhone m) throws InvalidOperationException,ElementNotFoundException{
     residentSet.Delete(m);
     Exchange node = this.getParent();
     if(node!=null)node.deleteMobile(m);
   }

   public void addExchange(Exchange node){
     childlist.addLast(node);
     node.setParent(this);
     size++;
   }

   public boolean hasChild(Exchange b){
     LinkedList.Node<Exchange> temp = childlist.head();
      while(temp!=null){
        if(temp.getElement().getIdentifier()==b.getIdentifier())return true;
        temp = temp.getNext();
      }
      return false;


   }


}
