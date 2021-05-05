
import java.io.IOException;
import java.util.ResourceBundle;
import sep.tinee.net.message.Push;
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
public class Close implements Command{

    private String user;
    public Close(String user)
    {
        this.user = user;
    }
    @Override
    public void execute(CLFormatter reciever, String[] rawArgs, ResourceBundle messages) throws IOException, ClassNotFoundException {
        if(reciever.getState().equals("Drafting"))
        {
            reciever.chan.send(new ReadRequest(reciever.getDraftTag()));
            ReadReply rep = (ReadReply) reciever.chan.receive();
            if(rep.lines.size() > 0)
            {
                if(rep.users.get(0).equals(this.user))
                {
                    reciever.addDraftLines("##CLOSE##");
                    reciever.chan.send(new Push(user, reciever.getDraftTag(), reciever.getDraftLines()));
                    reciever.clearDraftLines();
                    reciever.setState("Main");
                    reciever.setDraftTag(null);
                }
                else
                {
                    System.out.print("\n"+messages.getString("Not_Original_Author_Error")+"\n");
                }
            }
            else
            {
                reciever.addDraftLines("##CLOSE##");
                reciever.chan.send(new Push(user, reciever.getDraftTag(), reciever.getDraftLines()));
                reciever.clearDraftLines();
                reciever.setState("Main");
                reciever.setDraftTag(null);
            }
        }
        else
        {
            System.out.println(messages.getString("Command_Input_Error"));
        }
    }
}
