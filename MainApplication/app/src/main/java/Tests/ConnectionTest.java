package Tests;

import frontend.Connection;
import junit.framework.*;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

/**
 * Created by Choffa on 20-Mar-17.
 */
public class ConnectionTest extends TestCase {

    private Connection con;
    private ByteArrayOutputStream out;

    @Override
    public void setUp(){
        try {
            Socket s = mock(Socket.class);
            out = new ByteArrayOutputStream();
            when(s.getOutputStream()).thenReturn(out);
            con = new Connection(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOutput() throws UnsupportedEncodingException {
        int i = anyInt();
        con.getAverageSpeedRating(i);
        assertEquals("GET_AVERAGESPEEDRATING " + i, out.toString("UTF-8"));
    }
}
