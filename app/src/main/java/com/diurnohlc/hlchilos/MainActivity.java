package com.diurnohlc.hlchilos;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView contador;
    Button comenzar, parar;
    ProgressBar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contador = (TextView) findViewById(R.id.contador);
        comenzar = (Button) findViewById(R.id.comenzar);
        parar = (Button) findViewById(R.id.parar);
        barra = (ProgressBar) findViewById(R.id.barra);

        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MiTarea().execute(Integer.parseInt(contador.getText().toString()));

            }
        });


    }

    class MiTarea extends AsyncTask<Integer,Integer,Integer> {



        @Override
        //Este metodo preparatodo antes de la creacion del hilo
        protected void onPreExecute() {
//Le ponemos el maximo a la barra
            barra.setMax(10);
//Aqui le ponemos la funcionalidad al boton porque antes no tendría sentido si no existe hilo al que parar
            comenzar.setClickable(false);
            parar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MiTarea.this.cancel(true);
                    contador.setText(Integer.toString(0));
                    barra.setProgress(0);
                    comenzar.setClickable(true);
                }
            });

        }

        @Override
        //Este metodo lo haria en segundo plano y va devolviendo al principal ne cada itineracion
        protected Integer doInBackground(Integer... integers) {
            int resultado=0;
            for(int i=integers[0];i<=10;i++){
                if(this.isCancelled()){
                    break;
                }
                resultado=i;
                publishProgress(resultado);

                    SystemClock.sleep(500);
            }

            return resultado;
        }
        @Override
        //Mientras el otro esta en segudno plano este va poniendo la informacion en los componentes
        protected void onProgressUpdate(Integer... porc) {
            contador.setText(Integer.toString(porc[0]));
            barra.setProgress(porc[0]);
            }

        @Override
        protected void onPostExecute(Integer integer) {
            comenzar.setClickable(true);

        }

        //@Override
    /*    protected void onCancelled() {
            barra.setProgress(0);
            contador.setText(0);
        }*/
    }

}
