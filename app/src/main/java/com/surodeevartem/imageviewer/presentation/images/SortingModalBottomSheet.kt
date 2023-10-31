package com.surodeevartem.imageviewer.presentation.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.RadioButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.surodeevartem.imageviewer.R
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import com.surodeevartem.imageviewer.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingModalBottomSheet(
    currentSortingField: SortingField,
    onSortingFieldChange: (field: SortingField) -> Unit,
    currentSortingOrder: SortingOrder,
    onSortingOrderChange: (order: SortingOrder) -> Unit,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = Modifier.navigationBarsPadding(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.sort_by),
                style = Typography.headlineMedium,
            )
            Column(modifier = Modifier.selectableGroup()) {
                Row {
                    RadioButton(
                        selected = currentSortingField == SortingField.ID,
                        onClick = { onSortingFieldChange(SortingField.ID) },
                    )
                    Text(
                        text = "ID",
                        style = Typography.bodyMedium,
                    )
                }
                Row {
                    RadioButton(
                        selected = currentSortingField == SortingField.TITLE,
                        onClick = { onSortingFieldChange(SortingField.TITLE) },
                    )
                    Text(
                        text = "Title",
                        style = Typography.bodyMedium,
                    )
                }
            }
            TextButton(
                onClick = {
                    when (currentSortingOrder) {
                        SortingOrder.ASCENDING -> onSortingOrderChange(SortingOrder.DESCENDING)
                        SortingOrder.DESCENDING -> onSortingOrderChange(SortingOrder.ASCENDING)
                    }
                },
            ) {
                Text(
                    text = when (currentSortingOrder) {
                        SortingOrder.ASCENDING -> "Ascending"
                        SortingOrder.DESCENDING -> "Descending"
                    }
                )
            }
        }
    }
}
