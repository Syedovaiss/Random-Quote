package com.ovais.inspiration.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.ovais.inspiration.core.app.presentation.InspirationActivity
import com.ovais.inspiration.core.http.client.DefaultInspirationNetworkClient
import com.ovais.inspiration.core.http.client.InspirationNetworkClient
import com.ovais.inspiration.core.http.result.QuoteResult

private const val NO_QUOTE = "No quotes available!"
private const val SOME_THING_WENT_WRONG = "Something went wrong"
private const val ANONYMOUS = "Anonymous"

@SuppressLint("RestrictedApi")
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
        val response = getQuote()
        provideContent {
            when (response) {
                is QuoteResult.Success -> QuoteView(
                    response.result.firstOrNull()?.quote ?: NO_QUOTE,
                    response.result.firstOrNull()?.author ?: ANONYMOUS
                )

                is QuoteResult.Failure -> {
                    Log.e(RandomQuoteWidget::class.simpleName, response.message)
                    QuoteErrorView()
                }
            }
        }
    }

    @Composable
    private fun QuoteErrorView() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .clickable(actionStartActivity<InspirationActivity>())
                .background(ColorProvider(Color(0xFF1C1C1E)))
                .padding(16.dp)
                .cornerRadius(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = SOME_THING_WENT_WRONG,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = ColorProvider(Color(0xFFFF6B6B)),
                    textAlign = TextAlign.Center
                ),
                maxLines = 5
            )

            Spacer(modifier = GlanceModifier.height(12.dp))

            Text(
                text = "Retry",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(Color(0xFF58A6FF)),
                    textAlign = TextAlign.Center
                ),
                modifier = GlanceModifier
                    .clickable(actionRunCallback<RefreshQuoteCallback>())
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .cornerRadius(12.dp)
                    .background(ColorProvider(Color(0xFF3A3A3C)))
            )
        }
    }

    @Composable
    private fun QuoteView(quote: String, author: String) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .clickable(actionStartActivity<InspirationActivity>())
                .background(Color(0xFF1C1C1E))
                .padding(16.dp)
                .cornerRadius(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\"$quote\"",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = ColorProvider(Color(0xFFECECEC)),
                    textAlign = TextAlign.Center
                ),
                maxLines = 3
            )

            Spacer(modifier = GlanceModifier.height(8.dp))

            Text(
                text = "- $author",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = ColorProvider(Color(0xFFAAAAAA)),
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = GlanceModifier.height(20.dp))

            Text(
                text = "⟳ Refresh",
                modifier = GlanceModifier
                    .clickable(actionRunCallback<RefreshQuoteCallback>())
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .cornerRadius(12.dp)
                    .background(Color(0xFF3A3A3C)),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = ColorProvider(Color(0xFF58A6FF)),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }

    private suspend fun getQuote(): QuoteResult {
        return apiClient.getQuote()
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