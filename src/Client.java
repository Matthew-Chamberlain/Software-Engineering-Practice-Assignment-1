
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import sep.tinee.net.message.Bye;
import sep.tinee.net.message.Push;
import sep.tinee.net.message.ReadReply;
import sep.tinee.net.message.ReadRequest;
import sep.tinee.net.message.ShowReply;
import sep.tinee.net.message.ShowRequest;

/**
 * This class is an initial work-in-progress prototype for a command line
 * Tinee client. It has been hastily hacked together, as often the case
 * in early exploratory coding, and is incomplete and buggy. However, it
 * does compile and run, and <i>some</i> basic functionality, such as pushing
 * and reading tines to and from an instance of
 * {@link sep.tinee.server.Server}, is working. Try it out!
 * <p>
 * The arguments required to run a client correspond to the
 * {@link #set(String, String, int)} method: a user name, and the host name
 * and port number of a Tinee server.
 * <p>
 * You can compile and run this client using <b>NetBeans</b>; e.g., right-click
 * this file in the NetBeans editor and select "Run File".  Note, to provide
 * the above arguments, you should set up a <b>run configuration</b> for this
 * class: {@literal ->} "Set Project Configuration" {@literal ->} "Customize..."
 * {@literal ->} "New...".
 * <p>
 * Assuming compilation using NetBeans (etc.), you can also run {@code Client}
 * from the command line; e.g., on Windows, run:
 * <ul>
 * <li style="list-style-type: none;">
 * {@code C:\...\tinee>  java -cp build\classes Client userid localhost 8888}
 * </ul>
 * <p>
 * You will be significantly reworking and extending this client: your aim is
 * to meet the specification, and you have mostly free rein to do so.
 * (That is as opposed to the base framework, which you are <b>not</b> permitted
 * to modify, i.e., the packages {@link sep.tinee.server},
 * {@link sep.tinee.server}, {@link sep.tinee.server} and]
 * {@link sep.tinee.server}.) The constraints on your client are:
 * <ul>
 * <li>
 * You must retain a class named {@code Client}) as the frontend class for
 * running your client, i.e., via its static {@linkplain #main(String[]) main}
 * method.
 * <li>
 * The {@linkplain Client#main(String[]) main} method must accept the <i>same
 * arguments</i> as currently, i.e., user name, host name and port number.
 * <li>
 * Your client must continue to accept user commands on the <i>standard input
 * stream</i> ({@code System.in}), and output on the <i>standard output
 * stream</i> ({@code System.out}).
 * <li>
 * Any other conditions specified by the assignment tasks.
 * </ul>
 * <p>
 * <i>Tip:</i> generate and read the <b>JavaDoc</b> API documentation for the
 * provided base framework classes (if you're not already reading this there!).
 * After importing the code project into NetBeans, right-click the project in
 * the "Projects" window and select "Generate Javadoc".
 * By default, the output is written to the {@code dist/javadoc} directory.
 * You can directly read the comments in the code for the same information, but
 * the generated JavaDoc is formatted more nicely as HTML with click-able links.  
 *
 * @see CLFormatter
 */
public class Client {

    String user;
    String host;
    int port;
    private CLFormatter helper;
    private BufferedReader reader;
    boolean printSplash = true;
    private ResourceBundle messages;

    Client(String user, String host, int port, String language, String country) 
    {
        this.user = user;
        this.host = host;
        this.port = port;
        CLFormatter helper = null;
        BufferedReader reader = null;
        Locale locale = new Locale(language, country);
        messages = ResourceBundle.getBundle("resources/MessageBundle", locale);
    }

    public static void main(String[] args) throws IOException {
        String language;
        String country;
        String user = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);
        if(args.length != 5)
        {
            language = new String("en");
            country = new String("GB");
        }
        else
        {
            language = args[3];
            country = args[4];
        } 
        Client client = new Client(user, host, port, language, country);
        client.run();
    }
  // Run the client
    @SuppressFBWarnings(
        value = "DM_DEFAULT_ENCODING",
        justification = "When reading console, ignore 'default encoding' warning")
    void run() throws IOException {

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));

            if (this.user.isEmpty() || this.host.isEmpty()) 
            {
                System.err.println(messages.getString("User/Host_Error"));
                System.exit(1);
            }     
            helper = new CLFormatter(this.host, this.port);

            if (this.printSplash == true);
            {
                System.out.print(helper.formatSplash(user, messages));
            }
            loop(helper, reader);
        } 
        catch (Exception ex) 
        {
            throw new RuntimeException(ex);
        } 
        finally 
        {
            reader.close();
            if (helper.chan.isOpen()) 
            {
                // If the channel is open, send Bye and close
                helper.chan.send(new Bye());
                helper.chan.close();
            }
        }
    }

    public List<String> inputText() throws IOException
    {
        String raw = reader.readLine();
        if (raw == null) 
        {
            throw new IOException(messages.getString("IOException"));
        }
        return Arrays.stream(raw.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
    
    }
    
    public void outputText()
    {
        if(helper.getState().equals("Main"))
        {
            System.out.print(helper.formatMainMenuPrompt(messages));
        }
        else
        {
            System.out.print(helper.formatDraftingMenuPrompt(messages));
        }
    }
    // Main loop: print user options, read user input and process
    private void loop(CLFormatter helper, BufferedReader reader) throws IOException, ClassNotFoundException 
    {

        // The app is in one of two states: "Main" or "Drafting"
        helper.setState("Main");// Initial state

        // Holds the current draft data when in the "Drafting" state
        
        outputText();
        // The loop
        List<String> split = inputText();
        String cmd = split.remove(0);  // First word is the command keyword
        String[] rawArgs = split.toArray(new String[split.size()]);
        //boolean done = false;
        CommandChooser command = new CommandChooser(helper, user);
        while(!"exits".startsWith(cmd)) 
        {

            // Print user options
            command.getCommand(cmd, rawArgs, messages);
      
            outputText();  
            split = inputText();
            cmd = split.remove(0);  // First word is the command keyword
            rawArgs = split.toArray(new String[split.size()]);
        }
    }
}
