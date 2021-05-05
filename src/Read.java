
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import sep.tinee.net.message.ReadReply;
import sep.tinee.net.message.ReadRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mattc
 */
public class Read implements Command {

    @Override
    public void execute(CLFormatter reciever, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException
    {
        if(reciever.getState().equals("Main"))
        {
            if(rawArgs.length == 0)
            {
                reciever.setState("Main");
                return;
            }
            reciever.chan.send(new ReadRequest(rawArgs[0]));
            ReadReply rep = (ReadReply) reciever.chan.receive();
            System.out.print(reciever.formatRead(rawArgs[0], rep.users, rep.lines));
            reciever.setState("Main");
        }
        else
        {
            System.out.println(messages.getString("Command_Input_Error"));
        }
    }   
}
