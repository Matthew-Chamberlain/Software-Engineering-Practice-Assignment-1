
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import sep.tinee.net.channel.ClientChannel;
import sep.tinee.net.message.Message;

/**
 * A helper class for the current prototype {@link Client client}.  <i>E.g.</i>,
 * for formatting Command Line messages.
 */
public class CLFormatter {

  static ClientChannel chan;  // Client-side channel for talking to a Tinee server
  private static String draftTag = null;
  private static List<String> draftLines = new LinkedList<>();
  private String state;

  CLFormatter(String host, int port) {
    this.chan = new ClientChannel(host, port);
  }

  /* Interact with Tinee server */

  private void send(Message msg) throws IOException {
    this.chan.send(msg);
  }

  private Message receive() throws IOException, ClassNotFoundException {
    return this.chan.receive();
  }

  /* Following are the auxiliary methods for formatting the UI text */

  static String formatSplash(String user, ResourceBundle messages) {
    return "\n" + messages.getString("Welcome") + " " + user + "!\n"
        + messages.getString("Welcome_Note")
        + " read [mytag] => re [mytag]\n";
  }

  static String formatMainMenuPrompt(ResourceBundle messages) {
    return "\n["+messages.getString("Main")+"] " + messages.getString("Enter")
        + " read [mytag], "
        + "manage [mytag], "
        + "show, "    
        + "exit"
        + "\n> ";
  }

  static String formatDraftingMenuPrompt(ResourceBundle messages) {
    return "\n"+messages.getString("Drafting")+": " + formatDrafting()
        + "\n["+messages.getString("Drafting")+"] "+ messages.getString("Enter")
        + " line [mytext], "
        + "push, "
        + "discard, "
        + "undo, "    
        + "close, "    
        + "exit"
        + "\n> ";
  }

  static String formatDrafting() {
    StringBuilder b = new StringBuilder("#");
    b.append(draftTag);
    int i = 1;
    for (String x : draftLines) {
      b.append("\n");
      b.append(String.format("%12d", i++));
      b.append("  ");
      b.append(x);
    };
    return b.toString();
  }

  static String formatRead(String tag, List<String> users,
      List<String> read) {
    StringBuilder b = new StringBuilder("Read: #");
    b.append(tag);
    Iterator<String> it = read.iterator();
    for (String user : users) {
      b.append("\n");
      b.append(String.format("%12s", user));
      b.append("  ");
      b.append(it.next());
    };
    b.append("\n");
    return b.toString();
  }
  
  static String formatShow(Map tags)
    {
    StringBuilder b = new StringBuilder("Show: ");
    Iterator it = tags.entrySet().iterator();
    while (it.hasNext()) 
    {
        Map.Entry pair = (Map.Entry)it.next();
        b.append("\n");
        b.append(String.format("%12s",pair.getKey()));
        b.append("  ");
        b.append(pair.getValue());
    }
    b.append("\n");
    return b.toString();
  }
  
  public String getDraftTag()
  {
      return draftTag;
  }
  
  public void setDraftTag(String tag)
  {
      draftTag = tag;
  }
  
  public List<String> getDraftLines()
  {
      return draftLines;
  }
  
  public void addDraftLines(String line)
  {
      draftLines.add(line);
  }
  
  public void removeDraftLines(int line)
  {
      draftLines.remove(line);
  }
  
  public void clearDraftLines()
  {
      draftLines.clear();
  }
  
  public String getState()
  {
      return state;
  }
  
  public void setState(String state )
  {
      this.state = state;
  }
}
