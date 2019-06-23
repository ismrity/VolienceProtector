package com.achs.voilence_protector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Laws extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws);
        PDFView pdfView=(PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("Nepalgov_laws.pdf").load();
    }
}
