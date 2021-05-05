
import java.io.IOException;
import java.util.ResourceBundle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mattc
 */
public class Discard implements Command {

    @Override
    public void execute(CLFormatter reciever, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException {
        if(reciever.getState().equals("Drafting"))
        {
            reciever.getDraftLines().clear();
            reciever.setState("Main");   
        }
        else
        {
            System.out.println(messages.getString("Command_Input_Error"));
        }
    }
}
