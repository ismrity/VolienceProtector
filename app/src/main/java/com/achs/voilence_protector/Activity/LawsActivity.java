package com.achs.voilence_protector.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.achs.voilence_protector.R;
import com.github.barteksc.pdfviewer.PDFView;

public class LawsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws);
        PDFView pdfView=(PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("Nepalgov_laws.pdf").load();
    }
}
