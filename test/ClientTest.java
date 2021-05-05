/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mattc
 */
public class ClientTest {
    String user ="Matt";
    String host = "localhost";
    int port = 8888;
    
    public ClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception{
        
    }
    
    @After
    public void tearDown() {
        System.setIn(System.in);
    }

    /**
     * Tests what happens when two separate pushes are made to the same tine
     */
    @Test
    public void twoPushesToOneTine() throws Exception {
        String input = "manage Tester\nline hello\nline goodbye\npush\nmanage Tester\nline Test\npush\nread Tester\nexit";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Client client = new Client(user, host, port, "en", "GB");
        client.run();
    }    
    
    /**
     * Tests what happens when two separate pushes are made to separate tines
     */
    @Test
    public void pushesToTwoDifferentTines() throws Exception {
        String input = "manage Tester1\nline Test1\npush\nmanage Tester2\nline Test2\npush\nread Tester2\nexit";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Client client = new Client(user, host, port, "en", "GB");
        client.run();
    }
    
    /**
     * Tests what happens when the manage command is called without providing a tag
     */
    @Test
    public void manageCommandsEnteredWithoutTag() throws Exception
    {
        String input = "manage";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Client client = new Client(user, host, port, "en", "GB");
        client.run();
    }
    
    /**
     * Tests what happens when the read command is called without providing a tag
     */
    @Test
    public void readCommandsEnteredWithoutTag() throws Exception
    {
        String input = "read";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Client client = new Client(user, host, port, "en", "GB");
        client.run();
    } 
}
