
import java.io.IOException;
import java.util.ResourceBundle;
import sep.tinee.net.message.ShowReply;
import sep.tinee.net.message.ShowRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mattc
 */
public class Show implements Command {

    @Override
    public void execute(CLFormatter reciever, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException 
    {
        if(reciever.getState().equals("Main"))
        {
            reciever.chan.send(new ShowRequest());
            ShowReply rep = (ShowReply) reciever.chan.receive();
            System.out.print(reciever.formatShow(rep.tags));
            reciever.setState("Main");
        }
        else
        {
            System.out.println(messages.getString("Command_Input_Error"));
        }
        
    }

    
    
}
