package com.example.applistatarefas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applistatarefas.adapter.TarefaAdapter
import com.example.applistatarefas.database.TarefaDAO
import com.example.applistatarefas.databinding.ActivityMainBinding
import com.example.applistatarefas.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val vb by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(vb.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(vb){

            fabAdicionarTarefa.setOnClickListener {
                val intent = Intent(applicationContext, AdicionarTarefaActivity::class.java)
                startActivity(intent)
            }

            tarefaAdapter = TarefaAdapter(
                { id -> deleteConfirm(id)},
                { tarefa -> editTarefa(tarefa) }
            )

            rvTarefas.adapter = tarefaAdapter
            rvTarefas.layoutManager = LinearLayoutManager(applicationContext)

        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteConfirm(id: Int) {

        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Confirma exclusão?")
        alertBuilder.setMessage("Deseja excluir a tarefa?")

        alertBuilder.setPositiveButton("Sim"){_,_->

            val tarefaDAO = TarefaDAO(this)

            if(tarefaDAO.delete(id)){
                updateListaTarefas()
                Toast.makeText(this, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show()
            }

        }

        alertBuilder.setNegativeButton("Não"){_,_->}
        alertBuilder.create().show()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {

        super.onStart()
        updateListaTarefas()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateListaTarefas(){

        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.list()
        tarefaAdapter?.AddList(listaTarefas)

    }

    private fun editTarefa(tarefa: Tarefa) {

        intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)

    }

}