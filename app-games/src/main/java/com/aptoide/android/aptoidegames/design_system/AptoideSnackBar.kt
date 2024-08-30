package com.aptoide.android.aptoidegames.design_system

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.aptoide.android.aptoidegames.theme.AGTypography

@Composable
fun AptoideSnackBar(data: SnackbarData) {
  Snackbar(
    modifier = Modifier
      .padding(12.dp)
      .focusable(false)
      .clearAndSetSemantics {},
    shape = RoundedCornerShape(16.dp),
    content = {
      Text(
        text = data.message,
        style = AGTypography.InputsM,
      )
    },
  )
}