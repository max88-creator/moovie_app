# Movies App

## Демонстрация
![Movie Video](/app/src/main/res/drawable/movie_video.gif)

<img src="/app/src/main/res/drawable/film_info_dark.jpg" width="300" alt="Film Info Dark">

<img src="/app/src/main/res/drawable/film_info_lite.jpg" width="300" alt="Film Info Lite">

<img src="/app/src/main/res/drawable/popular_dark.jpg" width="300" alt="Popular Dark">

<img src="/app/src/main/res/drawable/popular_lite.jpg" width="300" alt="Popular Lite">

<img src="/app/src/main/res/drawable/upcoming_dark.jpg" width="300" alt="Upcoming">





## Описание
Приложение для просмотра информации о фильмах с использованием API The Movie Database (TMDB).

## Структура проекта
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/moviesapp/
│   │   │   ├── details/
│   │   │   │   └── presentation/ - Экраны детального просмотра фильма
│   │   │   ├── movieList/
│   │   │   │   ├── data/ - Слой данных (сетевой, локальный, мапперы)
│   │   │   │   ├── domain/ - Бизнес-логика
│   │   │   │   └── presentation/ - Экраны списка фильмов
│   │   │   └── ui/theme/ - Темизация приложения
│   │   └── res/ - Ресурсы приложения
└── build.gradle.kts - Конфигурация модуля
```

## Технологии
- **Язык программирования:** Kotlin
- **Платформа:** Android
- **UI Toolkit:** Jetpack Compose
- **DI:** Hilt
- **Сетевые запросы:** Retrofit
- **Локальное хранилище:** Room
- **Загрузка изображений:** Coil
- **Навигация:** Jetpack Navigation

## Архитектура
- **Clean Architecture** с разделением на слои: Presentation, Domain, Data
- **MVVM** паттерн для организации UI-логики
- **Repository** паттерн для управления источниками данных

## Настройка
Для работы приложения требуется указать API-ключ TMDB в файле `local.properties`:
```
MOVIE_API_KEY=your_api_key_here
```