package com.example.applistatarefas

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applistatarefas.database.TarefaDAO
import com.example.applistatarefas.databinding.ActivityAdicionarTarefaBinding
import com.example.applistatarefas.model.Tarefa
import java.util.Date

class AdicionarTarefaActivity : AppCompatActivity() {

    private val vb by lazy { ActivityAdicionarTarefaBinding.inflate(layoutInflater) }

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

        val bundle = intent.extras
        var tarefa: Tarefa? = null
        if(bundle != null){
            tarefa = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("tarefa", Tarefa::class.java)
            } else {
                intent.getParcelableExtra("tarefa")
            }

            vb.etDigiteTarefa.setText(tarefa?.descricao)

        }

        vb.btnSalvar.setOnClickListener {

            if(vb.etDigiteTarefa.text.isNotEmpty()){

                if(tarefa != null){
                    edit(tarefa)
                }
                else{
                    save()
                }

            }
            else{
                Toast.makeText(this, "Informe uma tarefa", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun edit(tarefa: Tarefa) {

        val tarefaDAO = TarefaDAO(this)

        val newTarefa = Tarefa(
            idTarefa = tarefa.idTarefa,
            descricao = vb.etDigiteTarefa.text.toString(),
            Date().toString()
        )

        if(tarefaDAO.update(newTarefa)){
            Toast.makeText(this, "Alterada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
        else {
            Toast.makeText(this, "Erro ao alterar", Toast.LENGTH_SHORT).show()
        }

    }

    private fun save() {

        val tarefaDAO = TarefaDAO(this)
        val tarefa = Tarefa(-1, vb.etDigiteTarefa.text.toString(), Date().toString())

        if (tarefaDAO.insert(tarefa)) {
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show()
        }

    }
}