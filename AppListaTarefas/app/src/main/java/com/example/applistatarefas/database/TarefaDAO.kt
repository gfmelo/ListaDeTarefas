package com.example.applistatarefas.database

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.applistatarefas.model.Tarefa
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TarefaDAO(context: Context) : ITarefaDAO {

    private val writerDAO = DatabaseHelper(context).writableDatabase
    private val readerDAO = DatabaseHelper(context).readableDatabase


    override fun insert(tarefa: Tarefa): Boolean {

        val valores = ContentValues()
        valores.put(DatabaseHelper.COLUNA_DESCRICAO, tarefa.descricao)
        valores.put(DatabaseHelper.COLUNA_DATA_CADASTRO, tarefa.dataCadastro)

        try{
            writerDAO.insert(DatabaseHelper.NOME_TABELA_TAREFAS, null, valores)
        }
        catch (e: Exception){
            Log.i("info_db", "Erro ao inserir registro")
            return false
        }

        return true

    }

    override fun update(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())
        val content = ContentValues()
        content.put("${DatabaseHelper.COLUNA_DESCRICAO}", tarefa.descricao)
        content.put("${DatabaseHelper.COLUNA_DATA_CADASTRO}", tarefa.dataCadastro)

        try{
            writerDAO.update(DatabaseHelper.NOME_TABELA_TAREFAS, content,"${DatabaseHelper.COLUNA_ID_TAREFA} = ?", args)
        }
        catch (e: Exception){
            Log.i("info_db", "Erro ao atualizar registro")
            return false
        }

        return true
    }

    override fun delete(idTarefa: Int) : Boolean {

        val args = arrayOf(idTarefa.toString())

        try{
            writerDAO.delete(DatabaseHelper.NOME_TABELA_TAREFAS, "${DatabaseHelper.COLUNA_ID_TAREFA} = ?", args)
        }
        catch (e: Exception){
            Log.i("info_db", "Erro ao inserir registro")
            return false
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun list(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()
        val sql = "SELECT id_tarefa, descricao, data_cadastro  FROM tarefas;"


        val cursor = readerDAO.rawQuery(sql, null)

        val id = cursor.getColumnIndex(DatabaseHelper.COLUNA_ID_TAREFA)
        val desc = cursor.getColumnIndex(DatabaseHelper.COLUNA_DESCRICAO)
        val dataCad = cursor.getColumnIndex(DatabaseHelper.COLUNA_DATA_CADASTRO)

        val formatterEntrada = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val formatterSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

        while (cursor.moveToNext()) {
            val idTarefa = cursor.getInt(id)
            val descricao = cursor.getString(desc)
            val dataCadastro = LocalDateTime.parse(cursor.getString(dataCad), formatterEntrada).format(formatterSaida)


            listaTarefas.add(Tarefa(idTarefa, descricao, dataCadastro.toString()))
        }

        return listaTarefas
    }
}