/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserviceserveradvanced;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author youcee
 */
public class MessageServiceServerAdvanced {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread td = new Thread(() -> {
            try {
                ServerSocket serversocket = new ServerSocket(1988);
                System.out.println("TCP Server Running, Listening on port 1988");
                while(true){
                    Socket socket = serversocket.accept();
                    DataInputStream dtst = new DataInputStream(socket.getInputStream());
                    String message = dtst.readUTF();
                    System.out.println("Querry recieved: " + message);
                    String [] contents = message.split(",");
                    String [] headerDetails = contents[0].split(":");
                    switch (headerDetails[1]){
                        case "SendMessage":
                            String [] bodyDetails = contents[1].split(":");
                            Message msg = new Message();
                            msg.setMessage(bodyDetails[1]);
                            boolean outcome = new MessageController().writeMessage(msg);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            if(outcome){
                               dos.writeUTF("success");
                            }else{
                                dos.writeUTF("failed");
                            }
                            dos.close();
                            break;
                        case "ShowMessages":
                            List<String> allmsgs = new MessageController().allMessages();
                            ObjectOutputStream obs = new ObjectOutputStream(socket.getOutputStream());
                            obs.writeObject(allmsgs);
                            obs.close();
                            break;
                        case "Login":
                            String [] usnameDetails = contents[1].split(":");
                            String [] pwordDetails = contents[2].split(":");
                            User user = new User();
                            user.setUsername(usnameDetails[1]);
                            user.setPassword(pwordDetails[1]);
                            System.out.println("Querry User details :" + user.toString());
                            String response = new UserController().confirmLoginDetails(user);
                            System.out.println(response);
                            DataOutputStream dos2 = new DataOutputStream(socket.getOutputStream());
                            dos2.writeUTF(response);
                            dos2.close();
                            break;
                        case "AddUser":
                            String [] usnameDetails2 = contents[1].split(":");
                            String [] pwordDetails2 = contents[2].split(":");
                            String [] roleDetails = contents[3].split(":");
                            User user2 = new User();
                            user2.setUsername(usnameDetails2[1]);
                            user2.setPassword(pwordDetails2[1]);
                            user2.setRole(roleDetails[1]);
                            boolean response2 = new UserController().addUser(user2);
                            DataOutputStream dos3 = new DataOutputStream(socket.getOutputStream());
                            if(response2){
                            dos3.writeUTF("success");
                            } else {
                            dos3.writeUTF("success");    
                            }
                            break;
                        case "RemoveUser":
                            String [] usnameDetails3 = contents[1].split(":");
                            String [] pwordDetails3 = contents[2].split(":");
                            User user3 = new User();
                            user3.setUsername(usnameDetails3[1]);
                            user3.setPassword(pwordDetails3[1]);
                            boolean response3 = new UserController().removeUser(user3);
                            DataOutputStream dos4 = new DataOutputStream(socket.getOutputStream());
                            if (response3){
                             dos4.writeUTF("success");
                            }else {
                               dos4.writeUTF("failed");
                            }
                            dos4.close();
                            break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MessageServiceServerAdvanced.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        td.start();
            
    }
    
}
