public class MobilePhone{

   private int number;
   private boolean switched_on = false ;
   private Exchange baseExchange = null;

   public MobilePhone(int num){

      number = num;
   }

   public void switchOn(){

      switched_on = true;
      //////
   }
   public void switchOff(){
     switched_on = false;

   }
   public int getIdentifier(){
     return number;
   }

   public boolean status(){return switched_on;}

   public Exchange location() throws InvalidOperationException {
    if(status())return baseExchange;
    throw new InvalidOperationException();

   }

   public void setLocation(Exchange b){
     baseExchange = b;
   }

   public boolean equals(Object o){
     if(this == o)return true;

     if(o == null)return false;

     if( getClass() != o.getClass())return false;

     MobilePhone mobile = (MobilePhone) o;

     return mobile.getIdentifier() == number;
   }



}
