package com.example.moviesapp.movieList.util


import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

/**
 * Вычисляет средний цвет изображения и возвращает его затемненную версию.
 * Эта функция полезна для создания фоновых цветов на основе изображений.
 *
 * @param imageBitmap изображение для анализа
 * @return затемненный средний цвет изображения
 */
@Composable
fun getAverageColor(imageBitmap: ImageBitmap): Color {
    // Состояние для хранения вычисленного среднего цвета
    var averageColor by remember { mutableStateOf(Color.Transparent) }

    // Запускаем вычисления в фоновом потоке
    LaunchedEffect(Unit) {
        // Преобразуем ImageBitmap в Bitmap с совместимой конфигурацией
        val compatibleBitmap = imageBitmap.asAndroidBitmap()
            .copy(Bitmap.Config.ARGB_8888, false)

        // Создаем массив для хранения всех пикселей изображения
        val pixels = IntArray(compatibleBitmap.width * compatibleBitmap.height)

        // Извлекаем пиксели из Bitmap в массив
        compatibleBitmap.getPixels(
            pixels,             // целевой массив
            0,                  // начальный индекс в массиве
            compatibleBitmap.width, // шаг (ширина строки)
            0, 0,               // координаты начала (x, y)
            compatibleBitmap.width, // ширина области
            compatibleBitmap.height // высота области
        )

        // Переменные для накопления сумм цветовых компонентов
        var redSum = 0
        var greenSum = 0
        var blueSum = 0

        // Проходим по всем пикселям и суммируем RGB компоненты
        for (pixel in pixels) {
            // Извлекаем красную, зеленую и синюю составляющие из пикселя
            val red = android.graphics.Color.red(pixel)
            val green = android.graphics.Color.green(pixel)
            val blue = android.graphics.Color.blue(pixel)

            // Добавляем к суммам
            redSum += red
            greenSum += green
            blueSum += blue
        }

        // Вычисляем средние значения для каждого цветового канала
        val pixelCount = pixels.size
        val averageRed = redSum / pixelCount
        val averageGreen = greenSum / pixelCount
        val averageBlue = blueSum / pixelCount

        // Сохраняем вычисленный средний цвет
        averageColor = Color(averageRed, averageGreen, averageBlue)
    }

    // Преобразуем средний цвет в HSL пространство для работы с яркостью
    val hsl = FloatArray(3) // массив для хранения Hue, Saturation, Lightness
    ColorUtils.colorToHSL(averageColor.toArgb(), hsl)

    // Уменьшаем яркость (Lightness) на 0.1 для затемнения цвета
    // Это делает цвет более подходящим для фона
    val darkerLightness = hsl[2] - 0.1f // Значение можно регулировать (от 0.0 до 1.0)

    // Создаем новый цвет с уменьшенной яркостью
    val darkerColor = ColorUtils.HSLToColor(
        floatArrayOf(
            hsl[0],        // Hue (оттенок) - сохраняем
            hsl[1],        // Saturation (насыщенность) - сохраняем
            darkerLightness // Lightness (яркость) - уменьшенная
        )
    )

    // Возвращаем затемненный цвет в формате Compose Color
    return Color(darkerColor)
}