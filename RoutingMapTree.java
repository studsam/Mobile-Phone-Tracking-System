public class RoutingMapTree{

    public Exchange root=null;
    public MobilePhoneSet allmobiles = new MobilePhoneSet();
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

         MobilePhone mobile = new MobilePhone(a);
         try{ temp = root.findMobile(mobile);// this line throws error if there is no mobile of number a;
          mobile = temp;
          switchOff(mobile);

         }
         catch(ElementNotFoundException e){
              ;
         }

         switchOn(mobile,bexchange);
         allmobiles.Insert(new MobilePhone(a));
      }
      catch(Exception e){answer+=("Error- No exchange with identifier "+b);}
      return answer;
    }

    public String switchOffMobile(int a){
     String answer="";
    try{
        MobilePhone mobile = root.findMobile(new MobilePhone(a));

          switchOff(mobile);
        //else
           //answer+=("Error- Mobile phone with identifier "+a+" is currently switched off");
     }

      catch(ElementNotFoundException|InvalidOperationException e) {
       boolean existed = hasExisted(new MobilePhone(a));
       if(!existed)
        answer+=("Error- No mobile phone with identifier "+a);
       else answer+=  ("Error- Mobile phone with identifier "+a+" is currently switched off");
       }
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
    try{
      Exchange exchange = findNode(a);

      try{
      Exchange childnode = exchange.child(b);
      answer+=childnode.getIdentifier();

      return answer;
      }
      catch(InvalidOperationException e){
        answer+=("Error- No "+b+" child of Exchange "+a);
        return answer;
      }
    }
    catch(ElementNotFoundException|InvalidOperationException e){answer+=("Error- No exchange with identifier "+a);}
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
		String[] inputstr = actionMessage.trim().split(" +");
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
      else if(inputstr[0].equals("movePhone")){

       answer+=  movePhone(Integer.parseInt(inputstr[1]),Integer.parseInt(inputstr[2]));
      }
      else if(inputstr[0].equals("queryFindCallPath")||inputstr[0].equals("findCallPath")){
        answer = "queryFindCallPath "+inputstr[1]+" "+inputstr[2]+": ";
        answer+= routeCall(Integer.parseInt(inputstr[1]),Integer.parseInt(inputstr[2]));
      }
      else if(inputstr[0].equals("queryLowestRouter")||inputstr[0].equals("lowestRouter")){
        answer = "queryLowestRouter "+inputstr[1]+" "+inputstr[2]+": ";
        answer+= lowestRouter(Integer.parseInt(inputstr[1]),Integer.parseInt(inputstr[2]));
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

       else if(inputstr[0].equals("queryFindPhone") || inputstr[0].equals("findPhone")){
         answer+="queryFindPhone"+" "+inputstr[1]+": ";
         answer += findPhone(Integer.parseInt(inputstr[1]));
       }


    }
    return answer;
	}


   public Exchange findPhone(MobilePhone m)throws ElementNotFoundException,InvalidOperationException{
      MobilePhone mobile = root.findMobile(m);
         return mobile.location();
    }
    public boolean hasExisted(MobilePhone m){
      try{allmobiles.find(m); return true;}
      catch(Exception e){;}
      return false;

    }
    public String findPhone(int m){
      String answer="";
      boolean existed = hasExisted(new MobilePhone(m));
      try{Exchange level0 = findPhone(new MobilePhone(m));
        answer+=level0.getIdentifier();
      }
      catch(Exception e){
        if(existed)answer+=("Error - Mobile phone with identifier "+m+" is currently switched off");
        else  answer+=("Error - No mobile phone with identifier "+m+" found in the network");
      }
      return answer;
    }


    public Exchange lowestRouter(Exchange a, Exchange b)throws Exception{

      if(a.getIdentifier()==b.getIdentifier())return a;
      ExchangeList path1 = new ExchangeList();
      ExchangeList path2 = new ExchangeList();

      Exchange iter1 = a;
      Exchange iter2 = b;

      while(iter1!=null){
        path1.addFirst(iter1);
        iter1 = iter1.getParent();
      }
      while(iter2!=null){
        path2.addFirst(iter2);
        iter2 = iter2.getParent();
      }

      LinkedList.Node<Exchange> anode = path1.head();
      LinkedList.Node<Exchange> bnode = path2.head();

      Exchange answer = null;
      while(anode!=null && bnode!=null ){
          if(anode.getElement().getIdentifier() == bnode.getElement().getIdentifier())answer = anode.getElement();
          else{
            break;
          }
          anode = anode.getNext();
          bnode = bnode.getNext();

      }
      if(answer==null)throw new Exception();
      return answer;

    }
    public String lowestRouter(int a, int  b){
     String answer="";
     try{
      Exchange aex   = findNode(a);
      Exchange bex   = findNode(b);
      Exchange lca =lowestRouter(aex,bex);
      answer += lca.getIdentifier();
     }

     catch(Exception e){
         try{
           Exchange aex = findNode(a);
           try{
             Exchange bex = findNode(b);
           }
           catch(Exception e0){
             answer+=("Error - No exchange with identifier "+b);
           }
         }
         catch(Exception e1){
            try{Exchange bex = findNode(b);
              answer+=("Error - No exchange with identifier "+a);
              }
            catch(Exception e2){
              answer+=("Error - No exchange with identifier "+a+" and no exchange with identifier "+b);
            }

         }
     }
     return answer;
    }

    public ExchangeList routeCall(MobilePhone a, MobilePhone b)throws ElementNotFoundException, InvalidOperationException,Exception{
         ExchangeList path = new ExchangeList();
         Exchange firstExchange = findPhone(a);
         Exchange secondExchange = findPhone(b);

         Exchange lca = lowestRouter(firstExchange,secondExchange);
         Exchange temp = secondExchange;
         ExchangeList tempList = new ExchangeList();

         while(temp.getIdentifier()!=lca.getIdentifier()){
           tempList.addFirst(temp);
           temp = temp.getParent();
         }
         tempList.addFirst(lca);
         temp = firstExchange;
         while(temp.getIdentifier()!= lca.getIdentifier()){
           path.addLast(temp);
           temp = temp.getParent();
         }
         while(!tempList.isEmpty()){
           path.addLast(tempList.removeFirst());
         }
         return path;
    }

    public String routeCall(int a, int b){
      String answer="";
      try{
       MobilePhone mobile1 = root.findMobile(new MobilePhone(a));
       MobilePhone mobile2 = root.findMobile(new MobilePhone(b));
       ExchangeList route = routeCall(mobile1,mobile2);
       LinkedList.Node<Exchange> node = route.head();
       while(node!=null){
         answer+=node.getElement().getIdentifier();
         if(node.hasNext())answer+=", ";
         node= node.getNext();
       }
      }
      catch(Exception e){
        int length;
        boolean aexist = hasExisted(new MobilePhone(a));
        boolean bexist = hasExisted(new MobilePhone(b));
        boolean aon = false;
        boolean bon = false;
        try{
          MobilePhone m1 = root.findMobile(new MobilePhone(a));
          aon = true;
        }
        catch(Exception e1){
          ;
        }
        try{
          MobilePhone m2 = root.findMobile(new MobilePhone(b));
          bon = true;
        }
        catch(Exception e2){;
        }
        answer+="Error - ";
        if(aon){
          if(bexist)answer+=("Mobile phone with identifier "+b+" is currently switched off");
          else answer+=("No mobile phone with identifier "+b+" found in the network");
        }
        else{
          if(aexist){answer+=("Mobile phone with identifier "+a+" is currently switched off");}
          else answer+=("No mobile phone with identifier "+a+" found in the network");

          if(!bon){
            if(bexist)answer+=(" and mobile phone with identifier "+b+" is currently switched off");
            else answer+=(" and no mobile phone with identifier "+b+" found in the network");
          }
          else;

        }

      }
      return answer;
    }

    public void movePhone (MobilePhone a, Exchange b)throws ElementNotFoundException,InvalidOperationException{

      b = findNode(b.getIdentifier());
      a = root.findMobile(a);
      switchOffMobile(a.getIdentifier());
      switchOnMobile(a.getIdentifier(),b.getIdentifier());

    }
   public String movePhone(int a, int b){
     String answer="";
     Exchange bexchange = new Exchange(b);
     MobilePhone amobile = new MobilePhone(a);
     boolean existed = hasExisted(new MobilePhone(a));
     try{movePhone(amobile,bexchange);}
     catch(Exception e){
       answer+="Error - ";
       int count = 0;
       try{
          Exchange bex = findNode(b);
       }
       catch(Exception e0){
         count++;
         answer+=("No exchange with identifier "+b);
       }
       try{
         MobilePhone mob = root.findMobile(amobile);
       }
       catch(Exception e1){
         if(!existed){
           if(count ==1){answer+=(" and no mobile phone with identifier "+a+" found in the network");}
           else answer+=("No mobile phone with identifier "+a+" found in the network");
         }
         else{
           if(count ==1){answer+=(" mobile phone with identifier "+a+" is currently switched off");}
           else answer+=("Mobile phone with identifier "+a+" is currently switched off");
         }
       }

     }
     return answer;
   }
}
