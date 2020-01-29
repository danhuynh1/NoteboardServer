import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



class Note {

 int x;
 int y;
 int layer = 0;
 int height;
 int width;
 String comment;
 String color;
 boolean ispinned;

 public Note(int x, int y, int height, int width, String color, String comment) {
  this.x = x;
  this.y = y;
  this.height = height;
  this.width = width;
  this.comment = comment;
  this.color = color;
  this.ispinned = false;
 }
 public String toString() {

  return ("["+x+","+y+ "]"+"("+width+"x"+height+")"+" color="+color+" comment="+comment+" ");
 }

}

class Pin {

 int x;
 int y;

 public Pin(int x, int y) {
  this.x = x;
  this.y = y;
 }
 public String toString() {

	  return ("["+x+","+y+ "]");
	 }
}


public class sboard {
 public static int boardheight;
 public static int boardwidth;
 public static int numberofcolors;
 public static int port;
 public static ArrayList<String> colors = new ArrayList<String>();
 public static ArrayList <Pin> pins = new ArrayList <Pin> ();
 public static ArrayList <Note> notes = new ArrayList <Note> ();


 public static void main(String[] args) throws Exception {


  port = Integer.parseInt(args[0]);
  boardwidth = Integer.parseInt(args[1]);
  boardheight = Integer.parseInt(args[2]);

  for (int i = 3; i < args.length; i++) {
  colors.add(args[i]);
  }

  System.out.println("Noteboard server is running on port: "+port);
  
  int clientNumber = 0;
  
  
  ServerSocket listener = new ServerSocket(port);
  try {
      while (true) {
          new board(listener.accept(), clientNumber++).start();
      }
  } finally {
      listener.close();
  }

 }


 private static class board extends Thread {
     private Socket socket;
     private int clientNumber;
     
     
     public board	(Socket socket, int clientNumber) {
         this.socket = socket;
         this.clientNumber = clientNumber;
         log("New connection with client# " + clientNumber + " at " + socket);
     }
     
