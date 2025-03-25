package com.example.applistatarefas.database

import com.example.applistatarefas.model.Tarefa

interface ITarefaDAO {

    fun insert (tarefa: Tarefa): Boolean
    fun update (tarefa: Tarefa): Boolean
    fun delete (idTarefa: Int): Boolean
    fun list (): List<Tarefa>

}