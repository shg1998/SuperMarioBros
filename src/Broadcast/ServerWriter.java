package Broadcast;

/**
 * A Method to send merged files and delete strings to clients over socket
 */

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerWriter extends Thread {
    private Socket socket;
    private OutputStream output;
    private PrintWriter pr;
    private BufferedReader br;
    private InputStream input;
    private String tag;
    private int num;


    public ServerWriter(Socket socket) {
        try {
            this.socket = socket;
            output = socket.getOutputStream();
            input = socket.getInputStream();

            pr = new PrintWriter(new OutputStreamWriter(output));
            br = new BufferedReader(new InputStreamReader(input));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        try {


            identify();
            for (int i = 0; i < MultiServer.data[num].size(); i++) {
                pr.println(MultiServer.data[num].get(i));
                pr.flush();
                System.out.println("in Server Writer num : "+num +" "+MultiServer.data[num].get(i));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * A Method to detect the client's turn
     *
     * @throws Exception
     */
    private void identify() throws Exception {
        tag = br.readLine();

        if (tag.equals("0"))
            num = 1;

        else
            num = 0;
    }


}
