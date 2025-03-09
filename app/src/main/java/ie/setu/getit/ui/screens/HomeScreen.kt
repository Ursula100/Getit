package ie.setu.getit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun HomeScreen (
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
){
    Column {
        Column(
            modifier = modifier.padding(
                top = paddingValues.calculateTopPadding() + 14.dp, // Extra space
                start = 24.dp,
                end = 24.dp
            )
        ) {
        }
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Composable
fun HomeScreenPreview(){
    GetitTheme {
        HomeScreen(
            modifier = Modifier,
            paddingValues = PaddingValues()
        )
    }
}