package com.example.jetpackcomposemvvm

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

interface UserViewModel {
    sealed class Action {
        data class AddUser(val user: User): Action()
        data class UpdateUser(val index: Int, val user: User): Action()
        data class DeleteUser(val user: User): Action()
        data class DeleteAtIndex(val index: Int): Action()
    }

    val nextRecordId: Int
    val users: List<User>
    fun emit(action: Action)
}

class DefaultUserViewModel : ViewModel(), UserViewModel {

    private val _users = mutableStateListOf<User>()
    private var _nextRecordId = 1
    override val nextRecordId: Int
        get() {
            val output = _nextRecordId
            _nextRecordId += 1
            return output
        }

    override val users: List<User>
        get() = _users

    override fun emit(action: UserViewModel.Action) {
        when(action) {
            is UserViewModel.Action.AddUser -> addUser(action.user)
            is UserViewModel.Action.DeleteAtIndex -> deleteAtIndex(action.index)
            is UserViewModel.Action.DeleteUser -> deleteUser(action.user)
            is UserViewModel.Action.UpdateUser -> updateUser(action.index, action.user)
        }
    }

    private fun addUser(user: User) {
        _users.add(user)
    }

    private fun updateUser(index: Int, user: User) {
        _users[index] = user
    }

    private fun deleteUser(user: User) {
        _users.remove(user)
    }

    private fun deleteAtIndex(index: Int) {
        _users.removeAt(index)
    }
}