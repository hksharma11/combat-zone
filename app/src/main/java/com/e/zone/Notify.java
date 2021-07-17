package com.e.zone;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class Notify {

    FirebaseFirestore mydb=FirebaseFirestore.getInstance();

    void push(String user, final String message, String date, final String notification_id)
    {
        long rightNow = Calendar.getInstance().getTimeInMillis();
        String time=Long.toString(rightNow);

        final HashMap<String, Object> notification = new HashMap<>();
        notification.put("message",message);
        notification.put("date",date);
        notification.put("id",rightNow);



        mydb.collection("Users").document(user).collection("Notifications").document(time).set(notification).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String jsonResponse;

                            URL url = new URL("https://onesignal.com/api/v1/notifications");
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            con.setUseCaches(false);
                            con.setDoOutput(true);
                            con.setDoInput(true);

                            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            con.setRequestMethod("POST");

                            String strJsonBody = "{"
                                    +   "\"app_id\": \"f4a70e3a-31ce-4de1-825b-d2afca3cd140\","
                                    +   "\"include_player_ids\": [\""+notification_id+"\"],"
                                    +   "\"data\": {\"foo\": \"bar\"},"
                                    +   "\"contents\": {\"en\": \""+message+"\"}"
                                    + "}";



                            System.out.println("strJsonBody:\n" + strJsonBody);

                            byte[] sendBytes = strJsonBody.getBytes(StandardCharsets.UTF_8);
                            con.setFixedLengthStreamingMode(sendBytes.length);

                            OutputStream outputStream = con.getOutputStream();
                            outputStream.write(sendBytes);

                            int httpResponse = con.getResponseCode();
                            System.out.println("httpResponse: " + httpResponse);

                            if (  httpResponse >= HttpURLConnection.HTTP_OK
                                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                scanner.close();
                            }
                            else {
                                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                scanner.close();
                            }
                            System.out.println("jsonResponse:\n" + jsonResponse);

                        } catch(Throwable t) {

                        }

                    }
                });

            }
        });
    }
}
