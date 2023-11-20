package test.s160419098.anmp.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.s160419098.anmp.todo.model.Todo
import test.s160419098.anmp.todo.model.TodoDatabase

class TodoDetailViewModel(app: Application) : AndroidViewModel(app) {
    val todo = MutableLiveData<Todo>()

    fun add(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoDatabase(getApplication()).todoDao().insertOrUpdate(todo)
        }
    }

    fun fetch(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todo.postValue(
                TodoDatabase(getApplication()).todoDao().find(id)
            )
        }
    }

    fun update(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoDatabase(getApplication()).todoDao().insertOrUpdate(todo)
        }
    }
}