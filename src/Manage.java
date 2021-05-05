
import java.io.IOException;
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
public class Manage implements Command 
{
    @Override
    public void execute(CLFormatter reciever, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException {
        if(reciever.getState().equals("Main"))
        {
            if(rawArgs.length == 0)
            {
                reciever.setState("Main");
                return;
            }
            reciever.chan.send(new ReadRequest(rawArgs[0]));
            ReadReply rep = (ReadReply) reciever.chan.receive();
                // Switch to "Drafting" state and start a new "draft"
            if(rep.lines.size() > 0 )
            {
                if(!rep.lines.get(rep.lines.size()-1).equals("##CLOSE##"))
                {
                    reciever.setDraftTag(rawArgs[0]);
                    reciever.setState("Drafting");
                }
                else
                {
                    System.out.print("\n"+messages.getString("Opening_Closed_Ticket_Error")+"\n");
                    reciever.setState("Main");
                }
            }
            else
            {
                reciever.setDraftTag(rawArgs[0]);
                reciever.setState("Drafting");            
            }
        }
        else
        {
            System.out.println(messages.getString("Command_Input_Error"));
        }
    }
}
