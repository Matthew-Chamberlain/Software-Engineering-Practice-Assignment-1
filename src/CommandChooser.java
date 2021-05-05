
import java.io.IOException;
import java.util.HashMap;
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
public class CommandChooser {
    
    private HashMap<String,Command> commandMap = new HashMap<String,Command>();
    CLFormatter helper;
    
    public CommandChooser(CLFormatter helper, String user)
    {
        this.helper = helper;
        
        commandMap.put("manage",new Manage());
        commandMap.put("read",new Read());
        commandMap.put("show",new Show());
        commandMap.put("line",new Line());
        commandMap.put("push",new PushCommand(user));
        commandMap.put("discard",new Discard());
        commandMap.put("undo",new Undo());
        commandMap.put("close",new Close(user));
        
    }
    
    public void getCommand(String com, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException
    {
        Command command = commandMap.get(com);
        command.execute(helper, rawArgs, messages);
    }
}
