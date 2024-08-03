package com.example.todolist.presentation.screens.task_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.todolist.navigation.Screens
import com.example.todolist.presentation.screens.task_detail.components.CompleteChipButton
import com.example.todolist.presentation.screens.task_detail.components.DeleteChipButton
import com.example.todolist.presentation.screens.task_detail.components.DeleteTaskDialogDetail
import com.example.todolist.presentation.screens.task_detail.components.FilterChipDetailOptions
import com.example.todolist.presentation.screens.task_detail.components.TaskDescriptionTextField
import com.example.todolist.presentation.screens.task_detail.components.TaskNameTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    navHostController: NavHostController
) {
    val taskDetailViewModel: TaskDetailViewModel = hiltViewModel()


    val currentTask by taskDetailViewModel.currentTask.collectAsStateWithLifecycle()

    val showTaskPopupDelete by taskDetailViewModel.showDeleteTaskDialog.collectAsStateWithLifecycle()

    LaunchedEffect(taskId) {
        taskDetailViewModel.loadTask(taskId)
    }

    DisposableEffect(Unit) {
        onDispose {
            taskDetailViewModel.clearTask()
        }
    }


    if (showTaskPopupDelete) {
        DeleteTaskDialogDetail(
            taskDetailViewModel = taskDetailViewModel,
            currentTask = currentTask,
            navHostController = navHostController
        )
    }

    Scaffold(
        containerColor = Color.White,
        contentColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {
                                navHostController.navigate(Screens.TaskListScreen.route) {
                                    popUpTo(Screens.TaskDetailScreen.route) {
                                        inclusive = true
                                    }
                                }
                                taskDetailViewModel.onBackButtonPressed()
                            },
                        tint = Color.Black
                    )
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Row {

                CompleteChipButton(
                    taskDetailViewModel = taskDetailViewModel,
                    currentTask = currentTask
                )

                Spacer(modifier = Modifier.width(8.dp))

                DeleteChipButton(taskDetailViewModel = taskDetailViewModel)
            }

            TaskNameTextField(currentTask = currentTask, taskDetailViewModel = taskDetailViewModel)

            TaskDescriptionTextField(
                currentTask = currentTask,
                taskDetailViewModel = taskDetailViewModel
            )

            HorizontalDivider(
                color = Color.LightGray.copy(0.5f),
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )

            FilterChipDetailOptions(taskDetailViewModel = taskDetailViewModel)

            HorizontalDivider(
                color = Color.LightGray.copy(0.5f),
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
        }
    }
}