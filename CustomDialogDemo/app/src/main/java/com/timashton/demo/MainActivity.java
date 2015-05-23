
package com.timashton.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");

        Button button = (Button) findViewById(R.id.button_show_dialog);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i(TAG, "onClick(View arg0)");

                AsyncTask asyncTask = new AsyncTask<Void, Void, Void>() {

                    Dialog dialog;

                    @Override
                    protected void onPreExecute() {

                        // Create the custom Dialog
                        dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_dialog);

                        dialog.show();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Busy Wait for 2 seconds
                        int timer = 0;
                        while (timer < 2000) {
                            try {
                                Thread.sleep(100);
                                timer += 100;

                            } catch (InterruptedException e) {
                                Log.e(TAG, e.toString());
                            }
                        }

                        // Ask the dialog to update its contents
                        publishProgress();

                        // Reset the timer and busy wait for 1 second
                        timer = 0;
                        while (timer < 1000) {
                            try {
                                Thread.sleep(100);
                                timer += 100;

                            } catch (InterruptedException e) {
                                Log.e(TAG, e.toString());
                            }
                        }

                        return null;
                    }


                    @Override
                    protected void onProgressUpdate(Void... values) {

                        // Update contents of dialog
                        TextView textView = (TextView) dialog.findViewById(R.id.dialog_text);
                        ProgressBar progressbar = (ProgressBar) dialog.findViewById(R.id.dialog_progress_bar);

                        textView.setText("Task Completed!");
                        progressbar.setIndeterminate(false);
                    }

                    @Override
                    protected void onPostExecute(Void v) {

                        // Finally dismiss the dialog
                        dialog.dismiss();
                    }
                }.execute();
            }
        });
    }
}
