package com.surodeevartem.imageviewer.presentation.images

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.surodeevartem.imageviewer.R
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import com.surodeevartem.imageviewer.presentation.theme.ImageViewerTheme
import com.surodeevartem.imageviewer.presentation.theme.Typography
import com.surodeevartem.imageviewer.presentation.utils.rippleClickable

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
                style = Typography.headlineSmall,
                modifier = Modifier.padding(start = 14.dp),
            )
            Column(modifier = Modifier.selectableGroup()) {
                RadioButtonLabeled(
                    selected = currentSortingField == SortingField.ID,
                    onClick = { onSortingFieldChange(SortingField.ID) },
                    titleId = R.string.id,
                )
                RadioButtonLabeled(
                    selected = currentSortingField == SortingField.TITLE,
                    onClick = { onSortingFieldChange(SortingField.TITLE) },
                    titleId = R.string.by_title,
                )
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
                        SortingOrder.ASCENDING -> stringResource(id = R.string.ascending)
                        SortingOrder.DESCENDING -> stringResource(id = R.string.descending)
                    },
                    style = Typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun RadioButtonLabeled(
    selected: Boolean,
    @StringRes titleId: Int,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .rippleClickable(onClick = onClick),
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
        )
        Text(
            text = stringResource(id = titleId),
            style = Typography.bodyLarge,
        )
    }
}
