
import java.io.IOException;
import java.util.ResourceBundle;
import sep.tinee.net.message.Push;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mattc
 */
public class PushCommand implements Command{

    private String user;
    public PushCommand(String user)
    {
        this.user= user;
    }
    @Override
    public void execute(CLFormatter reciever, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException {
        if(reciever.getState().equals("Drafting"))
        {
            reciever.chan.send(new Push(user, reciever.getDraftTag(), reciever.getDraftLines()));
            reciever.getDraftLines().clear();
            reciever.setDraftTag(null);
            reciever.setState("Main");
        }
        else
        {
            System.out.println(messages.getString("Command_Input_Error"));
        }
    } 
}
