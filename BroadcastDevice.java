import de.sciss.net.OSCChannel;
import de.sciss.net.OSCListener;
import de.sciss.net.OSCMessage;
import de.sciss.net.OSCServer;
import net.happybrackets.core.HBAction;
import net.happybrackets.core.HBReset;
import net.happybrackets.device.HB;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class BroadcastDevice implements HBAction, HBReset {
    int i = 0;
    int j = 0;
    int a = 0;
    int k = 10000;
    OSCServer server = null;

    @Override
    public void action(HB hb) {
        hb.reset(); //Clears any running code on the device
        long start = System.currentTimeMillis();
        //this example won't actually do anything useful, but it shows how you can build more regular
        //custom OSC senders and receivers.
        try {
            //server listens on port 5555
            server = OSCServer.newUsing(OSCChannel.UDP, 5555);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //try sending an OSC message (this will be received by the listener above because the
        //hostname (localhost) and port are correct). If you want to send to specific devices you
        //can enter their hostnames and choose specifc ports as you require.
        OSCMessage message = new OSCMessage("/hello" + i);
        try {
            server.send(message, new InetSocketAddress("192.168.0.116", 5555));
            hb.setStatus("SEND!");
        } catch (IOException e) {
            e.printStackTrace();
        }


        //add an OSC listener
        server.addOSCListener(new OSCListener() {
            @Override
            public void messageReceived(OSCMessage oscMessage, SocketAddress socketAddress, long l) {
                //receive incoming messages here on port 5555
                if (a != k) {
                    if (oscMessage.getName().startsWith("/helloB")) {
                        hb.testBleep();
                        i++;
                        hb.setStatus("Received" + j);
                        OSCMessage message = new OSCMessage("/hello" + i);
                        try {
                            server.send(message, new InetSocketAddress("192.168.0.116", 5555));
                            hb.setStatus("SEND: " + i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        j++;
                        a++;
                        //hb.setStatus("RECEIVED!" + j + "Sent: " + i + "TEST  " + a);
                    }
                }
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                hb.setStatus("MESSAGES  SENT:  " + i + "  MESSAGES  RECEIVED:  " + j + "  TIME  ELAPSED:  " + timeElapsed  + "  Seconds");
            }
        });
    }

    @Override
    public void doReset() {
        if(server != null) {
            server.dispose();
        }
    }


    //<editor-fold defaultstate="collapsed" desc="Debug Start">

    /**
     * This function is used when running sketch in IntelliJ IDE for debugging or testing
     *
     * @param args standard args required
     */
    public static void main(String[] args) {

        try {
            HB.runDebug(MethodHandles.lookup().lookupClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //</editor-fold>
}
