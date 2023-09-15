package com.example.iss.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iss.LocationUtils
import com.example.iss.R
import com.example.iss.db.ISSPosition
import com.example.iss.viewmodel.ISSLocationHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * ISS positions history list
 */
@AndroidEntryPoint
class ISSLocationHistoryActivity : ComponentActivity() {
    companion object {
        const val TAG = "ISSLocationHistoryActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                ScreenContent(viewModel())
            }
        }
    }
}

@Composable
fun ScreenContent(issLocationHistoryViewModel: ISSLocationHistoryViewModel) {
    val context = LocalContext.current
    val issLocationsHistory by issLocationHistoryViewModel.issLocationsHistoryLiveData.observeAsState()
    issLocationsHistory?.let {
        if (it.isEmpty()) {
            CenteredText(text = stringResource(id = R.string.no_iss_locations_in_db))
        } else {
            List(issPositions = it) { issPosition ->
                val location = LocationUtils.toLocation(issPosition)
                LocationUtils.showLocationOnGoogleMap(context, location)
            }
        }
    }
    LaunchedEffect(Unit) {
        issLocationHistoryViewModel.getISSLocationsHistory()
    }
}

@Composable
fun List(issPositions: List<ISSPosition>, callback: (ISSPosition) -> Unit) {
    Column {
        Header(text = stringResource(id = R.string.iss_locations_history))

        LazyColumn {
            items(items = issPositions) { issPosition ->
                Column(modifier = Modifier.selectable(selected = false, onClick = {
                    callback(issPosition)
                })) {
                    Item(text = stringResource(id = R.string.timestamp, issPosition.timeStamp))
                    Item(text = stringResource(id = R.string.latitude, issPosition.latitude))
                    Item(text = stringResource(id = R.string.longitude, issPosition.longitude))
                }
                Divider()
            }
        }
    }
}

@Composable
fun Header(text: String) {
    Text(
        text = text,
        fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Item(text: String) {
    Text(
        text = text,
        fontSize = dimensionResource(id = R.dimen.text_size_medium).value.sp,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 10.dp)
    )
}

@Composable
fun CenteredText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp
        )
    }
}