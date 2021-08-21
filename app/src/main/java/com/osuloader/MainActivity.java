package com.osuloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;

public class MainActivity extends AppCompatActivity {

    TextView text_oszFiles;
    TextView text_count;
    Button btn_load;
    Button btn_research;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text_oszFiles = (TextView) findViewById(R.id.text_oszFiles);
        text_count = (TextView) findViewById(R.id.text_count);
        btn_load = (Button) findViewById(R.id.btn_load);
        btn_research = (Button) findViewById(R.id.btn_research);


        // 저장소 권한 체크
        getPermissions();

        // 파일 새로고침
        researchFiles();


        // 새로고침
        btn_research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                researchFiles();
                Toast.makeText(getApplicationContext(), "새로고침", Toast.LENGTH_SHORT).show();
            }
        });

        // 로드하기
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFiles();
            }
        });


    }

    private void loadFiles(){

        btn_load.setEnabled(false);

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("osz");
            }
        };

        File dir = new File("/storage/emulated/0/Download");
        File files[] = dir.listFiles(filter);

        for(int i=0; i<files.length; i++){
            moveFile( files[i].toString(), "/storage/emulated/0/osu!droid/Songs/"+ files[i].getName() );
        }

        Toast.makeText(getApplicationContext(), "로드가 완료되었습니다.", Toast.LENGTH_LONG).show();

        researchFiles();

        btn_load.setEnabled(true);

    }

    private void moveFile( String sourceFile, String destinationFile ){
        File file = new File(sourceFile);
        boolean success = file.renameTo(new File( destinationFile ));

        if( !success ){
            Toast.makeText(getApplicationContext(), sourceFile + " 로드 실패!", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(), sourceFile + " 로드 성공!", Toast.LENGTH_LONG).show();
        }

    }


    private void researchFiles(){

        btn_research.setEnabled(false);

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("osz");
            }
        };

        File dir = new File("/storage/emulated/0/Download");
        File files[] = dir.listFiles(filter);
        String str = new String();




        text_count.setText( files.length + "개" );

        text_oszFiles.setText(null);
        for(int i=0; i<files.length; i++){
            str += files[i].getName().toString() + "\n";
        }
        text_oszFiles.setText( str );

        if( files.length <= 0 )
            text_oszFiles.setText("Empty");

        btn_research.setEnabled(true);

    }




    private void getPermissions(){

        int permissionCheck;

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

    }




}