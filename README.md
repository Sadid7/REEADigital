# REEADigital

Build can be found in projectBuilds folder

Designed to follow MVVM architecture wherever necessary

Follows KISS and SOLID practices as much as possible in the timeframe

Uses Retrofit, GSON, Android Navigation UI, GMS Play services

All the requirements are fulfilled except movie thumbnail image. Because the free api "https://api.themoviedb.org" provides faulty image URIs.
But a mock implementation of image downloading mechanism using picasso can be found in the MovieListViewAdapter.java class which is commented out.

The language swtching functionality was achieved through Locale and string.xml multi language support.

