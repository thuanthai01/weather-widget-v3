
package com.hyperos.weather.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.unit.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.layout.defaultWeight
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HyperOSWeatherWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WidgetContent()
        }
    }

    @Composable
    private fun WidgetContent() {
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val dayName = SimpleDateFormat("EEEE", Locale("vi", "VN")).format(Date())

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color(0x33FFFFFF))
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth().defaultWeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = GlanceModifier.defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentTime,
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Normal,
                            color = ColorProvider(day = Color.White, night = Color.White)
                        )
                    )
                    Spacer(modifier = GlanceModifier.width(8.dp))
                    Column {
                        Text(
                            text = "$dayName $currentDate",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = ColorProvider(day = Color.White, night = Color.White)
                            )
                        )
                        Text(
                            text = "Âm lịch: 28/03",
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ColorProvider(day = Color(0xFFFDE047), night = Color(0xFFFDE047))
                            )
                        )
                        Text(
                            text = "(Ất Tỵ)",
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = ColorProvider(day = Color(0xFFFEF08A), night = Color(0xFFFEF08A))
                            )
                        )
                    }
                }

                Spacer(
                    modifier = GlanceModifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color(0x33FFFFFF))
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = GlanceModifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "26°C",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = ColorProvider(day = Color.White, night = Color.White)
                        )
                    )
                    Text(
                        text = "📍 Hà Nội",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(day = Color.White, night = Color.White)
                        )
                    )
                    Text(
                        text = "Có mây",
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = ColorProvider(day = Color(0xCCFFFFFF), night = Color(0xCCFFFFFF))
                        )
                    )
                }
            }

            Spacer(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0x33FFFFFF))
            )

            Row(
                modifier = GlanceModifier.fillMaxWidth().defaultWeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = GlanceModifier.defaultWeight())
                ForecastColumn("Thứ 7", "24° / 32°", "Có mây")
                Spacer(modifier = GlanceModifier.defaultWeight())
                ForecastColumn("CN", "23° / 30°", "Mưa nhẹ")
                Spacer(modifier = GlanceModifier.defaultWeight())
                ForecastColumn("Thứ 2", "22° / 31°", "Có mây")
                Spacer(modifier = GlanceModifier.defaultWeight())
            }
        }
    }

    @Composable
    private fun ForecastColumn(day: String, temp: String, desc: String) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = day,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(day = Color.White, night = Color.White)
                )
            )
            Text(
                text = temp,
                style = TextStyle(
                    fontSize = 10.sp,
                    color = ColorProvider(day = Color.White, night = Color.White)
                )
            )
            Text(
                text = desc,
                style = TextStyle(
                    fontSize = 9.sp,
                    color = ColorProvider(day = Color(0xAAFFFFFF), night = Color(0xAAFFFFFF))
                )
            )
        }
    }
}
                    