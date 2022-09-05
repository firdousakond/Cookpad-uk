# Android Hiring Exercise

The Android Hiring Exercise is a small application that fetches and displays a list of curated recipe collections from our dedicated [Mobile Hiring API](https://cookpad.github.io/global-mobile-hiring/).

## Background

The project has already been started, but it's far from finished. You and your team have now inherited the project and it'll be your responsibility to maintain it and to add new functionality going forward.

<p align="center">
  <img src="https://user-images.githubusercontent.com/482871/152530652-462af2df-517a-46d7-9600-3bf9e0fea505.png" width="600" />
</p>

To start with, your product owner would like you to work towards adding a new feature described in the Feature Specification section below.

## Project Requirements

- Android Studio Arctic Fox | 2020.3.1
- Gradle version 7.0.2
- MinSdk 21
- TargetSdk 31

## Feature Specification

The recipe collection card has a favorite button, but it's not yet functional. We'd like for the user to be able to favorite specific collections and view them in a dedicated interface. The core scenarios are defined below, but you are free to build the user experience as you see fit.

### Scenarios

#### Collections List

```
Given the recipe collection card
When the user taps the favorite button
Then collection should be added to the favorite list
And the favorite button icon should indicate that the collection is a favorite
```

```
Given a recipe collection card for a favorite collection
When the user taps the favorite button
Then collection should be removed from the favorite list
And the favorite button icon should indicate that this collection is not a favorite
```

#### Favorites List

```
Given the user views their favorites
Then a list of favorite collections should be presented
```

```
Given the recipe collection card
When the user taps the favorite button in the favorite list
Then the recipe collection should be removed from the favorite list
```


## Instructions

We would like you:

- Spend approximately 4 hours on this task
  - Its not likely that you can finish the entire task in this time and that's fine. Please make do with what you have and remember that you are doing this exercise to demonstrate your thought process, your approach and awareness around trade-offs.
- Make logical descriptive commits
- Wrap up your commits into a Pull Request
- Provide a write-up in the description touching on:
  - What you changed and why
  - What things gave you problems
  - Other thoughts, where you would like to take this given more time, etc

Once you have completed the task, please let the hiring manager know and our engineers will review your submission.

If you have any other questions, please don't hesitate to ask. Thank you for taking the time to complete this task!
