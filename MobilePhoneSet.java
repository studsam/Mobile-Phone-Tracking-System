public class  MobilePhoneSet extends Myset<MobilePhone> {


      public LinkedList< MobilePhone> getElementList(){

         return (LinkedList< MobilePhone>)super.getElementList();
      }

      public void Insert( MobilePhone m){
        super.Insert( m);
      }
      public void Delete( MobilePhone m) throws ElementNotFoundException {
        super.Delete( m);
      }

      public  MobilePhoneSet Union( MobilePhoneSet a){
        Myset myunion =super.Union(a);
        MobilePhoneSet union = new MobilePhoneSet();
        union.setElementList(myunion.getElementList());
        return union;
      }

      public  MobilePhoneSet Intersection( MobilePhoneSet a){
        Myset myintersection =super.Intersection(a);
        MobilePhoneSet intersection = new MobilePhoneSet();
        intersection.setElementList(myintersection.getElementList());
        return intersection;
      }

      public MobilePhoneSet(){super();}

      /*public static void main(String[] args){

         MobilePhoneSet   set1 = new MobilePhoneSet ();
         MobilePhoneSet   set2 = new MobilePhoneSet ();
         System.out.println("SET A");
         set1.Insert(1);
         set1.Insert(2);
         set1.Insert(3);
         set1.Insert(4);
         set1.Delete(3);
         set1.Delete(-1);
         LinkedList list1 = set1.getElementList();
         LinkedList.Node head = list1.head();
         while(head!= null){

           System.out.println(head.getElement());
           head = head.getNext();
         }

         System.out.println("SET B");
         set2.Insert(2);
         set2.Insert(0);
         set2.Insert(1);
         set2.Delete(1);

         LinkedList list2 = set2.getElementList();
          head = list2.head();
         while(head!= null){

           System.out.println(head.getElement());
           head = head.getNext();
         }
         System.out.println("Printing the union");

         MobilePhoneSet set3 = set1.Union(set2);
         LinkedList list3 = set3.getElementList();
          head = list3.head();
         while(head!= null){

           System.out.println(head.getElement());
           head = head.getNext();
         }

         MobilePhoneSet   set4 = set1.Intersection(set2);
         LinkedList list4 = set4.getElementList();
          head = list4.head();
          System.out.println("Printing the intersection");
         while(head!= null){

           System.out.println(head.getElement());
           head = head.getNext();
         }


        return;

      }
      */
}
