package com.example.functionsample;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.afollestad.materialdialogs.MaterialDialog;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 2804;
    CardView message, contacts,helpline, police, isSafe;
    SharedPreferences mSharedPreferences;
    String number, serial;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing cardviews
        message = (CardView) findViewById(R.id.cardView2);
        isSafe = (CardView) findViewById(R.id.cardView7);
        contacts = (CardView) findViewById(R.id.cardView1);
        police = (CardView) findViewById(R.id.cardView5);
        helpline = (CardView) findViewById(R.id.cardView4);

        //checking permisions for locaton,sms,call

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Permissions.askforPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.CALL_PHONE
                    },
                    new Permissions.OnpermissionResultListner() {
                        @Override
                        public void OnGranted(String fperman) {
                            // Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnDenied(String fperman) {
                            //  Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        startService();

        Intent intent = new Intent(this, FakeCallRecevier.class);

        intent.putExtra("FAKENAME", "DAD");
        intent.putExtra("FAKENUMBER", "9412164248");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0 ,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.women)
                .setContentText("Fake call after 10 seconds")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .addAction(R.drawable.phone,"CALL",pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

        //safe
        public void isSafe (View view){
            if (isLocationAvailable()) {
                Intent intent = new Intent(MainActivity.this, isSafe.class);
                startActivity(intent);
            } else {
                LocationPermissionCheck();
            }
        }
//contacts

        public void contacts (View view){
            Intent i = new Intent(MainActivity.this, Contact.class);
            startActivity(i);
        }
//police

   public void police(View view) {

            if(isLocationAvailable()) {
                Intent intent = new Intent(MainActivity.this,Police.class);
                startActivity(intent);
            }
            else
            {
                LocationPermissionCheck();
            }
    }

//message
        public void message (View view){
            MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this)
                    .title(R.string.input)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("Enter Here", null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            mSharedPreferences = getSharedPreferences("Women", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString("Message", input.toString());
                            editor.apply();
                        }

                    });
            MaterialDialog dialog = builder.build();
            dialog.show();
    }
    //fakecall notification



            private void startService () {
            Intent intent = new Intent(MainActivity.this, MessageService.class);
            startService(intent);
        }

        //helpline
public void helpline(View view) {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:1091"));
    startActivity(intent);
}

        boolean isLocationAvailable () {
            boolean gpsenabled = false;
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager location = (LocationManager) MainActivity.this.getSystemService(LOCATION_SERVICE);
                if (location.isProviderEnabled(location.GPS_PROVIDER)) {
                    gpsenabled = true;
                }
            }
            return gpsenabled;
        }



        public void LocationPermissionCheck () {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Permissions.askforPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        new Permissions.OnpermissionResultListner() {
                            @Override
                            public void OnGranted(String fperman) {

                            }

                            @Override
                            public void OnDenied(String fperman) {

                            }
                        });
            } else {
                AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                String msg = "To continue ,let your device turn on location using Google's Location Service." +
                        System.getProperty("line.separator") + System.getProperty("line.separator");
                build.setCancelable(false).setMessage(msg)
                        .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                                String msg = "Panchi services will not work properly please give the loaction permission to work" +
                                        System.getProperty("line.separator") + System.getProperty("line.separator");
                                build.setCancelable(false).setMessage(msg).setTitle("ALERT")
                                        .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                build.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(i);
                                        dialog.dismiss();
                                    }
                                });
                                build.show();
                            }
                        });
                build.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
                build.show();
            }


        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            Permissions.OnPermResult(requestCode, permissions, grantResults);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }



}