package org.egreen.opensms.server.listner;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pramoda-nf on 12/3/15.
 */
public class SecureDispatcher extends DispatcherServlet {

    private static final Queue QUEUE = new ConcurrentLinkedQueue();
    private static Boolean isLogging = false;

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uri = request.getScheme() + "://" +   // "http" + "://
                request.getServerName() +       // "myhost"
                ":" +                           // ":"
                request.getServerPort() +       // "8080"
                request.getRequestURI() +       // "/people"
                "?" +                           // "?"
                request.getQueryString();       // "lastname=Fox&age=30"

        QUEUE.add(new Date() + "," + request.getHeader("username")
                        + "," + request.getHeader("nic") + ","
                        + request.getHeader("userId") + ","
                        + uri
        );
        check();
        super.doDispatch(request, response);

    }


    private void check() {
        if (!isLogging) {
            isLogging = true;
            new Thread(new LogUserDetails()).start();
        }
    }


    private class LogUserDetails implements Runnable {
        @Override
        public void run() {
            Object line = QUEUE.remove();
            while (line != null) {

                try {
                    Date date = new Date();

                    java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(line + "", ",");
                    stringTokenizer.nextElement();

                    String path = System.getProperty("user.home") + "/BookingSystemLog/";
                    Calendar cal = Calendar.getInstance();


                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DATE);




                    File file = new File(path + year + "" + month + "" + day + "");
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    file = new File(path + year + "" + month + "" + day + "/" + stringTokenizer.nextElement() + ".txt");

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    BufferedWriter out = null;
                    try {
                        FileWriter fstream = new FileWriter(file, true); //true tells to append data.
                        out = new BufferedWriter(fstream);
                        out.write(line + "\n");
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                } catch (Exception e) {
                   // System.out.println("NOt Write");
                }

               // System.out.println("Done");
                try {
                    line = QUEUE.remove();
                } catch (Exception e) {
                    isLogging = false;
                    return;
                }
            }
            isLogging = false;
        }
    }


}
