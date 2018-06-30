# Project 2 - *Flicks*

Flicks shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: 17.5 hours spent in total

## User Stories

The following **required** functionality is completed:

* :heavy_check_mark: User can **scroll through current movies** from the Movie Database API

The following **optional** features are implemented:

* :heavy_check_mark: For each movie displayed, user can see the following details:
  * :heavy_check_mark: Title, Poster Image, Overview (Portrait mode)
  * :heavy_check_mark: Title, Backdrop Image, Overview (Landscape mode)
* :heavy_check_mark: Display a nice default [placeholder graphic](https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* :heavy_check_mark: Allow user to view details of the movie including ratings and popularity within a separate activity
* :heavy_check_mark: Improved the user interface by experimenting with styling and coloring.
* :heavy_check_mark: Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library#transformations)
* :heavy_check_mark: Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* :heavy_check_mark: Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* :heavy_check_mark: Added "polish" goals included at end of Youtube activity. 
  * Display a “play” overlay image over the backdrop on the details activity as a visual cue to the user
  * Take a closer look at the data returned from the videos endpoint, and instead of just using the first video:
  * Use the site value to filter out any videos that aren’t YouTube, as these would not work with the player view
  * Use the size value to choose the best video to show based on your app’s bounds
  * Declare a new field on Movie to store the video id and use it to avoid fetching the same value more than once
* :heavy_check_mark: Embeded the Youtube video within the details page instead of having the user click on image to redirect to a new page.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/LHYhnxf.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
https://i.imgur.com/LHYhnxf.gif

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

I found the Youtube part of the app, especially the embedding within the details page, to be the most diffucult, but really satisfying and educational. 

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- Butterknife

## License

    Copyright 2018 Connie

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
