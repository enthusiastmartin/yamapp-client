package studio.streesedout.yamapp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import studio.streesedout.yamapp.R;

/**
 * Created by martin on 5/30/17.
 */

public class MainActivity extends AppCompatActivity {

  private final static String TAG = MainActivity.class.getSimpleName();

  private Button button;
  private TextView textView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    button = (Button) findViewById(R.id.btn);
    textView = (TextView) findViewById(R.id.input_text);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(TAG, "Trying to connect...");
        try {
          Socket socket = IO.socket("http://192.168.1.9:5000/android");

          socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
              Log.d(TAG, "Connected!");
            }
          })
          .on("response", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
              String msg = (String)args[0];
              Log.d(TAG,"RECEIVED " + msg);
            }
          });
          socket.connect();
        }catch (URISyntaxException ex){
          Log.d(TAG, "ERROR " + ex.getLocalizedMessage());
        }
      }
    });
  }
}
