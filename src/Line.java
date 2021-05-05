
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mattc
 */
public class Line implements Command {

    @Override
    public void execute(CLFormatter reciever, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException {
        if(reciever.getState().equals("Drafting"))
        {
            String line = Arrays.stream(rawArgs).
            collect(Collectors.joining());
            reciever.addDraftLines(line);
            reciever.setState("Drafting");
        }
        else
        {
            System.out.println(messages.getString("Command_Input_Error"));
        }
    }   
}
