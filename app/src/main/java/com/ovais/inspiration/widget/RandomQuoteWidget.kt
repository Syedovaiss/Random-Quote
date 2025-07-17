package com.ovais.inspiration.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.ovais.inspiration.core.app.presentation.InspirationActivity
import com.ovais.inspiration.core.http.client.DefaultInspirationNetworkClient
import com.ovais.inspiration.core.http.client.InspirationNetworkClient
import com.ovais.inspiration.core.http.result.QuoteResult

class RandomQuoteWidget() : GlanceAppWidget() {

    private val networkClient by lazy { WidgetNetworkClient.instance }
    private val apiClient: InspirationNetworkClient by lazy {
        DefaultInspirationNetworkClient(networkClient)
    }

    private companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            BIG_SQUARE
        )
    )

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        val result = getQuote()
        provideContent {
            when (result) {
                is QuoteResult.Success -> QuoteView(
                    result.result.firstOrNull()?.quote ?: "No quote available",
                    result.result.firstOrNull()?.author ?: "Unknown"
                )

                is QuoteResult.Failure -> QuoteErrorView(result.message)
            }
        }
    }

    @Composable
    private fun QuoteErrorView(message: String) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .cornerRadius(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 5,
            )

            Spacer(modifier = GlanceModifier.height(12.dp))

            Text(
                text = "Retry",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = GlanceModifier
                    .clickable(actionRunCallback<RetryQuoteCallback>())
                    .padding(8.dp)
                    .cornerRadius(8.dp)
                    .background(Color(0xFFE0F7FA)) // Light cyan background
            )
        }
    }

    @Composable
    private fun QuoteView(quote: String, author: String) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .cornerRadius(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\"$quote\"",
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 3
            )

            Spacer(modifier = GlanceModifier.height(8.dp))

            Text(
                text = "- $author",
                style = TextStyle(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = GlanceModifier.height(16.dp))

            Row(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Refresh",
                    modifier = GlanceModifier
                        .padding(horizontal = 8.dp)
                        .clickable(actionRunCallback<RefreshQuoteCallback>()),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = GlanceModifier.width(16.dp))

                Text(
                    text = "Open App",
                    modifier = GlanceModifier
                        .padding(horizontal = 8.dp)
                        .clickable(actionStartActivity<InspirationActivity>()),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

    private suspend fun getQuote(): QuoteResult {
        return apiClient.getQuote()
    }
}

class RetryQuoteCallback : ActionCallback {


    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        RandomQuoteWidget().updateAll(context)
    }
}

class RefreshQuoteCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        RandomQuoteWidget().updateAll(context)
    }
}