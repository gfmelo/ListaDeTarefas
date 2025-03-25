package com.example.applistatarefas.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSAO) {


    companion object {
        const val DATABASE_NAME = "ListaTarefas.db"
        const val VERSAO = 1
        const val NOME_TABELA_TAREFAS = "tarefas"
        const val COLUNA_ID_TAREFA = "id_tarefa"
        const val COLUNA_DESCRICAO = "descricao"
        const val COLUNA_DATA_CADASTRO = "data_cadastro"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $NOME_TABELA_TAREFAS(" +
                "$COLUNA_ID_TAREFA INTEGER NOT NULL PRIMARY KEY," +
                "$COLUNA_DESCRICAO VARCHAR(70)," +
                "$COLUNA_DATA_CADASTRO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try{
            db?.execSQL(sql)
            Log.i("info_db", "Tabela $NOME_TABELA_TAREFAS criada com sucesso!")
        }
        catch (e: Exception){
            Log.i("info_db", e.message.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}