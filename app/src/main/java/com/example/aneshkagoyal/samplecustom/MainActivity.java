package com.example.aneshkagoyal.samplecustom;

import android.content.res.AssetFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.google.firebase.ml.custom.model.FirebaseLocalModelSource;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
//    FirebaseModelInterpreter firebaseInterpreter;
//    FirebaseModelInputs inputs;
//    FirebaseModelInputOutputOptions inputOutputOptions;
    TextView t;
    Interpreter tflite;
    Button bt;
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = findViewById(R.id.my_text);
        bt = findViewById(R.id.my_button);
        ed = findViewById(R.id.input);
        try {
            tflite = new Interpreter(LoadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String in = ed.getText().toString();
                float [] input = new float[1];
                input[0]= Float.valueOf(in);
                float out[][] = new float[1][1];
                tflite.run(input,out);
                float infer = out[0][0];
                String str = Float.toString(infer);
                t.setText(str);


            }
        });



    }
    private MappedByteBuffer LoadModelFile() throws IOException{
        AssetFileDescriptor fd = this.getAssets().openFd("my_model_add.tflite");
        FileInputStream fi = new FileInputStream(fd.getFileDescriptor());
        FileChannel fc = fi.getChannel();
        long startoff = fd.getStartOffset();
        long length = fd.getDeclaredLength();
        return fc.map(FileChannel.MapMode.READ_ONLY,startoff,length);
    }



}
