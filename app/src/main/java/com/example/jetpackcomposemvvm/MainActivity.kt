package com.example.jetpackcomposemvvm

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposemvvm.ui.theme.JetpackComposeMVVMTheme
import java.util.*
import kotlin.math.abs
import kotlin.random.Random.Default.nextInt

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeMVVMTheme {
                val viewModel by remember { mutableStateOf(DefaultUserViewModel()) }
                this.viewModel = viewModel

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserView()
                }
            }
        }
    }

    @Composable
    fun UserView() {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    modifier = Modifier,
                    title = { Text("Users") },
                    actions = {
                        IconButton(onClick = {
                            val index = viewModel.nextRecordId
                            val user = User(UUID.randomUUID(), "User #${index}")
                            viewModel.emit(UserViewModel.Action.AddUser(user))
                        }) {
                            Icon(Icons.Filled.Add, null)
                        }

                        IconButton(onClick = {
                            val index = abs(Random().nextInt())%viewModel.users.size
                            val user = User(UUID.randomUUID(), "User #${index + 1}")
                            viewModel.emit(UserViewModel.Action.UpdateUser(index, user))
                        }) {
                            Icon(Icons.Filled.Edit, null)
                        }
                        IconButton(onClick = {
                            val index = abs(Random().nextInt())%viewModel.users.size
                            viewModel.emit(UserViewModel.Action.DeleteAtIndex(index))
                        }) {
                            Icon(Icons.Filled.Delete, null)
                        }
                    })
            },
            content = {
                Column(modifier = Modifier.padding(16.dp)) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(viewModel.users) {
                            Column(modifier = Modifier.padding(4.dp)) {
                                Text(modifier = Modifier, text = it.name)
                                Text(modifier = Modifier, text = "${it.id}")
                                Divider(modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))
                            }
                        }
                    }
                }
            }
        )
    }

    @Preview(
        fontScale = 1.5f,
        name = "Light Mode",
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        showSystemUi = true,
        showBackground = true,
    )
    @Preview(
        fontScale = 1.5f,
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showSystemUi = true,
        showBackground = true
    )
    @Composable
    fun DefaultPreview() {
        JetpackComposeMVVMTheme {
            val viewModel by remember { mutableStateOf(DefaultUserViewModel()) }
            this.viewModel = viewModel
            viewModel.emit(UserViewModel.Action.AddUser(User(UUID(5, 1), "User")))
            viewModel.emit(UserViewModel.Action.AddUser(User(UUID(4, 2), "User")))
            viewModel.emit(UserViewModel.Action.AddUser(User(UUID(3, 3), "User")))
            viewModel.emit(UserViewModel.Action.AddUser(User(UUID(2, 4), "User")))
            viewModel.emit(UserViewModel.Action.AddUser(User(UUID(1, 5), "User")))

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                UserView()
            }
        }
    }
}
