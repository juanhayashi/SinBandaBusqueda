package com.tokenautocomplete;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class TokenActivity extends Activity implements TokenCompleteTextView.TokenListener<Data> {
    ContactsCompletionView completionView;
    Data[] datos;
    ArrayAdapter<Data> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datos = new Data[]{
                new Data("estilo_musical", "Pop"),
                new Data("estilo_musical", "Rock"),
                new Data("estilo_musical", "Metal"),
                new Data("estilo_musical", "JPop"),
                new Data("habilidad_musical", "Guitarra"),
                new Data("habilidad_musical", "Canto"),
                new Data("habilidad_musical", "Contrabajo")
        };

        adapter = new FilteredArrayAdapter<Data>(this, R.layout.person_layout, datos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.person_layout, parent, false);
                }

                Data p = getItem(position);
                return convertView;
            }

            @Override
            protected boolean keepObject(Data data, String mask) {
                mask = mask.toLowerCase();
                return data.getLlave().toLowerCase().startsWith(mask) || data.getValor().toLowerCase().startsWith(mask);
            }
        };

        completionView = (ContactsCompletionView)findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);


        if (savedInstanceState == null) {
            completionView.setPrefix("Estilos: ");
            completionView.addObject(datos[0]);
            completionView.addObject(datos[1]);
        }

        Button removeButton = (Button)findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Data> people = completionView.getObjects();
                if (people.size() > 0) {
                    completionView.removeObject(people.get(people.size() - 1));
                }
            }
        });

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                completionView.addObject(datos[rand.nextInt(datos.length)]);
            }
        });

    }

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token: completionView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }

        ((TextView)findViewById(R.id.tokens)).setText(sb);
    }


    @Override
    public void onTokenAdded(Data token) {
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Data token) {
        updateTokenConfirmation();
    }
}