     public void run() {
         try {
             BufferedReader in = new BufferedReader(
             new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

             // Send a welcome message to the client.
             out.println("Hello, you are client #" + clientNumber + ".");

             for (String color : colors) {   
            	    out.println(color);
            	}
             
             while (true) {
                 String input = in.readLine();
                 if (input == null || input.equals("")) {
                     break;
                 }
                 String[] query = input.split(" ");
                 //
                 if (query[0].equals("POST")) {
                	int x = Integer.parseInt(query[1]);
                	int y = Integer.parseInt(query[2]);
                	int width = Integer.parseInt(query[3]);
                	int height = Integer.parseInt(query[4]);
                	
                	 
                  if (query.length >= 6 && (x+width) <= boardwidth && (y+height) <= boardheight) {
                   String[] commentarray = Arrays.copyOfRange(query, 6, query.length);
                   String comment = String.join(" ", commentarray);

                   Note newnote = CreateNote(x, y, width, height, query[5], comment);
                   
                   
                   notes.add(newnote);

                   // System.out.println(i);


                   out.println(newnote.toString()+ "has been posted.");

                  } else {
                    out.println("Post not on board or too big.");
                 }
                  
                 }else if (query[0].equals("GET")) {
                   if (query[1].equals("PINS")) {
                   if (pins.isEmpty()==false) {
                	   for (Pin pin: pins) {
                           out.println(pin.toString());
                          }
                   }else {
                	   out.println("There aren't any pins");
                   }
        
                  } else {
                	 	if(notes.isEmpty()==false) {
                  	       String color = null;
                	       int x = -1;
                	       int y = -1;
                	       String comment = null;
                	 
                	       if (input.contains("color=")) {
                	        color = query[1].substring(6, query[1].length());
                	       }
                	    
                	       if (input.contains("contains=")) {
                	        if (input.contains("color=")) {
                	         x = Integer.parseInt((query[2].substring(9, query[2].length())));
                	         y = Integer.parseInt((query[3]));
                	         
                	        } else {
                	 
                	         x = Integer.parseInt((query[1].substring(9, query[1].length())));
                	         y = Integer.parseInt((query[2]));
                	 
                	        }
                	       }
                	 
                	 
                	       if (input.indexOf("refersTo=")!= -1) {
                	        comment = input.substring(input.indexOf("refersTo=") + 9);
                	       }
                	 
                	       String results = get(color, x, y, comment);
                	 
                	       if (results == "") {
                	    	   out.println("No notes matching.");
                	  
                	       } else {
                	        out.println(results);
                	       }
                	 
                	     } else {
                	      out.println("No Notes Yet");
                	     }
                   
                  }
                   
                 }else if (query[0].equals("PIN")) {
                	   
                     int x = Integer.parseInt((query[1]));
                     int y = Integer.parseInt((query[2]));
                     boolean addpin = true;
                     if (x<=boardwidth && y<boardheight) {
                    	 Pin newpin = new Pin(x,y);
                    	 if (pins.isEmpty()==false) {
                           for (Pin current: pins) {
                        	 if (current.x ==x && current.y ==y) {
                        		 out.println("Pin already exists");
                        		 addpin =false;
                        	 }

                    	 }
                    	 
                     }
                    	 if (addpin=true) {
                    		 out.println("Pin added");
                    		 pins.add(newpin);
                    		 for (int i = 0; i < notes.size(); i++) {
                               if (notes.get(i).x == x && notes.get(i).x + notes.get(i).width >= x) {
                                if (notes.get(i).y == y && notes.get(i).y + notes.get(i).height >= y) {
                                 notes.get(i).ispinned = true;
                                }
                               }
                    		 }
                    	 }
                     }else {
                    	 out.println("Not on board");
                     }
                  } else if (query[0].equals("UNPIN")) {
                	  if(pins.isEmpty()==false) {
                		  int x = Integer.parseInt((query[1]));
                          int y = Integer.parseInt((query[2]));
                          boolean found = false;
                    	if (x<=boardwidth && y<boardheight) {
                            for (int i =0; i < pins.size();i++) {
                            	Pin current = pins.get(i);
                            	if (current.x == x && current.y==y) {
                            		out.println("pin removed");
                            		found=true;
                                  pins.remove(current);
                                  for (i = 0; i < notes.size(); i++) {
                                    if (notes.get(i).x <= x && notes.get(i).x + notes.get(i).width >= x) {
                                     if (notes.get(i).y <= y && notes.get(i).y + notes.get(i).height >= y) {
                                      notes.get(i).ispinned = false;
                              
                               }
                        	}
                        }
                                  break;
                            	}
                            	if (found==false) {
                              		out.println("No pin there");
                            		
                            	}
                              }
                     
                    	}else {
                    		out.println("not on board");
                    	}

                	  }else {
                		  out.println("No pins on board");
                	  }
                     
                  }
                 else if (query[0].equals("CLEAR")) {
                   int i = 0;
               
                   while (i < notes.size()) {
                    Note a = notes.get(i);
               
                    if (a.ispinned == true) {
                     i++;
                    } else {
                     notes.remove(i);
                    }
               
                   }
               
                   out.println("Board has been cleared");
                   
                 }else if (query[0].equals("DISCONNECT")) {
                	 out.println("Goodbye");
                	 break;
                 }

             }
         }
         catch (IOException e) {
             log("Error handling client# " + clientNumber + ": " + e);
         } finally {
             try {
                 socket.close();
             } catch (IOException e) {
                 log("Couldn't close a socket, what's going on?");
             }
             log("Connection with client# " + clientNumber + " closed");
         }
     }
     
     private void log(String message) {
         System.out.println(message);
     }
     
 public static Note CreateNote(int x, int y, int length, int width, String color, String comment) {
  Note newnote = new Note(x, y, length, width, color, comment);
  return newnote;
 }
 
 
 public static String get(String color, int x, int y, String message) {
  
ArrayList < Note > results = new ArrayList < Note > ();
String output="";
  for (int i=0;i<notes.size();i++) {
	 Note current = notes.get(i);
   boolean match = true;
   if (color != null && (current.color.equals(color))==false) {
    match = false;


   } else if (x != -1 && (current.x >x || current.x + current.width < x ||
     current.y > y || current.y + current.height < y)) {
    match = false;

   } else if (message != null && current.comment.equals(message)==false) {
    match = false;
   }
   if (match==(true)) {

    results.add(current);
   }
  }
  
  
  for (int i=0;i<results.size();i++) {
      output+= results.get(i).toString()+"\n";
     }
  
  return output;
 }


 }

}