package br.com.mariojp.exercise3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TarefaListaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new TarefaListaAdapter(this);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.removeTarefa(position);
            }
        });

        Button buttonAdicionar = findViewById(R.id.buttonAdicionar);
        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editDescricao = findViewById(R.id.editDescricao);
                EditText editPrioridade = findViewById(R.id.editPrioridade);

                String descricao = editDescricao.getText().toString();
                int prioridade = new Integer(editPrioridade.getText().toString());

                if(prioridade < 1 || prioridade > 10) {
                    Toast.makeText(MainActivity.this, "A prioridade deve estar entre 1 e 10.", Toast.LENGTH_SHORT).show();
                } else if(mAdapter.tarefaExiste(descricao)) {
                    Toast.makeText(MainActivity.this, "Tarefa já cadastrada.", Toast.LENGTH_SHORT).show();
                } else {
                    Tarefa tarefa = new Tarefa(descricao, prioridade);
                    mAdapter.adicionarTarefa(tarefa);

                    alternarBotaoRemover();
                    editDescricao.setText("");
                    editPrioridade.setText("");
                }
            }
        });

        Button buttonRemover = findViewById(R.id.buttonRemover);
        buttonRemover.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.this.removeTarefa(0);
            }
        });

        alternarBotaoRemover();
    }

    protected void removeTarefa(int position) {
        mAdapter.removerTarefa(position);
        alternarBotaoRemover();
    }

    protected void alternarBotaoRemover() {
        boolean vazio = mAdapter.isEmpty();
        ((Button)findViewById(R.id.buttonRemover)).setEnabled(!vazio);
    }
}