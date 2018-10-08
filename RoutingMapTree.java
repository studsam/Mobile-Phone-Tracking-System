public class RoutingMapTree{

    public Exchange root=null;
    public MobilePhoneSet allmobiles = null;
	  public RoutingMapTree() {
		//
	    root = new Exchange(0);
	  }

    public RoutingMapTree(Exchange node){
      root = node;
    }

    public boolean containsNode(Exchange a){
      if(root==null)return false;
      try{
        if(root.numChildren()==0){
          if(a.getIdentifier()==root.getIdentifier())return true;
        }
        else{
          for(int i = 1;i<= root.numChildren();i++){
            if(root.subtree(i).containsNode(a))return true;
          }
        }
      return false;
      }
      catch (Exception e){;}
      return false;
    }

    public Exchange findNode(int id) throws ElementNotFoundException,InvalidOperationException{
      Exchange answernode = null;
     if(root == null)return null;
     if(id==root.getIdentifier())return root;
     if(root.numChildren()==0){
       if(id == root.getIdentifier()) return root;
     }
     else{
       for(int i = 0; i< root.numChildren();i++){
         try{answernode = root.subtree(i).findNode(id);
         return answernode;}
         catch(Exception e){;}
       }
     }
      throw new ElementNotFoundException();

    }


    public void switchOn(MobilePhone a, Exchange b){
      a.switchOn();
      b.addMobile(a);// upadates all resident set
      a.setLocation(b);
    }
    public void switchOff(MobilePhone a) throws InvalidOperationException,ElementNotFoundException{
        a.location().deleteMobile(a);// the delete mobile method deletes it from the entire network
        a.switchOff();
        a.setLocation(null);
    }

    public String switchOnMobile(int a, int b){/* It will throw exception only when exchange b is not there*/
       String answer ="";
       try{
         Exchange bexchange = findNode(b);
          MobilePhone temp;
         //if(bexchange==null){System.out.println("No exchange found"); return;}
         MobilePhone mobile = new MobilePhone(a);
         try{ temp = root.findMobile(mobile);// this line throws error if there is no mobile of number b;
          mobile = temp;
          switchOff(mobile);
         }
         catch(ElementNotFoundException e){
           ;
         }
         switchOn(mobile,bexchange);
      }
      catch(Exception e){answer+=("Error- No exchange with identifier "+b);}
      return answer;
    }

    public String switchOffMobile(int a){
     String answer="";
    try{
        MobilePhone mobile = root.findMobile(new MobilePhone(a));
        switchOff(mobile);
     }

      catch(ElementNotFoundException|InvalidOperationException e) {answer+=("Error- No mobile phone with identifier "+a);}
     return answer;
     }


  public String addExchange(int a , int b){
     String answer="";
     try{
       Exchange node_b = findNode(b);
       answer+=("Error- Exchange with identifier "+b+" already exists");
      }
      catch(Exception error){
       try{
        Exchange node_a = findNode(a);
        Exchange exchange = new Exchange(b);

        node_a.addExchange(exchange);
       }

      catch(Exception e)
          {answer+=("Error- No exchange with identifier "+a);}
      }
      return answer;
  }


  public String queryNthChild(int a, int b){
    String answer="";
    try{Exchange exchange = findNode(a);

    //if(exchange == null)answer+="No exchange with identifier "+ a+" found in the network";

      //System.out.println(exchange.numChildren());
      try{
      Exchange childnode = exchange.child(b);// since we have been 1 based indexing in our code
      answer+=childnode.getIdentifier();
      //else answer+= b + "th child cannot be find ";
      return answer;
      }
      catch(InvalidOperationException e){
        answer+=("Error- No "+b+" child of Exchange "+a);
        return answer;
      }
    }
    catch(ElementNotFoundException|InvalidOperationException e){System.out.println("Error- No exchange with identifier "+a);}
    return answer;
  }

  public String queryMobilePhoneSet(int a){
    String answer="";
    try{
     Exchange exchange = findNode(a);


          LinkedList elements  = exchange.residentSet().getElementList();
          LinkedList.Node<MobilePhone> temp = elements.head();

          while(temp!=null){
            answer+=temp.getElement().getIdentifier();
            if(temp.getNext()!=null){
              answer+=", ";
            }
            temp = temp.getNext();
          }


    }
    catch(ElementNotFoundException|InvalidOperationException e){
       answer+=("Error- No exchange with identifier "+a);
     }
     return answer;
  }


	public String performAction(String actionMessage) {
		String[] inputstr = actionMessage.split(" ");
     String answer= "";
    if(inputstr.length == 3){
      if(inputstr[0].equals("addExchange")){

      answer+=addExchange(Integer.parseInt(inputstr[1]),Integer.parseInt(inputstr[2]));

      }
      else if(inputstr[0].equals("switchOnMobile")){

        answer+=switchOnMobile(Integer.parseInt(inputstr[1]),Integer.parseInt(inputstr[2]));
      }

      else if(inputstr[0].equals("queryNthChild")){
           answer = actionMessage+": ";
           answer=answer+queryNthChild(Integer.parseInt(inputstr[1]),Integer.parseInt(inputstr[2]));
      }
     
     }
    else if(inputstr.length == 2){
       if(inputstr[0].equals("queryMobilePhoneSet")){
         answer+=actionMessage+": ";
          answer+=queryMobilePhoneSet(Integer.parseInt(inputstr[1]));
       }
       else if(inputstr[0].equals("switchOffMobile")){

         answer+=switchOffMobile(Integer.parseInt(inputstr[1]));
       }

     
    }
    return answer;
	}


}
