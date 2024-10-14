package com.example.apprest_cl2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apprest_cl2.entity.Posts;
import com.example.apprest_cl2.service.PostService;
import com.example.apprest_cl2.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner spnPosts;
    ArrayAdapter<String> adaptadorPosts;
    ArrayList<String> listaPost = new ArrayList<String>();

    Button btnMostrar;
    TextView txtContenido;

    PostService postService;

    List<Posts> lstSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adaptadorPosts = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaPost);
        spnPosts = findViewById(R.id.spnPosts);
        spnPosts.setAdapter(adaptadorPosts);

        btnMostrar = findViewById(R.id.btnMostrar);
        txtContenido = findViewById(R.id.txtContenido);

        postService = ConnectionRest.getConnecion().create(PostService.class);

        cargarPosts();

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lstSalida != null && !lstSalida.isEmpty()) {
                    int idPost = spnPosts.getSelectedItemPosition();
                    Posts objPost = lstSalida.get(idPost);
                    String salida = "Publicación: " + "\n\n";
                    salida += "USERID : " + "  " + objPost.getUserId() + "\n";
                    salida += "ID : " + "  " + objPost.getId() + "\n";
                    salida += "TITLE : " + "  " + objPost.getTitle() + "\n\n";
                    salida += "BODY : " + "  " + objPost.getBody() + "\n";
                    txtContenido.setText(salida);
                } else {
                    txtContenido.setText("No hay posts disponibles.");
                }
            }
        });
    }
    void cargarPosts() {
        Call<List<Posts>> call = postService.listaPublicaciones();
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if (response.isSuccessful()) {
                    lstSalida = response.body();
                    listaPost.clear();
                    for (Posts obj : lstSalida) {
                        listaPost.add(obj.getTitle());
                    }
                    adaptadorPosts.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

            }
        });
    }

}