package it.sms.eproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;

@AutoreCodice(autore = "Giandomenico Bucci")

public class QRscannerActivity extends AppCompatActivity {

    Button btn_scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanner);
        btn_scanner=findViewById(R.id.btnscanner);
       btn_scanner.setOnClickListener(v->{
            scanCode();
        });
    }

    /**SCANNERIZZARE IL QR CODE
     *
     *
     */
    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Accendere il flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(),result -> {
        if(result.getContents()!= null){
            AlertDialog.Builder builder = new AlertDialog.Builder(QRscannerActivity.this);
            builder.setTitle("Risultato");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("visita pagina", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String url = result.getContents();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            }).show();
        }
    } );




}
