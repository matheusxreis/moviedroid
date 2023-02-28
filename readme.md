# Moviedroid

<div style="display:flex">
<img align="center" alt="android" height="40" width="50" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/android/android-original.svg" />
<img align="center" alt="kotlin" height="40" width="50" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/kotlin/kotlin-original.svg" />
<img align="center" alt="androidstudio" height="40" width="50" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/androidstudio/androidstudio-original.svg" />
</div>

<img style="width:1000px;margin-top:20px" src="/readmeimages/readmeimage.png" />

### About

This is a app for movies/tv shows fans, who would like reading about and keeping a list of their favorite movies/tv shows. The application consumes The Movie DB Api.

### What was used to build it

- **MVVM**: the model-view-viewmodel architecture;
- **Kotlin Coroutines**: to deal with async called.
- **Navigation component**: to make a stack navigation between fragments;
- **Data Binding**: to bind ui components in my layouts;
- **Retrofit**: to consumes the API.
- **Room**: to save the user notes;
- **Hilt**: to inject the dependencies;
- **Motion Layout**: to deal with some animations;
- **Search Result Activity and searchable**: to define search configurations of the app.

### Features

Using this app, you can:

- See the populatest movies and tv shows;
- Search for a movie/tv show throught its name;
- See informations about the movie/tv show;
- See recommendations;
- Add items in your favorites list;
- Create new lists and populate as you want.


### How run with my api key

For you run with your api key, you need to create a **local.properties** file and add the following line:

``` 
apiKey=your_api_key
```

That's all folks! Never stop learning! :metal: