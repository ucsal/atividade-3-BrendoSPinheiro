package br.com.mariojp.exercise3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TarefaListaAdapter extends BaseAdapter {

    List<Tarefa> Tarefas = new ArrayList<Tarefa>();
    Context context;
    Set<String> descricoes = new HashSet<String>();

    public TarefaListaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return Tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int resource = android.R.layout.simple_list_item_2;
        View view;

        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        TextView descricao = view.findViewById(android.R.id.text1);
        TextView prioridade = view.findViewById(android.R.id.text2);

        Tarefa tarefa = (Tarefa) getItem(position);

        descricao.setText(tarefa.getDescricao());
        prioridade.setText("Prioridade: " + tarefa.getPrioridade());

        return view;
    }

    @Override
    public boolean isEmpty() {
        return Tarefas.isEmpty();
    }

    public boolean tarefaExiste(String descricao) {
        return descricoes.contains(descricao);
    }

    public void adicionarTarefa(Tarefa tarefa) {
        descricoes.add(tarefa.getDescricao());

        if(this.isEmpty()) {
            Tarefas.add(tarefa);
        } else {
            int pNovaTarefa = tarefa.getPrioridade();
            int posicao = -1;

            for(int i = 0; i < Tarefas.size(); i++) {
                int pTarefaAtual = Tarefas.get(i).getPrioridade();
                if(pNovaTarefa < pTarefaAtual) {
                    posicao = i;
                    break;
                }
            }

            if(posicao != -1){
                Tarefas.add(posicao, tarefa);
            }
            else {
                Tarefas.add(tarefa);
            }
        }

        notifyDataSetChanged();
    }

    public void removerTarefa(int position) {
        Tarefa t = Tarefas.get(position);
        descricoes.remove(t.getDescricao());
        Tarefas.remove(t);

        notifyDataSetInvalidated();
    }
}
